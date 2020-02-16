package com.sts.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sts.domain.FamilyMember} entity.
 */
public class FamilyMemberDTO implements Serializable {

    private Long id;


    private Long parentId;

    private String parentTcId;

    private Long childId;

    private String childTcId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long customerOfId) {
        this.parentId = customerOfId;
    }

    public String getParentTcId() {
        return parentTcId;
    }

    public void setParentTcId(String customerOfTcId) {
        this.parentTcId = customerOfTcId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long studentId) {
        this.childId = studentId;
    }

    public String getChildTcId() {
        return childTcId;
    }

    public void setChildTcId(String studentTcId) {
        this.childTcId = studentTcId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FamilyMemberDTO familyMemberDTO = (FamilyMemberDTO) o;
        if (familyMemberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), familyMemberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FamilyMemberDTO{" +
            "id=" + getId() +
            ", parentId=" + getParentId() +
            ", parentTcId='" + getParentTcId() + "'" +
            ", childId=" + getChildId() +
            ", childTcId='" + getChildTcId() + "'" +
            "}";
    }
}
