package org.jhipster.health.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Garde.
 */
@Entity
@Table(name = "garde")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "garde")
public class Garde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "seller_residant")
    private String seller_residant;

    @Column(name = "buyer_resident")
    private String buyer_resident;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Garde date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public Garde amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSeller_residant() {
        return seller_residant;
    }

    public Garde seller_residant(String seller_residant) {
        this.seller_residant = seller_residant;
        return this;
    }

    public void setSeller_residant(String seller_residant) {
        this.seller_residant = seller_residant;
    }

    public String getBuyer_resident() {
        return buyer_resident;
    }

    public Garde buyer_resident(String buyer_resident) {
        this.buyer_resident = buyer_resident;
        return this;
    }

    public void setBuyer_resident(String buyer_resident) {
        this.buyer_resident = buyer_resident;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Garde garde = (Garde) o;
        if (garde.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), garde.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Garde{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", seller_residant='" + getSeller_residant() + "'" +
            ", buyer_resident='" + getBuyer_resident() + "'" +
            "}";
    }
}
