package org.acme.client;

import org.acme.DTOs.Offer.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@RegisterRestClient(configKey = "borrow-service")
public interface OfferRestClient {
    @POST
    @Path("/offers/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    OfferCreateDTO createOffer(OfferCreateDTO offer);


    @POST
    @Path("/offers/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    boolean cancelOffer(OfferCancelDTO offer);

    @PUT
    @Path("/offers/return")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    boolean returnOffer(OfferReturnDTO offer);

    @PUT
    @Path("/offers/take")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    boolean takeOffer(OfferTakeDTO offer);

    @GET
    @Path("/offers/available")
    @Produces(MediaType.APPLICATION_JSON)
    Set<OfferShowDTO> availableOffers();
}
