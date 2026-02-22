Photo/Video Equipment Rental DBMS

A Java-based Database Management System for managing a photo/video equipment rental business.

This project provides end-to-end support for customers, equipment inventory, rentals, payments, employees, and damage reports, backed by a fully normalized Oracle SQL database and a Java Swing desktop interface.

Features:

Customer Management:
- Register, view, and delete customers
- Store contact information and rental history

Equipment Management:
- Track rental equipment, brand, condition, availability, and daily rental rates
- Prevent scheduling conflicts through availability tracking

Rental Management:
- Create and manage rental transactions
- Automatically calculate total rental cost based on daily rate and rental duration
- Track rental status (Active, Returned, Due)

Payment Tracking:
- Record payments with method, amount, and date
- Link payments to specific rentals

Damage Reporting:
- Log damaged equipment, repair cost, and associated rental
- Maintain equipment condition history

Employee Handling:
- Track employees responsible for rental transactions

Tech Stack:

Programming Language: Java
Database: Oracle Database
Database Access: JDBC
UI Framework: Java Swing
Query Language: SQL

Database Design:

- Relational schema designed using ER modeling
- Functional dependencies analyzed
- All tables normalized to 3NF and verified to satisfy BCNF
- Referential integrity enforced using primary and foreign key constraints

Notes:

This project was completed as a group academic project and demonstrates hands-on experience with database design, Java application development, and SQL-backed systems.
