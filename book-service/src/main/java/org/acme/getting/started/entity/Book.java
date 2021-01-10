package org.acme.getting.started.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

@Entity
public class Book extends PanacheEntity {
    public String isbn;

    public static void add(String isbn){
        Book book = new Book();
        book.isbn = isbn;
        book.persist();
    }

    public static Book findByISBN(String isbn){
        try {
            return find("isbn", isbn).firstResult();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("couldn't find book with ISBN: "+ isbn);
        }

    }

    public static void deleteByISBN(String isbn){
        try {
            delete("isbn", isbn);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("cannot delete book with ISBN: "+isbn);
        }

    }
}