package com.dev.QueuePay.user.dto;

public class MerchantSummary {
    private String businessName;
    private String email;

    public MerchantSummary(String businessName, String email) {
        this.businessName = businessName;
        this.email = email;
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
