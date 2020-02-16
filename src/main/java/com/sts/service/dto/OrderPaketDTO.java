package com.sts.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sts.domain.OrderPaket} entity.
 */
public class OrderPaketDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant orderedDate;

    private Boolean isExpired;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;


    private Long companyId;

    private String companyName;

    private Long paketDetayId;

    private String paketDetayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Instant orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Boolean isIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getPaketDetayId() {
        return paketDetayId;
    }

    public void setPaketDetayId(Long paketDetayId) {
        this.paketDetayId = paketDetayId;
    }

    public String getPaketDetayName() {
        return paketDetayName;
    }

    public void setPaketDetayName(String paketDetayName) {
        this.paketDetayName = paketDetayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderPaketDTO orderPaketDTO = (OrderPaketDTO) o;
        if (orderPaketDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderPaketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderPaketDTO{" +
            "id=" + getId() +
            ", orderedDate='" + getOrderedDate() + "'" +
            ", isExpired='" + isIsExpired() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", companyId=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            ", paketDetayId=" + getPaketDetayId() +
            ", paketDetayName='" + getPaketDetayName() + "'" +
            "}";
    }
}
