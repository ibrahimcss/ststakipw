package com.sts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class ExtraParamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraParam.class);
        ExtraParam extraParam1 = new ExtraParam();
        extraParam1.setId(1L);
        ExtraParam extraParam2 = new ExtraParam();
        extraParam2.setId(extraParam1.getId());
        assertThat(extraParam1).isEqualTo(extraParam2);
        extraParam2.setId(2L);
        assertThat(extraParam1).isNotEqualTo(extraParam2);
        extraParam1.setId(null);
        assertThat(extraParam1).isNotEqualTo(extraParam2);
    }
}
