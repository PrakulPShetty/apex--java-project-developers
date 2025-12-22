import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Signup Frame - Lecturer Registration
 */
public class SignupFrame extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton signupButton;
    private JButton backButton;

    public SignupFrame() {
        setTitle("Student Attendance System - Signup");
        setSize(400, 350);
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
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Name Label
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Name:"), gbc);

        // Name Field
        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(nameField, gbc);

        // Email Label
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Email:"), gbc);

        // Email Field
        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(emailField, gbc);

        // Password Label
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Password:"), gbc);

        // Password Field
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordField, gbc);

        // Signup Button
        signupButton = new JButton("Sign Up");
        signupButton.setPreferredSize(new Dimension(120, 30));
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(signupButton, gbc);

        // Back Button
        backButton = new JButton("Back to Login");
        backButton.setPreferredSize(new Dimension(120, 30));
        gbc.gridy = 5;
        mainPanel.add(backButton, gbc);

        add(mainPanel);

        // Event Listeners
        signupButton.addActionListener(e -> handleSignup());
        backButton.addActionListener(e -> backToLogin());
    }

    private void handleSignup() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (emailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email already registered",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (saveLecturer(name, email, password)) {
            JOptionPane.showMessageDialog(this, "Account created successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            backToLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Error creating account",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean emailExists(String email) {
        File file = new File("data/lecturers.txt");
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].trim().equals(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean saveLecturer(String name, String email, String password) {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter("data/lecturers.txt", true)) {
            writer.write(name + "," + email + "," + password + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void backToLogin() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
