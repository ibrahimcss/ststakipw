package com.sts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class StudentToTravelPathTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentToTravelPath.class);
        StudentToTravelPath studentToTravelPath1 = new StudentToTravelPath();
        studentToTravelPath1.setId(1L);
        StudentToTravelPath studentToTravelPath2 = new StudentToTravelPath();
        studentToTravelPath2.setId(studentToTravelPath1.getId());
        assertThat(studentToTravelPath1).isEqualTo(studentToTravelPath2);
        studentToTravelPath2.setId(2L);
        assertThat(studentToTravelPath1).isNotEqualTo(studentToTravelPath2);
        studentToTravelPath1.setId(null);
        assertThat(studentToTravelPath1).isNotEqualTo(studentToTravelPath2);
    }
}
