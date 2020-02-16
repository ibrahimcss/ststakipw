package com.sts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class WorksInTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorksIn.class);
        WorksIn worksIn1 = new WorksIn();
        worksIn1.setId(1L);
        WorksIn worksIn2 = new WorksIn();
        worksIn2.setId(worksIn1.getId());
        assertThat(worksIn1).isEqualTo(worksIn2);
        worksIn2.setId(2L);
        assertThat(worksIn1).isNotEqualTo(worksIn2);
        worksIn1.setId(null);
        assertThat(worksIn1).isNotEqualTo(worksIn2);
    }
}
