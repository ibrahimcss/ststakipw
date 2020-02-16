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
 * A TravelPath.
 */
@Entity
@Table(name = "travel_path")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "travelpath")
public class TravelPath implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 250)
    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private Instant departureTime;

    @NotNull
    @Column(name = "depart_address", nullable = false)
    private String departAddress;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    @Column(name = "depart_lon", nullable = false)
    private Double departLon;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    @Column(name = "depart_lat", nullable = false)
    private Double departLat;

    @NotNull
    @Column(name = "arrival_time", nullable = false)
    private Instant arrivalTime;

    @NotNull
    @Column(name = "arrival_address", nullable = false)
    private String arrivalAddress;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    @Column(name = "arrival_lon", nullable = false)
    private Double arrivalLon;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    @Column(name = "arrival_lat", nullable = false)
    private Double arrivalLat;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("travelPaths")
    private Company company;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("travelPaths")
    private User employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TravelPath name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public TravelPath departureTime(Instant departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartAddress() {
        return departAddress;
    }

    public TravelPath departAddress(String departAddress) {
        this.departAddress = departAddress;
        return this;
    }

    public void setDepartAddress(String departAddress) {
        this.departAddress = departAddress;
    }

    public Double getDepartLon() {
        return departLon;
    }

    public TravelPath departLon(Double departLon) {
        this.departLon = departLon;
        return this;
    }

    public void setDepartLon(Double departLon) {
        this.departLon = departLon;
    }

    public Double getDepartLat() {
        return departLat;
    }

    public TravelPath departLat(Double departLat) {
        this.departLat = departLat;
        return this;
    }

    public void setDepartLat(Double departLat) {
        this.departLat = departLat;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public TravelPath arrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalAddress() {
        return arrivalAddress;
    }

    public TravelPath arrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
        return this;
    }

    public void setArrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    public Double getArrivalLon() {
        return arrivalLon;
    }

    public TravelPath arrivalLon(Double arrivalLon) {
        this.arrivalLon = arrivalLon;
        return this;
    }

    public void setArrivalLon(Double arrivalLon) {
        this.arrivalLon = arrivalLon;
    }

    public Double getArrivalLat() {
        return arrivalLat;
    }

    public TravelPath arrivalLat(Double arrivalLat) {
        this.arrivalLat = arrivalLat;
        return this;
    }

    public void setArrivalLat(Double arrivalLat) {
        this.arrivalLat = arrivalLat;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public TravelPath startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public TravelPath endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public TravelPath enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Company getCompany() {
        return company;
    }

    public TravelPath company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getEmployee() {
        return employee;
    }

    public TravelPath employee(User user) {
        this.employee = user;
        return this;
    }

    public void setEmployee(User user) {
        this.employee = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TravelPath)) {
            return false;
        }
        return id != null && id.equals(((TravelPath) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TravelPath{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", departureTime='" + getDepartureTime() + "'" +
            ", departAddress='" + getDepartAddress() + "'" +
            ", departLon=" + getDepartLon() +
            ", departLat=" + getDepartLat() +
            ", arrivalTime='" + getArrivalTime() + "'" +
            ", arrivalAddress='" + getArrivalAddress() + "'" +
            ", arrivalLon=" + getArrivalLon() +
            ", arrivalLat=" + getArrivalLat() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
