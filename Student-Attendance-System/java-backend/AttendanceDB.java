import java.sql.*;

/*
 * AttendanceDB.java
 * ------------------
 * This Java program handles all database operations.
 * It reads database credentials from environment variables (.env).
 * It is executed from Node.js using child_process.exec().
 */
public class AttendanceDB {

    // ===== DATABASE CONFIG FROM ENV =====
    static final String DB_URL  = System.getenv("DB_URL");
    static final String DB_USER = System.getenv("DB_USER");
    static final String DB_PASS = System.getenv("DB_PASS");

    /*
     * args[0] -> operation name
     * args[1..] -> parameters
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("NO_OPERATION");
            return;
        }

        String operation = args[0];

        try {
            switch (operation) {

                case "signup":
                    signup(args[1], args[2]);
                    break;

                case "login":
                    login(args[1], args[2]);
                    break;

                case "addStudent":
                    addStudent(args[1], args[2], args[3]);
                    break;

                case "markAttendance":
                    markAttendance(args[1], args[2]);
                    break;

                default:
                    System.out.println("INVALID_OPERATION");
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }

    /*
     * Create DB connection
     */
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    /*
     * SIGNUP
     */
    static void signup(String username, String password) {
        try (Connection con = getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username, password) VALUES (?, ?)"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            System.out.println("SIGNUP_SUCCESS");

        } catch (Exception e) {
            System.out.println("SIGNUP_FAILED");
        }
    }

    /*
     * LOGIN
     */
    static void login(String username, String password) {
        try (Connection con = getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("LOGIN_SUCCESS");
            } else {
                System.out.println("INVALID_CREDENTIALS");
            }

        } catch (Exception e) {
            System.out.println("LOGIN_FAILED");
        }
    }

    /*
     * ADD STUDENT
     */
    static void addStudent(String name, String rollNo, String department) {
        try (Connection con = getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO students(name, roll_no, department) VALUES (?, ?, ?)"
            );
            ps.setString(1, name);
            ps.setString(2, rollNo);
            ps.setString(3, department);
            ps.executeUpdate();

            System.out.println("STUDENT_ADDED");

        } catch (Exception e) {
            System.out.println("STUDENT_ADD_FAILED");
        }
    }

    /*
     * MARK ATTENDANCE
     */
    static void markAttendance(String rollNo, String present) {
        try (Connection con = getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO attendance(roll_no, date, present) VALUES (?, CURRENT_DATE, ?)"
            );
            ps.setString(1, rollNo);
            ps.setBoolean(2, Boolean.parseBoolean(present));
            ps.executeUpdate();

            System.out.println("ATTENDANCE_MARKED");

        } catch (Exception e) {
            System.out.println("ATTENDANCE_FAILED");
        }
    }
}
