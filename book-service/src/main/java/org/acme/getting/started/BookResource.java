package org.acme.getting.started;

import org.acme.getting.started.entity.Book;
import org.acme.getting.started.service.BookService;
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
    public List<Book> getAll(){
        return Book.listAll();
    }

    @GET
    @Path("{isbn}")
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
    public Book create(Book book){
        return bookService.create(book);
    }

    @PUT
    @Path("{isbn}")
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
