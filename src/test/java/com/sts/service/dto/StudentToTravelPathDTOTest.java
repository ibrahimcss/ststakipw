package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class StudentToTravelPathDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentToTravelPathDTO.class);
        StudentToTravelPathDTO studentToTravelPathDTO1 = new StudentToTravelPathDTO();
        studentToTravelPathDTO1.setId(1L);
        StudentToTravelPathDTO studentToTravelPathDTO2 = new StudentToTravelPathDTO();
        assertThat(studentToTravelPathDTO1).isNotEqualTo(studentToTravelPathDTO2);
        studentToTravelPathDTO2.setId(studentToTravelPathDTO1.getId());
        assertThat(studentToTravelPathDTO1).isEqualTo(studentToTravelPathDTO2);
        studentToTravelPathDTO2.setId(2L);
        assertThat(studentToTravelPathDTO1).isNotEqualTo(studentToTravelPathDTO2);
        studentToTravelPathDTO1.setId(null);
        assertThat(studentToTravelPathDTO1).isNotEqualTo(studentToTravelPathDTO2);
    }
}
