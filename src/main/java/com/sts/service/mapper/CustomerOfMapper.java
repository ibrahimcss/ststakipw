package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.CustomerOfDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerOf} and its DTO {@link CustomerOfDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CompanyMapper.class})
public interface CustomerOfMapper extends EntityMapper<CustomerOfDTO, CustomerOf> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.login", target = "customerLogin")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    CustomerOfDTO toDto(CustomerOf customerOf);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "companyId", target = "company")
    CustomerOf toEntity(CustomerOfDTO customerOfDTO);

    default CustomerOf fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerOf customerOf = new CustomerOf();
        customerOf.setId(id);
        return customerOf;
    }
}
