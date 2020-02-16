package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class WorksInDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorksInDTO.class);
        WorksInDTO worksInDTO1 = new WorksInDTO();
        worksInDTO1.setId(1L);
        WorksInDTO worksInDTO2 = new WorksInDTO();
        assertThat(worksInDTO1).isNotEqualTo(worksInDTO2);
        worksInDTO2.setId(worksInDTO1.getId());
        assertThat(worksInDTO1).isEqualTo(worksInDTO2);
        worksInDTO2.setId(2L);
        assertThat(worksInDTO1).isNotEqualTo(worksInDTO2);
        worksInDTO1.setId(null);
        assertThat(worksInDTO1).isNotEqualTo(worksInDTO2);
    }
}
