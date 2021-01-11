package org.acme.getting.started;

import org.acme.getting.started.entity.User;
import org.acme.getting.started.service.UserService;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.metrics.MetricUnits;

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
<<<<<<< HEAD
    @Counted(name = "getAll", description = "List all the users in database")
    @Timed(name = "checksTimerGetAll", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
=======
    @Counted(name = "users.getAll.counter")
    @Timed(name = "users.getAll.timer")
>>>>>>> 881800497b9cf01d3635b85a8dc239e634882162
    public List<User> getAll() {
        return User.listAll();
    }

    @GET
    @Path("{id}")
<<<<<<< HEAD
    @Counted(name = "getUser", description = "Get user with given ID")
    @Timed(name = "checksTimerGet", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
=======
    @Counted(name = "users.get.counter")
    @Timed(name = "users.get.timer")
>>>>>>> 881800497b9cf01d3635b85a8dc239e634882162
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
<<<<<<< HEAD
    @Counted(name = "register", description = "Register new user to database")
    @Timed(name = "checksTimerRegister", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
=======
    @Counted(name = "users.create.counter")
    @Timed(name = "users.create.timer")
>>>>>>> 881800497b9cf01d3635b85a8dc239e634882162
    public User create(User user){
        return userService.create(user);
    }

    @POST
    @Path("login")
<<<<<<< HEAD
    @Counted(name = "login", description = "Verify the credential to database record whether the user is authorized")
    @Timed(name = "checksTimerLogin", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
=======
    @Counted(name = "users.login.counter")
    @Timed(name = "users.login.timer")
>>>>>>> 881800497b9cf01d3635b85a8dc239e634882162
    public Long login(UserLogin login){
        return userService.login(login);
    }

    @PUT
    @Path("{id}")
<<<<<<< HEAD
    @Counted(name = "update", description = "Update the record of user with given ID")
    @Timed(name = "checksTimerUpdate", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
=======
    @Counted(name = "users.update.counter")
    @Timed(name = "users.update.timer")
>>>>>>> 881800497b9cf01d3635b85a8dc239e634882162
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
<<<<<<< HEAD
    @Counted(name = "delete", description = "Delete user with given ID")
    @Timed(name = "checksTimerDelete", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
=======
    @Counted(name = "users.delete.counter")
    @Timed(name = "users.delete.timer")
>>>>>>> 881800497b9cf01d3635b85a8dc239e634882162
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