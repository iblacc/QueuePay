package com.dev.QueuePay.services.auth;

import com.dev.QueuePay.user.models.document.ProfileUpdate;
import org.springframework.stereotype.Service;

@Service
public interface ProfileUpdateService{

    ProfileUpdate updateProfile(ProfileUpdate profileUpdate, Long user_id);
}
