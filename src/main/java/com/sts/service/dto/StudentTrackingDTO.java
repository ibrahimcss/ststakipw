package com.sts.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.sts.domain.enumeration.TrackingStep;

/**
 * A DTO for the {@link com.sts.domain.StudentTracking} entity.
 */
public class StudentTrackingDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant createdAt;

    @Lob
    private byte[] photo;

    private String photoContentType;
    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double lon;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double lat;

    @NotNull
    private TrackingStep tStep;


    private Long studentToTravelPathId;

    private Long vehicleId;

    private String vehicleLicencePlate;

    private Long studentId;

    private String studentTcId;

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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
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

    public TrackingStep gettStep() {
        return tStep;
    }

    public void settStep(TrackingStep tStep) {
        this.tStep = tStep;
    }

    public Long getStudentToTravelPathId() {
        return studentToTravelPathId;
    }

    public void setStudentToTravelPathId(Long studentToTravelPathId) {
        this.studentToTravelPathId = studentToTravelPathId;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentTcId() {
        return studentTcId;
    }

    public void setStudentTcId(String studentTcId) {
        this.studentTcId = studentTcId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentTrackingDTO studentTrackingDTO = (StudentTrackingDTO) o;
        if (studentTrackingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentTrackingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentTrackingDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", lon=" + getLon() +
            ", lat=" + getLat() +
            ", tStep='" + gettStep() + "'" +
            ", studentToTravelPathId=" + getStudentToTravelPathId() +
            ", vehicleId=" + getVehicleId() +
            ", vehicleLicencePlate='" + getVehicleLicencePlate() + "'" +
            ", studentId=" + getStudentId() +
            ", studentTcId='" + getStudentTcId() + "'" +
            "}";
    }
}
