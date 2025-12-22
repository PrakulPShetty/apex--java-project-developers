Student Attendance Management System
====================================

Overview
--------
This is a simple, menu-driven Student Attendance Management System that uses:
- Core Java for Backend logic
- Text files for Data Persistence (No Database!)
- HTML/CSS/JS for the Frontend Interface

Project Structure
-----------------
Student-Attendance-System/
├── backend/
│   ├── AttendanceSystem.java  (Main Server Login)
│   ├── users.txt              (Credentials: admin,admin123)
│   ├── students.txt           (Student Data)
│   └── attendance.txt         (Attendance Records)
├── frontend/
│   ├── login.html             (Entry Point)
│   ├── dashboard.html         (Main Menu)
│   ├── attendance.html        (Actions)
│   ├── css/style.css          (Styling)
│   └── js/app.js              (Logic)
└── README.txt

How to Run
----------
1. Open a terminal/command prompt.
2. Navigate to the project folder 'Student-Attendance-System'.
3. Compile the backend:
   javac backend/AttendanceSystem.java
4. Run the backend:
   java -cp backend AttendanceSystem

5. Open your browser and go to:
   http://localhost:8000/login.html

Credentials
-----------
username: admin
password: admin123

Note: The system creates/reads files in the 'backend' folder relative to where you run the execution command. Ensure you run from 'Student-Attendance-System' directory.
