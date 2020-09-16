package com.managment.student.service;

import com.managment.student.exception.GraphQLErrorHandler;
import com.managment.student.interfaces.DataValidation;
import com.managment.student.interfaces.StudentService;
import com.managment.student.model.Marks;
import com.managment.student.model.Student;
import com.managment.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IStudentService implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DataValidation dataValidation;

    @Override
    public Student saveStudent(Student student) {
        if(studentRepository.existsById(student.getStudentId()))
            throw new GraphQLErrorHandler("entity already exist");
        dataValidation.validateStudent(student);
        student.setCurrentSem(1);
        student.setYear(1);
        return studentRepository.save(student);
    }

    @Override
    public Marks saveMarks(Marks marks) {
        dataValidation.validateMarks(marks);
        Student student = studentRepository.findByMarks(marks);
        if(student == null || student.getMarks().getRollNo() == null){
            throw new GraphQLErrorHandler("RollNo: "+marks.getRollNo()+" not found");
        }
        student.setMarks(marks.mergeMarks(student.getMarks()));
        studentRepository.save(student);
        return marks;
    }

    @Override
    public Student updateStudent(Student student) {
        if(!studentRepository.existsById(student.getStudentId()))
            throw new GraphQLErrorHandler("entity not exist");
        Student student1 = student.mergeStudent(findStudentById(student.getStudentId()));
        dataValidation.validateStudent(student1);
        return studentRepository.save(student1);
    }

    @Override
    public Student  deleteStudentById(String studentId) {
        studentRepository.deleteById(studentId);
        return null;
    }

    @Override
    public Student deleteStudentByRollNo(String rollNo) {
        studentRepository.deleteByStudentRollNo(rollNo);
        return null;
    }

    @Override
    public int generateRollNo(int year, String branch) {
        List<Student> studentList = findFilterStudent(year,branch).stream()
                .map(this::getRollNo)
                .collect(Collectors.toList());
        studentRepository.saveAll(studentList);
        return studentList.size();
    }

    private Student getRollNo(Student student){
        Marks marks =new Marks();
        marks.setRollNo(student.getBranch()+student.getStudentId()+student.getStudentName().substring(0,3));
        student.setMarks(marks);
        return student;
    }

    @Override
    public int promoted(String branch, int year ){
        List<Student> studentList = findFilterStudent(year,branch).stream()
                .map(this ::getPromoted)
                .collect(Collectors.toList());
        studentRepository.saveAll(studentList);

        return studentList.size();

    }

    private Student getPromoted(Student student){
        int i = student.getCurrentSem()+1;
        student.setCurrentSem(i);
        if(i%2 != 0){
            i = student.getYear()+1;
            student.setYear(i);
        }
        return student;
    }

    @Override
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public List<Marks> findAllStudentMarks() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getMarks)
                .collect(Collectors.toList());

    }

    @Override
    public List<Student> findFilterStudent(int year, String branch) {
        dataValidation.validateYearNBranch(year,branch);
        List<Student> studentList = studentRepository.findByYearAndBranch(year,branch);
        if(studentList.size() == 0){
            throw new GraphQLErrorHandler("no entry find for year: "+year+" branch: "+branch);
        }
        return studentList;
    }

    @Override
    public List<Student> findStudentByYear(int year) {
        dataValidation.validateYear(year);
        List<Student> studentList = studentRepository.findByYear(year);
        if(studentList.size() == 0){
            throw new GraphQLErrorHandler("no entry find for year: "+year);
        }
        return studentList;
    }

    @Override
    public List<Student> findStudentByBranch(String branch) {
        dataValidation.validateBranch(branch);
        List<Student> studentList = studentRepository.findByBranch(branch);
        if(studentList.isEmpty()){
            throw new GraphQLErrorHandler("no entry find for branch: "+branch);
        }
        return studentList;
    }

    @Override
    public List<Student> findStudentByName(String name) {
        List<Student> studentList = studentRepository.findByStudentName(name);
        if(studentList.isEmpty()){
            throw new GraphQLErrorHandler("no entry found for name: "+name);
        }
        return studentList;
    }

    @Override
    public Student findStudentById(String id) {
        Student student = studentRepository.findById(id).orElse(null);
        if(student == null){
            throw new GraphQLErrorHandler("no entry found for id: "+id);
        }
        return student;
    }

    @Override
    public Student findStudentByRollNo(String rollNo) {
        Student student = studentRepository.findStudentByRollNo(rollNo);
        if(student == null){
            throw new GraphQLErrorHandler("no entry found for rollNo: "+rollNo);
        }
        return student;
    }

}
