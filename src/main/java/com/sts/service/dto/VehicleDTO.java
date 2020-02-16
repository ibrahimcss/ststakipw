package com.sts.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sts.domain.Vehicle} entity.
 */
public class VehicleDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    private String licencePlate;

    @NotNull
    @Size(min = 3, max = 20)
    private String brand;

    @NotNull
    @Min(value = 2000)
    private Integer model;

    @NotNull
    @Min(value = 1)
    private Integer quota;

    @NotNull
    private Boolean enabled;


    private Long companyId;

    private String companyName;

    private Long credentialId;

    private String credentialLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
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

    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long userId) {
        this.credentialId = userId;
    }

    public String getCredentialLogin() {
        return credentialLogin;
    }

    public void setCredentialLogin(String userLogin) {
        this.credentialLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleDTO vehicleDTO = (VehicleDTO) o;
        if (vehicleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", licencePlate='" + getLicencePlate() + "'" +
            ", brand='" + getBrand() + "'" +
            ", model=" + getModel() +
            ", quota=" + getQuota() +
            ", enabled='" + isEnabled() + "'" +
            ", companyId=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            ", credentialId=" + getCredentialId() +
            ", credentialLogin='" + getCredentialLogin() + "'" +
            "}";
    }
}
