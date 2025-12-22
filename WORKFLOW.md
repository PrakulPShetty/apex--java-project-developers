# Student Attendance System - Complete Workflow Guide

## 🚀 Application Workflow

### Step 1: Start the Application

```bash
cd StudentAttendanceSystem
java Main
```

**What happens:** LoginFrame window opens

---

### Step 2: Create Lecturer Account (First Time Only)

1. Click **"Create Account"** button
2. **SignupFrame** opens
3. Fill in the form:
   - **Name**: Your full name (e.g., "Dr. John Smith")
   - **Email**: Your email (e.g., "john@college.edu")
   - **Password**: Choose a password
4. Click **"Sign Up"**
5. **Success message** appears
6. Automatically returns to **LoginFrame**

**Behind the scenes:**
- Data saved to `data/lecturers.txt` as: `name,email,password`
- Email is checked for duplicates before saving

---

### Step 3: Login

1. Enter your **Email** and **Password**
2. Click **"Login"** (or press Enter)
3. **DashboardFrame** opens

**Behind the scenes:**
- Application reads `data/lecturers.txt`
- Compares your email and password with stored data
- If match found, opens dashboard with your email

---

### Step 4: Add Students

1. From Dashboard, click **"Add Student"**
2. **AddStudentFrame** opens
3. Fill in student details:
   - **Student ID**: Unique ID (e.g., "S001")
   - **Name**: Student name (e.g., "Alice Smith")
   - **Department**: Department name (e.g., "Computer Science")
   - **Semester**: Number only (e.g., "3")
4. Click **"Save Student"**
5. **Success message** appears
6. Form clears automatically
7. Add more students or click **"Back to Dashboard"**

**Behind the scenes:**
- Data appended to `data/students.txt` as: `studentId,name,department,semester`
- Student ID is checked for duplicates
- Semester is validated to be a number

**Example data:**
```
S001,Alice Smith,Computer Science,3
S002,Bob Johnson,Mathematics,2
S003,Carol White,Physics,4
```

---

### Step 5: Mark Attendance

1. From Dashboard, click **"Mark Attendance"**
2. **MarkAttendanceFrame** opens
3. **Date field** is auto-filled with today's date (YYYY-MM-DD format)
   - You can change it if needed
4. **Table displays all students** with columns:
   - Student ID
   - Name
   - Department
   - Semester
   - **Status** (dropdown)
5. For each student, click the **Status** column and select:
   - **Present** (default)
   - **Absent**
6. Click **"Save Attendance"**
7. **Success message** appears
8. Returns to **Dashboard**

**Behind the scenes:**
- Reads all students from `data/students.txt`
- Displays them in a JTable
- When saved, writes to `data/attendance.txt` as: `date,studentId,status`

**Example data:**
```
2025-12-22,S001,Present
2025-12-22,S002,Absent
2025-12-22,S003,Present
```

---

### Step 6: View Attendance

1. From Dashboard, click **"View Attendance"**
2. **ViewAttendanceFrame** opens
3. Enter **Date** (YYYY-MM-DD format)
4. Click **"Search"**
5. **Table displays attendance records** with columns:
   - Student ID
   - Student Name (fetched from students.txt)
   - Status
6. **Statistics popup** shows:
   - Total students
   - Present count
   - Absent count

**Behind the scenes:**
- Reads `data/attendance.txt` for the specified date
- Joins with `data/students.txt` to get student names
- Uses HashMap for efficient name lookup
- Calculates statistics

---

### Step 7: Logout

1. From Dashboard, click **"Logout"**
2. Returns to **LoginFrame**
3. You can login again or close the application

---

## 📁 Data Files Explained

### data/lecturers.txt
**Format:** `name,email,password`

**Example:**
```
Dr. John Smith,john@college.edu,password123
Prof. Jane Doe,jane@college.edu,secure456
```

**Used for:** Login authentication

---

### data/students.txt
**Format:** `studentId,name,department,semester`

**Example:**
```
S001,Alice Smith,Computer Science,3
S002,Bob Johnson,Mathematics,2
S003,Carol White,Physics,4
S004,David Brown,Chemistry,1
```

**Used for:** 
- Displaying students in Mark Attendance
- Looking up student names in View Attendance

---

### data/attendance.txt
**Format:** `date,studentId,status`

**Example:**
```
2025-12-22,S001,Present
2025-12-22,S002,Absent
2025-12-22,S003,Present
2025-12-23,S001,Present
2025-12-23,S002,Present
```

**Used for:** Viewing attendance records by date

---

## 🔄 Complete Daily Workflow

### Morning Routine:
1. **Login** to the system
2. **Mark Attendance** for today
   - Date is auto-filled
   - Mark each student Present/Absent
   - Save

### Checking Records:
1. **View Attendance** for any date
2. Enter date and search
3. See who was present/absent
4. View statistics

### Adding New Students:
1. **Add Student** when new student joins
2. Fill details and save
3. Student immediately available for attendance

---

## 🎯 Key Features

✅ **Auto-filled dates** - Today's date automatically filled  
✅ **Dropdown selection** - Easy Present/Absent selection  
✅ **Duplicate prevention** - Can't add same email or student ID twice  
✅ **Data persistence** - All data saved in text files  
✅ **Statistics** - Automatic calculation of attendance counts  
✅ **Clean navigation** - Easy to move between screens  

---

## 💡 Tips

1. **Date Format**: Always use YYYY-MM-DD (e.g., 2025-12-22)
2. **Student ID**: Use consistent format (e.g., S001, S002, S003)
3. **Backup**: Copy the `data/` folder to backup your data
4. **Multiple Days**: You can mark attendance for different dates
5. **Editing**: To edit data, manually edit the text files

---

## 🔧 Recompiling After Changes

If you make any code changes:

```bash
cd StudentAttendanceSystem
javac *.java
java Main
```

---

## ✨ Now You Can Maximize!

All windows are now **resizable** - you can maximize or resize them as needed!
