# 🏨 Hotel Reservation System (Java + MySQL)

A console-based hotel reservation management application developed using Core Java and MySQL with JDBC  connectivity. This system helps hotel staff manage bookings and store customer details efficiently in a database.

---

## ✨ Features

✔ Add new room reservations  
✔ View all reservations in a formatted table  
✔ Search room number using Reservation ID and Guest Name  
✔ Update booking information  
✔ Delete reservation records  
✔ Real-time data storage with CRUD operations  

---

## 🛠 Tech Stack

| Technology | Usage |
|----------|------|
| Java | Main application logic |
| JDBC | Database connectivity |
| MySQL | Data storage and operations |
| IntelliJ IDEA | Development environment |

---

## 📂 Database Setup

Create a database named `hotel_db` and add the table below:

```sql
CREATE TABLE reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(255) NOT NULL,
    room_number INT NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
▶ How to Run
Install Java JDK 17+

Install MySQL Server

Import the project into IntelliJ or any Java IDE

Download and add MySQL Connector/J to classpath

Update DB credentials in code if needed:

java
Copy code
private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
private static final String username = "root";
private static final String password = "YOUR_PASSWORD";
Run HotelReservationSystem.java

🎯 Learning Outcomes
JDBC connectivity and SQL query execution

Handling user input and exceptions in Java

Modular backend logic and database communication

CRUD operations in real-time systems

