package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaketDetayMapperTest {

    private PaketDetayMapper paketDetayMapper;

    @BeforeEach
    public void setUp() {
        paketDetayMapper = new PaketDetayMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paketDetayMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paketDetayMapper.fromId(null)).isNull();
    }
}
