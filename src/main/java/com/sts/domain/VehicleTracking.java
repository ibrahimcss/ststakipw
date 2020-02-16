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
 * A VehicleTracking.
 */
@Entity
@Table(name = "vehicle_tracking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vehicletracking")
public class VehicleTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    @Column(name = "lon", nullable = false)
    private Double lon;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    @Column(name = "lat", nullable = false)
    private Double lat;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vehicleTrackings")
    private Vehicle vehicle;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vehicleTrackings")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public VehicleTracking createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Double getLon() {
        return lon;
    }

    public VehicleTracking lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public VehicleTracking lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehicleTracking vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Company getCompany() {
        return company;
    }

    public VehicleTracking company(Company company) {
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
        if (!(o instanceof VehicleTracking)) {
            return false;
        }
        return id != null && id.equals(((VehicleTracking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VehicleTracking{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", lon=" + getLon() +
            ", lat=" + getLat() +
            "}";
    }
}
