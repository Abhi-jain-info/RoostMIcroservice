package com.library.management.service;

import com.library.management.exception.GraphQLErrorHandler;
import com.library.management.interfaces.BookService;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import com.library.management.validator.BookValidation;
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
    BookValidation bookValidation;

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
    public List<Book> findBookAvailable() {
        return bookRepository.findAvailableBook();
    }

    @Override
    public Book findBookById(String id) {
        bookExist(id);
        return bookRepository.getOne(id);
    }

    @Override
    public Book findBookByName(String bookName) {
        if(bookName == null || bookName.isEmpty()){
            throw new GraphQLErrorHandler("Book Name cannot be Empty");
        }
        return bookRepository.findBookByBookName(bookName);
    }

    @Override
    public Book addBook(Book book) {
        if(bookRepository.existsById(book.getBookId())){
            throw new GraphQLErrorHandler("Entity Already exist");
        }
        bookValidation.validate(book);
        book.setStudentId("available");
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        bookExist(book.getBookId());
        bookValidation.validate(book);
        return bookRepository.save(book);
    }

    @Override
    public Book deleteBook(String bookId) {
        bookExist(bookId);
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

    private void bookExist(String bookId){
        if(bookId == null || !bookRepository.existsById(bookId)){
            throw new GraphQLErrorHandler("Entity does not exist");
        }
    }

}
