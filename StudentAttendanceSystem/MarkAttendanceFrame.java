import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Mark Attendance Frame - Record Daily Attendance
 */
public class MarkAttendanceFrame extends JFrame {
    private String lecturerEmail;
    private JTextField dateField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton saveButton;
    private JButton backButton;

    public MarkAttendanceFrame(String email) {
        this.lecturerEmail = email;
        setTitle("Mark Attendance");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();
        loadStudents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top Panel - Title and Date
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Mark Attendance", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        datePanel.setBackground(Color.WHITE);
        datePanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField(15);
        dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        datePanel.add(dateField);
        topPanel.add(datePanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center Panel - Table
        String[] columns = { "Student ID", "Name", "Department", "Semester", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Status column is editable
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        studentTable.getColumnModel().getColumn(4).setCellEditor(
                new DefaultCellEditor(new JComboBox<>(new String[] { "Present", "Absent" })));

        JScrollPane scrollPane = new JScrollPane(studentTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel - Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        saveButton = new JButton("Save Attendance");
        saveButton.setPreferredSize(new Dimension(150, 35));
        buttonPanel.add(saveButton);

        backButton = new JButton("Back to Dashboard");
        backButton.setPreferredSize(new Dimension(150, 35));
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Event Listeners
        saveButton.addActionListener(e -> handleSave());
        backButton.addActionListener(e -> backToDashboard());
    }

    private void loadStudents() {
        File file = new File("data/students.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "No students found. Please add students first.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    tableModel.addRow(new Object[] {
                            parts[0].trim(), // Student ID
                            parts[1].trim(), // Name
                            parts[2].trim(), // Department
                            parts[3].trim(), // Semester
                            "Present" // Default status
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSave() {
        String date = dateField.getText().trim();

        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a date",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No students to mark attendance",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

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

    private void backToDashboard() {
        this.dispose();
        new DashboardFrame(lecturerEmail).setVisible(true);
    }
}
