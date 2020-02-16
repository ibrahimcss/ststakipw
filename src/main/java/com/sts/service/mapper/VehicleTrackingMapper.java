package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.VehicleTrackingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleTracking} and its DTO {@link VehicleTrackingDTO}.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class, CompanyMapper.class})
public interface VehicleTrackingMapper extends EntityMapper<VehicleTrackingDTO, VehicleTracking> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.licencePlate", target = "vehicleLicencePlate")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    VehicleTrackingDTO toDto(VehicleTracking vehicleTracking);

    @Mapping(source = "vehicleId", target = "vehicle")
    @Mapping(source = "companyId", target = "company")
    VehicleTracking toEntity(VehicleTrackingDTO vehicleTrackingDTO);

    default VehicleTracking fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleTracking vehicleTracking = new VehicleTracking();
        vehicleTracking.setId(id);
        return vehicleTracking;
    }
}
