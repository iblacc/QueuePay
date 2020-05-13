package com.dev.QueuePay.services.auth;


import com.dev.QueuePay.user.models.user.User;

import java.util.Map;
import java.util.UUID;

public interface AuthService {
    void createUser(User user);
    void verifyUser(String token);
    Map<String, Object> signInUser(String email, String password);

    void resetPassword(String email);

    void setNewPassword(String token, String newPassword);


}
