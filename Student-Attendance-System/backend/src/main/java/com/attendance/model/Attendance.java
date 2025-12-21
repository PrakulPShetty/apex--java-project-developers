package com.attendance.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rollNumber;
    private LocalDate date;
    private boolean present;

    public Attendance() {}

    public Attendance(String rollNumber, LocalDate date, boolean present) {
        this.rollNumber = rollNumber;
        this.date = date;
        this.present = present;
    }

    public Long getId() { return id; }
    public String getRollNumber() { return rollNumber; }
    public LocalDate getDate() { return date; }
    public boolean isPresent() { return present; }

    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setPresent(boolean present) { this.present = present; }
}
