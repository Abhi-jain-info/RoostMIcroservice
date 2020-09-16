package com.managment.student.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.yaml.snakeyaml.error.Mark;

import javax.persistence.*;


@Entity
@Table(name = "student")
public class Student {

    @JsonProperty("studentId")
    @Id
    String studentId;

    @JsonProperty("marks")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rollNo")
    Marks marks;


    @JsonProperty("studentName")
    @Column(name = "student_name")
    String studentName;

    @JsonProperty("fatherName")
    @Column(name = "father_name")
    String fatherName;

    @JsonProperty("address")
    @Column(name = "address")
    String address;

    @JsonProperty("branch")
    @Column(name = "branch")
    String branch;

    @JsonProperty("tenth")
    @Column(name = "secondary")
    Double tenth;

    @JsonProperty("twelth")
    @Column(name = "senior_secondary")
    Double twelth;

    @JsonProperty("dob")
    @Column(name = "dob")
    String dob;

    @JsonProperty("year")
    @Column(name = "year")
    int year;

    @JsonProperty("current_sem")
    @Column(name = "current_sem")
    int currentSem;


    public int getCurrentSem() { return currentSem; }

    public void setCurrentSem(int currentSem) { this.currentSem = currentSem; }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public Marks getMarks() {
        return marks;
    }

    public void setMarks(Marks marks) {
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Double getTenth() {
        return tenth;
    }

    public void setTenth(Double tenth) {
        this.tenth = tenth;
    }

    public Double getTwelth() {
        return twelth;
    }

    public void setTwelth(Double twelth) {
        this.twelth = twelth;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Student mergeStudent(Student orgStudent){

        if(this.studentName != null && !this.studentName.isEmpty() && !this.studentName.equalsIgnoreCase(orgStudent.getStudentName())){
            orgStudent.setStudentName(this.studentName);
        }
        if(this.fatherName != null && !this.fatherName.isEmpty() && !this.fatherName.equalsIgnoreCase(orgStudent.getFatherName())){
            orgStudent.setFatherName(this.fatherName);
        }
        if(this.address != null && !this.address.isEmpty() && !this.address.equalsIgnoreCase(orgStudent.getAddress())){
            orgStudent.setAddress(this.address);
        }
        if(this.branch != null && !this.branch.isEmpty() && !this.branch.equalsIgnoreCase(orgStudent.getBranch())){
            orgStudent.setBranch(this.branch);
        }
        if(this.tenth != null && this.tenth != 0 && !this.tenth.equals(orgStudent.getTenth())){
            orgStudent.setTenth(this.tenth);
        }
        if(this.twelth != null && this.twelth != 0 && !this.twelth.equals(orgStudent.getTwelth())){
            orgStudent.setTwelth(this.twelth);
        }
        if(this.dob != null && !this.dob.isEmpty() && !this.dob.equals(orgStudent.getDob())){
            orgStudent.setDob(this.dob);
        }
        return orgStudent;
    }
}
