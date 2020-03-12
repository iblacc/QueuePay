package com.dev.QueuePay.user.dto.auth;

import javax.validation.constraints.NotBlank;

public class UpdateRequest {
    @NotBlank
    private String businessDescription;

    @NotBlank
    private String cacURL;

    @NotBlank
    private String logoURL;

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getCacURL() {
        return cacURL;
    }

    public void setCacURL(String cacURL) {
        this.cacURL = cacURL;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }
}
