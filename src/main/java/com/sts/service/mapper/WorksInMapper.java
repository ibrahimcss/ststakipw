package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.WorksInDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorksIn} and its DTO {@link WorksInDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CompanyMapper.class})
public interface WorksInMapper extends EntityMapper<WorksInDTO, WorksIn> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.login", target = "employeeLogin")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    WorksInDTO toDto(WorksIn worksIn);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "companyId", target = "company")
    WorksIn toEntity(WorksInDTO worksInDTO);

    default WorksIn fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorksIn worksIn = new WorksIn();
        worksIn.setId(id);
        return worksIn;
    }
}
