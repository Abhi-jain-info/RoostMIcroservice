package com.library.management.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "book_data")
public class Book {

    @JsonProperty("bookId")
    @Id
    String bookId;

    @JsonProperty("bookName")
    @Column(name = "book_name")
    String bookName;

    @JsonProperty("price")
    @Column(name = "price")
    int price;

    @JsonProperty("pages")
    @Column(name = "pages")
    int pages;

    @JsonProperty("publication")
    @Column(name = "publication")
    String publication;

    @JsonProperty("authorName")
    @Column(name = "author_name")
    String authorName;

    @JsonProperty("bookGenre")
    @Column(name = "book_genre")
    String genre;

    @JsonProperty("studentId")
    @Column(name = "student_id")
    String studentId;

    @JsonProperty("studentName")
    @Column(name = "student_name")
    String studentName;

    @JsonProperty("issueDate")
    @Column(name = "issue_date")
    LocalDate issueDate;

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
