package com.sts.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sts.domain.StudentToTravelPath} entity.
 */
public class StudentToTravelPathDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant boardingTimeOfArrival;

    @NotNull
    private Instant landingTimeOfArrival;

    @NotNull
    private Instant boardingTimeOfReturn;

    @NotNull
    private Instant landingTimeOfReturn;

    @NotNull
    private Boolean enabled;


    private Long studentId;

    private String studentTcId;

    private Long travelPathId;

    private String travelPathName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBoardingTimeOfArrival() {
        return boardingTimeOfArrival;
    }

    public void setBoardingTimeOfArrival(Instant boardingTimeOfArrival) {
        this.boardingTimeOfArrival = boardingTimeOfArrival;
    }

    public Instant getLandingTimeOfArrival() {
        return landingTimeOfArrival;
    }

    public void setLandingTimeOfArrival(Instant landingTimeOfArrival) {
        this.landingTimeOfArrival = landingTimeOfArrival;
    }

    public Instant getBoardingTimeOfReturn() {
        return boardingTimeOfReturn;
    }

    public void setBoardingTimeOfReturn(Instant boardingTimeOfReturn) {
        this.boardingTimeOfReturn = boardingTimeOfReturn;
    }

    public Instant getLandingTimeOfReturn() {
        return landingTimeOfReturn;
    }

    public void setLandingTimeOfReturn(Instant landingTimeOfReturn) {
        this.landingTimeOfReturn = landingTimeOfReturn;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public Long getTravelPathId() {
        return travelPathId;
    }

    public void setTravelPathId(Long travelPathId) {
        this.travelPathId = travelPathId;
    }

    public String getTravelPathName() {
        return travelPathName;
    }

    public void setTravelPathName(String travelPathName) {
        this.travelPathName = travelPathName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentToTravelPathDTO studentToTravelPathDTO = (StudentToTravelPathDTO) o;
        if (studentToTravelPathDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentToTravelPathDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentToTravelPathDTO{" +
            "id=" + getId() +
            ", boardingTimeOfArrival='" + getBoardingTimeOfArrival() + "'" +
            ", landingTimeOfArrival='" + getLandingTimeOfArrival() + "'" +
            ", boardingTimeOfReturn='" + getBoardingTimeOfReturn() + "'" +
            ", landingTimeOfReturn='" + getLandingTimeOfReturn() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", studentId=" + getStudentId() +
            ", studentTcId='" + getStudentTcId() + "'" +
            ", travelPathId=" + getTravelPathId() +
            ", travelPathName='" + getTravelPathName() + "'" +
            "}";
    }
}
