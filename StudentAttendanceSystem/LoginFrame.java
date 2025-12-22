import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Login Frame - Lecturer Authentication
 */
public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginFrame() {
        setTitle("Student Attendance System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Lecturer Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

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

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 30));
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        // Signup Button
        signupButton = new JButton("Create Account");
        signupButton.setPreferredSize(new Dimension(120, 30));
        gbc.gridy = 4;
        mainPanel.add(signupButton, gbc);

        add(mainPanel);

        // Event Listeners
        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> openSignupFrame());

        // Enter key on password field triggers login
        passwordField.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter email and password",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

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

    private boolean authenticateLecturer(String email, String password) {
        File file = new File("data/lecturers.txt");
        if (!file.exists()) {
            return false;
        }

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

    private void openSignupFrame() {
        this.dispose();
        new SignupFrame().setVisible(true);
    }
}
