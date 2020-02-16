package com.sts.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sts.domain.WorksIn} entity.
 */
public class WorksInDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 10000000000L)
    @Max(value = 99999999999L)
    private Long tcId;

    @NotNull
    @Pattern(regexp = "^\\+90[0-9]{10}$")
    private String phone;

    @NotNull
    private Boolean enabled;


    private Long employeeId;

    private String employeeLogin;

    private Long companyId;

    private String companyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTcId() {
        return tcId;
    }

    public void setTcId(Long tcId) {
        this.tcId = tcId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

        WorksInDTO worksInDTO = (WorksInDTO) o;
        if (worksInDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), worksInDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorksInDTO{" +
            "id=" + getId() +
            ", tcId=" + getTcId() +
            ", phone='" + getPhone() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeLogin='" + getEmployeeLogin() + "'" +
            ", companyId=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
