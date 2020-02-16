package com.sts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class VehicleTrackingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleTracking.class);
        VehicleTracking vehicleTracking1 = new VehicleTracking();
        vehicleTracking1.setId(1L);
        VehicleTracking vehicleTracking2 = new VehicleTracking();
        vehicleTracking2.setId(vehicleTracking1.getId());
        assertThat(vehicleTracking1).isEqualTo(vehicleTracking2);
        vehicleTracking2.setId(2L);
        assertThat(vehicleTracking1).isNotEqualTo(vehicleTracking2);
        vehicleTracking1.setId(null);
        assertThat(vehicleTracking1).isNotEqualTo(vehicleTracking2);
    }
}
