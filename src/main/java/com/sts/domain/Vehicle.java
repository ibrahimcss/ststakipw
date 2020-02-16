package com.sts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "licence_plate", length = 20, nullable = false)
    private String licencePlate;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "brand", length = 20, nullable = false)
    private String brand;

    @NotNull
    @Min(value = 2000)
    @Column(name = "model", nullable = false)
    private Integer model;

    @NotNull
    @Min(value = 1)
    @Column(name = "quota", nullable = false)
    private Integer quota;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vehicles")
    private Company company;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vehicles")
    private User credential;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public Vehicle licencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
        return this;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getBrand() {
        return brand;
    }

    public Vehicle brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getModel() {
        return model;
    }

    public Vehicle model(Integer model) {
        this.model = model;
        return this;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getQuota() {
        return quota;
    }

    public Vehicle quota(Integer quota) {
        this.quota = quota;
        return this;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Vehicle enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Company getCompany() {
        return company;
    }

    public Vehicle company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getCredential() {
        return credential;
    }

    public Vehicle credential(User user) {
        this.credential = user;
        return this;
    }

    public void setCredential(User user) {
        this.credential = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", licencePlate='" + getLicencePlate() + "'" +
            ", brand='" + getBrand() + "'" +
            ", model=" + getModel() +
            ", quota=" + getQuota() +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
