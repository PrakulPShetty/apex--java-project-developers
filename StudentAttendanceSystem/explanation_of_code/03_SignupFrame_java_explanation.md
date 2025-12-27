# SignupFrame.java - Complete Explanation

## 📌 Purpose
This file creates the **Signup/Registration Screen** where new lecturers can create their accounts by providing their name, email, and password.

---

## 🎯 What Does This File Do?

Creates a registration form with:
- Name input field
- Email input field
- Password input field
- Sign Up button
- Back to Login button

**Simple analogy**: Like creating a new account on Instagram or Twitter.

---

## 📦 Import Statements

```java
import javax.swing.*;  // GUI components
import java.awt.*;     // Layouts and colors
import java.io.*;      // File operations
```

---

## 🏗️ Class Structure

```java
public class SignupFrame extends JFrame {
```

- Extends `JFrame` - Makes it a window
- Similar to LoginFrame but for registration

---

## 📊 Class Variables

```java
private JTextField nameField;
private JTextField emailField;
private JPasswordField passwordField;
private JButton signupButton;
private JButton backButton;
```

**Components**:
- 3 input fields (name, email, password)
- 2 buttons (signup, back)

---

## 🔧 Constructor Method

```java
public SignupFrame() {
    setTitle("Student Attendance System - Signup");
    setSize(400, 350);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(true);
    
    initComponents();
}
```

**Similar to LoginFrame but**:
- Different title
- Slightly taller (350 instead of 300) - more fields

---

## 🎨 initComponents() Method

### Layout Structure

```
┌─────────────────────────────┐
│     Create Account          │  ← Title
├─────────────────────────────┤
│ Name:     [____________]    │  ← Name field
│ Email:    [____________]    │  ← Email field
│ Password: [____________]    │  ← Password field
│        [Sign Up]            │  ← Signup button
│     [Back to Login]         │  ← Back button
└─────────────────────────────┘
```

### Code Breakdown

**Same as LoginFrame**:
- Uses GridBagLayout
- White background
- 10-pixel spacing

**Three input fields instead of two**:
1. Name field (new!)
2. Email field
3. Password field

**Event Listeners**:
```java
signupButton.addActionListener(e -> handleSignup());
backButton.addActionListener(e -> backToLogin());
```

---

## ✍️ handleSignup() Method - Registration Logic

```java
private void handleSignup() {
    String name = nameField.getText().trim();
    String email = emailField.getText().trim();
    String password = new String(passwordField.getPassword()).trim();
```

**Step 1: Get user input**
- Get text from all three fields
- Remove extra spaces with `trim()`

```java
    if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill all fields",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
```

**Step 2: Validation - Check empty fields**
- All fields must be filled
- Show error if any field is empty

```java
    if (emailExists(email)) {
        JOptionPane.showMessageDialog(this, "Email already registered",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
```

**Step 3: Check duplicate email**
- Call `emailExists()` method
- Prevent same email being used twice

```java
    if (saveLecturer(name, email, password)) {
        JOptionPane.showMessageDialog(this, "Account created successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        backToLogin();
    } else {
        JOptionPane.showMessageDialog(this, "Error creating account",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

**Step 4: Save to file**
- Call `saveLecturer()` method
- If successful: show success message and go to login
- If failed: show error message

---

## 🔍 emailExists() Method - Duplicate Check

```java
private boolean emailExists(String email) {
    File file = new File("data/lecturers.txt");
    if (!file.exists()) {
        return false;  // File doesn't exist = no users yet
    }
```

**Step 1: Check if file exists**
- If file doesn't exist, no users registered yet
- Return false (email doesn't exist)

```java
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2 && parts[1].trim().equals(email)) {
                return true;  // Email found!
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return false;  // Email not found
}
```

**Step 2: Read file and search for email**

**File format**: `Name,Email,Password`

**How it works**:
1. Read file line by line
2. Split each line by comma
3. Check if `parts[1]` (email) matches the input email
4. Return true if match found
5. Return false if no match

**Why check `parts.length >= 2`?**
- Ensures the line has at least 2 parts
- Prevents errors if file is corrupted

---

## 💾 saveLecturer() Method - Writing to File

```java
private boolean saveLecturer(String name, String email, String password) {
    File dataDir = new File("data");
    if (!dataDir.exists()) {
        dataDir.mkdirs();
    }
```

**Step 1: Create data directory if it doesn't exist**
- **`mkdirs()`** - Creates directory (and parent directories if needed)
- Ensures the folder exists before saving

```java
    try (FileWriter writer = new FileWriter("data/lecturers.txt", true)) {
        writer.write(name + "," + email + "," + password + "\n");
        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
```

**Step 2: Write to file**

**Key points**:
- **`FileWriter(..., true)`** - The `true` means **append mode**
  - Adds new line at the end
  - Doesn't overwrite existing data
- **Format**: `Name,Email,Password\n`
  - Comma-separated values
  - `\n` adds new line
- **Try-with-resources** - Automatically closes file

**Example**:
If file contains:
```
John Doe,john@email.com,pass123
```

After saving "Jane Smith, jane@email.com, pass456":
```
John Doe,john@email.com,pass123
Jane Smith,jane@email.com,pass456
```

---

## 🚪 backToLogin() Method

```java
private void backToLogin() {
    this.dispose();
    new LoginFrame().setVisible(true);
}
```

**What it does**:
1. Close signup window
2. Open login window

---

## 🔄 Complete Flow Diagram

```
User clicks "Create Account" on login screen
    ↓
SignupFrame appears
    ↓
User fills: Name, Email, Password
    ↓
Clicks "Sign Up" button
    ↓
handleSignup() called
    ↓
Validates: All fields filled?
    ↓
emailExists() - Email already registered?
    ↓
saveLecturer() - Save to file
    ↓
Success? → Yes → Show success → Go to login
         → No  → Show error message
```

---

## 🎓 Key Concepts Used

1. **File I/O**
   - Reading: Check if email exists
   - Writing: Save new user data

2. **Data Validation**
   - Empty field check
   - Duplicate email check

3. **File Append Mode**
   - `FileWriter(..., true)` - Adds to end of file

4. **Directory Creation**
   - `mkdirs()` - Creates folder if needed

5. **CSV Format**
   - Comma-Separated Values
   - Easy to read and write

---

## 📁 File Operations

### Before Signup (file doesn't exist):
```
data/ folder doesn't exist
```

### After First Signup:
```
data/
  └── lecturers.txt
      └── John Doe,john@email.com,pass123
```

### After Second Signup:
```
data/
  └── lecturers.txt
      ├── John Doe,john@email.com,pass123
      └── Jane Smith,jane@email.com,pass456
```

---

## 💡 Important Notes

1. **No password strength check** - Any password is accepted
2. **No email format validation** - Doesn't check if email is valid
3. **Plain text storage** - Passwords not encrypted (insecure!)
4. **Duplicate prevention** - Checks email before saving
5. **Auto-creates folder** - Creates `data/` if it doesn't exist

---

## 🆚 Comparison with LoginFrame

| Feature | LoginFrame | SignupFrame |
|---------|-----------|-------------|
| Fields | 2 (email, password) | 3 (name, email, password) |
| File Operation | Read | Write |
| Purpose | Authenticate | Register |
| Validation | Credentials match? | Email unique? |
| Success Action | Open Dashboard | Go to Login |

---

## 🎯 Summary

**In Simple Words**:
- Creates a registration form
- Takes name, email, and password
- Checks if email already exists
- Saves new user to `lecturers.txt` file
- Returns to login screen

**Total Lines**: 167 lines
**Complexity**: Medium ⭐⭐⭐
**Role**: User Registration
