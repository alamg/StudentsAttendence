package com.cse.cou.alamgir.booksstore.Model;

public class Student {
    private String studentName;
    private String studentRoll;
    private int totalAttandene;
    private int percentage;
    private int lecture;

    public Student() {
    }

    public Student(String studentName, String studentRoll, int totalAttandene, int percentage, int lecture) {
        this.studentName = studentName;
        this.studentRoll = studentRoll;
        this.totalAttandene = totalAttandene;
        this.percentage = percentage;
        this.lecture = lecture;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRoll() {
        return studentRoll;
    }

    public void setStudentRoll(String studentRoll) {
        this.studentRoll = studentRoll;
    }

    public int getTotalAttandene() {
        return totalAttandene;
    }

    public void setTotalAttandene(int totalAttandene) {
        this.totalAttandene = totalAttandene;
    }

    public int getPercentage() {
        return percentage=(totalAttandene*100)/lecture;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getLecture() {
        return lecture;
    }

    public void setLecture(int lecture) {
        this.lecture = lecture;
    }
}
