import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Add Student Frame - Student Registration
 */
public class AddStudentFrame extends JFrame {
    private String lecturerEmail;
    private JTextField studentIdField;
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField semesterField;
    private JButton saveButton;
    private JButton backButton;

    public AddStudentFrame(String email) {
        this.lecturerEmail = email;
        setTitle("Add Student");
        setSize(450, 400);
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
        JLabel titleLabel = new JLabel("Add New Student");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Student ID
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Student ID:"), gbc);

        studentIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(studentIdField, gbc);

        // Name
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Name:"), gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(nameField, gbc);

        // Department
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Department:"), gbc);

        departmentField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(departmentField, gbc);

        // Semester
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Semester:"), gbc);

        semesterField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(semesterField, gbc);

        // Save Button
        saveButton = new JButton("Save Student");
        saveButton.setPreferredSize(new Dimension(150, 35));
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(saveButton, gbc);

        // Back Button
        backButton = new JButton("Back to Dashboard");
        backButton.setPreferredSize(new Dimension(150, 35));
        gbc.gridy = 6;
        mainPanel.add(backButton, gbc);

        add(mainPanel);

        // Event Listeners
        saveButton.addActionListener(e -> handleSave());
        backButton.addActionListener(e -> backToDashboard());
    }

    private void handleSave() {
        String studentId = studentIdField.getText().trim();
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();
        String semester = semesterField.getText().trim();

        if (studentId.isEmpty() || name.isEmpty() || department.isEmpty() || semester.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Integer.parseInt(semester);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Semester must be a number",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (studentIdExists(studentId)) {
            JOptionPane.showMessageDialog(this, "Student ID already exists",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (saveStudent(studentId, name, department, semester)) {
            JOptionPane.showMessageDialog(this, "Student added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Error adding student",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean studentIdExists(String studentId) {
        File file = new File("data/students.txt");
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].trim().equals(studentId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean saveStudent(String studentId, String name, String department, String semester) {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter("data/students.txt", true)) {
            writer.write(studentId + "," + name + "," + department + "," + semester + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void clearFields() {
        studentIdField.setText("");
        nameField.setText("");
        departmentField.setText("");
        semesterField.setText("");
    }

    private void backToDashboard() {
        this.dispose();
        new DashboardFrame(lecturerEmail).setVisible(true);
    }
}
