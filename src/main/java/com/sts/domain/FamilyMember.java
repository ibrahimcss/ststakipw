package com.sts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A FamilyMember.
 */
@Entity
@Table(name = "family_member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "familymember")
public class FamilyMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("familyMembers")
    private CustomerOf parent;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("familyMembers")
    private Student child;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerOf getParent() {
        return parent;
    }

    public FamilyMember parent(CustomerOf customerOf) {
        this.parent = customerOf;
        return this;
    }

    public void setParent(CustomerOf customerOf) {
        this.parent = customerOf;
    }

    public Student getChild() {
        return child;
    }

    public FamilyMember child(Student student) {
        this.child = student;
        return this;
    }

    public void setChild(Student student) {
        this.child = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyMember)) {
            return false;
        }
        return id != null && id.equals(((FamilyMember) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
            "id=" + getId() +
            "}";
    }
}
