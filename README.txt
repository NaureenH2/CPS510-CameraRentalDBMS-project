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
- Functional dependencies identified and analyzed
- All tables normalized to Third Normal Form (3NF) and verified to satisfy Boyce–Codd Normal Form (BCNF)
- Enforced referential integrity using primary keys, foreign keys, and constraints

Prereqs:
- Java 11+ JDK installed
- Oracle DB accessible
- ojdbc11.jar in lib/ directory (place in project root lib/)

Setup:
1. Run Sql developer using OPENVPN
2. Import DatabaseInit.sql to TMU Oracle Database 11g
3. In the root file, do >> touch .env
4. Add the following to the .env file (replace ___ with your username and password for TMU Oracle Database):
   UserID = ______
   Password = _______
4. Compile (DIRECTLY):
   mkdir bin
   javac -cp "lib/ojdbc11.jar" -d bin src/env/*.java src/db/*.java src/util/*.java src/ui/*.java src/*.java
   Run:
   java -cp "bin;lib/ojdbc11.jar" LoginUI   (Windows)
  
5. Compile (For JAR file):
   javac -cp "lib/ojdbc11.jar" -d bin src/env/*.java src/db/*.java src/util/*.java src/ui/*.java src/*.java -d out
   jar cfm T31.jar .\Manifest.txt -C out .
   Run:
   java -jar .\T31.jar

UserName: Admin
Password: t31510

