package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.TravelPathDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPath} and its DTO {@link TravelPathDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, UserMapper.class})
public interface TravelPathMapper extends EntityMapper<TravelPathDTO, TravelPath> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.login", target = "employeeLogin")
    TravelPathDTO toDto(TravelPath travelPath);

    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "employeeId", target = "employee")
    TravelPath toEntity(TravelPathDTO travelPathDTO);

    default TravelPath fromId(Long id) {
        if (id == null) {
            return null;
        }
        TravelPath travelPath = new TravelPath();
        travelPath.setId(id);
        return travelPath;
    }
}
