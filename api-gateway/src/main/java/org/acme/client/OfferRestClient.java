package org.acme.client;

import org.acme.DTOs.Offer.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonObject;
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


    @DELETE
    @Path("/offers/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void cancelOffer(OfferCancelDTO offer);

    @PUT
    @Path("/offers/return")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void returnOffer(OfferReturnDTO offer);

    @POST
    @Path("/offers/take")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject takeOffer(OfferTakeDTO offer);

    @GET
    @Path("/offers/available")
    @Produces(MediaType.APPLICATION_JSON)
    Set<OfferShowDTO> availableOffers();
}
