package app;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private String studentCode;
    private String name;
    private int yob;
    private String address;
    private double gpa;

    public Student(int id, String studentCode, String name, int yob, String address, double gpa) {
        this.gpa = gpa;
        this.id = id;
        this.studentCode = studentCode;
        this.name = name;
        this.address = address;
        this.yob = yob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getYob() {
        return yob;
    }

    public void setYob(int yob) {
        this.yob = yob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    @Override
    public String toString() {
        return id + " " + studentCode + " " + name + " " + yob + " " + address + " " + gpa;
    }
}
