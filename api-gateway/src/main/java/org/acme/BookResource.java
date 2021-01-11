package org.acme;

import org.acme.DTOs.Book.Book;
import org.acme.client.BookRestClient;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/books")
public class BookResource {

    @Inject
    @RestClient
    BookRestClient bookRestClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "book.gettAllBooks.counter")
    @Timed(name = "book.getAllBooks.timer")
    @Operation(summary = "Retrieves list of isbn of all the books in the system")
    public Set<Book> getAllBooks(){
        return bookRestClient.getAllBooks();

    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "book.create.counter")
    @Timed(name = "book.create.timer")
    @Operation(summary = "Only inserts book into system")
    public Book createBook(Book book) {
        System.out.println(book.isbn);
        return bookRestClient.createBook(book);
    }
}
