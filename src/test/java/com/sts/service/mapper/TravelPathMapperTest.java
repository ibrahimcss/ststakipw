package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TravelPathMapperTest {

    private TravelPathMapper travelPathMapper;

    @BeforeEach
    public void setUp() {
        travelPathMapper = new TravelPathMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(travelPathMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(travelPathMapper.fromId(null)).isNull();
    }
}
