package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class StudentTrackingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentTrackingDTO.class);
        StudentTrackingDTO studentTrackingDTO1 = new StudentTrackingDTO();
        studentTrackingDTO1.setId(1L);
        StudentTrackingDTO studentTrackingDTO2 = new StudentTrackingDTO();
        assertThat(studentTrackingDTO1).isNotEqualTo(studentTrackingDTO2);
        studentTrackingDTO2.setId(studentTrackingDTO1.getId());
        assertThat(studentTrackingDTO1).isEqualTo(studentTrackingDTO2);
        studentTrackingDTO2.setId(2L);
        assertThat(studentTrackingDTO1).isNotEqualTo(studentTrackingDTO2);
        studentTrackingDTO1.setId(null);
        assertThat(studentTrackingDTO1).isNotEqualTo(studentTrackingDTO2);
    }
}
