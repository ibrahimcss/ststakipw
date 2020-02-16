package com.sts.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sts.domain.VehicleTracking} entity.
 */
public class VehicleTrackingDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant createdAt;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double lon;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double lat;


    private Long vehicleId;

    private String vehicleLicencePlate;

    private Long companyId;

    private String companyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleLicencePlate() {
        return vehicleLicencePlate;
    }

    public void setVehicleLicencePlate(String vehicleLicencePlate) {
        this.vehicleLicencePlate = vehicleLicencePlate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleTrackingDTO vehicleTrackingDTO = (VehicleTrackingDTO) o;
        if (vehicleTrackingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleTrackingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleTrackingDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", lon=" + getLon() +
            ", lat=" + getLat() +
            ", vehicleId=" + getVehicleId() +
            ", vehicleLicencePlate='" + getVehicleLicencePlate() + "'" +
            ", companyId=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
