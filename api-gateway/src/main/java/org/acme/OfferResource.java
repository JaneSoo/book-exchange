package org.acme;

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
    @Consumes(MediaType.TEXT_PLAIN)
    @Counted(name = "offers.createOffer.counter")
    @Timed(name = "offers.createOffer.timer")
    @Operation(summary = "Users makes his book available to others")
    public boolean createOffer(@RequestBody(description = "ISBN of given book", required = true)
                               String isbn){
        OfferCreateDTO offer = new OfferCreateDTO();
        offer.isbn = isbn;
        try {
            offer.userId = userContext.getUserId();
        }catch (Exception e){
            throw new ClientErrorException(e.getMessage(), Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(e.getMessage())
                    .build());
        }
        return offerRestClient.createOffer(offer);
    }

    @POST
    @Path("/cancel")
    @Consumes(MediaType.TEXT_PLAIN)
    @Counted(name = "offers.cancelOffer.counter")
    @Timed(name = "offers.cancelOffer.timer")
    @Operation(summary = "Users no longer wants to provide the book for others")
    public boolean cancelOffer(@RequestBody(description = "ID of given offer", required = true)
                               long offerId){

        OfferCancelDTO offer = new OfferCancelDTO();
        offer.offerId = offerId;
        try {
            offer.userId = userContext.getUserId();
        }catch (Exception e){
            throw new ClientErrorException(e.getMessage(), Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(e.getMessage())
                    .build());
        }
        return offerRestClient.cancelOffer(offer);
    }

    @PUT
    @Path("/return")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "offers.returnOffer.counter")
    @Timed(name = "offers.returnOffer.timer")
    @Operation(summary = "User returns the borrowed book")
    public boolean returnOffer(@RequestBody(description = "ID of given offer", required = true)
                               long offerId){

        OfferReturnDTO offer = new OfferReturnDTO();
        offer.offerId = offerId;
        try {
            offer.userId = userContext.getUserId();
        }catch (Exception e){
            throw new ClientErrorException(e.getMessage(), Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(e.getMessage())
                    .build());
        }
        return offerRestClient.returnOffer(offer);
    }

    @POST
    @Path("/take")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "offers.takeOffer.counter")
    @Timed(name = "offers.takeOffer.timer")
    @Operation(summary = "User wants to borrow given book")
    public boolean takeOffer(@RequestBody(description = "ID of given offer", required = true)
                             long offerId){

        OfferTakeDTO offer = new OfferTakeDTO();
        offer.offerId = offerId;
        try {
            offer.userId = userContext.getUserId();
        }catch (Exception e){
            throw new ClientErrorException(e.getMessage(), Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(e.getMessage())
                    .build());
        }
        return offerRestClient.takeOffer(offer);
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
