package com.dev.QueuePay.user.dto.auth;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class PasswordResetRequest {

    @NotBlank
    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
