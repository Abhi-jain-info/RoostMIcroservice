package com.library.management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.exception.GraphQLErrorHandler;
import com.library.management.model.Book;
import graphql.GraphqlErrorException;
import org.hibernate.graph.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import static com.library.management.constants.DataConstant.*;

@Service
public class BorrowerService {

    private String studentId;
    private String studentName;

    @LoadBalanced
    private final WebClient.Builder webClient;

    @Autowired
    private RestTemplate restTemplate;

    public BorrowerService(WebClient.Builder webClient) {
        this.webClient = webClient;
    }


    public void verifyBorrower(String studentId) {

        final String query = "query{\n" +
                "  studentById(studentId:\"" + studentId + "\"){\n" +
                "    studentId\n" +
                "    studentName\n" +
                "  }\n" +
                "}";
        try {
            Mono<LinkedHashMap> response = webClient.baseUrl(URL)
                    .defaultHeader(CONTENT_TYPE, APPLICATION_GRAPHQL)
                    .build()
                    .post()
                    .bodyValue(query)
                    .retrieve()
                    .bodyToMono(LinkedHashMap.class);

            LinkedHashMap res = (LinkedHashMap) response.block().get("data");
            LinkedHashMap data = (LinkedHashMap) res.get("studentById");
            this.studentId = (String) data.get("studentId");
            this.studentName = (String) data.get("studentName");
        } catch (Exception e) {
            tryWithRestTemplate(query);
        }
    }

    private void tryWithRestTemplate(String query)
    {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.add(CONTENT_TYPE, APPLICATION_GRAPHQL);

            ResponseEntity<LinkedHashMap> response = restTemplate.postForEntity(URL, new HttpEntity<>(query, headers), LinkedHashMap.class);
            ObjectMapper objectMapper = new ObjectMapper();
            LinkedHashMap res = (LinkedHashMap) response.getBody().get("data");
            LinkedHashMap data = (LinkedHashMap) res.get("studentById");

            this.studentId = (String) data.get("studentId");
            this.studentName = (String) data.get("studentName");
        }catch (Exception e){
            throw new GraphQLErrorHandler("Issue in connecting with student Microservice");
        }
    }

    public Book issuedToBorrower(Book book) {
        book.setStudentName(this.studentName);
        book.setStudentId(this.studentId);
        book.setIssueDate(LocalDate.now());
        return book;
    }

    public Book returnByBorrower(Book book) {
        book.setIssueDate(null);
        book.setStudentName(null);
        book.setStudentId("available");
        return book;
    }
}
