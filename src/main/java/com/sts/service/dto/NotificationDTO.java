package com.sts.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.sts.domain.enumeration.NotifyType;

/**
 * A DTO for the {@link com.sts.domain.Notification} entity.
 */
public class NotificationDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String message;

    @NotNull
    private NotifyType type;

    @NotNull
    private Instant createdAt;

    @Size(max = 250)
    private String urlImage;

    private Boolean isRead;


    private Long travelPathId;

    private String travelPathName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotifyType getType() {
        return type;
    }

    public void setType(NotifyType type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Long getTravelPathId() {
        return travelPathId;
    }

    public void setTravelPathId(Long travelPathId) {
        this.travelPathId = travelPathId;
    }

    public String getTravelPathName() {
        return travelPathName;
    }

    public void setTravelPathName(String travelPathName) {
        this.travelPathName = travelPathName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if (notificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", message='" + getMessage() + "'" +
            ", type='" + getType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", urlImage='" + getUrlImage() + "'" +
            ", isRead='" + isIsRead() + "'" +
            ", travelPathId=" + getTravelPathId() +
            ", travelPathName='" + getTravelPathName() + "'" +
            "}";
    }
}
