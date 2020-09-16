package com.library.management.validator;

import com.library.management.exception.GraphQLErrorHandler;
import com.library.management.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BookValidation {

    public void validate(Book book){

        if(empty(book.getBookId())){
            throw new GraphQLErrorHandler("BookId cannot be null or empty");
        }

        if(empty(book.getBookName())){
            throw new GraphQLErrorHandler("BookName cannot be null or empty");
        }
        if(empty(book.getAuthorName())){
            throw new GraphQLErrorHandler("AuthorName cannot be null or empty");
        }
        if(book.getPages() < 0 || book.getPrice() <0){
            throw new GraphQLErrorHandler("Pages ang Price should be greater than 0");
        }
    }

    private boolean empty(String string){
        return string == null || string.isEmpty();
    }
}

