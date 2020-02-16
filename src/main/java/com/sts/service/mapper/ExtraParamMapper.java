package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.ExtraParamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExtraParam} and its DTO {@link ExtraParamDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface ExtraParamMapper extends EntityMapper<ExtraParamDTO, ExtraParam> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    ExtraParamDTO toDto(ExtraParam extraParam);

    @Mapping(source = "companyId", target = "company")
    ExtraParam toEntity(ExtraParamDTO extraParamDTO);

    default ExtraParam fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExtraParam extraParam = new ExtraParam();
        extraParam.setId(id);
        return extraParam;
    }
}
