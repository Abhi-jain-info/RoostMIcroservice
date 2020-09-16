package com.managment.student.validator;

import com.managment.student.exception.GraphQLErrorHandler;
import com.managment.student.interfaces.DataValidation;
import com.managment.student.model.Marks;
import com.managment.student.model.Student;
import org.springframework.stereotype.Component;


@Component
public class StudentValidator implements DataValidation {

    public void validateYear(int year) {
        if(year > 8 || year < 0){
            throw new GraphQLErrorHandler("year should be between 1-8");
        }
    }

    public void validateBranch(String branch) {
        if(!branch.equalsIgnoreCase("cs") && !branch.equalsIgnoreCase("me")){
            throw new GraphQLErrorHandler(branch+" is not a valid branch");
        }
    }

    public void validateYearNBranch(int year, String branch) {
        validateBranch(branch);
        validateYear(year);
    }

    public void validateStudent(Student student){

        if(empty(student.getStudentId())){
            throw new GraphQLErrorHandler("studentId is mandatory");
        }

        if(empty(student.getStudentName())){
            throw new GraphQLErrorHandler("student name is mandatory");
        }

        if(student.getTenth() == null ||student.getTenth() == 0 ||
                student.getTwelth() == null ||student.getTwelth() == 0){
            throw new GraphQLErrorHandler("tenth and twelth marks cannot be null or empty");
        }

        if(empty(student.getFatherName())){
            throw new GraphQLErrorHandler("father name cannot be null or empty");
        }

        if(empty(student.getBranch())){
            throw new GraphQLErrorHandler("branch cannot be null or empty");
        }
        validateBranch(student.getBranch());

        if(empty(student.getAddress())){
            throw new GraphQLErrorHandler("address cannot be null or empty");
        }

        if(empty(student.getDob()) || !student.getDob().matches("^([1-2][0-9]|[1-9]|3[01]|0[1-9])/(1[012]|[1-9]|0[1-9])/[0-9]{4}$")){
            throw new GraphQLErrorHandler("dob should be in dd/mm/yyyy format");
        }
    }

    public void validateMarks(Marks student) {
        if(empty(student.getRollNo())){
            throw new GraphQLErrorHandler("RollNo cannot null or empty");
        }
        if(     checkRange(student.getSem1()) || checkRange(student.getSem2()) ||
                checkRange(student.getSem3()) || checkRange(student.getSem4()) ||
                checkRange(student.getSem5()) || checkRange(student.getSem6()) ||
                checkRange(student.getSem7()) || checkRange(student.getSem8()))
        {
            throw new GraphQLErrorHandler("marks should range between 0 - 1000");
        }
    }

    private boolean empty(String string){
        return string == null || string.isEmpty();
    }

    private boolean checkRange(int marks){
        return marks > 1000 || marks < -1;
    }
}
