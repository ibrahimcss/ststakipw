package com.sts.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sts.domain.TravelPath} entity.
 */
public class TravelPathDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 250)
    private String name;

    @NotNull
    private Instant departureTime;

    @NotNull
    private String departAddress;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double departLon;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double departLat;

    @NotNull
    private Instant arrivalTime;

    @NotNull
    private String arrivalAddress;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double arrivalLon;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double arrivalLat;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private Boolean enabled;


    private Long companyId;

    private String companyName;

    private Long employeeId;

    private String employeeLogin;

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

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartAddress() {
        return departAddress;
    }

    public void setDepartAddress(String departAddress) {
        this.departAddress = departAddress;
    }

    public Double getDepartLon() {
        return departLon;
    }

    public void setDepartLon(Double departLon) {
        this.departLon = departLon;
    }

    public Double getDepartLat() {
        return departLat;
    }

    public void setDepartLat(Double departLat) {
        this.departLat = departLat;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    public Double getArrivalLon() {
        return arrivalLon;
    }

    public void setArrivalLon(Double arrivalLon) {
        this.arrivalLon = arrivalLon;
    }

    public Double getArrivalLat() {
        return arrivalLat;
    }

    public void setArrivalLat(Double arrivalLat) {
        this.arrivalLat = arrivalLat;
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

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long userId) {
        this.employeeId = userId;
    }

    public String getEmployeeLogin() {
        return employeeLogin;
    }

    public void setEmployeeLogin(String userLogin) {
        this.employeeLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TravelPathDTO travelPathDTO = (TravelPathDTO) o;
        if (travelPathDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), travelPathDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TravelPathDTO{" +
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
            ", companyId=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeLogin='" + getEmployeeLogin() + "'" +
            "}";
    }
}
