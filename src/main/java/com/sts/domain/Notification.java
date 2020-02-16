package com.sts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.sts.domain.enumeration.NotifyType;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotifyType type;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Size(max = 250)
    @Column(name = "url_image", length = 250)
    private String urlImage;

    @Column(name = "is_read")
    private Boolean isRead;

    @ManyToOne
    @JsonIgnoreProperties("notifications")
    private TravelPath travelPath;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Notification title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public Notification message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotifyType getType() {
        return type;
    }

    public Notification type(NotifyType type) {
        this.type = type;
        return this;
    }

    public void setType(NotifyType type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Notification createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public Notification urlImage(String urlImage) {
        this.urlImage = urlImage;
        return this;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Boolean isIsRead() {
        return isRead;
    }

    public Notification isRead(Boolean isRead) {
        this.isRead = isRead;
        return this;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public TravelPath getTravelPath() {
        return travelPath;
    }

    public Notification travelPath(TravelPath travelPath) {
        this.travelPath = travelPath;
        return this;
    }

    public void setTravelPath(TravelPath travelPath) {
        this.travelPath = travelPath;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", message='" + getMessage() + "'" +
            ", type='" + getType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", urlImage='" + getUrlImage() + "'" +
            ", isRead='" + isIsRead() + "'" +
            "}";
    }
}
