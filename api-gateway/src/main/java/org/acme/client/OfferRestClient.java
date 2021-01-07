package org.acme.client;

import org.acme.DTOs.Book.BookDTO;
import org.acme.DTOs.Offer.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@RegisterRestClient(configKey = "offer-service")
public interface OfferRestClient {
    @POST
    @Path("/offer/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    boolean createOffer(OfferCreateDTO offer);


    @POST
    @Path("/offer/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    boolean cancelOffer(OfferCancelDTO offer);

    @PUT
    @Path("/offer/return")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    boolean returnOffer(OfferReturnDTO offer);

    @PUT
    @Path("/offer/take")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    boolean takeOffer(OfferTakeDTO offer);

    @GET
    @Path("/offer/available")
    @Produces(MediaType.APPLICATION_JSON)
    Set<OfferShowDTO> availableOffers();
}
