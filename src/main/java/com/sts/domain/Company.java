package com.sts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 250)
    @Column(name = "name", length = 250, nullable = false, unique = true)
    private String name;

    @NotNull
    @Pattern(regexp = "^\\+90[0-9]{10}$")
    @Column(name = "phone", nullable = false)
    private String phone;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(min = 3, max = 250)
    @Column(name = "address_line", length = 250, nullable = false)
    private String addressLine;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "city", length = 20, nullable = false)
    private String city;

    @Column(name = "postal_code")
    private Integer postalCode;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "country", length = 20, nullable = false)
    private String country;

    @Size(max = 250)
    @Column(name = "url", length = 250)
    private String url;

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

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Company phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public Company email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public Company addressLine(String addressLine) {
        this.addressLine = addressLine;
        return this;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public Company city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public Company postalCode(Integer postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public Company country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public Company url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getLon() {
        return lon;
    }

    public Company lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Company lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", addressLine='" + getAddressLine() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode=" + getPostalCode() +
            ", country='" + getCountry() + "'" +
            ", url='" + getUrl() + "'" +
            ", lon=" + getLon() +
            ", lat=" + getLat() +
            "}";
    }
}
