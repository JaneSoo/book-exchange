package org.acme.getting.started.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/books")
@RegisterRestClient(configKey = "book-service")
public interface BookRESTClient {

    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getByISBN(@PathParam("isbn") String isbn);
}
