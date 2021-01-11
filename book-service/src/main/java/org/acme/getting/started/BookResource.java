package org.acme.getting.started;

import org.acme.getting.started.entity.Book;
import org.acme.getting.started.service.BookService;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookService bookService;

    @GET
    @Counted(name = "books.getAll.counter")
    @Timed(name = "books.getAll.timer")
    public List<Book> getAll(){
        return Book.listAll();
    }

    @GET
    @Path("{isbn}")
    @Counted(name = "books.get.counter")
    @Timed(name = "books.get.timer")
    public Response get(@PathParam String isbn){
        Book book = Book.findByISBN(isbn);
        if (book == null){
            return Response
              .status(Response.Status.NOT_FOUND)
              .entity(String.format("Book for isbn %s is not found", isbn))
              .build();
        }
        return Response.ok(book).build();
    }

    @POST
    @Path("/create")
    @Counted(name = "books.create.counter")
    @Timed(name = "books.create.timer")
    public Book create(Book book){
        return bookService.create(book);
    }

    @PUT
    @Path("{isbn}")
    @Counted(name = "books.update.counter")
    @Timed(name = "books.update.timer")
    public Book update(@PathParam String isbn, JsonObject update){
        try{
            return bookService.update(isbn, update);
        } catch (IllegalArgumentException iae){
            throw new ClientErrorException(iae.getMessage(), Response
              .status(Response.Status.PRECONDITION_FAILED)
              .entity(iae.getMessage())
              .build());
        }
    }

    @DELETE
    @Path("{isbn}")
    @Counted(name = "books.delete.counter")
    @Timed(name = "books.delete.timer")
    public Response delete(@org.jboss.resteasy.annotations.jaxrs.PathParam String isbn){
        Book book;
        try{
            book = bookService.delete(isbn);
            if(book == null){
                return Response
                  .status(Response.Status.INTERNAL_SERVER_ERROR)
                  .entity("Cannot delete book with isbn: " + isbn)
                  .build();
            }
        }catch (NotFoundException nfe){
            return Response.status(Response.Status.NOT_FOUND)
              .entity(String.format("Book with ISBN %s not found.", isbn))
              .build();
        }
        return Response.ok(book).build();
    }
}
