package org.acme.getting.started.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.getting.started.entity.Offer;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OfferRepository implements PanacheRepository<Offer> {

    public List<Offer> findAvailable() {
        return list("availability", true);
    }
}
