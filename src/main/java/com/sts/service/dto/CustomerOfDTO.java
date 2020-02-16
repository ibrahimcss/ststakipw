package com.sts.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.sts.domain.enumeration.Sex;

/**
 * A DTO for the {@link com.sts.domain.CustomerOf} entity.
 */
public class CustomerOfDTO implements Serializable {

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

    @NotNull
    private Sex sex;


    private Long customerId;

    private String customerLogin;

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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long userId) {
        this.customerId = userId;
    }

    public String getCustomerLogin() {
        return customerLogin;
    }

    public void setCustomerLogin(String userLogin) {
        this.customerLogin = userLogin;
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

        CustomerOfDTO customerOfDTO = (CustomerOfDTO) o;
        if (customerOfDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerOfDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerOfDTO{" +
            "id=" + getId() +
            ", tcId=" + getTcId() +
            ", phone='" + getPhone() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", sex='" + getSex() + "'" +
            ", customerId=" + getCustomerId() +
            ", customerLogin='" + getCustomerLogin() + "'" +
            ", companyId=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
