package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.PaketDetayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaketDetay} and its DTO {@link PaketDetayDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaketDetayMapper extends EntityMapper<PaketDetayDTO, PaketDetay> {



    default PaketDetay fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaketDetay paketDetay = new PaketDetay();
        paketDetay.setId(id);
        return paketDetay;
    }
}
