# LoginFrame.java - Complete Explanation

## 📌 Purpose
This file creates the **Login Screen** where lecturers enter their email and password to access the Student Attendance System.

---

## 🎯 What Does This File Do?

This file creates a window with:
- Email input field
- Password input field
- Login button
- Signup button (to create new account)

**Simple analogy**: Like the login page of Facebook or Gmail, but for our attendance system.

---

## 📦 Import Statements

```java
import javax.swing.*;      // For GUI components (JFrame, JButton, etc.)
import java.awt.*;         // For layouts and colors
import java.awt.event.*;   // For handling button clicks
import java.io.*;          // For reading/writing files
```

---

## 🏗️ Class Structure

```java
public class LoginFrame extends JFrame {
```

- **`LoginFrame`** - Name of our class
- **`extends JFrame`** - Inherits from JFrame (makes it a window)
- **Simple analogy**: JFrame is like a blank canvas, we paint our login form on it

---

## 📊 Class Variables (Instance Variables)

```java
private JTextField emailField;
private JPasswordField passwordField;
private JButton loginButton;
private JButton signupButton;
```

**What are these?**
- `JTextField` - A box where users can type text (for email)
- `JPasswordField` - A box where password is hidden with dots (•••)
- `JButton` - Clickable buttons

**Why "private"?**
- Only this class can access them
- Keeps our code organized and secure

---

## 🔧 Constructor Method

```java
public LoginFrame() {
    setTitle("Student Attendance System - Login");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(true);
    
    initComponents();
}
```

**What does each line do?**

1. **`setTitle(...)`** - Sets window title (appears at top)
2. **`setSize(400, 300)`** - Window size: 400 pixels wide, 300 pixels tall
3. **`setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`** - Close program when X is clicked
4. **`setLocationRelativeTo(null)`** - Center the window on screen
5. **`setResizable(true)`** - Allow user to resize the window
6. **`initComponents()`** - Calls method to create all the components

---

## 🎨 initComponents() Method - Building the UI

### Step 1: Create Main Panel
```java
JPanel mainPanel = new JPanel();
mainPanel.setLayout(new GridBagLayout());
mainPanel.setBackground(Color.WHITE);
```

- **JPanel** - Container to hold other components
- **GridBagLayout** - Flexible layout manager (like a grid)
- **White background** - Makes it look clean

### Step 2: GridBagConstraints (Positioning System)
```java
GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(10, 10, 10, 10);
```

- **GridBagConstraints** - Controls where components are placed
- **Insets** - Spacing around components (top, left, bottom, right = 10 pixels each)

### Step 3: Add Title Label
```java
JLabel titleLabel = new JLabel("Lecturer Login");
titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
gbc.gridx = 0;
gbc.gridy = 0;
gbc.gridwidth = 2;
mainPanel.add(titleLabel, gbc);
```

- **JLabel** - Displays text (non-editable)
- **Font** - Arial, Bold, Size 24
- **gridx = 0, gridy = 0** - Position: column 0, row 0 (top-left)
- **gridwidth = 2** - Spans 2 columns

### Step 4: Email Label and Field
```java
// Email Label
gbc.gridwidth = 1;
gbc.gridy = 1;
gbc.gridx = 0;
gbc.anchor = GridBagConstraints.EAST;
mainPanel.add(new JLabel("Email:"), gbc);

// Email Field
emailField = new JTextField(20);
gbc.gridx = 1;
gbc.anchor = GridBagConstraints.WEST;
mainPanel.add(emailField, gbc);
```

**Layout**:
```
Row 1:  [Email:]  [___________________]
         (Label)   (Text Field)
```

- **anchor = EAST** - Align label to the right
- **anchor = WEST** - Align field to the left
- **JTextField(20)** - 20 columns wide

### Step 5: Password Label and Field
```java
// Password Label
gbc.gridy = 2;
gbc.gridx = 0;
gbc.anchor = GridBagConstraints.EAST;
mainPanel.add(new JLabel("Password:"), gbc);

// Password Field
passwordField = new JPasswordField(20);
gbc.gridx = 1;
gbc.anchor = GridBagConstraints.WEST;
mainPanel.add(passwordField, gbc);
```

- **JPasswordField** - Hides password with dots (•••)

### Step 6: Login Button
```java
loginButton = new JButton("Login");
loginButton.setPreferredSize(new Dimension(120, 30));
gbc.gridy = 3;
gbc.gridx = 0;
gbc.gridwidth = 2;
gbc.anchor = GridBagConstraints.CENTER;
mainPanel.add(loginButton, gbc);
```

- **Dimension(120, 30)** - 120 pixels wide, 30 pixels tall
- **gridwidth = 2** - Spans both columns
- **CENTER** - Centered in the row

### Step 7: Signup Button
```java
signupButton = new JButton("Create Account");
signupButton.setPreferredSize(new Dimension(120, 30));
gbc.gridy = 4;
mainPanel.add(signupButton, gbc);
```

### Step 8: Add Event Listeners
```java
loginButton.addActionListener(e -> handleLogin());
signupButton.addActionListener(e -> openSignupFrame());
passwordField.addActionListener(e -> handleLogin());
```

**What are event listeners?**
- They "listen" for button clicks or key presses
- **`e -> handleLogin()`** - When clicked, call `handleLogin()` method
- **Lambda expression** - Modern Java shortcut

**Why password field has listener?**
- Pressing Enter in password field triggers login
- User convenience!

---

## 🔐 handleLogin() Method - Authentication Logic

```java
private void handleLogin() {
    String email = emailField.getText().trim();
    String password = new String(passwordField.getPassword()).trim();
```

**Step 1: Get user input**
- **`getText()`** - Gets text from email field
- **`getPassword()`** - Gets password (returns char array for security)
- **`trim()`** - Removes extra spaces

```java
    if (email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter email and password",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
```

**Step 2: Validation**
- Check if fields are empty
- **`JOptionPane.showMessageDialog()`** - Shows popup message
- **`return`** - Stop execution if validation fails

```java
    if (authenticateLecturer(email, password)) {
        JOptionPane.showMessageDialog(this, "Login Successful!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        new DashboardFrame(email).setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid email or password",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

**Step 3: Authenticate**
- Call `authenticateLecturer()` method
- **If successful**:
  - Show success message
  - Close login window (`dispose()`)
  - Open dashboard window
- **If failed**:
  - Show error message

---

## 🔍 authenticateLecturer() Method - File Reading

```java
private boolean authenticateLecturer(String email, String password) {
    File file = new File("data/lecturers.txt");
    if (!file.exists()) {
        return false;
    }
```

**Step 1: Check if file exists**
- **`File`** - Represents the lecturers.txt file
- **`exists()`** - Returns true if file exists
- **Return false** if file doesn't exist (no users registered)

```java
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String storedEmail = parts[1].trim();
                String storedPassword = parts[2].trim();
                if (storedEmail.equals(email) && storedPassword.equals(password)) {
                    return true;
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return false;
}
```

**Step 2: Read file and check credentials**

**File format**: `Name,Email,Password`
Example: `John Doe,john@email.com,pass123`

**How it works**:
1. **`BufferedReader`** - Reads file line by line
2. **`readLine()`** - Reads one line
3. **`split(",")`** - Splits line by comma
   - `parts[0]` = Name
   - `parts[1]` = Email
   - `parts[2]` = Password
4. **Compare** stored email/password with user input
5. **Return true** if match found
6. **Return false** if no match

**Try-with-resources**:
- Automatically closes the file after reading
- Prevents memory leaks

---

## 🚪 openSignupFrame() Method

```java
private void openSignupFrame() {
    this.dispose();
    new SignupFrame().setVisible(true);
}
```

**What it does**:
1. Close current login window
2. Open signup window

---

## 🔄 Complete Flow Diagram

```
User opens app
    ↓
LoginFrame appears
    ↓
User enters email & password
    ↓
Clicks "Login" button
    ↓
handleLogin() called
    ↓
Validates input (not empty?)
    ↓
authenticateLecturer() called
    ↓
Reads data/lecturers.txt
    ↓
Compares credentials
    ↓
Match found? → Yes → Open Dashboard
             → No  → Show error message
```

---

## 🎓 Key Concepts Used

1. **Inheritance** - `extends JFrame`
2. **Event Handling** - Button click listeners
3. **File I/O** - Reading from text file
4. **String manipulation** - `split()`, `trim()`, `equals()`
5. **GUI Layout** - GridBagLayout
6. **Exception Handling** - try-catch blocks
7. **Lambda Expressions** - `e -> method()`

---

## 📁 File Structure Used

**data/lecturers.txt**:
```
Name,Email,Password
John Doe,john@email.com,password123
Jane Smith,jane@email.com,pass456
```

---

## 💡 Important Notes

1. **Security Warning**: Passwords are stored in plain text (not secure for real applications!)
2. **File location**: `data/lecturers.txt` must exist
3. **Case sensitive**: Email and password must match exactly
4. **No database**: Uses simple text file storage

---

## 🎯 Summary

**In Simple Words**:
- Creates a login window
- Takes email and password from user
- Reads `lecturers.txt` file
- Checks if credentials match
- Opens dashboard if correct, shows error if wrong

**Total Lines**: 141 lines
**Complexity**: Medium ⭐⭐⭐
**Role**: User Authentication & Entry Point
