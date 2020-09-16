package com.library.management.provider;


import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.library.management.fetcher.BookFetcher;
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
    BookFetcher bookFetcher;

    private GraphQL graphQL;

    @Bean
    GraphQL getGraphQL(){
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schemas/librarySchema.graphql");
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
                .dataFetcher("allBook",bookFetcher.findAll());
    }

    private TypeRuntimeWiring.Builder mutationBuilder() {
        return newTypeWiring("Mutation")
                .dataFetcher("createBook",bookFetcher.save())
                .dataFetcher("updateBook",bookFetcher.update())
                .dataFetcher("deleteBook",bookFetcher.deleteById())
                .dataFetcher("borrowBook",bookFetcher.borrowBook())
                .dataFetcher("returnBook",bookFetcher.returnBook());
    }
}
