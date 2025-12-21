import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;

public class AttendanceApp {

    // ===== DATABASE CONFIG =====
    static final String DB_URL = "jdbc:postgresql://localhost:5432/attendance_db";
    static final String DB_USER = "postgres";
    static final String DB_PASS = "postgres123";

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/signup", AttendanceApp::signup);
        server.createContext("/login", AttendanceApp::login);
        server.createContext("/addStudent", AttendanceApp::addStudent);
        server.createContext("/markAttendance", AttendanceApp::markAttendance);

        server.start();
        System.out.println("Backend running at http://localhost:8080");
    }

    // ===== DB CONNECTION =====
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // ===== SIGNUP =====
    static void signup(HttpExchange ex) throws IOException {
        enableCORS(ex);

        String body = new String(ex.getRequestBody().readAllBytes());
        String[] data = body.split(",");

        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username, password) VALUES (?, ?)");
            ps.setString(1, data[0]);
            ps.setString(2, data[1]);
            ps.executeUpdate();
            respond(ex, "SIGNUP_SUCCESS");
        } catch (Exception e) {
            respond(ex, "ERROR");
        }
    }

    // ===== LOGIN =====
    static void login(HttpExchange ex) throws IOException {
        enableCORS(ex);

        String body = new String(ex.getRequestBody().readAllBytes());
        String[] data = body.split(",");

        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, data[0]);
            ps.setString(2, data[1]);

            ResultSet rs = ps.executeQuery();
            respond(ex, rs.next() ? "LOGIN_SUCCESS" : "INVALID");
        } catch (Exception e) {
            respond(ex, "ERROR");
        }
    }

    // ===== ADD STUDENT =====
    static void addStudent(HttpExchange ex) throws IOException {
        enableCORS(ex);

        String body = new String(ex.getRequestBody().readAllBytes());
        String[] d = body.split(",");

        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO students(name, roll_no, department) VALUES (?, ?, ?)");
            ps.setString(1, d[0]);
            ps.setString(2, d[1]);
            ps.setString(3, d[2]);
            ps.executeUpdate();
            respond(ex, "STUDENT_ADDED");
        } catch (Exception e) {
            respond(ex, "ERROR");
        }
    }

    // ===== MARK ATTENDANCE =====
    static void markAttendance(HttpExchange ex) throws IOException {
        enableCORS(ex);

        String body = new String(ex.getRequestBody().readAllBytes());
        String[] d = body.split(",");

        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO attendance(roll_no, date, present) VALUES (?, CURRENT_DATE, ?)");
            ps.setString(1, d[0]);
            ps.setBoolean(2, Boolean.parseBoolean(d[1]));
            ps.executeUpdate();
            respond(ex, "ATTENDANCE_MARKED");
        } catch (Exception e) {
            respond(ex, "ERROR");
        }
    }

    // ===== RESPONSE =====
    static void respond(HttpExchange ex, String msg) throws IOException {
        ex.sendResponseHeaders(200, msg.length());
        ex.getResponseBody().write(msg.getBytes());
        ex.close();
    }

    // ===== CORS SUPPORT =====
    static void enableCORS(HttpExchange ex) {
        ex.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        ex.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
    }
}
