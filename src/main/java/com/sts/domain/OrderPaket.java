package com.sts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A OrderPaket.
 */
@Entity
@Table(name = "order_paket")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "orderpaket")
public class OrderPaket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ordered_date", nullable = false)
    private Instant orderedDate;

    @Column(name = "is_expired")
    private Boolean isExpired;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orderPakets")
    private Company company;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orderPakets")
    private PaketDetay paketDetay;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderedDate() {
        return orderedDate;
    }

    public OrderPaket orderedDate(Instant orderedDate) {
        this.orderedDate = orderedDate;
        return this;
    }

    public void setOrderedDate(Instant orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Boolean isIsExpired() {
        return isExpired;
    }

    public OrderPaket isExpired(Boolean isExpired) {
        this.isExpired = isExpired;
        return this;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public OrderPaket startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public OrderPaket endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Company getCompany() {
        return company;
    }

    public OrderPaket company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public PaketDetay getPaketDetay() {
        return paketDetay;
    }

    public OrderPaket paketDetay(PaketDetay paketDetay) {
        this.paketDetay = paketDetay;
        return this;
    }

    public void setPaketDetay(PaketDetay paketDetay) {
        this.paketDetay = paketDetay;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaket)) {
            return false;
        }
        return id != null && id.equals(((OrderPaket) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderPaket{" +
            "id=" + getId() +
            ", orderedDate='" + getOrderedDate() + "'" +
            ", isExpired='" + isIsExpired() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
