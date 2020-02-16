package com.sts.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.sts.domain.VehicleTracking} entity. This class is used
 * in {@link com.sts.web.rest.VehicleTrackingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-trackings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleTrackingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdAt;

    private DoubleFilter lon;

    private DoubleFilter lat;

    private LongFilter vehicleId;

    private LongFilter companyId;

    public VehicleTrackingCriteria() {
    }

    public VehicleTrackingCriteria(VehicleTrackingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.lon = other.lon == null ? null : other.lon.copy();
        this.lat = other.lat == null ? null : other.lat.copy();
        this.vehicleId = other.vehicleId == null ? null : other.vehicleId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
    }

    @Override
    public VehicleTrackingCriteria copy() {
        return new VehicleTrackingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public DoubleFilter getLon() {
        return lon;
    }

    public void setLon(DoubleFilter lon) {
        this.lon = lon;
    }

    public DoubleFilter getLat() {
        return lat;
    }

    public void setLat(DoubleFilter lat) {
        this.lat = lat;
    }

    public LongFilter getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(LongFilter vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleTrackingCriteria that = (VehicleTrackingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(lon, that.lon) &&
            Objects.equals(lat, that.lat) &&
            Objects.equals(vehicleId, that.vehicleId) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdAt,
        lon,
        lat,
        vehicleId,
        companyId
        );
    }

    @Override
    public String toString() {
        return "VehicleTrackingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (lon != null ? "lon=" + lon + ", " : "") +
                (lat != null ? "lat=" + lat + ", " : "") +
                (vehicleId != null ? "vehicleId=" + vehicleId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
