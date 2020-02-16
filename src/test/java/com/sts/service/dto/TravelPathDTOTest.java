package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class TravelPathDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravelPathDTO.class);
        TravelPathDTO travelPathDTO1 = new TravelPathDTO();
        travelPathDTO1.setId(1L);
        TravelPathDTO travelPathDTO2 = new TravelPathDTO();
        assertThat(travelPathDTO1).isNotEqualTo(travelPathDTO2);
        travelPathDTO2.setId(travelPathDTO1.getId());
        assertThat(travelPathDTO1).isEqualTo(travelPathDTO2);
        travelPathDTO2.setId(2L);
        assertThat(travelPathDTO1).isNotEqualTo(travelPathDTO2);
        travelPathDTO1.setId(null);
        assertThat(travelPathDTO1).isNotEqualTo(travelPathDTO2);
    }
}
