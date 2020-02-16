package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class VehicleTrackingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleTrackingDTO.class);
        VehicleTrackingDTO vehicleTrackingDTO1 = new VehicleTrackingDTO();
        vehicleTrackingDTO1.setId(1L);
        VehicleTrackingDTO vehicleTrackingDTO2 = new VehicleTrackingDTO();
        assertThat(vehicleTrackingDTO1).isNotEqualTo(vehicleTrackingDTO2);
        vehicleTrackingDTO2.setId(vehicleTrackingDTO1.getId());
        assertThat(vehicleTrackingDTO1).isEqualTo(vehicleTrackingDTO2);
        vehicleTrackingDTO2.setId(2L);
        assertThat(vehicleTrackingDTO1).isNotEqualTo(vehicleTrackingDTO2);
        vehicleTrackingDTO1.setId(null);
        assertThat(vehicleTrackingDTO1).isNotEqualTo(vehicleTrackingDTO2);
    }
}
