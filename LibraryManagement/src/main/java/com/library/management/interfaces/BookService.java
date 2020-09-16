package com.library.management.interfaces;

import com.library.management.model.Book;

import java.util.LinkedHashMap;
import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book addBook(Book book);

    Book updateBook(Book book);

    Book deleteBook(String bookId);

    String borrowBook(LinkedHashMap<String,Object> data);

    String returnBook(List<String> bookList);

//    Boolean verifyBookExist(Book book);

}
