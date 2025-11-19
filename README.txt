Equipment Rental System - Java Swing

Prereqs:
- Java 11+ JDK installed
- Oracle DB accessible
- ojdbc11.jar in lib/ directory (place in project root lib/)

Setup:
1. Create schema/tables in Oracle using provided SQL or dummy_records.sql
2. Create sequences (optional) as noted in README or use fixed IDs
3. Update src/db/DBConnection.java placeholders: SERVICE, USER, PASSWORD
4. Compile:
   mkdir bin
   javac -cp "lib/ojdbc11.jar" -d bin src/db/*.java src/util/*.java src/ui/*.java src/*.java
5. Run:
   java -cp "bin;lib/ojdbc11.jar" MainMenu   (Windows)
   java -cp "bin:lib/ojdbc11.jar" MainMenu   (mac/linux)

Files to submit to D2L:
- Source code (src/)
- README.txt
- dummy_records.sql
- BCNF_Proof.txt
- PresentationScript.txt
