package com.managment.student.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managment.student.interfaces.StudentService;
import com.managment.student.model.Student;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StudentFetcher {

    @Autowired
    StudentService studentService;

    public DataFetcher<Integer> generateRollNo(){
        return dataFetchingEnvironment -> {
            String branch = dataFetchingEnvironment.getArgument("branch");
            int year = dataFetchingEnvironment.getArgument("year");
            return studentService.generateRollNo(year,branch);
        };
    }

    public DataFetcher<Integer> promoteStudent(){
        return dataFetchingEnvironment -> {
            String branch = dataFetchingEnvironment.getArgument("branch");
            int year = dataFetchingEnvironment.getArgument("year");
            return studentService.promoted(branch,year);
        };
    }


    public DataFetcher<Student> save() {
        return dataFetchingEnvironment -> {
            ObjectMapper mapper = new ObjectMapper();
            Student student=mapper.convertValue(dataFetchingEnvironment.getArgument("studentData"),Student.class);
            return studentService.saveStudent(student);
        };
    }

    public DataFetcher<Student> update() {
        return dataFetchingEnvironment -> {
            ObjectMapper mapper = new ObjectMapper();
            Student student=mapper.convertValue(dataFetchingEnvironment.getArgument("studentData"),Student.class);
            return studentService.updateStudent(student);
        };
    }

    public DataFetcher<Student> deleteById() {
        return dataFetchingEnvironment ->{
            String studentId = dataFetchingEnvironment.getArgument("studentId");
            return studentService.deleteStudentById(studentId);
        };
    }

    public DataFetcher<Student> deleteByRollNo() {
        return dataFetchingEnvironment -> {
            String rollNo = dataFetchingEnvironment.getArgument("rollNo");
            return studentService.deleteStudentByRollNo(rollNo);        };
    }

    public DataFetcher<List<Student>> findAll() {
        return dataFetchingEnvironment -> studentService.findAllStudent();
    }

    public DataFetcher<List<Student>> findStudentByName() {
        return dataFetchingEnvironment -> {
            String studentName = dataFetchingEnvironment.getArgument("studentName");
            return studentService.findStudentByName(studentName);
        };
    }

    public DataFetcher<Student> findStudentById() {
        return dataFetchingEnvironment -> {
            String studentId = dataFetchingEnvironment.getArgument("studentId");
            return studentService.findStudentById(studentId);
        };
    }

    public DataFetcher<Student> findStudentByRollNo() {
        return dataFetchingEnvironment -> {
            String rollNo = dataFetchingEnvironment.getArgument("rollNo");
            return studentService.findStudentByRollNo(rollNo);
        };
    }

    public DataFetcher<List<Student>> findStudentByYear() {
        return dataFetchingEnvironment -> {
            int year = dataFetchingEnvironment.getArgument("year");
            return studentService.findStudentByYear(year);
        };
    }

    public DataFetcher<List<Student>> findStudentByBranch() {
        return dataFetchingEnvironment -> {
            String branch = dataFetchingEnvironment.getArgument("branch");
            return studentService.findStudentByBranch(branch);
        };
    }

    public DataFetcher<List<Student>> findStudentByYearNBranch() {
        return dataFetchingEnvironment -> {
            String branch = dataFetchingEnvironment.getArgument("branch");
            int year = dataFetchingEnvironment.getArgument("year");
            return studentService.findFilterStudent(year, branch);
        };
    }


}
