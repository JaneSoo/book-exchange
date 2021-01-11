package org.acme.getting.started.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Loan extends PanacheEntityBase implements Serializable {
    @EmbeddedId
    public LoanId loan_id;

    @MapsId("offer_id")
    @ManyToOne
    public Offer offer;

    public static Loan add(Long user_id, Offer offer) {
        offer.availability = false;
        offer.persist();
        LoanId loanId = new LoanId();
        loanId.offer_id = offer.offer_id;
        loanId.user_id = user_id;
        Loan loan = new Loan();
        loan.loan_id = loanId;
        loan.offer = offer;
        loan.persist();
        return loan;
    }

    @Override
    public String toString() {
        return "Loan{" +
            "loan_id=" + loan_id +
            ", offer=" + offer +
            '}';
    }
}

