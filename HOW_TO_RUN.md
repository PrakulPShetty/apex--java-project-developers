# How to Run the Student Attendance System

## 📋 Prerequisites

Before running the project, ensure you have:
- ✅ Java installed (Java 17 or higher)
- ✅ Terminal/Command Prompt access
- ✅ All 7 Java files in `StudentAttendanceSystem` folder

---

## 🚀 Step-by-Step Execution Guide

### Step 1: Open Terminal

Open your terminal (Git Bash, Command Prompt, or PowerShell)

---

### Step 2: Navigate to Project Directory

```bash
cd StudentAttendanceSystem
```

**Expected Result:** You should be inside the `StudentAttendanceSystem` folder

---

### Step 3: Verify Java Installation

```bash
java -version
```

**Expected Output:**
```
openjdk version "17.0.17" 2023-10-17
```

If you see an error, Java is not installed or not in PATH.

---

### Step 4: Compile All Java Files

```bash
javac *.java
```

**What this does:**
- Compiles all `.java` files
- Creates `.class` files for each Java file
- Checks for syntax errors

**Expected Result:** 
- No output = Success! ✅
- If errors appear, there's a syntax problem in the code

**Files created:**
- `Main.class`
- `LoginFrame.class`
- `SignupFrame.class`
- `DashboardFrame.class`
- `AddStudentFrame.class`
- `MarkAttendanceFrame.class`
- `ViewAttendanceFrame.class`

---

### Step 5: Run the Application

```bash
java Main
```

**IMPORTANT:** Use capital `M` in `Main`!

**What this does:**
- Starts the Java Virtual Machine (JVM)
- Loads the `Main` class
- Executes the `main()` method
- Opens the LoginFrame window

**Expected Result:** A GUI window appears with login screen

---

### Step 6: Use the Application

#### First Time:
1. Click **"Create Account"**
2. Fill in: Name, Email, Password
3. Click **"Sign Up"**
4. You'll return to login screen

#### Every Time:
1. Enter your **Email** and **Password**
2. Click **"Login"**
3. Dashboard opens

---

## 🔄 If You Make Code Changes

After editing any `.java` file:

```bash
# Step 1: Recompile
javac *.java

# Step 2: Run again
java Main
```

---

## 📁 Project Structure

```
StudentAttendanceSystem/
├── Main.java                    # Entry point
├── LoginFrame.java              # Login screen
├── SignupFrame.java             # Registration screen
├── DashboardFrame.java          # Main menu
├── AddStudentFrame.java         # Add student form
├── MarkAttendanceFrame.java     # Attendance marking
├── ViewAttendanceFrame.java     # View records
│
├── Main.class                   # Compiled files
├── LoginFrame.class
├── SignupFrame.class
├── DashboardFrame.class
├── AddStudentFrame.class
├── MarkAttendanceFrame.class
├── ViewAttendanceFrame.class
│
└── data/                        # Created automatically
    ├── lecturers.txt           # Login credentials
    ├── students.txt            # Student records
    └── attendance.txt          # Attendance data
```

---

## 🎯 Complete Workflow

### 1. Compilation Phase
```bash
javac *.java
```
- Java compiler reads `.java` files
- Checks syntax and semantics
- Generates bytecode (`.class` files)

### 2. Execution Phase
```bash
java Main
```
- JVM loads `Main.class`
- Calls `main()` method
- `main()` creates `LoginFrame`
- GUI appears on screen

### 3. Runtime Phase
- User interacts with GUI
- Button clicks trigger event listeners
- Data is read/written to text files
- Windows navigate between frames

---

## ⚡ Quick Commands Reference

| Action | Command |
|--------|---------|
| Navigate to folder | `cd StudentAttendanceSystem` |
| Check Java version | `java -version` |
| Compile all files | `javac *.java` |
| Run application | `java Main` |
| List files | `ls` or `dir` |
| View file content | `cat filename.txt` |

---

## 🐛 Troubleshooting

### Error: "javac: command not found"
**Solution:** Java is not installed or not in PATH
- Install Java JDK
- Add to system PATH

### Error: "Could not find or load main class Main"
**Solutions:**
1. Make sure you're in `StudentAttendanceSystem` folder
2. Check that `Main.class` exists (compile first)
3. Use capital `M`: `java Main` not `java main`

### Error: Compilation errors
**Solution:** Check the error message
- Line number tells you where the problem is
- Fix syntax errors in the `.java` file
- Recompile

### Application doesn't start
**Solutions:**
1. Check if compilation was successful
2. Look for `.class` files
3. Try: `javac *.java` then `java Main`

### Data not saving
**Solution:** 
- `data/` folder is created automatically on first save
- Check file permissions
- Look inside `data/` folder for `.txt` files

---

## 📝 Important Notes

1. **Case Sensitivity:** Java is case-sensitive
   - `java Main` ✅
   - `java main` ❌

2. **File Location:** Always run from `StudentAttendanceSystem` folder

3. **Compilation:** Must compile before running
   - After any code change, recompile

4. **Data Persistence:** Data is saved in `data/` folder
   - Backup this folder to preserve data

---

## 🎓 For Viva Demonstration

### Show Compilation:
```bash
javac *.java
```
Explain: "This compiles all Java source files into bytecode"

### Show Execution:
```bash
java Main
```
Explain: "This starts the JVM and runs the Main class"

### Show Data Files:
```bash
cat data/students.txt
```
Explain: "This shows the CSV format we use for data storage"

---

## ✅ Success Checklist

- [ ] Java is installed and working
- [ ] In correct directory (`StudentAttendanceSystem`)
- [ ] All 7 `.java` files present
- [ ] Compilation successful (no errors)
- [ ] Application runs (`java Main`)
- [ ] GUI window appears
- [ ] Can create account and login
- [ ] Data saves to `data/` folder

---

**You're ready to demonstrate your project!** 🎉
