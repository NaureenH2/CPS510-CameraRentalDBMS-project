Equipment Rental System - Java Swing

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

Files to submit to D2L:
- Source code (src/)
- README.txt
- DatabaseInit.sql
- BCNF_Proof.txt

