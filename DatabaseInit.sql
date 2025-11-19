
-- DROP TABLES
DROP TABLE Handles;
DROP TABLE DamageReport;
DROP TABLE Payment;
DROP TABLE Rental;
DROP TABLE Employee;
DROP TABLE Equipment;
DROP TABLE Customer;


-- DROP SEQUENCES
DROP SEQUENCE customer_seq;
DROP SEQUENCE employee_seq;
DROP SEQUENCE equipment_seq;
DROP SEQUENCE rental_seq;
DROP SEQUENCE payment_seq;
DROP SEQUENCE damage_seq;


-- CREATE SEQUENCES
CREATE SEQUENCE customer_seq START WITH 0 INCREMENT BY 1 MINVALUE -1;
CREATE SEQUENCE employee_seq START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE equipment_seq START WITH 500 INCREMENT BY 1;
CREATE SEQUENCE rental_seq START WITH 9000 INCREMENT BY 1;
CREATE SEQUENCE payment_seq START WITH 7000 INCREMENT BY 1;
CREATE SEQUENCE damage_seq START WITH 8000 INCREMENT BY 1;


-- CREATE TABLES (BCNF)
CREATE TABLE Customer (
  CustomerID NUMBER PRIMARY KEY,
  Name VARCHAR2(100) NOT NULL,
  Email VARCHAR2(100) UNIQUE,
  Phone VARCHAR2(20)
);

CREATE TABLE Employee (
  EmployeeID NUMBER PRIMARY KEY,
  Name VARCHAR2(100) NOT NULL,
  Role VARCHAR2(50),
  Phone VARCHAR2(20),
  Email VARCHAR2(100) UNIQUE
);

CREATE TABLE Equipment (
  EquipmentID NUMBER PRIMARY KEY,
  Name VARCHAR2(100) NOT NULL,
  Brand VARCHAR2(50),
  Daily_Rate NUMBER(10,2),
  Condition VARCHAR2(50),
  Availability VARCHAR2(20)
);

CREATE TABLE Rental (
  RentalID NUMBER PRIMARY KEY,
  CustomerID NUMBER NOT NULL,
  EquipmentID NUMBER NOT NULL,
  Status VARCHAR2(20),
  Start_Date DATE,
  Return_Date DATE,
  Total_Cost NUMBER(10,2),
  FOREIGN KEY(CustomerID) REFERENCES Customer(CustomerID),
  FOREIGN KEY(EquipmentID) REFERENCES Equipment(EquipmentID)
);

CREATE TABLE Payment (
  PaymentID NUMBER PRIMARY KEY,
  RentalID NUMBER NOT NULL,
  Amount NUMBER(10,2),
  MethodUsed VARCHAR2(30),
  PaymentDate DATE,
  FOREIGN KEY(RentalID) REFERENCES Rental(RentalID)
);

CREATE TABLE DamageReport (
  ReportID NUMBER PRIMARY KEY,
  RentalID NUMBER NOT NULL,
  EquipmentID NUMBER NOT NULL,
  Description VARCHAR2(400),
  Cost NUMBER(10,2),
  ReportDate DATE,
  FOREIGN KEY(RentalID) REFERENCES Rental(RentalID),
  FOREIGN KEY(EquipmentID) REFERENCES Equipment(EquipmentID)
);

CREATE TABLE Handles (
  EmployeeID NUMBER NOT NULL,
  RentalID NUMBER NOT NULL,
  PRIMARY KEY(EmployeeID, RentalID),
  FOREIGN KEY(EmployeeID) REFERENCES Employee(EmployeeID),
  FOREIGN KEY(RentalID) REFERENCES Rental(RentalID)
);


-- DUMMY DATA (small set for demo)
INSERT INTO Customer VALUES (customer_seq.NEXTVAL,'John Doe','john.doe@example.com','647-555-0100');
INSERT INTO Customer VALUES (customer_seq.NEXTVAL,'Alice White','alice.white@example.com','416-555-0200');
INSERT INTO Customer VALUES (customer_seq.NEXTVAL,'Bob Green','bob.green@example.com','647-555-0300');

INSERT INTO Employee VALUES (employee_seq.NEXTVAL,'David Smith','Manager','647-222-2222','dsmith@example.com');
INSERT INTO Employee VALUES (employee_seq.NEXTVAL,'Sara Blake','Clerk','416-333-3333','sblake@example.com');

INSERT INTO Equipment VALUES (equipment_seq.NEXTVAL,'Canon R6 Camera','Canon',49.99,'Good','Available');
INSERT INTO Equipment VALUES (equipment_seq.NEXTVAL,'Sony Tripod','Sony',9.99,'Good','Available');
INSERT INTO Equipment VALUES (equipment_seq.NEXTVAL,'LED Light Kit','Generic',19.99,'Good','Unavailable');

INSERT INTO Rental VALUES (rental_seq.NEXTVAL,1,501,'Active',TO_DATE('2025-11-10','YYYY-MM-DD'),NULL,149.97);
INSERT INTO Rental VALUES (rental_seq.NEXTVAL,2,502,'Returned',TO_DATE('2025-10-20','YYYY-MM-DD'),TO_DATE('2025-10-22','YYYY-MM-DD'),19.98);
INSERT INTO Rental VALUES (rental_seq.NEXTVAL,3,503,'Active',TO_DATE('2025-11-13','YYYY-MM-DD'),NULL,39.98);

INSERT INTO Payment VALUES (payment_seq.NEXTVAL,9002,19.98,'Credit Card',TO_DATE('2025-10-20','YYYY-MM-DD'));
INSERT INTO Payment VALUES (payment_seq.NEXTVAL,9001,149.97,'Credit Card',TO_DATE('2025-11-11','YYYY-MM-DD'));

INSERT INTO DamageReport VALUES (8001,9002,502,'Scratched leg',9.99,TO_DATE('2025-10-22','YYYY-MM-DD'));

INSERT INTO Handles VALUES (101,9001);
INSERT INTO Handles VALUES (102,9002);

COMMIT;