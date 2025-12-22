import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

public class AttendanceSystem {

    private static final int PORT = 8000;
    private static final String BACKEND_DIR = "backend";
    // Adjust frontend path relative to where java is run.
    // We assume running from 'Student-Attendance-System' root or similar, but
    // let's try to be robust. If running from root: "frontend"
    private static final String FRONTEND_DIR = "frontend"; 

    public static void main(String[] args) throws IOException {
        // Ensure backend directory exists (sanity check, though we created it)
        new File(BACKEND_DIR).mkdirs();
        ensureFileExists(BACKEND_DIR + "/users.txt");
        ensureFileExists(BACKEND_DIR + "/students.txt");
        ensureFileExists(BACKEND_DIR + "/attendance.txt");

        // Create HTTP Server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // API Contexts
        server.createContext("/api/login", new LoginHandler());
        server.createContext("/api/students", new StudentHandler()); // POST to add, GET to list if needed
        server.createContext("/api/attendance", new AttendanceHandler());
        
        // Static File Serving (Frontend)
        server.createContext("/", new StaticFileHandler());

        server.setExecutor(null); // default executor
        System.out.println("Server started on port " + PORT);
        System.out.println("Open http://localhost:" + PORT + "/login.html to access the system.");
        server.start();

        // Console Menu for Server Management
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Server Menu ---");
            System.out.println("1. View Logic Logs");
            System.out.println("2. Stop Server and Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();
            if ("2".equals(choice)) {
                System.out.println("Stopping server...");
                server.stop(0);
                break;
            } else if ("1".equals(choice)) {
                System.out.println("Server is running. Logs will appear in console on request.");
            } else {
                System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void ensureFileExists(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            if (path.endsWith("users.txt")) {
                // Add default admin if creating fresh
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("admin,admin123");
                    writer.newLine();
                }
            }
        }
    }

    // --- Handlers ---

    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                // Simple parsing "username=...&password=..." or JSON. Let's assume JSON for cleaner JS, 
                // but if manual parsing is safer for constraints:
                // Let's stick to JSON for "Professional" feel, parsing manually in Java.
                String user = extractJsonValue(body, "username");
                String pass = extractJsonValue(body, "password");

                boolean authenticated = checkCredentials(user, pass);
                String response = authenticated ? "{\"status\":\"success\"}" : "{\"status\":\"error\", \"message\":\"Invalid credentials\"}";
                
                sendResponse(exchange, authenticated ? 200 : 401, response);
            } else {
                sendResponse(exchange, 405, "Method Not Allowed");
            }
        }

        private boolean checkCredentials(String user, String pass) {
            try (BufferedReader br = new BufferedReader(new FileReader(BACKEND_DIR + "/users.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[0].trim().equals(user) && parts[1].trim().equals(pass)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    static class StudentHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String rollNo = extractJsonValue(body, "rollNo");
                String name = extractJsonValue(body, "name");
                String dept = extractJsonValue(body, "dept");

                if (rollNo != null && name != null && dept != null) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(BACKEND_DIR + "/students.txt", true))) {
                        bw.write(rollNo + "," + name + "," + dept);
                        bw.newLine();
                    }
                    sendResponse(exchange, 200, "{\"status\":\"success\"}");
                } else {
                    sendResponse(exchange, 400, "{\"status\":\"error\", \"message\":\"Missing fields\"}");
                }
            } else {
                 sendResponse(exchange, 405, "Method Not Allowed");
            }
        }
    }

    static class AttendanceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
             if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String rollNo = extractJsonValue(body, "rollNo");
                String status = extractJsonValue(body, "status");
                String date = LocalDate.now().toString();

                if (rollNo != null && status != null) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(BACKEND_DIR + "/attendance.txt", true))) {
                        bw.write(rollNo + "," + date + "," + status);
                        bw.newLine();
                    }
                    sendResponse(exchange, 200, "{\"status\":\"success\"}");
                } else {
                     sendResponse(exchange, 400, "{\"status\":\"error\", \"message\":\"Missing fields\"}");
                }
            } else {
                sendResponse(exchange, 405, "Method Not Allowed");
            }
        }
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/login.html"; // Default page

            // SECURITY: Prevent directory traversal
            if (path.contains("..")) {
                sendResponse(exchange, 403, "Forbidden");
                return;
            }

            File file = new File(FRONTEND_DIR + path);
            if (file.exists() && !file.isDirectory()) {
                String contentType = "text/html";
                if (path.endsWith(".css")) contentType = "text/css";
                else if (path.endsWith(".js")) contentType = "application/javascript";
                
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, file.length());
                try (OutputStream os = exchange.getResponseBody(); FileInputStream fs = new FileInputStream(file)) {
                    fs.transferTo(os);
                }
            } else {
                sendResponse(exchange, 404, "Not Found: " + path);
            }
        }
    }

    // --- Helpers ---

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        // Add CORS for development comfort if we opened HTML file directly (though we are serving it now)
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    // Quick and dirty JSON value extractor purely with string manipulation to avoid libraries
    private static String extractJsonValue(String json, String key) {
        String search = "\"" + key + "\"";
        int start = json.indexOf(search);
        if (start == -1) return null;
        
        start = json.indexOf(":", start) + 1;
        while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == '"')) {
            start++;
        }
        
        int end = json.indexOf("\"", start);
        if (end == -1) end = json.indexOf("}", start); // Handle if it's the last element without quotes (rare in strict json but possible in loose) - basic failover
        if (json.charAt(end-1) == ' ' || json.charAt(end-1) == '\n' || json.charAt(end-1) == '\r') { // trim end
             // scan back
             int walk = end -1;
             while(walk > start && (json.charAt(walk)==' ' || json.charAt(walk)=='"')) walk--;
             end = walk + 1;
        }

        // More robust:
        // Key is "key": "value"
        // Find "key"
        // Find :
        // Find first " after :
        // Find next " after that
        try {
            int keyIndex = json.indexOf("\"" + key + "\"");
            if (keyIndex == -1) return null;
            int colonIndex = json.indexOf(":", keyIndex);
            int firstQuote = json.indexOf("\"", colonIndex);
            int secondQuote = json.indexOf("\"", firstQuote + 1);
            return json.substring(firstQuote + 1, secondQuote);
        } catch (Exception e) {
            return null;
        }
    }
}
