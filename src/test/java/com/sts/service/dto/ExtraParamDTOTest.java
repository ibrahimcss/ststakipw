package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class ExtraParamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraParamDTO.class);
        ExtraParamDTO extraParamDTO1 = new ExtraParamDTO();
        extraParamDTO1.setId(1L);
        ExtraParamDTO extraParamDTO2 = new ExtraParamDTO();
        assertThat(extraParamDTO1).isNotEqualTo(extraParamDTO2);
        extraParamDTO2.setId(extraParamDTO1.getId());
        assertThat(extraParamDTO1).isEqualTo(extraParamDTO2);
        extraParamDTO2.setId(2L);
        assertThat(extraParamDTO1).isNotEqualTo(extraParamDTO2);
        extraParamDTO1.setId(null);
        assertThat(extraParamDTO1).isNotEqualTo(extraParamDTO2);
    }
}
