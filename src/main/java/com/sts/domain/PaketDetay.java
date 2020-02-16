package com.sts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.sts.domain.enumeration.PaketEnum;

/**
 * A PaketDetay.
 */
@Entity
@Table(name = "paket_detay")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paketdetay")
public class PaketDetay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private PaketEnum name;

    @NotNull
    @Size(min = 5)
    @Column(name = "description", nullable = false)
    private String description;

    @Min(value = 1L)
    @Column(name = "price")
    private Long price;

    @Min(value = 5)
    @Max(value = 1000)
    @Column(name = "vehicle_quota")
    private Integer vehicleQuota;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "enabled")
    private Boolean enabled;

    @NotNull
    @Min(value = 1)
    @Column(name = "year", nullable = false)
    private Integer year;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaketEnum getName() {
        return name;
    }

    public PaketDetay name(PaketEnum name) {
        this.name = name;
        return this;
    }

    public void setName(PaketEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public PaketDetay description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public PaketDetay price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getVehicleQuota() {
        return vehicleQuota;
    }

    public PaketDetay vehicleQuota(Integer vehicleQuota) {
        this.vehicleQuota = vehicleQuota;
        return this;
    }

    public void setVehicleQuota(Integer vehicleQuota) {
        this.vehicleQuota = vehicleQuota;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public PaketDetay photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public PaketDetay photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public PaketDetay enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getYear() {
        return year;
    }

    public PaketDetay year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaketDetay)) {
            return false;
        }
        return id != null && id.equals(((PaketDetay) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaketDetay{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", vehicleQuota=" + getVehicleQuota() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", year=" + getYear() +
            "}";
    }
}
