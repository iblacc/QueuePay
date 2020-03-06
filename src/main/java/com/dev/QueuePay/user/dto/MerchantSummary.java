package com.dev.QueuePay.user.dto;

import java.util.UUID;

public class MerchantSummary {
    private String businessName;
    private String email;
    private UUID id;

    public MerchantSummary(UUID id, String businessName, String email) {
        this.id = id;
        this.businessName = businessName;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
