package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderPaketMapperTest {

    private OrderPaketMapper orderPaketMapper;

    @BeforeEach
    public void setUp() {
        orderPaketMapper = new OrderPaketMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(orderPaketMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(orderPaketMapper.fromId(null)).isNull();
    }
}
