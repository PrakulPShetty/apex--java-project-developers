import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * View Attendance Frame - Display Attendance Records
 */
public class ViewAttendanceFrame extends JFrame {
    private String lecturerEmail;
    private JTextField dateField;
    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;
    private JButton backButton;

    public ViewAttendanceFrame(String email) {
        this.lecturerEmail = email;
        setTitle("View Attendance");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top Panel - Title and Search
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("View Attendance Records", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField(15);
        searchPanel.add(dateField);
        searchButton = new JButton("Search");
        searchPanel.add(searchButton);
        topPanel.add(searchPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center Panel - Table
        String[] columns = { "Student ID", "Student Name", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        attendanceTable = new JTable(tableModel);
        attendanceTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel - Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        backButton = new JButton("Back to Dashboard");
        backButton.setPreferredSize(new Dimension(150, 35));
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Event Listeners
        searchButton.addActionListener(e -> handleSearch());
        backButton.addActionListener(e -> backToDashboard());
    }

    private void handleSearch() {
        String date = dateField.getText().trim();

        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a date",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Clear existing data
        tableModel.setRowCount(0);

        // Load student names map
        Map<String, String> studentNames = loadStudentNames();

        // Load attendance records
        File file = new File("data/attendance.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "No attendance records found",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

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

        if (recordCount == 0) {
            JOptionPane.showMessageDialog(this, "No attendance records found for " + date,
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

    private Map<String, String> loadStudentNames() {
        Map<String, String> studentNames = new HashMap<>();
        File file = new File("data/students.txt");

        if (!file.exists()) {
            return studentNames;
        }

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

    private void backToDashboard() {
        this.dispose();
        new DashboardFrame(lecturerEmail).setVisible(true);
    }
}
