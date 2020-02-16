package com.sts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class CustomerOfTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOf.class);
        CustomerOf customerOf1 = new CustomerOf();
        customerOf1.setId(1L);
        CustomerOf customerOf2 = new CustomerOf();
        customerOf2.setId(customerOf1.getId());
        assertThat(customerOf1).isEqualTo(customerOf2);
        customerOf2.setId(2L);
        assertThat(customerOf1).isNotEqualTo(customerOf2);
        customerOf1.setId(null);
        assertThat(customerOf1).isNotEqualTo(customerOf2);
    }
}
