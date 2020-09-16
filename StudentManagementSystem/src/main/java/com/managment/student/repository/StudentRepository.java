package com.managment.student.repository;

import com.managment.student.model.Marks;
import com.managment.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

    Student findByMarks(Marks marks);

    List<Student> findByYear(int year);

    List<Student> findByBranch(String branch);

    List<Student> findByStudentName(String studentName);

    List<Student> findByYearAndBranch(int year, String branch);

    @Query("from Student where roll_no = ?1")
    Student findStudentByRollNo(String rollNo);

    @Modifying
    @Transactional
    @Query("delete from Student where roll_no =?1")
    void deleteByStudentRollNo(String rollNo);



}
