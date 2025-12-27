# ViewAttendanceFrame.java - Complete Explanation

## 📌 Purpose
This file creates the **View Attendance Screen** where lecturers can search and view attendance records for any specific date.

---

## 🎯 What Does This File Do?

Creates an interface to:
- Enter a date to search
- Display attendance records for that date
- Show student names with their status
- Calculate and display statistics (Present/Absent count)

**Simple analogy**: Like checking the attendance register for a specific day.

---

## 📦 Import Statements

```java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;  // For HashMap
```

**Key import**: `HashMap` - To map Student IDs to Names

---

## 🏗️ Class Structure

```java
public class ViewAttendanceFrame extends JFrame {
    private String lecturerEmail;
    private JTextField dateField;
    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;
    private JButton backButton;
```

**Similar to MarkAttendanceFrame** but:
- Has **Search button** instead of Save
- Table is **read-only** (not editable)

---

## 🔧 Constructor Method

```java
public ViewAttendanceFrame(String email) {
    this.lecturerEmail = email;
    setTitle("View Attendance");
    setSize(700, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(true);
    
    initComponents();
}
```

**Note**: Does NOT auto-load data (unlike MarkAttendanceFrame)
- User must search for a specific date

---

## 🎨 initComponents() Method

### Visual Layout

```
┌─────────────────────────────────────────┐
│     View Attendance Records             │
│ Date (YYYY-MM-DD): [__________] [Search]│
├─────────────────────────────────────────┤
│ Student ID │ Student Name │ Status      │
├────────────┼──────────────┼─────────────┤
│ S001       │ John Doe     │ Present     │
│ S002       │ Jane Smith   │ Absent      │
│ S003       │ Bob Johnson  │ Present     │
└─────────────────────────────────────────┘
│        [Back to Dashboard]              │
└─────────────────────────────────────────┘
```

### Table Setup

```java
String[] columns = { "Student ID", "Student Name", "Status" };
tableModel = new DefaultTableModel(columns, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;  // All cells read-only
    }
};
```

**Key differences from MarkAttendanceFrame**:
- **3 columns** instead of 5 (ID, Name, Status only)
- **All cells read-only** - Can't edit
- **Starts empty** - Populated after search

---

## 🔍 handleSearch() Method - Main Logic

```java
private void handleSearch() {
    String date = dateField.getText().trim();
    
    if (date.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a date",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
```

**Step 1: Validate date input**

```java
    // Clear existing data
    tableModel.setRowCount(0);
```

**Step 2: Clear previous search results**
- **`setRowCount(0)`** - Removes all rows
- Fresh start for new search

```java
    // Load student names map
    Map<String, String> studentNames = loadStudentNames();
```

**Step 3: Load student names**
- Creates a map: Student ID → Student Name
- **Why?** attendance.txt only has IDs, not names

```java
    // Load attendance records
    File file = new File("data/attendance.txt");
    if (!file.exists()) {
        JOptionPane.showMessageDialog(this, "No attendance records found",
                "Info", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
```

**Step 4: Check if attendance file exists**

```java
    int recordCount = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[0].trim().equals(date)) {
                String studentId = parts[1].trim();
                String status = parts[2].trim();
                String studentName = studentNames.getOrDefault(studentId, "Unknown");
                
                tableModel.addRow(new Object[] { studentId, studentName, status });
                recordCount++;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
```

**Step 5: Search and populate table**

**Breaking it down**:
- **Read attendance.txt** line by line
- **Format**: `Date,StudentID,Status`
- **Check if date matches** - `parts[0].trim().equals(date)`
- **Get student name** from map - `studentNames.getOrDefault(studentId, "Unknown")`
- **Add row** to table
- **Count records** found

**Example**:
```
File line: 2025-12-27,S001,Present
If searching for 2025-12-27:
  → Match found
  → studentId = S001
  → status = Present
  → studentName = John Doe (from map)
  → Add row: [S001, John Doe, Present]
```

```java
    if (recordCount == 0) {
        JOptionPane.showMessageDialog(this, 
            "No attendance records found for " + date,
            "Info", JOptionPane.INFORMATION_MESSAGE);
    } else {
        // Calculate statistics
        int present = 0, absent = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String status = tableModel.getValueAt(i, 2).toString();
            if (status.equalsIgnoreCase("Present")) {
                present++;
            } else {
                absent++;
            }
        }
        
        JOptionPane.showMessageDialog(this,
            "Total: " + recordCount + " | Present: " + present + " | Absent: " + absent,
            "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }
}
```

**Step 6: Show statistics**
- **If no records** - Show "not found" message
- **If records found**:
  - Count Present
  - Count Absent
  - Show popup with statistics

**Example popup**:
```
Statistics
Total: 25 | Present: 22 | Absent: 3
```

---

## 🗺️ loadStudentNames() Method - Creating Name Map

```java
private Map<String, String> loadStudentNames() {
    Map<String, String> studentNames = new HashMap<>();
    File file = new File("data/students.txt");
    
    if (!file.exists()) {
        return studentNames;  // Return empty map
    }
```

**Step 1: Create HashMap and check file**

```java
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                studentNames.put(parts[0].trim(), parts[1].trim());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return studentNames;
}
```

**Step 2: Read students.txt and build map**

**How it works**:
- **File format**: `StudentID,Name,Department,Semester`
- **Extract**: ID (parts[0]) and Name (parts[1])
- **Store in map**: `studentNames.put(ID, Name)`

**Example**:
```
File:
S001,John Doe,Computer Science,3
S002,Jane Smith,Electronics,2

Map:
{
  "S001" → "John Doe",
  "S002" → "Jane Smith"
}
```

**Why use HashMap?**
- **Fast lookup** - O(1) time complexity
- **Easy to use** - `map.get(key)` returns value
- **`getOrDefault()`** - Returns "Unknown" if ID not found

---

## 🔄 Complete Flow Diagram

```
User clicks "View Attendance" on dashboard
    ↓
ViewAttendanceFrame opens (empty table)
    ↓
User enters date: 2025-12-27
    ↓
Clicks "Search"
    ↓
handleSearch() called
    ↓
Clear previous results
    ↓
loadStudentNames() - Build ID→Name map
    ↓
Read attendance.txt
    ↓
Filter records matching date
    ↓
For each match:
  ├─ Get Student ID
  ├─ Get Status
  ├─ Lookup Name from map
  └─ Add row to table
    ↓
Calculate statistics
    ↓
Show popup with Total/Present/Absent counts
```

---

## 🎓 Key Concepts Used

1. **HashMap (Map Interface)**
   - Key-Value pairs
   - Fast lookups
   - `getOrDefault()` method

2. **Data Joining**
   - Combine data from two files
   - attendance.txt + students.txt

3. **Filtering**
   - Search by date
   - Only show matching records

4. **Statistics Calculation**
   - Count Present/Absent
   - Loop through table data

5. **Dynamic Table Population**
   - Clear and refill table
   - Based on search criteria

---

## 📁 File Interactions

### Reads from students.txt:
```
S001,John Doe,Computer Science,3
S002,Jane Smith,Electronics,2
S003,Bob Johnson,Mechanical,4
```

### Reads from attendance.txt:
```
2025-12-27,S001,Present
2025-12-27,S002,Absent
2025-12-27,S003,Present
2025-12-28,S001,Present
2025-12-28,S002,Present
```

### Displays (for 2025-12-27):
```
┌──────────┬──────────────┬─────────┐
│ S001     │ John Doe     │ Present │
│ S002     │ Jane Smith   │ Absent  │
│ S003     │ Bob Johnson  │ Present │
└──────────┴──────────────┴─────────┘
Statistics: Total: 3 | Present: 2 | Absent: 1
```

---

## 💡 Important Notes

1. **Two-file lookup** - Combines students.txt and attendance.txt
2. **HashMap efficiency** - Fast name lookups
3. **Read-only table** - Can't edit attendance
4. **Statistics popup** - Quick overview
5. **Empty on start** - Must search to see data
6. **Unknown students** - Shows "Unknown" if ID not in students.txt

---

## 🆚 Comparison with MarkAttendanceFrame

| Feature | MarkAttendanceFrame | ViewAttendanceFrame |
|---------|---------------------|---------------------|
| Purpose | Record attendance | View attendance |
| Table Columns | 5 (ID, Name, Dept, Sem, Status) | 3 (ID, Name, Status) |
| Editable | Yes (Status column) | No (read-only) |
| Data Source | students.txt | attendance.txt + students.txt |
| Auto-load | Yes (all students) | No (search required) |
| Button | Save | Search |
| Statistics | No | Yes (Present/Absent count) |
| File Operation | Write | Read |

---

## 🎯 Summary

**In Simple Words**:
- Search attendance by date
- Displays student names with their status
- Combines data from two files
- Shows statistics (Total, Present, Absent)
- Read-only view - can't modify

**Total Lines**: 175 lines
**Complexity**: Medium-High ⭐⭐⭐⭐
**Role**: Attendance Reporting & Analysis
