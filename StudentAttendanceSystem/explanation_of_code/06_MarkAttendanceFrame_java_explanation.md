# MarkAttendanceFrame.java - Complete Explanation

## 📌 Purpose
This file creates the **Mark Attendance Screen** where lecturers can record daily attendance for all students.

---

## 🎯 What Does This File Do?

Creates an interface to:
- Display all students in a table
- Set date for attendance
- Mark each student as Present/Absent
- Save attendance records to file

**Simple analogy**: Like taking roll call in class and marking who's present.

---

## 📦 Import Statements

```java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;  // For table data
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;           // For date formatting
import java.util.*;                          // For Date class
```

**New imports**:
- `DefaultTableModel` - Manages table data
- `SimpleDateFormat` - Formats dates
- `Date` - Current date/time

---

## 🏗️ Class Structure

```java
public class MarkAttendanceFrame extends JFrame {
    private String lecturerEmail;
    private JTextField dateField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton saveButton;
    private JButton backButton;
```

**Key components**:
- **JTable** - Displays students in rows and columns
- **DefaultTableModel** - Stores table data
- **dateField** - Input for attendance date

---

## 🔧 Constructor Method

```java
public MarkAttendanceFrame(String email) {
    this.lecturerEmail = email;
    setTitle("Mark Attendance");
    setSize(700, 500);  // Larger window for table
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(true);
    
    initComponents();
    loadStudents();  // Load students immediately
}
```

**Important**:
- **Larger size** (700×500) - Needs space for table
- **`loadStudents()`** - Automatically loads students when screen opens

---

## 🎨 initComponents() Method

### Visual Layout

```
┌─────────────────────────────────────────┐
│        Mark Attendance                  │
│   Date (YYYY-MM-DD): [2025-12-27]       │
├─────────────────────────────────────────┤
│ ID  │ Name      │ Dept │ Sem │ Status  │
├─────┼───────────┼──────┼─────┼─────────┤
│ S001│ John Doe  │ CS   │ 3   │ Present │
│ S002│ Jane Smith│ EC   │ 2   │ Absent  │
│ S003│ Bob       │ ME   │ 4   │ Present │
└─────────────────────────────────────────┘
│   [Save Attendance] [Back to Dashboard] │
└─────────────────────────────────────────┘
```

### Code Breakdown

**BorderLayout** (Different from previous screens):
```java
JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
```
- **NORTH** - Title and date
- **CENTER** - Table
- **SOUTH** - Buttons

**Date Field with Auto-fill**:
```java
dateField = new JTextField(15);
dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
```
- **`SimpleDateFormat("yyyy-MM-dd")`** - Format: 2025-12-27
- **`new Date()`** - Current date
- **Auto-fills today's date** - User convenience

**Table Setup**:
```java
String[] columns = { "Student ID", "Name", "Department", "Semester", "Status" };
tableModel = new DefaultTableModel(columns, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 4;  // Only Status column editable
    }
};
```

**Breaking it down**:
- **5 columns** - ID, Name, Dept, Semester, Status
- **`DefaultTableModel(columns, 0)`** - 0 rows initially
- **Override `isCellEditable`** - Only Status column can be edited
- **Why?** Student info shouldn't change, only attendance status

**Dropdown for Status**:
```java
studentTable.getColumnModel().getColumn(4).setCellEditor(
    new DefaultCellEditor(new JComboBox<>(new String[] { "Present", "Absent" })));
```
- **Column 4** - Status column
- **JComboBox** - Dropdown menu
- **Options** - Present or Absent only
- **Prevents typos** - Can't type "Presnt" or "Abscent"

---

## 📊 loadStudents() Method - Populating Table

```java
private void loadStudents() {
    File file = new File("data/students.txt");
    if (!file.exists()) {
        JOptionPane.showMessageDialog(this, 
            "No students found. Please add students first.",
            "Info", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
```

**Check if students exist**
- If no file, show message
- Can't mark attendance without students!

```java
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                tableModel.addRow(new Object[] {
                    parts[0].trim(),  // Student ID
                    parts[1].trim(),  // Name
                    parts[2].trim(),  // Department
                    parts[3].trim(),  // Semester
                    "Present"         // Default status
                });
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

**Read students.txt and add to table**
- **`tableModel.addRow()`** - Adds one row
- **Default status** - "Present" (optimistic!)
- **Lecturer can change** to "Absent" if needed

**Example**:
File: `S001,John Doe,Computer Science,3`
Table row: `[S001, John Doe, Computer Science, 3, Present]`

---

## 💾 handleSave() Method - Saving Attendance

```java
private void handleSave() {
    String date = dateField.getText().trim();
    
    if (date.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a date",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
```

**Step 1: Validate date**
- Must have a date

```java
    if (tableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No students to mark attendance",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
```

**Step 2: Check if table has students**
- Can't save empty attendance

```java
    File dataDir = new File("data");
    if (!dataDir.exists()) {
        dataDir.mkdirs();
    }
```

**Step 3: Create data folder**

```java
    try (FileWriter writer = new FileWriter("data/attendance.txt", true)) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String studentId = tableModel.getValueAt(i, 0).toString();
            String status = tableModel.getValueAt(i, 4).toString();
            writer.write(date + "," + studentId + "," + status + "\n");
        }
        
        JOptionPane.showMessageDialog(this, "Attendance saved successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        backToDashboard();
        
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving attendance",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

**Step 4: Write to file**
- **Loop through all rows** in table
- **Get Student ID** from column 0
- **Get Status** from column 4
- **Format**: `Date,StudentID,Status`
- **Append mode** - Adds to existing records

**Example output** (attendance.txt):
```
2025-12-27,S001,Present
2025-12-27,S002,Absent
2025-12-27,S003,Present
```

---

## 🔄 Complete Flow Diagram

```
User clicks "Mark Attendance" on dashboard
    ↓
MarkAttendanceFrame opens
    ↓
loadStudents() - Reads students.txt
    ↓
Table populated with all students
    ↓
Date auto-filled with today's date
    ↓
User changes some statuses to "Absent"
    ↓
Clicks "Save Attendance"
    ↓
handleSave() called
    ↓
Validates date and students exist
    ↓
Loops through table rows
    ↓
Writes each record to attendance.txt
    ↓
Success → Return to dashboard
```

---

## 🎓 Key Concepts Used

1. **JTable & DefaultTableModel**
   - Display data in rows/columns
   - Editable cells

2. **Cell Editors**
   - Dropdown for Status column
   - Restricts input options

3. **Date Formatting**
   - `SimpleDateFormat` - Format dates
   - Auto-fill current date

4. **Table Iteration**
   - Loop through rows
   - Get values from specific columns

5. **File Append**
   - Add new attendance records
   - Don't overwrite previous dates

---

## 📁 File Interactions

### Reads from:
```
data/students.txt
S001,John Doe,Computer Science,3
S002,Jane Smith,Electronics,2
```

### Writes to:
```
data/attendance.txt
2025-12-27,S001,Present
2025-12-27,S002,Absent
2025-12-28,S001,Present
2025-12-28,S002,Present
```

---

## 💡 Important Notes

1. **All students loaded** - Can't select specific students
2. **Default Present** - Optimistic approach
3. **Dropdown prevents errors** - Can't type wrong values
4. **Date format** - YYYY-MM-DD (sortable)
5. **Append mode** - Multiple dates in one file
6. **No duplicate check** - Can mark same date twice (potential issue!)

---

## 🎯 Summary

**In Simple Words**:
- Displays all students in a table
- Auto-fills today's date
- Each student has a dropdown: Present/Absent
- Saves attendance records to file
- One record per student per date

**Total Lines**: 162 lines
**Complexity**: Medium-High ⭐⭐⭐⭐
**Role**: Daily Attendance Recording
