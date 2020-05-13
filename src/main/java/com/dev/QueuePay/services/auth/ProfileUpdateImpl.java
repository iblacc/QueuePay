package com.dev.QueuePay.services.auth;

import com.dev.QueuePay.exceptions.CustomException;
import com.dev.QueuePay.user.models.document.ProfileUpdate;
import com.dev.QueuePay.user.repositories.ProfileUpdateRepository;
import com.dev.QueuePay.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProfileUpdateImpl implements ProfileUpdateService {

    @Autowired
    private ProfileUpdateRepository profileUpdateRepository;
    private UserRepository userRepository;

    public ProfileUpdateImpl(ProfileUpdateRepository profileUpdateRepository, UserRepository userRepository) {
        this.profileUpdateRepository = profileUpdateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProfileUpdate updateProfile(ProfileUpdate profileUpdate, Long user_id) {
//        if (profileUpdate.getBusinessDescription() == null || profileUpdate.getCacURL() == null || profileUpdate.getLogoURL()== null){
//            throw new CustomException("Make sure you fill in all the fields", HttpStatus.BAD_REQUEST);
//        }
        return userRepository.findById(user_id).map(profile -> {
            profileUpdate.setUser(profile);
            profileUpdate.setEnabled(true);
            return profileUpdateRepository.save(profileUpdate);
        }).orElseThrow(() -> new CustomException("PostId " + user_id + " not found", HttpStatus.NOT_FOUND));
    }


}
