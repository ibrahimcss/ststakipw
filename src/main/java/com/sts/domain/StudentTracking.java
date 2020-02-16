package com.sts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.sts.domain.enumeration.TrackingStep;

/**
 * A StudentTracking.
 */
@Entity
@Table(name = "student_tracking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "studenttracking")
public class StudentTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "t_step", nullable = false)
    private TrackingStep tStep;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("studentTrackings")
    private StudentToTravelPath studentToTravelPath;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("studentTrackings")
    private Vehicle vehicle;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("studentTrackings")
    private Student student;

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

    public StudentTracking createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public StudentTracking photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public StudentTracking photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Double getLon() {
        return lon;
    }

    public StudentTracking lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public StudentTracking lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public TrackingStep gettStep() {
        return tStep;
    }

    public StudentTracking tStep(TrackingStep tStep) {
        this.tStep = tStep;
        return this;
    }

    public void settStep(TrackingStep tStep) {
        this.tStep = tStep;
    }

    public StudentToTravelPath getStudentToTravelPath() {
        return studentToTravelPath;
    }

    public StudentTracking studentToTravelPath(StudentToTravelPath studentToTravelPath) {
        this.studentToTravelPath = studentToTravelPath;
        return this;
    }

    public void setStudentToTravelPath(StudentToTravelPath studentToTravelPath) {
        this.studentToTravelPath = studentToTravelPath;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public StudentTracking vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Student getStudent() {
        return student;
    }

    public StudentTracking student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentTracking)) {
            return false;
        }
        return id != null && id.equals(((StudentTracking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StudentTracking{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", lon=" + getLon() +
            ", lat=" + getLat() +
            ", tStep='" + gettStep() + "'" +
            "}";
    }
}
