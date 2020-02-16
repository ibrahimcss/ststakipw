package com.sts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.sts.domain.enumeration.Sex;

/**
 * A CustomerOf.
 */
@Entity
@Table(name = "customer_of")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "customerof")
public class CustomerOf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 10000000000L)
    @Max(value = 99999999999L)
    @Column(name = "tc_id", nullable = false)
    private Long tcId;

    @NotNull
    @Pattern(regexp = "^\\+90[0-9]{10}$")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("customerOfs")
    private User customer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("customerOfs")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTcId() {
        return tcId;
    }

    public CustomerOf tcId(Long tcId) {
        this.tcId = tcId;
        return this;
    }

    public void setTcId(Long tcId) {
        this.tcId = tcId;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerOf phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public CustomerOf enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Sex getSex() {
        return sex;
    }

    public CustomerOf sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public User getCustomer() {
        return customer;
    }

    public CustomerOf customer(User user) {
        this.customer = user;
        return this;
    }

    public void setCustomer(User user) {
        this.customer = user;
    }

    public Company getCompany() {
        return company;
    }

    public CustomerOf company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerOf)) {
            return false;
        }
        return id != null && id.equals(((CustomerOf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerOf{" +
            "id=" + getId() +
            ", tcId=" + getTcId() +
            ", phone='" + getPhone() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", sex='" + getSex() + "'" +
            "}";
    }
}
