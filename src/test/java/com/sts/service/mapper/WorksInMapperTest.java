package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WorksInMapperTest {

    private WorksInMapper worksInMapper;

    @BeforeEach
    public void setUp() {
        worksInMapper = new WorksInMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(worksInMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(worksInMapper.fromId(null)).isNull();
    }
}
