import javax.swing.*;
import java.awt.*;

/**
 * Dashboard Frame - Main Navigation
 */
public class DashboardFrame extends JFrame {
    private String lecturerEmail;

    public DashboardFrame(String email) {
        this.lecturerEmail = email;
        setTitle("Student Attendance System - Dashboard");
        setSize(500, 400);
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
        gbc.insets = new Insets(15, 15, 15, 15);

        // Title
        JLabel titleLabel = new JLabel("Lecturer Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + lecturerEmail);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        mainPanel.add(welcomeLabel, gbc);

        // Add Student Button
        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.setPreferredSize(new Dimension(200, 40));
        addStudentBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 2;
        mainPanel.add(addStudentBtn, gbc);

        // Mark Attendance Button
        JButton markAttendanceBtn = new JButton("Mark Attendance");
        markAttendanceBtn.setPreferredSize(new Dimension(200, 40));
        markAttendanceBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 3;
        mainPanel.add(markAttendanceBtn, gbc);

        // View Attendance Button
        JButton viewAttendanceBtn = new JButton("View Attendance");
        viewAttendanceBtn.setPreferredSize(new Dimension(200, 40));
        viewAttendanceBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 4;
        mainPanel.add(viewAttendanceBtn, gbc);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setPreferredSize(new Dimension(200, 40));
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        gbc.gridy = 5;
        mainPanel.add(logoutBtn, gbc);

        add(mainPanel);

        // Event Listeners
        addStudentBtn.addActionListener(e -> openAddStudent());
        markAttendanceBtn.addActionListener(e -> openMarkAttendance());
        viewAttendanceBtn.addActionListener(e -> openViewAttendance());
        logoutBtn.addActionListener(e -> logout());
    }

    private void openAddStudent() {
        this.dispose();
        new AddStudentFrame(lecturerEmail).setVisible(true);
    }

    private void openMarkAttendance() {
        this.dispose();
        new MarkAttendanceFrame(lecturerEmail).setVisible(true);
    }

    private void openViewAttendance() {
        this.dispose();
        new ViewAttendanceFrame(lecturerEmail).setVisible(true);
    }

    private void logout() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
