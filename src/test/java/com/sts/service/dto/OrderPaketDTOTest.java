package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class OrderPaketDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPaketDTO.class);
        OrderPaketDTO orderPaketDTO1 = new OrderPaketDTO();
        orderPaketDTO1.setId(1L);
        OrderPaketDTO orderPaketDTO2 = new OrderPaketDTO();
        assertThat(orderPaketDTO1).isNotEqualTo(orderPaketDTO2);
        orderPaketDTO2.setId(orderPaketDTO1.getId());
        assertThat(orderPaketDTO1).isEqualTo(orderPaketDTO2);
        orderPaketDTO2.setId(2L);
        assertThat(orderPaketDTO1).isNotEqualTo(orderPaketDTO2);
        orderPaketDTO1.setId(null);
        assertThat(orderPaketDTO1).isNotEqualTo(orderPaketDTO2);
    }
}
