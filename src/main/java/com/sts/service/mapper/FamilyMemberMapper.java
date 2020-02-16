package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.FamilyMemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FamilyMember} and its DTO {@link FamilyMemberDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerOfMapper.class, StudentMapper.class})
public interface FamilyMemberMapper extends EntityMapper<FamilyMemberDTO, FamilyMember> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.tcId", target = "parentTcId")
    @Mapping(source = "child.id", target = "childId")
    @Mapping(source = "child.tcId", target = "childTcId")
    FamilyMemberDTO toDto(FamilyMember familyMember);

    @Mapping(source = "parentId", target = "parent")
    @Mapping(source = "childId", target = "child")
    FamilyMember toEntity(FamilyMemberDTO familyMemberDTO);

    default FamilyMember fromId(Long id) {
        if (id == null) {
            return null;
        }
        FamilyMember familyMember = new FamilyMember();
        familyMember.setId(id);
        return familyMember;
    }
}
