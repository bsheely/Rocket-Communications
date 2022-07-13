package com.rocketcommunications.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document("contacts")
public class Contact {
    @Id
    private String id;

    private String contactId;
    private String contactStatus;
    private int contactName;
    private String contactGround;
    private String contactSatellite;
    private String contactEquipment;
    private String contactState;
    private String contactStep;
    private String contactDetail;
    private long contactBeginTimestamp;
    private long contactEndTimestamp;
    private double contactLatitude;
    private double contactLongitude;
    private double contactAzimuth;
    private double contactElevation;
    private String contactResolution;
    private String contactResolutionStatus;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

    public int getContactName() {
        return contactName;
    }

    public void setContactName(int contactName) {
        this.contactName = contactName;
    }

    public String getContactGround() {
        return contactGround;
    }

    public void setContactGround(String contactGround) {
        this.contactGround = contactGround;
    }

    public String getContactSatellite() {
        return contactSatellite;
    }

    public void setContactSatellite(String contactSatellite) {
        this.contactSatellite = contactSatellite;
    }

    public String getContactEquipment() {
        return contactEquipment;
    }

    public void setContactEquipment(String contactEquipment) {
        this.contactEquipment = contactEquipment;
    }

    public String getContactState() {
        return contactState;
    }

    public void setContactState(String contactState) {
        this.contactState = contactState;
    }

    public String getContactStep() {
        return contactStep;
    }

    public void setContactStep(String contactStep) {
        this.contactStep = contactStep;
    }

    public String getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(String contactDetail) {
        this.contactDetail = contactDetail;
    }

    public long getContactBeginTimestamp() {
        return contactBeginTimestamp;
    }

    public LocalDateTime getBeginTimestampAsLocalDateTime() {
        return Instant.ofEpochSecond(contactBeginTimestamp).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

    public void setContactBeginTimestamp(long contactBeginTimestamp) {
        this.contactBeginTimestamp = contactBeginTimestamp;
    }

    public long getContactEndTimestamp() {
        return contactEndTimestamp;
    }

    public LocalDateTime getEndTimestampAsLocalDateTime() {
        return Instant.ofEpochSecond(contactEndTimestamp).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

    public void setContactEndTimestamp(long contactEndTimestamp) {
        this.contactEndTimestamp = contactEndTimestamp;
    }

    public double getContactLatitude() {
        return contactLatitude;
    }

    public void setContactLatitude(double contactLatitude) {
        this.contactLatitude = contactLatitude;
    }

    public double getContactLongitude() {
        return contactLongitude;
    }

    public void setContactLongitude(double contactLongitude) {
        this.contactLongitude = contactLongitude;
    }

    public double getContactAzimuth() {
        return contactAzimuth;
    }

    public void setContactAzimuth(double contactAzimuth) {
        this.contactAzimuth = contactAzimuth;
    }

    public double getContactElevation() {
        return contactElevation;
    }

    public void setContactElevation(double contactElevation) {
        this.contactElevation = contactElevation;
    }

    public String getContactResolution() {
        return contactResolution;
    }

    public void setContactResolution(String contactResolution) {
        this.contactResolution = contactResolution;
    }

    public String getContactResolutionStatus() {
        return contactResolutionStatus;
    }

    public void setContactResolutionStatus(String contactResolutionStatus) {
        this.contactResolutionStatus = contactResolutionStatus;
    }
}
