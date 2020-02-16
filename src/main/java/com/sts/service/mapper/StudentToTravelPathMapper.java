package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.StudentToTravelPathDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentToTravelPath} and its DTO {@link StudentToTravelPathDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, TravelPathMapper.class})
public interface StudentToTravelPathMapper extends EntityMapper<StudentToTravelPathDTO, StudentToTravelPath> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.tcId", target = "studentTcId")
    @Mapping(source = "travelPath.id", target = "travelPathId")
    @Mapping(source = "travelPath.name", target = "travelPathName")
    StudentToTravelPathDTO toDto(StudentToTravelPath studentToTravelPath);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "travelPathId", target = "travelPath")
    StudentToTravelPath toEntity(StudentToTravelPathDTO studentToTravelPathDTO);

    default StudentToTravelPath fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentToTravelPath studentToTravelPath = new StudentToTravelPath();
        studentToTravelPath.setId(id);
        return studentToTravelPath;
    }
}
