package com.managment.student.interfaces;

import com.managment.student.model.Marks;
import com.managment.student.model.Student;

public interface DataValidation {

    void validateStudent(Student student);

    void validateMarks(Marks student);

    void validateYear(int year);

    void validateBranch(String branch);

    void validateYearNBranch(int year, String branch);

}
