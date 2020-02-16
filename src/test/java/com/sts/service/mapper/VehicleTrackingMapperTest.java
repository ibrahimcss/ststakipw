package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VehicleTrackingMapperTest {

    private VehicleTrackingMapper vehicleTrackingMapper;

    @BeforeEach
    public void setUp() {
        vehicleTrackingMapper = new VehicleTrackingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(vehicleTrackingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vehicleTrackingMapper.fromId(null)).isNull();
    }
}
