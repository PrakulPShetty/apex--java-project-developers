// Load environment variables from .env (project root)
require("dotenv").config({ path: "../.env" });

const express = require("express");
const path = require("path");
const { exec } = require("child_process");

const app = express();

// ===== MIDDLEWARE =====
app.use(express.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, "public")));

// ===== EJS SETUP =====
app.set("view engine", "ejs");
app.set("views", path.join(__dirname, "views"));

/*
 * HOME → LOGIN PAGE
 */
app.get("/", (req, res) => {
    res.render("login");
});

/*
 * SIGNUP PAGE
 */
app.get("/signup", (req, res) => {
    res.render("signup");
});

/*
 * DASHBOARD PAGE
 */
app.get("/dashboard", (req, res) => {
    res.render("dashboard");
});

/*
 * ATTENDANCE PAGE
 */
app.get("/attendance", (req, res) => {
    res.render("attendance");
});

/*
 * SIGNUP LOGIC
 */
app.post("/signup", (req, res) => {
    const { username, password } = req.body;

    const cmd = `java -cp ../java-backend AttendanceDB signup ${username} ${password}`;

    exec(cmd, (err, stdout) => {
        if (stdout.includes("SIGNUP_SUCCESS")) {
            res.render("login", { msg: "Signup successful. Please login." });
        } else {
            res.render("signup", { msg: "Signup failed." });
        }
    });
});

/*
 * LOGIN LOGIC
 */
app.post("/login", (req, res) => {
    const { username, password } = req.body;

    const cmd = `java -cp ../java-backend AttendanceDB login ${username} ${password}`;

    exec(cmd, (err, stdout) => {
        if (stdout.includes("LOGIN_SUCCESS")) {
            res.render("dashboard");
        } else {
            res.render("login", { msg: "Invalid credentials" });
        }
    });
});

/*
 * ADD STUDENT
 */
app.post("/addStudent", (req, res) => {
    const { name, roll, department } = req.body;

    const cmd = `java -cp ../java-backend AttendanceDB addStudent ${name} ${roll} ${department}`;

    exec(cmd, (err, stdout) => {
        if (stdout.includes("STUDENT_ADDED")) {
            res.render("attendance", { msg: "Student added successfully" });
        } else {
            res.render("attendance", { msg: "Error adding student" });
        }
    });
});

/*
 * MARK ATTENDANCE
 */
app.post("/markAttendance", (req, res) => {
    const { roll, present } = req.body;

    const cmd = `java -cp ../java-backend AttendanceDB markAttendance ${roll} ${present}`;

    exec(cmd, (err, stdout) => {
        if (stdout.includes("ATTENDANCE_MARKED")) {
            res.render("attendance", { msg: "Attendance marked successfully" });
        } else {
            res.render("attendance", { msg: "Attendance failed" });
        }
    });
});

// ===== START SERVER =====
app.listen(3000, () => {
    console.log("Server running at http://localhost:3000");
});
