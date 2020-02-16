package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentToTravelPathMapperTest {

    private StudentToTravelPathMapper studentToTravelPathMapper;

    @BeforeEach
    public void setUp() {
        studentToTravelPathMapper = new StudentToTravelPathMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(studentToTravelPathMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(studentToTravelPathMapper.fromId(null)).isNull();
    }
}
