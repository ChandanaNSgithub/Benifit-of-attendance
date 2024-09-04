package com.example.myapplication;

public class StudentForm {
    private String Student;
    private String USN;
    private String Start_date;
    private String End_date;
    private String Reason;
    private String Certificate;
    private String Coordinator;

    public StudentForm() {
    }

    public StudentForm(String Student, String USN, String Start_date, String End_date, String Reason, String Certificate, String Coordinator) {
        this.Student = Student;
        this.USN = USN;
        this.Start_date = Start_date;
        this.End_date = End_date;
        this.Reason = Reason;
        this.Certificate = Certificate;
        this.Coordinator = Coordinator;
    }

    public String getStudent() {
        return Student;
    }

    public void setStudent(String student) {
        Student = student;
    }

    public String getUSN() {
        return USN;
    }

    public void setUSN(String USN) {
        this.USN = USN;
    }

    public String getStart_date() {
        return Start_date;
    }

    public void setStart_date(String start_date) {
        Start_date = start_date;
    }

    public String getEnd_date() {
        return End_date;
    }

    public void setEnd_date(String end_date) {
        End_date = end_date;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getCertificate() {
        return Certificate;
    }

    public void setCertificate(String certificate) {
        Certificate = certificate;
    }

    public String getCoordinator() {
        return Coordinator;
    }

    public void setCoordinator(String coordinator) {
        Coordinator = coordinator;
    }
}