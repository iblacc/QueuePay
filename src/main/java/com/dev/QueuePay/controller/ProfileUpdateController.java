package com.dev.QueuePay.controller;

import com.dev.QueuePay.responses.Response;
import com.dev.QueuePay.services.auth.ProfileUpdateService;
import com.dev.QueuePay.user.dto.auth.UpdateRequest;
import com.dev.QueuePay.user.models.document.ProfileUpdate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin
public class ProfileUpdateController {

    @Autowired
    private ProfileUpdateService profileUpdateService;

    private ModelMapper modelMapper;

    public ProfileUpdateController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping("updateProfile/{user_id}")
    public ResponseEntity<Response<ProfileUpdate>> updateProfile( @PathVariable Long user_id, @Valid @RequestBody UpdateRequest updateRequest) {
        profileUpdateService.updateProfile(modelMapper.map(updateRequest, ProfileUpdate.class), user_id);
        Response<ProfileUpdate> response = new Response<>(HttpStatus.CREATED);
        response.setMessage("Your profile has successfully been updated. You can now enjoy all our services");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
