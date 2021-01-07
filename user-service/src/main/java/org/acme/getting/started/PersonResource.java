package org.acme.getting.started;

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
public class PersonResource {

    @Inject
    PersonService personService;

    @GET
    public List<Person> getAll() {
        return Person.listAll();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam long id) {
        Person person = Person.findById(id);

        if (person == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(String.format("Person for id %d not found.", id))
                    .build();
        }

        return Response.ok(person).build();
    }

    @POST
    @Path("register")
    public Person create(Person person){
        return personService.create(person);
    }

    @PUT
    @Path("{id}")
    public Person update(@PathParam long id, JsonObject update){
        try{
            return personService.update(id, update);
        } catch (IllegalArgumentException iae){
            throw new ClientErrorException(iae.getMessage(), Response
                .status(Response.Status.PRECONDITION_FAILED)
                .entity(iae.getMessage())
                .build());
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@org.jboss.resteasy.annotations.jaxrs.PathParam long id){
        Person person;
        try{
            person = personService.delete(id);
            if(person == null){
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
        return Response.ok(person).build();
    }

}