package org.jhipster.health.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Garde entity. This class is used in GardeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /gardes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GardeCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private DoubleFilter amount;

    private StringFilter seller_residant;

    private StringFilter buyer_resident;

    public GardeCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public StringFilter getSeller_residant() {
        return seller_residant;
    }

    public void setSeller_residant(StringFilter seller_residant) {
        this.seller_residant = seller_residant;
    }

    public StringFilter getBuyer_resident() {
        return buyer_resident;
    }

    public void setBuyer_resident(StringFilter buyer_resident) {
        this.buyer_resident = buyer_resident;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GardeCriteria that = (GardeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(seller_residant, that.seller_residant) &&
            Objects.equals(buyer_resident, that.buyer_resident);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        amount,
        seller_residant,
        buyer_resident
        );
    }

    @Override
    public String toString() {
        return "GardeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (seller_residant != null ? "seller_residant=" + seller_residant + ", " : "") +
                (buyer_resident != null ? "buyer_resident=" + buyer_resident + ", " : "") +
            "}";
    }

}
