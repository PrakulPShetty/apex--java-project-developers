# Student Attendance Management System

## Pure Java Swing Desktop Application

A simple, professional desktop application for managing student attendance using **Core Java** with **Swing GUI** and **text file storage**.

---

## ✨ Features

- **Lecturer Signup & Login** - Secure authentication with file-based storage
- **Add Students** - Register students with ID, name, department, and semester
- **Mark Attendance** - Record daily attendance (Present/Absent) for all students
- **View Attendance** - Search and view attendance records by date with statistics
- **Text-Only UI** - Clean, professional interface without icons or logos

---

## 🛠️ Technology Stack

- **Language**: Pure Java (Java 17/24 compatible)
- **GUI**: Java Swing (JFrame, JTable, JButton, JTextField)
- **Storage**: Text files (.txt)
- **Build**: No Maven, no external dependencies
- **Compilation**: `javac`
- **Execution**: `java`

---

## 📁 Project Structure

```
StudentAttendanceSystem/
├── Main.java                    # Entry point
├── LoginFrame.java              # Login GUI
├── SignupFrame.java             # Registration GUI
├── DashboardFrame.java          # Main navigation
├── AddStudentFrame.java         # Add student GUI
├── MarkAttendanceFrame.java     # Mark attendance GUI
├── ViewAttendanceFrame.java     # View records GUI
└── data/
    ├── lecturers.txt           # Lecturer credentials
    ├── students.txt            # Student records
    └── attendance.txt          # Attendance records
```

---

## 🚀 How to Run

### Compilation

```bash
cd StudentAttendanceSystem
javac *.java
```

### Execution

```bash
java Main
```

The application window will open automatically!

---

## 📖 Usage Guide

### 1. First Time Setup
1. Run the application
2. Click "Create Account"
3. Enter your name, email, and password
4. Click "Sign Up"

### 2. Login
1. Enter your email and password
2. Click "Login"

### 3. Add Students
1. From Dashboard, click "Add Student"
2. Fill in: Student ID, Name, Department, Semester
3. Click "Save Student"

### 4. Mark Attendance
1. From Dashboard, click "Mark Attendance"
2. Select or enter date (format: YYYY-MM-DD)
3. For each student, select "Present" or "Absent" from dropdown
4. Click "Save Attendance"

### 5. View Attendance
1. From Dashboard, click "View Attendance"
2. Enter date (format: YYYY-MM-DD)
3. Click "Search"
4. View records and statistics

---

## 📊 Data Files Format

### lecturers.txt
```
name,email,password
John Doe,john@example.com,password123
```

### students.txt
```
studentId,name,department,semester
S001,Alice Smith,Computer Science,3
S002,Bob Johnson,Mathematics,2
```

### attendance.txt
```
date,studentId,status
2025-12-22,S001,Present
2025-12-22,S002,Absent
```

---

## 🎯 Java Concepts Demonstrated

1. **Object-Oriented Programming** - Classes and objects
2. **GUI Development** - Java Swing components
3. **File Handling** - FileReader, BufferedReader, FileWriter
4. **Exception Handling** - try-catch blocks
5. **Event Handling** - ActionListener
6. **Data Structures** - ArrayList, HashMap
7. **Input Validation** - Form validation
8. **Data Persistence** - Text file storage

---

## 🎨 UI Design

- **Clean and Professional** - Academic look
- **Text-Only** - No icons, logos, or symbols
- **Consistent Layout** - Proper spacing and alignment
- **User-Friendly** - Intuitive navigation
- **Responsive Feedback** - Dialog messages for all actions

---

## 💡 Viva Explanation Points

### How It Works
"This is a desktop application built using Java Swing for the GUI. When the user performs actions like login or adding students, the data is stored in text files. File handling is done using BufferedReader for reading and FileWriter for writing."

### Architecture
"The application follows a modular design with separate JFrame classes for each screen. Each frame handles its own UI components and file operations. Data flows from user input → validation → file storage."

### File Handling
"We use three text files: lecturers.txt for authentication, students.txt for student records, and attendance.txt for attendance data. Each file uses comma-separated values (CSV) format for easy parsing."

---

## 🔧 Troubleshooting

### Compilation Error
- Ensure you're in the `StudentAttendanceSystem` directory
- Check that all 7 .java files are present
- Verify Java is installed: `java -version`

### Application Won't Start
- Make sure compilation was successful (no .class files = compilation failed)
- Run from correct directory: `cd StudentAttendanceSystem`

### Data Not Saving
- Check if `data/` directory exists (created automatically on first save)
- Verify file permissions

---

## 📝 Notes

- **No External Dependencies** - Uses only Java standard library
- **Cross-Platform** - Runs on Windows, Mac, Linux
- **Lightweight** - Small file size, fast startup
- **Educational** - Perfect for college projects and viva presentations

---

## 🎓 Perfect for College Viva

This project is ideal for academic presentations because:
- ✅ Uses core Java concepts
- ✅ Easy to explain
- ✅ No complex frameworks
- ✅ Clean, readable code
- ✅ Demonstrates file handling
- ✅ Professional GUI design

---

## 📄 License

Created for educational purposes.

---

## 👨‍💻 Author

Student Attendance Management System  
Pure Java Swing Application  
Version 1.0
