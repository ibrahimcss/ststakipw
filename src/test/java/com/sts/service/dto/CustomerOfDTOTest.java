package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class CustomerOfDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOfDTO.class);
        CustomerOfDTO customerOfDTO1 = new CustomerOfDTO();
        customerOfDTO1.setId(1L);
        CustomerOfDTO customerOfDTO2 = new CustomerOfDTO();
        assertThat(customerOfDTO1).isNotEqualTo(customerOfDTO2);
        customerOfDTO2.setId(customerOfDTO1.getId());
        assertThat(customerOfDTO1).isEqualTo(customerOfDTO2);
        customerOfDTO2.setId(2L);
        assertThat(customerOfDTO1).isNotEqualTo(customerOfDTO2);
        customerOfDTO1.setId(null);
        assertThat(customerOfDTO1).isNotEqualTo(customerOfDTO2);
    }
}
