package com.dev.QueuePay.services.auth;

import com.dev.QueuePay.exceptions.CustomException;
import com.dev.QueuePay.security.JwtTokenProvider;
import com.dev.QueuePay.user.models.user.EmailVerificationStatus;
import com.dev.QueuePay.user.models.user.Role;
import com.dev.QueuePay.user.models.user.User;
import com.dev.QueuePay.user.repositories.UserRepository;
import com.dev.QueuePay.utils.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailSender emailSender;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                           BCryptPasswordEncoder bCryptPasswordEncoder, EmailSender emailSender,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
        this.authenticationManager = authenticationManager;
    }

    public void createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException("Email already has an account with us", HttpStatus.CONFLICT);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(Role.ROLE_MERCHANT));
        String token = jwtTokenProvider.generateToken(user.getEmail());
        user.setVerifyEmailToken(token);
        String url = "http://localhost:8081/auth/verifyEmail/" + token;
        String message =
                "Hello" + user.getBusinessName() + ",\n" +
                "You just created an account on QueuePay\n" +
                "Click the link below to verify your account\n" + url + "\n"
                ;
        emailSender.sendEmail(user.getEmail(), "QueuePay Registration Verification", message);

        userRepository.save(user);
    }

    public void verifyUser(String token) {
        User user = userRepository.findByVerifyEmailToken(token);
//        System.out.println(user);
        if (user == null) {
            throw new CustomException("Unable to verify that you registered here", HttpStatus.BAD_REQUEST);
        }
        user.setEmailVerificationStatus(EmailVerificationStatus.VERIFIED);
        log.info("logging {}", user);
        userRepository.save(user);
    }

    public String signInUser(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        if (authentication == null) {
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(email);
        if (user.getEmailVerificationStatus() == EmailVerificationStatus.UNVERIFIED) {
            throw new CustomException("You haven't verified your account yet", HttpStatus.BAD_REQUEST);
        }
        return jwtTokenProvider.createToken(user.getUserId(), email, user.getRoles(), 86400000);

    }

    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("You are yet to register on Banka", HttpStatus.BAD_REQUEST);
        }
        String token = jwtTokenProvider.createToken(user.getUserId(), user.getEmail(),
                user.getRoles(), 3600000);
        System.out.println(token);
        String url = "http://localhost:8081/password-reset/" + user.getUserId() + token;

        String message =
                "Hello" + user.getBusinessName() + ",\n" +
                        "You can use the following link to reset your password:\n" + url + "\n" +
                        "If you don’t use this link within 1 hour, it will expire.";
        emailSender.sendEmail(user.getEmail(), "QueuePay Reset Password", message);
    }

    public void setNewPassword(UUID id, String newPassword, String token) {
        if (jwtTokenProvider.isTokenExpired(token)) {
            throw new CustomException("The token has expired", HttpStatus.FORBIDDEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        if (!userRepository.existsByEmail(email)) {
            throw new CustomException("There is a compromise", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findByUserId(id);
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    @Override
    public String toString() {
        return "AuthServiceImpl{" +
                "userRepository=" + userRepository +
                '}';
    }
}
