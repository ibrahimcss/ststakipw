package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.VehicleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vehicle} and its DTO {@link VehicleDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, UserMapper.class})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "credential.id", target = "credentialId")
    @Mapping(source = "credential.login", target = "credentialLogin")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "credentialId", target = "credential")
    Vehicle toEntity(VehicleDTO vehicleDTO);

    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}
