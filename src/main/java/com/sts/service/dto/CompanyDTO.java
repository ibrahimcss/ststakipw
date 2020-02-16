package com.sts.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sts.domain.Company} entity.
 */
public class CompanyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 250)
    private String name;

    @NotNull
    @Pattern(regexp = "^\\+90[0-9]{10}$")
    private String phone;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    @NotNull
    @Size(min = 3, max = 250)
    private String addressLine;

    @NotNull
    @Size(min = 3, max = 20)
    private String city;

    private Integer postalCode;

    @NotNull
    @Size(min = 3, max = 20)
    private String country;

    @Size(max = 250)
    private String url;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double lon;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double lat;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (companyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
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
