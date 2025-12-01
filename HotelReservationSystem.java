import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "ABDULashiq@1212";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("1. Reserve a room");
                System.out.println("2. view Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.println("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Fix input skipping issue

                switch (choice) {
                    case 1:
                        reserveRoom(connection, sc);
                        break;
                    case 2:
                        viewreservation(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, sc);
                        break;
                    case 4:
                        updateReservation(connection, sc);
                        break;
                    case 5:
                        deleteReservation(connection, sc);
                        break;
                    case 0:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid Choice.Try again.");
                }
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void reserveRoom(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter a guest name: ");
            String guestName = sc.nextLine();

            System.out.println("Enter room number: ");
            int roomNumber = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter contact number: ");
            String ContactNumber = sc.nextLine();

            String sql = "INSERT INTO reservation(guest_name, room_number, contact_number) VALUES ('"
                    + guestName + "', " + roomNumber + ", '" + ContactNumber + "')";

            try (Statement statement = connection.createStatement()) {
                int affectRooms = statement.executeUpdate(sql);
                if (affectRooms > 0) {
                    System.out.println("Reservation Successfull!");
                } else {
                    System.out.println("Reservation failed.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewreservation(Connection connection) throws SQLException {
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservation";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\nCurrent Reservations:");
            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------+");
            System.out.printf("| %-14s | %-25s | %-13s | %-20s | %-25s |%n",
                    "Reservation ID", "Guest Name", "Room Number", "Contact Number", "Reservation Date");
            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------+");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                System.out.printf("| %-14d | %-25s | %-13d | %-20s | %-25s |%n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }

            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------+");
        }
    }

    private static void getRoomNumber(Connection connection, Scanner sc){
        try{
            System.out.println("Enter reservation ID :");
            int reservationId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter guest name :");
            String guestName = sc.nextLine();

            String sql = "SELECT room_number FROM reservation WHERE reservation_id = "
                    + reservationId + " AND TRIM(guest_name) = TRIM('" + guestName + "')";

            try(Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)){

                if(resultSet.next()){
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room number for reservation ID " + reservationId
                            + " and Guest " + guestName + " is :" + roomNumber);
                }else{
                    System.out.println("reservation not found for the given ID and guest name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter a reservation ID to update :");
            int reservationId = sc.nextInt();
            sc.nextLine();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            System.out.println("Enter new guest name :");
            String newGuestName = sc.nextLine();

            System.out.println("Enter new room number :");
            int newRoomNumber = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter new contact number");
            String newContactNumber = sc.nextLine();

            String sql = "UPDATE reservation SET guest_name ='" + newGuestName +
                    "', room_number =" + newRoomNumber + ", contact_number ='" + newContactNumber +
                    "' WHERE reservation_id =" + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation updated Successfully!");
                } else {
                    System.out.println("Reservation Update Failed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter Id To delete the reservation :");
            int reservationId = sc.nextInt();
            sc.nextLine();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("reservation not found for the given ID.");
                return;
            }

            String sql = "DELETE FROM reservation WHERE reservation_id =" + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affetedRows = statement.executeUpdate(sql);
                if (affetedRows > 0) {
                    System.out.println("Reservation deleted Succesfully!");
                } else {
                    System.out.println("Reservation deleted failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT reservation_id FROM reservation WHERE reservation_id =" + reservationId;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException {
        System.out.println("Exiting system");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For using Hotel Reservation System!.");
    }
}
