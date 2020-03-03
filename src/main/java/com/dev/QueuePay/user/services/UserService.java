package com.dev.QueuePay.user.services;

import com.dev.QueuePay.user.dto.InfoRequest;
import com.dev.QueuePay.user.dto.MerchantProfile;
import com.dev.QueuePay.user.dto.MerchantSummary;
import com.dev.QueuePay.user.dto.UserIdentityAvailability;
import com.dev.QueuePay.user.models.user.User;
import com.dev.QueuePay.user.security.UserPrincipal;

public interface UserService {
    MerchantSummary getCurrentUser(UserPrincipal currentUser);

    UserIdentityAvailability checkEmailAvailability(String email);

    MerchantProfile getProfile(String email);

    User addUser(User user);

    User updateUser(User newUser, String email, UserPrincipal currentUser);

    ApiResponse deleteUser(String email, UserPrincipal currentUser);

    ApiResponse giveAdmin(String email);

    ApiResponse removeAdmin(String email);

    MerchantProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);
}
