package com.library.management.service;

import com.library.management.exception.GraphQLErrorHandler;
import com.library.management.interfaces.BookService;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.library.management.constants.DataConstant.MAX_BOOK_ISSUE_PERIOD;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class IBookService implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowerService borrowerService;

    private Long fine;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        book.setStudentId("available");
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book deleteBook(String bookId) {
        bookRepository.deleteById(bookId);
        return null;
    }

    @Override
    public String borrowBook(LinkedHashMap<String,Object> data) {
        List<String> bookId = (List)data.get("bookId");
        int n = bookId.size();
        final String studentId = (String)data.get("studentId");
        borrowerService.verifyBorrower(studentId);
        List<Book> bookList = bookId.stream()
                .map(this::verifyBookAvailable)
                .map(book -> borrowerService.issuedToBorrower(book))
                .collect(Collectors.toList());
        bookRepository.saveAll(bookList);
        return "Success Full";
    }

    @Override
    public String returnBook(List<String> bookList) {
        fine = 0L;
        List<Book> bookList1 = bookList.stream()
                .map(this::verifyBookNotAvailable)
                .map(book ->{
                    calculateFine(book.getIssueDate());
                    return borrowerService.returnByBorrower(book);
                })
                .collect(Collectors.toList());

        bookRepository.saveAll(bookList1);
        return "Fine: "+fine*2L;
    }

    private void calculateFine(LocalDate issueDate) {
        LocalDate today = LocalDate.now();
        Long diff = DAYS.between(issueDate, LocalDate.parse("2020-10-02"));
        if(diff > MAX_BOOK_ISSUE_PERIOD){
            fine += diff - MAX_BOOK_ISSUE_PERIOD;
        }
    }

    private Book verifyBookAvailable(String  bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book == null || !book.getStudentId().equals("available")){
            throw new GraphQLErrorHandler("Either BookId is Incorrect or book does not available");
        }
        return book;
    }

    private Book verifyBookNotAvailable(String  bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book == null || book.getStudentId().equals("available")){
            throw new GraphQLErrorHandler("Either BookId is Incorrect or book is not Borrowed");
        }
        return book;
    }
}
