const API_BASE = "/api";

// Helper to show messages
function showMessage(elementId, text, isError = false) {
    const el = document.getElementById(elementId);
    if (!el) return;
    el.textContent = text;
    el.className = isError ? 'message error' : 'message success';
    el.style.display = 'block';
    setTimeout(() => {
        el.style.display = 'none';
    }, 3000);
}

// Login Function
async function handleLogin(event) {
    event.preventDefault();
    const user = document.getElementById('username').value;
    const pass = document.getElementById('password').value;

    try {
        const response = await fetch(`${API_BASE}/login`, {
            method: 'POST',
            body: JSON.stringify({ username: user, password: pass }),
        });
        const data = await response.json();

        if (data.status === 'success') {
            window.location.href = 'dashboard.html';
        } else {
            showMessage('login-msg', data.message || 'Login failed', true);
        }
    } catch (e) {
        showMessage('login-msg', 'Server error', true);
    }
}

// Add Student Function
async function handleAddStudent(event) {
    event.preventDefault();
    const rollNo = document.getElementById('rollNo').value;
    const name = document.getElementById('name').value;
    const dept = document.getElementById('dept').value;

    try {
        const response = await fetch(`${API_BASE}/students`, {
            method: 'POST',
            body: JSON.stringify({ rollNo, name, dept }),
        });
        const data = await response.json();

        if (data.status === 'success') {
            showMessage('student-msg', 'Student added successfully!');
            event.target.reset();
        } else {
            showMessage('student-msg', 'Failed to add student', true);
        }
    } catch (e) {
        showMessage('student-msg', 'Error connecting to server', true);
    }
}

// Mark Attendance Function
async function handleAttendance(event) {
    event.preventDefault();
    const rollNo = document.getElementById('att-rollNo').value;
    const status = document.getElementById('status').value;

    try {
        const response = await fetch(`${API_BASE}/attendance`, {
            method: 'POST',
            body: JSON.stringify({ rollNo, status }),
        });
        const data = await response.json();

        if (data.status === 'success') {
            showMessage('att-msg', 'Attendance marked!');
            event.target.reset();
        } else {
            showMessage('att-msg', 'Failed to mark attendance', true);
        }
    } catch (e) {
        showMessage('att-msg', 'Error connecting to server', true);
    }
}

// Attach listeners based on page
document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    if (loginForm) loginForm.addEventListener('submit', handleLogin);

    const studentForm = document.getElementById('add-student-form');
    if (studentForm) studentForm.addEventListener('submit', handleAddStudent);

    const attForm = document.getElementById('attendance-form');
    if (attForm) attForm.addEventListener('submit', handleAttendance);
});
