package org.acme;


import org.acme.DTOs.User.UserCreateDTO;
import org.acme.DTOs.User.UserLoginDTO;
import org.acme.client.UserRestClient;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @Inject
    @RestClient
    UserRestClient userRestClient;

    @Inject
    UserContext userContext;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "users.register.counter")
    @Timed(name = "users.register.timer")
    @Operation(summary = "User registration")
    public UserCreateDTO register(UserCreateDTO user){
        return userRestClient.register(user);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "users.login.counter")
    @Timed(name = "users.login.timer")
    @Operation(summary = "User login")
    public Response login(UserLoginDTO user) throws Exception {
        var id = userRestClient.login(user);
        System.out.println(id);
        if (id == null)
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Wrong credentials")
                    .build();

        userContext.setUserId(id);
        return Response.ok().build();
    }
}
