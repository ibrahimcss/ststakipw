package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.StudentTrackingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentTracking} and its DTO {@link StudentTrackingDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentToTravelPathMapper.class, VehicleMapper.class, StudentMapper.class})
public interface StudentTrackingMapper extends EntityMapper<StudentTrackingDTO, StudentTracking> {

    @Mapping(source = "studentToTravelPath.id", target = "studentToTravelPathId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.licencePlate", target = "vehicleLicencePlate")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.tcId", target = "studentTcId")
    StudentTrackingDTO toDto(StudentTracking studentTracking);

    @Mapping(source = "studentToTravelPathId", target = "studentToTravelPath")
    @Mapping(source = "vehicleId", target = "vehicle")
    @Mapping(source = "studentId", target = "student")
    StudentTracking toEntity(StudentTrackingDTO studentTrackingDTO);

    default StudentTracking fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentTracking studentTracking = new StudentTracking();
        studentTracking.setId(id);
        return studentTracking;
    }
}
