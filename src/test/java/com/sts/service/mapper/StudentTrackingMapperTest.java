package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentTrackingMapperTest {

    private StudentTrackingMapper studentTrackingMapper;

    @BeforeEach
    public void setUp() {
        studentTrackingMapper = new StudentTrackingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(studentTrackingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(studentTrackingMapper.fromId(null)).isNull();
    }
}
