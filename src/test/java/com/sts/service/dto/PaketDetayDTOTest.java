package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class PaketDetayDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaketDetayDTO.class);
        PaketDetayDTO paketDetayDTO1 = new PaketDetayDTO();
        paketDetayDTO1.setId(1L);
        PaketDetayDTO paketDetayDTO2 = new PaketDetayDTO();
        assertThat(paketDetayDTO1).isNotEqualTo(paketDetayDTO2);
        paketDetayDTO2.setId(paketDetayDTO1.getId());
        assertThat(paketDetayDTO1).isEqualTo(paketDetayDTO2);
        paketDetayDTO2.setId(2L);
        assertThat(paketDetayDTO1).isNotEqualTo(paketDetayDTO2);
        paketDetayDTO1.setId(null);
        assertThat(paketDetayDTO1).isNotEqualTo(paketDetayDTO2);
    }
}
