package com.dev.QueuePay.controller;

import com.dev.QueuePay.responses.Response;
import com.dev.QueuePay.services.auth.AuthService;
import com.dev.QueuePay.user.dto.auth.LoginRequest;
import com.dev.QueuePay.user.dto.auth.NewPasswordRequest;
import com.dev.QueuePay.user.dto.auth.PasswordResetRequest;
import com.dev.QueuePay.user.dto.auth.SignUpRequest;
import com.dev.QueuePay.user.models.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("auth")
public class AuthController {

    private AuthService authService;
    private ModelMapper modelMapper;

    @Autowired
    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("signup")
    public ResponseEntity<Response<String>> createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.createUser(modelMapper.map(signUpRequest, User.class));
        Response<String> response = new Response<>(HttpStatus.CREATED);
        response.setMessage("You have successfully signed up on Qpay");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping ("verifyEmail/{token}")
    public ResponseEntity<Response<String>> verifyUser(@PathVariable("token") String token) {
        authService.verifyUser(token);
        Response<String> response = new Response<>(HttpStatus.ACCEPTED);
        response.setMessage("You are now a verified user of Qpay");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("login")
    public ResponseEntity<Response<Map<String, String>>> signInUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.signInUser(loginRequest.getEmail(), loginRequest.getPassword());
        Response<Map<String, String>> response = new Response<>(HttpStatus.OK);
        response.setMessage("User successfully logged in");
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        response.setData(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("password-reset")
    public ResponseEntity<Response<String>> resetPassword(@Valid @RequestBody
                                                                  PasswordResetRequest passwordResetRequest) {
        authService.resetPassword(passwordResetRequest.getEmail());
        Response<String> response = new Response<>(HttpStatus.OK);
        response.setMessage("A password reset link has been sent to your email");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("set-new-password")
    public ResponseEntity<Response<String>> setNewPassword(@RequestParam("id") UUID id,
                                                           @Valid @RequestBody NewPasswordRequest newPasswordRequest,
                                                           @RequestParam String token) {
        authService.setNewPassword(id, newPasswordRequest.getNewPassword(), token);
        Response<String> response = new Response<>(HttpStatus.OK);
        response.setMessage("Password successfully reset");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
