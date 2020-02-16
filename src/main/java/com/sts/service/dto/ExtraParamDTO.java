package com.sts.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sts.domain.ExtraParam} entity.
 */
public class ExtraParamDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    private String key;

    @NotNull
    @Size(min = 1, max = 250)
    private String value;


    private Long companyId;

    private String companyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

        ExtraParamDTO extraParamDTO = (ExtraParamDTO) o;
        if (extraParamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extraParamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtraParamDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            ", companyId=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
