package com.managment.student.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_marks")
public class Marks {

    @JsonProperty("rollNo")
    @Id
    String rollNo;

    @JsonProperty("sem1")
    @Column(name = "sem1")
    int sem1;

    @JsonProperty("sem2")
    @Column(name = "sem2")
    int sem2;

    @JsonProperty("sem3")
    @Column(name = "sem3")
    int sem3;

    @JsonProperty("sem4")
    @Column(name = "sem4")
    int sem4;

    @JsonProperty("sem5")
    @Column(name = "sem5")
    int sem5;

    @JsonProperty("sem6")
    @Column(name = "sem6")
    int sem6;

    @JsonProperty("sem7")
    @Column(name = "sem7")
    int sem7;

    @JsonProperty("sem8")
    @Column(name = "sem8")
    int sem8;

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public int getSem1() {
        return sem1;
    }

    public void setSem1(int sem1) {
        this.sem1 = sem1;
    }

    public int getSem2() {
        return sem2;
    }

    public void setSem2(int sem2) {
        this.sem2 = sem2;
    }

    public int getSem3() {
        return sem3;
    }

    public void setSem3(int sem3) {
        this.sem3 = sem3;
    }

    public int getSem4() {
        return sem4;
    }

    public void setSem4(int sem4) {
        this.sem4 = sem4;
    }

    public int getSem5() {
        return sem5;
    }

    public void setSem5(int sem5) {
        this.sem5 = sem5;
    }

    public int getSem6() {
        return sem6;
    }

    public void setSem6(int sem6) {
        this.sem6 = sem6;
    }

    public int getSem7() {
        return sem7;
    }

    public void setSem7(int sem7) {
        this.sem7 = sem7;
    }

    public int getSem8() {
        return sem8;
    }

    public void setSem8(int sem8) {
        this.sem8 = sem8;
    }

    public Marks mergeMarks(Marks orgMarks){

        if(this.sem1 != 0){
            orgMarks.setSem1(this.sem1);
        }
        if(this.sem2 != 0){
            orgMarks.setSem2(this.sem2);
        }
        if(this.sem3 != 0){
            orgMarks.setSem3(this.sem3);
        }
        if(this.sem4 != 0){
            orgMarks.setSem4(this.sem4);
        }
        if(this.sem5 != 0){
            orgMarks.setSem5(this.sem5);
        }
        if(this.sem6 != 0){
            orgMarks.setSem6(this.sem6);
        }
        if(this.sem7 != 0){
            orgMarks.setSem7(this.sem7);
        }
        if(this.sem8 != 0){
            orgMarks.setSem8(this.sem8);
        }

        return orgMarks;
    }
}
