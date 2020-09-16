package com.managment.student.provider;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.managment.student.fetcher.MarksFetcher;
import com.managment.student.fetcher.StudentFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@RestController
@Component
public class GraphQLProvider {

    @Autowired
    StudentFetcher studentFetcher;

    @Autowired
    MarksFetcher marksFetcher;

    private GraphQL graphQL;

    @Bean
    GraphQL getGraphQL(){
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schemas/studentSchema.graphql");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(queryBuilder())
                .type(mutationBuilder()
                ).build();
    }
    private TypeRuntimeWiring.Builder queryBuilder(){
        return newTypeWiring("Query")
                .dataFetcher("allStudent",studentFetcher.findAll())
                .dataFetcher("allStudentMarks",marksFetcher.findAll())
                .dataFetcher("studentByName",studentFetcher.findStudentByName())
                .dataFetcher("studentByYear",studentFetcher.findStudentByYear())
                .dataFetcher("studentByBranch",studentFetcher.findStudentByBranch())
                .dataFetcher("studentById",studentFetcher.findStudentById())
                .dataFetcher("studentByRollNo",studentFetcher.findStudentByRollNo())
                .dataFetcher("studentByYearNBranch",studentFetcher.findStudentByYearNBranch());
    }

    private TypeRuntimeWiring.Builder mutationBuilder(){
        return newTypeWiring("Mutation")
                .dataFetcher("createStudent",studentFetcher.save())
                .dataFetcher("deleteByRollNo",studentFetcher.deleteByRollNo())
                .dataFetcher("deleteById",studentFetcher.deleteById())
                .dataFetcher("updateStudent",studentFetcher.update())
                .dataFetcher("generateRollNo",studentFetcher.generateRollNo())
                .dataFetcher("addMark",marksFetcher.save())
                .dataFetcher("promoteStudents",studentFetcher.promoteStudent());
    }
}
