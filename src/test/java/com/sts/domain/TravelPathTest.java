package com.sts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class TravelPathTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravelPath.class);
        TravelPath travelPath1 = new TravelPath();
        travelPath1.setId(1L);
        TravelPath travelPath2 = new TravelPath();
        travelPath2.setId(travelPath1.getId());
        assertThat(travelPath1).isEqualTo(travelPath2);
        travelPath2.setId(2L);
        assertThat(travelPath1).isNotEqualTo(travelPath2);
        travelPath1.setId(null);
        assertThat(travelPath1).isNotEqualTo(travelPath2);
    }
}
