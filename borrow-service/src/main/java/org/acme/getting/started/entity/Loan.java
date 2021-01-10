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

    @Override
    public String toString() {
        return "Loan{" +
            "loan_id=" + loan_id +
            ", offer=" + offer +
            '}';
    }
}

