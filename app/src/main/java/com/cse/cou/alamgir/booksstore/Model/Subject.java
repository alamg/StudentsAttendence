package com.cse.cou.alamgir.booksstore.Model;

public class Subject {
    private  String subjectName;
    private  String semester;

    public Subject() {
    }

    public Subject(String subjectName, String semester) {
        this.subjectName = subjectName;
        this.semester = semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
