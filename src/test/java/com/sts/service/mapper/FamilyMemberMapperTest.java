package com.sts.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FamilyMemberMapperTest {

    private FamilyMemberMapper familyMemberMapper;

    @BeforeEach
    public void setUp() {
        familyMemberMapper = new FamilyMemberMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(familyMemberMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(familyMemberMapper.fromId(null)).isNull();
    }
}
