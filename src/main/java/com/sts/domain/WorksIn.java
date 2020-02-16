package com.sts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A WorksIn.
 */
@Entity
@Table(name = "works_in")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "worksin")
public class WorksIn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 10000000000L)
    @Max(value = 99999999999L)
    @Column(name = "tc_id", nullable = false)
    private Long tcId;

    @NotNull
    @Pattern(regexp = "^\\+90[0-9]{10}$")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("worksIns")
    private User employee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("worksIns")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTcId() {
        return tcId;
    }

    public WorksIn tcId(Long tcId) {
        this.tcId = tcId;
        return this;
    }

    public void setTcId(Long tcId) {
        this.tcId = tcId;
    }

    public String getPhone() {
        return phone;
    }

    public WorksIn phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public WorksIn enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public User getEmployee() {
        return employee;
    }

    public WorksIn employee(User user) {
        this.employee = user;
        return this;
    }

    public void setEmployee(User user) {
        this.employee = user;
    }

    public Company getCompany() {
        return company;
    }

    public WorksIn company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorksIn)) {
            return false;
        }
        return id != null && id.equals(((WorksIn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WorksIn{" +
            "id=" + getId() +
            ", tcId=" + getTcId() +
            ", phone='" + getPhone() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
