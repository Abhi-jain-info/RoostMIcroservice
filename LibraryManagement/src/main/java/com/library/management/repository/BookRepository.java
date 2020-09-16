package com.library.management.repository;

import com.library.management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,String> {

    @Query("from Book where student_id = 'available'")
    List<Book> findAvailableBook();

    Book findBookByBookName(String bookName);
}
