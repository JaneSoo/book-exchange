package org.acme.getting.started.service;

import org.acme.getting.started.entity.Book;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class BookService {
private Logger log = Logger.getLogger(BookService.class);

@Transactional
public Book create(Book book){
    book.persist();
    return book;
}

@Transactional
public Book update(String isbn, JsonObject updateJson){
    Book book = Book.findByISBN(isbn);
    if (book == null){
        return null;
    }

    updateJson.entrySet().forEach(entry -> {
        String value = entry.getValue().toString().replace("\"", "");
        switch (entry.getKey()) {
            case "isbn":
                book.isbn = value;
                break;
            default:
                throw new IllegalArgumentException("Unknown property provided for update book: " + entry.getKey());
        }
    });
    book.persist();
    return book;
}

    @Transactional
    public Book delete(String isbn){
        Book book = Book.findByISBN(isbn);
        if (book == null){
            throw new NotFoundException("Cannot find book with ISBN: " + isbn);
        }
        try {
            Book.deleteByISBN(isbn);
            return book;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("null");
        }

    }
}