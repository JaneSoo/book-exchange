package org.acme.getting.started;

import org.acme.getting.started.entity.User;
import org.acme.getting.started.service.UserService;
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

@Path("/users")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Counted(name = "users.getAll.counter")
    @Timed(name = "users.getAll.timer")
    public List<User> getAll() {
        return User.listAll();
    }

    @GET
    @Path("{id}")
    @Counted(name = "users.get.counter")
    @Timed(name = "users.get.timer")
    public Response get(@PathParam long id) {
        User user = userService.findById(id);

        if (user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(String.format("User for id %d not found.", id))
                    .build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Path("register")
    @Counted(name = "users.create.counter")
    @Timed(name = "users.create.timer")
    public User create(User user){
        return userService.create(user);
    }

    @POST
    @Path("login")
    @Counted(name = "users.login.counter")
    @Timed(name = "users.login.timer")
    public Long login(UserLogin login){
        return userService.login(login);
    }

    @PUT
    @Path("{id}")
    @Counted(name = "users.update.counter")
    @Timed(name = "users.update.timer")
    public User update(@PathParam long id, JsonObject update){
        try{
            return userService.update(id, update);
        } catch (IllegalArgumentException iae){
            throw new ClientErrorException(iae.getMessage(), Response
                .status(Response.Status.PRECONDITION_FAILED)
                .entity(iae.getMessage())
                .build());
        }
    }

    @DELETE
    @Path("{id}")
    @Counted(name = "users.delete.counter")
    @Timed(name = "users.delete.timer")
    public Response delete(@org.jboss.resteasy.annotations.jaxrs.PathParam long id){
        User user;
        try{
            user = userService.delete(id);
            if(user == null){
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Cannot delete user with id: " + id)
                        .build();
            }
        }catch (NotFoundException nfe){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("User with id %d not found.", id))
                    .build();
        }
        return Response.ok(user).build();
    }

}