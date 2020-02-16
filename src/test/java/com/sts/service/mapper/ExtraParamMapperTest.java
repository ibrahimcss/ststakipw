package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtraParamMapperTest {

    private ExtraParamMapper extraParamMapper;

    @BeforeEach
    public void setUp() {
        extraParamMapper = new ExtraParamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(extraParamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(extraParamMapper.fromId(null)).isNull();
    }
}
