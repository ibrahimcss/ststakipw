package com.sts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class PaketDetayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaketDetay.class);
        PaketDetay paketDetay1 = new PaketDetay();
        paketDetay1.setId(1L);
        PaketDetay paketDetay2 = new PaketDetay();
        paketDetay2.setId(paketDetay1.getId());
        assertThat(paketDetay1).isEqualTo(paketDetay2);
        paketDetay2.setId(2L);
        assertThat(paketDetay1).isNotEqualTo(paketDetay2);
        paketDetay1.setId(null);
        assertThat(paketDetay1).isNotEqualTo(paketDetay2);
    }
}
