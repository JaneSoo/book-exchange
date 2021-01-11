package org.acme.getting.started;

import io.quarkus.runtime.StartupEvent;
import org.acme.getting.started.entity.Loan;
import org.acme.getting.started.entity.Offer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

@ApplicationScoped
public class OffersGenerator {

    @Transactional
    public void generateOffersWithLoans(@Observes StartupEvent event) {
        Offer.add(1L, "978-3836277471");
        Offer.add(2L, "978-3836277372");
        Offer offer3 = Offer.add(3L, "978-1983399404");
        Loan.add(4L, offer3);
    }
}
