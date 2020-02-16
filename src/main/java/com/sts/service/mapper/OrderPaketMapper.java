package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.OrderPaketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderPaket} and its DTO {@link OrderPaketDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, PaketDetayMapper.class})
public interface OrderPaketMapper extends EntityMapper<OrderPaketDTO, OrderPaket> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "paketDetay.id", target = "paketDetayId")
    @Mapping(source = "paketDetay.name", target = "paketDetayName")
    OrderPaketDTO toDto(OrderPaket orderPaket);

    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "paketDetayId", target = "paketDetay")
    OrderPaket toEntity(OrderPaketDTO orderPaketDTO);

    default OrderPaket fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderPaket orderPaket = new OrderPaket();
        orderPaket.setId(id);
        return orderPaket;
    }
}
