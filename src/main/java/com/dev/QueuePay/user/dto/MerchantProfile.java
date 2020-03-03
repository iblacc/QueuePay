package com.dev.QueuePay.user.dto;

import com.dev.QueuePay.user.models.document.Document;

import java.time.Instant;
import java.util.UUID;

public class MerchantProfile {
    private UUID id;
    private String email;
    private String businessName;
    private String businessDescription;
    private byte[] logo;
    private Instant joinedAt;
    private Document document;

    public MerchantProfile() {
    }

    public MerchantProfile(UUID id, String email, String businessName, String businessDescription, byte[] logo, Instant joinedAt, Document document) {
        this.id = id;
        this.email = email;
        this.businessName = businessName;
        this.businessDescription = businessDescription;
        this.logo = logo;
        this.joinedAt = joinedAt;
        this.document = document;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
