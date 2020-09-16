package com.managment.student.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managment.student.interfaces.DataValidation;
import com.managment.student.interfaces.StudentService;
import com.managment.student.model.Marks;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class MarksFetcher {

    @Autowired
    StudentService studentService;

    public DataFetcher<List<Marks>> findAll() {
        return dataFetchingEnvironment -> studentService.findAllStudentMarks();
    }

    public DataFetcher<Marks> save() {
        return dataFetchingEnvironment -> {
            ObjectMapper mapper = new ObjectMapper();
            Marks marks =mapper.convertValue(dataFetchingEnvironment.getArgument("studentMarks"),Marks.class);
            return studentService.saveMarks(marks);
        };
    }

}
