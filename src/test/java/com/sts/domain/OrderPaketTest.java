package com.sts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class OrderPaketTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPaket.class);
        OrderPaket orderPaket1 = new OrderPaket();
        orderPaket1.setId(1L);
        OrderPaket orderPaket2 = new OrderPaket();
        orderPaket2.setId(orderPaket1.getId());
        assertThat(orderPaket1).isEqualTo(orderPaket2);
        orderPaket2.setId(2L);
        assertThat(orderPaket1).isNotEqualTo(orderPaket2);
        orderPaket1.setId(null);
        assertThat(orderPaket1).isNotEqualTo(orderPaket2);
    }
}
