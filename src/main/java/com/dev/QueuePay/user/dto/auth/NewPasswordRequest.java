package com.dev.QueuePay.user.dto.auth;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class NewPasswordRequest {

    @NotNull
    @NotBlank
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
