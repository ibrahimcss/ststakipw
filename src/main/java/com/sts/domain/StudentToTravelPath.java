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
 * A StudentToTravelPath.
 */
@Entity
@Table(name = "student_to_travel_path")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "studenttotravelpath")
public class StudentToTravelPath implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "boarding_time_of_arrival", nullable = false)
    private Instant boardingTimeOfArrival;

    @NotNull
    @Column(name = "landing_time_of_arrival", nullable = false)
    private Instant landingTimeOfArrival;

    @NotNull
    @Column(name = "boarding_time_of_return", nullable = false)
    private Instant boardingTimeOfReturn;

    @NotNull
    @Column(name = "landing_time_of_return", nullable = false)
    private Instant landingTimeOfReturn;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("studentToTravelPaths")
    private Student student;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("studentToTravelPaths")
    private TravelPath travelPath;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBoardingTimeOfArrival() {
        return boardingTimeOfArrival;
    }

    public StudentToTravelPath boardingTimeOfArrival(Instant boardingTimeOfArrival) {
        this.boardingTimeOfArrival = boardingTimeOfArrival;
        return this;
    }

    public void setBoardingTimeOfArrival(Instant boardingTimeOfArrival) {
        this.boardingTimeOfArrival = boardingTimeOfArrival;
    }

    public Instant getLandingTimeOfArrival() {
        return landingTimeOfArrival;
    }

    public StudentToTravelPath landingTimeOfArrival(Instant landingTimeOfArrival) {
        this.landingTimeOfArrival = landingTimeOfArrival;
        return this;
    }

    public void setLandingTimeOfArrival(Instant landingTimeOfArrival) {
        this.landingTimeOfArrival = landingTimeOfArrival;
    }

    public Instant getBoardingTimeOfReturn() {
        return boardingTimeOfReturn;
    }

    public StudentToTravelPath boardingTimeOfReturn(Instant boardingTimeOfReturn) {
        this.boardingTimeOfReturn = boardingTimeOfReturn;
        return this;
    }

    public void setBoardingTimeOfReturn(Instant boardingTimeOfReturn) {
        this.boardingTimeOfReturn = boardingTimeOfReturn;
    }

    public Instant getLandingTimeOfReturn() {
        return landingTimeOfReturn;
    }

    public StudentToTravelPath landingTimeOfReturn(Instant landingTimeOfReturn) {
        this.landingTimeOfReturn = landingTimeOfReturn;
        return this;
    }

    public void setLandingTimeOfReturn(Instant landingTimeOfReturn) {
        this.landingTimeOfReturn = landingTimeOfReturn;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public StudentToTravelPath enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Student getStudent() {
        return student;
    }

    public StudentToTravelPath student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TravelPath getTravelPath() {
        return travelPath;
    }

    public StudentToTravelPath travelPath(TravelPath travelPath) {
        this.travelPath = travelPath;
        return this;
    }

    public void setTravelPath(TravelPath travelPath) {
        this.travelPath = travelPath;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentToTravelPath)) {
            return false;
        }
        return id != null && id.equals(((StudentToTravelPath) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StudentToTravelPath{" +
            "id=" + getId() +
            ", boardingTimeOfArrival='" + getBoardingTimeOfArrival() + "'" +
            ", landingTimeOfArrival='" + getLandingTimeOfArrival() + "'" +
            ", boardingTimeOfReturn='" + getBoardingTimeOfReturn() + "'" +
            ", landingTimeOfReturn='" + getLandingTimeOfReturn() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
