package com.library.management.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.interfaces.BookService;
import com.library.management.model.Book;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class BookFetcher {

    @Autowired
    BookService bookService;

    @Autowired
    ObjectMapper mapper;

    public DataFetcher<List<Book>> findAll() {
        return dataFetchingEnvironment -> bookService.findAll();
    }

    public DataFetcher<List<Book>> findAllAvailableBook() {
        return dataFetchingEnvironment -> bookService.findBookAvailable();
    }

    public DataFetcher<Book> findBookById() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("bookId");
            return bookService.findBookById(bookId);
        };
    }
    public DataFetcher<Book> findBookByBookName() {
        return dataFetchingEnvironment -> {
            String bookName = dataFetchingEnvironment.getArgument("bookName");
            return bookService.findBookByName(bookName);
        };
    }


    public DataFetcher<Book> save() {
        return dataFetchingEnvironment -> {
            Book book=mapper.convertValue(dataFetchingEnvironment.getArgument("book"),Book.class);
            return bookService.addBook(book);
        };
    }

    public DataFetcher<Book> update() {
        return dataFetchingEnvironment -> {
            Book student=mapper.convertValue(dataFetchingEnvironment.getArgument("book"),Book.class);
            return bookService.updateBook(student);
        };
    }

    public DataFetcher<Book> deleteById() {
        return dataFetchingEnvironment ->{
            String bookId = dataFetchingEnvironment.getArgument("bookId");
            return bookService.deleteBook(bookId);
        };
    }

    public DataFetcher<String> borrowBook() {
        return dataFetchingEnvironment ->{
            LinkedHashMap<String,Object> linkedHashMap = dataFetchingEnvironment.getArgument("borrow");
            return bookService.borrowBook(linkedHashMap);
        };
    }

    public DataFetcher<String> returnBook() {
        return dataFetchingEnvironment ->{
            List<String> list = dataFetchingEnvironment.getArgument("bookId");
            return bookService.returnBook(list);
        };
    }

}
