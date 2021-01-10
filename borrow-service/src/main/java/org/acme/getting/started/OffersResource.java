package org.acme.getting.started;

import org.acme.getting.started.client.BookRESTClient;
import org.acme.getting.started.client.UserRESTClient;
import org.acme.getting.started.entity.*;
import org.acme.getting.started.repository.OfferRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/offers")
public class OffersResource {

    private static final Logger LOG = Logger.getLogger(OffersResource.class);

    @Inject
    OfferRepository offerRepository;

    @Inject
    @RestClient
    BookRESTClient bookRESTClient;

    @Inject
    @RestClient
    UserRESTClient userRESTClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "list all offers")
    public List<Offer> listAllOffers() {
        LOG.info("Listing all offers.");
        return Offer.listAll();
    }

    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "list all available offers")
    public List<Offer> listAvailableOffers() {
        LOG.info("Listing all available offers.");
        return offerRepository.findAvailable();
    }

    @Transactional
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "create offer of some new book")
    public Offer createOffer(@Valid Offer offer) {
        assertBookExists(offer.isbn);
        assertUserExists(offer.owner_id);
        if (!offer.availability) {
            throw new WebApplicationException("Offer must be set as available.", 403);
        }
        LOG.info("Storing new offer into the database: " + offer);
        offer.persist();
        return offer;
    }

    @Transactional
    @POST
    @Path("/take")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "take offer of some book")
    public Loan takeOffer(LoanId loanId) {
        Optional<Offer> offer = Offer.findByIdOptional(loanId.offer_id);
        if (offer.isEmpty()) {
            throw new WebApplicationException("Offer with given id not found.", 400);
        }
        assertUserExists(loanId.user_id);
        if (!offer.get().availability) {
            throw new WebApplicationException("Offer is not available.", 403);
        }
        LOG.info("Storing new loan with following parameters: " + loanId);
        Loan loan = new Loan();
        loan.loan_id = loanId;
        loan.offer = offer.get();
        loan.offer.availability = false;
        loan.offer.persist();
        loan.persist();
        return loan;
    }

    @Transactional
    @PUT
    @Path("/return")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "return borrowed book")
    public void returnBook(LoanId loanId) {
        Optional<Loan> loan = Loan.findByIdOptional(loanId);
        if (loan.isEmpty()) {
            throw new WebApplicationException("Loan with given id not found.", 400);
        }
        LOG.info("Returning book: " + loan);
        loan.get().offer.availability = true;
        loan.get().offer.persist();
        Loan.deleteById(loanId);
    }

    @Transactional
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Operation(description = "cancel offer of some book")
    public void cancelOffer(@PathParam("id") Long id) {
        Optional<Offer> offer = Offer.findByIdOptional(id);
        if (offer.isEmpty()) {
            throw new WebApplicationException("Offer with given id not found.", 404);
        }
        if (!offer.get().availability) {
            throw new WebApplicationException("Offer cannot be canceled because book is borrowed.", 403);
        }
        LOG.info("Canceling offer: " + offer);
        Offer.deleteById(id);
    }

    private void assertBookExists(String isbn) { // TODO
//        Book book = bookRESTClient.getByISBN(isbn);
//        if (!book.isbn.equals(isbn)) {
//            throw new WebApplicationException("Referenced book does not exist.", 400);
//        }
    }

    private void assertUserExists(Long userId) { // TODO
//        User user = userRESTClient.getById(userId);
//        if (!user.user_id.equals(userId)) {
//            throw new WebApplicationException("Referenced user does not exist.", 400);
//        }
    }
}
