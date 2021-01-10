package org.acme.getting.started.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LoanId implements Serializable {
    public Long offer_id;
    public Long user_id;

    @Override
    public String toString() {
        return "LoanId{" +
            "offer_id=" + offer_id +
            ", user_id=" + user_id +
            '}';
    }
}
