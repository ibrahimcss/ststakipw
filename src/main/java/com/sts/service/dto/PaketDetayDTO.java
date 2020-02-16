package com.sts.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.sts.domain.enumeration.PaketEnum;

/**
 * A DTO for the {@link com.sts.domain.PaketDetay} entity.
 */
public class PaketDetayDTO implements Serializable {

    private Long id;

    @NotNull
    private PaketEnum name;

    @NotNull
    @Size(min = 5)
    private String description;

    @Min(value = 1L)
    private Long price;

    @Min(value = 5)
    @Max(value = 1000)
    private Integer vehicleQuota;

    @Lob
    private byte[] photo;

    private String photoContentType;
    private Boolean enabled;

    @NotNull
    @Min(value = 1)
    private Integer year;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaketEnum getName() {
        return name;
    }

    public void setName(PaketEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getVehicleQuota() {
        return vehicleQuota;
    }

    public void setVehicleQuota(Integer vehicleQuota) {
        this.vehicleQuota = vehicleQuota;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaketDetayDTO paketDetayDTO = (PaketDetayDTO) o;
        if (paketDetayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paketDetayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaketDetayDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", vehicleQuota=" + getVehicleQuota() +
            ", photo='" + getPhoto() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", year=" + getYear() +
            "}";
    }
}
