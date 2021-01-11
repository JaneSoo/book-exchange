package org.acme.getting.started.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Offer extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public Long offer_id;

    @NotNull
    public Long owner_id;

    @NotBlank
    public String isbn;

    public Boolean availability = true;

    public static Offer add(Long owner_id, String isbn) {
        Offer offer = new Offer();
        offer.owner_id = owner_id;
        offer.isbn = isbn;
        offer.persist();
        return offer;
    }

    @Override
    public String toString() {
        return "Offer{" +
            "offer_id=" + offer_id +
            ", owner_id=" + owner_id +
            ", isbn='" + isbn + '\'' +
            ", availability=" + availability +
            '}';
    }
}
