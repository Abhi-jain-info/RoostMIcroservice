package com.managment.student.interfaces;

import com.managment.student.model.Marks;
import com.managment.student.model.Student;

import java.util.List;

public interface StudentService {


    int generateRollNo(int year , String branch);

    Student saveStudent(Student student);

    Marks saveMarks(Marks marks);

    List<Student> findAllStudent();

    List<Marks> findAllStudentMarks();

    List<Student> findFilterStudent(int year , String branch);

    List<Student> findStudentByYear(int year);

    List<Student> findStudentByBranch(String branch);

    List<Student> findStudentByName(String name);

    Student findStudentById(String Id);

    Student findStudentByRollNo(String rollNo);

    Student updateStudent(Student student);

    Student deleteStudentById(String studentId);

    Student deleteStudentByRollNo(String rollNo);

    int promoted(String branch, int year);

}

