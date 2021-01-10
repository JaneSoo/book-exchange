package org.acme.client;


import org.acme.DTOs.Book.Book;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;


@RegisterRestClient(configKey = "book-service")
public interface BookRestClient {
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    Set<Book> getAllBooks();

    @POST
    @Path("/books/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Book createBook(Book book);
}
