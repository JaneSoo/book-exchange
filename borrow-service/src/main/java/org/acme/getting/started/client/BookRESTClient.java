package org.acme.getting.started.client;

import org.acme.getting.started.entity.Book;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/books")
@RegisterRestClient(configKey = "book-service")
public interface BookRESTClient {

    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    Book getByISBN(@PathParam("isbn") String isbn);
}
