package org.acme.getting.started.client;

import org.acme.getting.started.entity.User;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@RegisterRestClient(configKey = "user-service")
public interface UserRESTClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    User getById(@PathParam("id") Long id);
}
