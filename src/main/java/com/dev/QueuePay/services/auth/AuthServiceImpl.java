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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailSender emailSender;
    private AuthenticationManager authenticationManager;

    @Value("${server.port}")
    private String port;

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
        user.setRole(Role.ROLE_MERCHANT);
        String token = jwtTokenProvider.generateToken(user.getEmail());
        user.setVerifyEmailToken(token);

        String url = "http://localhost:"+ port + "/auth/verifyEmail/" + token;
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

    public Map<String, Object> signInUser(String email, String password) {

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

        Map<String, Object> result = new HashMap<>();
        String token = jwtTokenProvider.createToken(user.getId(), email, user.getRole(), 86400000);
        Long userId = user.getId();
        String userEmail = user.getEmail();
        String userBusinessName = user.getBusinessName();
        result.put("token", token);
        result.put("userId", userId);
        result.put("userEmail", userEmail);
        result.put("userBusinessName", userBusinessName);
        return  result;
    }

    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("You are yet to register on QueuePay", HttpStatus.BAD_REQUEST);
        }
        String token = jwtTokenProvider.createToken(user.getId(), user.getEmail(),
                user.getRole(), 3600000);
        System.out.println(token);
        user.setResetPasswordToken(token);
        String url = "http://localhost:" + port + "/password-reset?token="+user.getResetPasswordToken();

        userRepository.save(user);

        String message =
                "Hello" + user.getBusinessName() + ",\n" +
                        "A request was made to change Password. If it was not you, " +
                        "please ignore." +

        String url = "http://localhost:" + port + "/password-reset/" + user.getUserId() + token;

        String message =
                "Hello " + user.getBusinessName() + ",\n" +
                        "You can use the following link to reset your password:\n" + url + "\n" +
                        "If you don’t use this link within 1 hour, it will expire.";
        emailSender.sendEmail(user.getEmail(), "QueuePay Reset Password", message);
    }

    public void setNewPassword(String token, String newPassword) {

        Optional<User> user = userRepository.findByResetPasswordToken(token);
        if (!user.isPresent()) {
            throw new CustomException("There is a compromise", HttpStatus.BAD_REQUEST);
        }
        User resetUser = user.get();
            resetUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
            resetUser.setResetPasswordToken(null);
            userRepository.save(resetUser);


    }


    @Override
    public String toString() {
        return "AuthServiceImpl{" +
                "userRepository=" + userRepository +
                '}';
    }


}
