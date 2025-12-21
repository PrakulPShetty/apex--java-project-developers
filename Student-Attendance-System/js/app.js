function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const msg = document.getElementById("msg");

    if (username === "" || password === "") {
        msg.textContent = "Please fill all fields";
        msg.className = "message error";
        return;
    }

    fetch("http://localhost:8080/login", {
        method: "POST",
        body: username + "," + password
    })
    .then(res => res.text())
    .then(data => {
        if (data === "LOGIN_SUCCESS") {
            msg.textContent = "Login successful";
            msg.className = "message success";
            setTimeout(() => {
                window.location.href = "dashboard.html";
            }, 1000);
        } else {
            msg.textContent = "Invalid username or password";
            msg.className = "message error";
        }
    })
    .catch(() => {
        msg.textContent = "Server error";
        msg.className = "message error";
    });
}

function signup() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const msg = document.getElementById("msg");

    if (username === "" || password === "") {
        msg.textContent = "Please fill all fields";
        msg.className = "message error";
        return;
    }

    fetch("http://localhost:8080/signup", {
        method: "POST",
        body: username + "," + password
    })
    .then(res => res.text())
    .then(data => {
        if (data === "SIGNUP_SUCCESS") {
            msg.textContent = "Signup successful! Redirecting...";
            msg.className = "message success";
            setTimeout(() => {
                window.location.href = "login.html";
            }, 1200);
        } else {
            msg.textContent = "Signup failed";
            msg.className = "message error";
        }
    })
    .catch(() => {
        msg.textContent = "Server error";
        msg.className = "message error";
    });
}


function goToAttendance() {
    window.location.href = "attendance.html";
}

function logout() {
    window.location.href = "login.html";
}

function addStudent() {
    const name = document.getElementById("sname").value;
    const roll = document.getElementById("roll").value;
    const dept = document.getElementById("dept").value;
    const msg = document.getElementById("studentMsg");

    if (name === "" || roll === "" || dept === "") {
        msg.textContent = "Please fill all fields";
        msg.className = "message error";
        return;
    }

    fetch("http://localhost:8080/addStudent", {
        method: "POST",
        body: name + "," + roll + "," + dept
    })
    .then(res => res.text())
    .then(data => {
        if (data === "STUDENT_ADDED") {
            msg.textContent = "Student added successfully";
            msg.className = "message success";
        } else {
            msg.textContent = "Error adding student";
            msg.className = "message error";
        }
    })
    .catch(() => {
        msg.textContent = "Server error";
        msg.className = "message error";
    });
}

function markAttendance() {
    const roll = document.getElementById("aroll").value;
    const present = document.getElementById("present").value;
    const msg = document.getElementById("attMsg");

    if (roll === "") {
        msg.textContent = "Enter roll number";
        msg.className = "message error";
        return;
    }

    fetch("http://localhost:8080/markAttendance", {
        method: "POST",
        body: roll + "," + present
    })
    .then(res => res.text())
    .then(data => {
        if (data === "ATTENDANCE_MARKED") {
            msg.textContent = "Attendance marked successfully";
            msg.className = "message success";
        } else {
            msg.textContent = "Error marking attendance";
            msg.className = "message error";
        }
    })
    .catch(() => {
        msg.textContent = "Server error";
        msg.className = "message error";
    });
}

function goBack() {
    window.location.href = "dashboard.html";
}
