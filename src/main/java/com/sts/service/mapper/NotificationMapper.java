package com.sts.service.mapper;


import com.sts.domain.*;
import com.sts.service.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {TravelPathMapper.class})
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {

    @Mapping(source = "travelPath.id", target = "travelPathId")
    @Mapping(source = "travelPath.name", target = "travelPathName")
    NotificationDTO toDto(Notification notification);

    @Mapping(source = "travelPathId", target = "travelPath")
    Notification toEntity(NotificationDTO notificationDTO);

    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}
