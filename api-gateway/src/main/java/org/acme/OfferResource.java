package org.acme;

import org.acme.DTOs.Book.Book;
import org.acme.DTOs.Offer.*;
import org.acme.client.OfferRestClient;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/offers")
public class OfferResource {

    @Inject
    @RestClient
    OfferRestClient offerRestClient;

    @Inject
    UserContext userContext;


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "offers.createOffer.counter")
    @Timed(name = "offers.createOffer.timer")
    @Operation(summary = "Users makes his book available to others")
    public OfferCreateDTO createOffer(Book book){
        OfferCreateDTO offer = new OfferCreateDTO();
        offer.isbn = book.isbn;
        try {
            offer.owner_id = userContext.getUserId();
        }catch (Exception e){
            throw new ClientErrorException(e.getMessage(), Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(e.getMessage())
                    .build());
        }
        System.out.println("userId"+offer.owner_id);
        System.out.println("isbn"+book.isbn);
        return offerRestClient.createOffer(offer);
    }

    @DELETE
    @Path("/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "offers.cancelOffer.counter")
    @Timed(name = "offers.cancelOffer.timer")
    @Operation(summary = "Users no longer wants to provide the book for others")
    public void cancelOffer(@RequestBody(description = "ID of given offer", required = true)
                                    OfferDTO dto){

        OfferCancelDTO offer = new OfferCancelDTO();
        offer.offer_id = dto.offer_id;
        try {
            offer.owner_id = userContext.getUserId();
        }catch (Exception e){
            throw new ClientErrorException(e.getMessage(), Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(e.getMessage())
                    .build());
        }
        offerRestClient.cancelOffer(offer);
    }

    @PUT
    @Path("/return")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "offers.returnOffer.counter")
    @Timed(name = "offers.returnOffer.timer")
    @Operation(summary = "User returns the borrowed book")
    public void returnOffer(OfferDTO dto){

        OfferReturnDTO offer = new OfferReturnDTO();
        offer.offer_id = dto.offer_id;
        try {
            offer.user_id = userContext.getUserId();
        }catch (Exception e){
            throw new ClientErrorException(e.getMessage(), Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(e.getMessage())
                    .build());
        }
        offerRestClient.returnOffer(offer);
    }

    @POST
    @Path("/take")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "offers.takeOffer.counter")
    @Timed(name = "offers.takeOffer.timer")
    @Operation(summary = "User wants to borrow given book")
    public boolean takeOffer(OfferDTO dto){

        OfferTakeDTO offer = new OfferTakeDTO();
        offer.offer_id = dto.offer_id;
        try {
            offer.user_id = userContext.getUserId();
        }catch (Exception e){
            throw new ClientErrorException(e.getMessage(), Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(e.getMessage())
                    .build());
        }
        var result = offerRestClient.takeOffer(offer);
        if (!result.isEmpty())
            return true;
        return false;
    }

    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "offers.availableOffer.counter")
    @Timed(name = "offers.availableOffer.timer")
    @Operation(summary = "Returns list of offers")
    public Set<OfferShowDTO> availableOffers(){
        return offerRestClient.availableOffers();
    }

}
