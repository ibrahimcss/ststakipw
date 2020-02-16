package com.sts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class StudentTrackingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentTracking.class);
        StudentTracking studentTracking1 = new StudentTracking();
        studentTracking1.setId(1L);
        StudentTracking studentTracking2 = new StudentTracking();
        studentTracking2.setId(studentTracking1.getId());
        assertThat(studentTracking1).isEqualTo(studentTracking2);
        studentTracking2.setId(2L);
        assertThat(studentTracking1).isNotEqualTo(studentTracking2);
        studentTracking1.setId(null);
        assertThat(studentTracking1).isNotEqualTo(studentTracking2);
    }
}
