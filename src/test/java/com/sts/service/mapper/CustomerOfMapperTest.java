package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerOfMapperTest {

    private CustomerOfMapper customerOfMapper;

    @BeforeEach
    public void setUp() {
        customerOfMapper = new CustomerOfMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerOfMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerOfMapper.fromId(null)).isNull();
    }
}
