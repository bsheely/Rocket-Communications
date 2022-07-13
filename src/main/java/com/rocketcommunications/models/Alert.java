package com.rocketcommunications.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document("alerts")
public class Alert {
    @Id
    private String id;

    private String errorId;
    private String errorSeverity;
    private String errorCategory;
    private String errorMessage;
    private String longMessage;
    private long errorTime;
    private boolean isSelected;
    private boolean isNew;
    private boolean isExpanded;

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorSeverity() {
        return errorSeverity;
    }

    public void setErrorSeverity(String errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    public String getErrorCategory() {
        return errorCategory;
    }

    public void setErrorCategory(String errorCategory) {
        this.errorCategory = errorCategory;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getLongMessage() {
        return longMessage;
    }

    public void setLongMessage(String longMessage) {
        this.longMessage = longMessage;
    }

    public long getErrorTime() {
        return errorTime;
    }

    public LocalDateTime getErrorTimeAsLocalDateTime() {
        return Instant.ofEpochMilli(errorTime).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

    public void setErrorTime(long errorTime) {
        this.errorTime = errorTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
