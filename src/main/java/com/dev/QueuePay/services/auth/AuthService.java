package com.dev.QueuePay.services.auth;


import com.dev.QueuePay.user.models.user.User;

import java.util.UUID;

public interface AuthService {
    void createUser(User user);
    void verifyUser(String token);
    String signInUser(String email, String password);

    void resetPassword(String email);

    void setNewPassword(UUID id, String newPassword, String token);

//    void resetPassword(String email);
//    void setNewPassword(UUID id, String newPassword, String token);
}
