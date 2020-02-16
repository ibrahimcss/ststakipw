package com.sts.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sts.web.rest.TestUtil;

public class FamilyMemberDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyMemberDTO.class);
        FamilyMemberDTO familyMemberDTO1 = new FamilyMemberDTO();
        familyMemberDTO1.setId(1L);
        FamilyMemberDTO familyMemberDTO2 = new FamilyMemberDTO();
        assertThat(familyMemberDTO1).isNotEqualTo(familyMemberDTO2);
        familyMemberDTO2.setId(familyMemberDTO1.getId());
        assertThat(familyMemberDTO1).isEqualTo(familyMemberDTO2);
        familyMemberDTO2.setId(2L);
        assertThat(familyMemberDTO1).isNotEqualTo(familyMemberDTO2);
        familyMemberDTO1.setId(null);
        assertThat(familyMemberDTO1).isNotEqualTo(familyMemberDTO2);
    }
}
