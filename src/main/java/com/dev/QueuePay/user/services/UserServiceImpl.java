package com.dev.QueuePay.user.services;

import com.amazonaws.services.rds.model.ResourceNotFoundException;
import com.dev.QueuePay.exception.AccessDeniedException;
import com.dev.QueuePay.exception.BadRequestException;
import com.dev.QueuePay.exception.UnauthorizedException;
import com.dev.QueuePay.user.dto.InfoRequest;
import com.dev.QueuePay.user.dto.MerchantProfile;
import com.dev.QueuePay.user.dto.MerchantSummary;
import com.dev.QueuePay.user.dto.UserIdentityAvailability;
import com.dev.QueuePay.user.models.document.Document;
import com.dev.QueuePay.user.models.role.Role;
import com.dev.QueuePay.user.models.role.RoleName;
import com.dev.QueuePay.user.models.user.User;
import com.dev.QueuePay.user.repositories.RoleRepository;
import com.dev.QueuePay.user.repositories.UserRepository;
import com.dev.QueuePay.user.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MerchantSummary getCurrentUser(UserPrincipal currentUser) {
        return new MerchantSummary(currentUser.getId(), currentUser.getEmail(), currentUser.getBusinessName());
    }

    @Override
    public UserIdentityAvailability checkEmailAvailability(String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @Override
    public MerchantProfile getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));


        return new MerchantProfile(user.getId(), user.getEmail(), user.getBusinessName(), user.getBusinessDescription(),
                user.getLogo(), user.getCreatedAt(), user.getDocument());
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Email has already been taken");
            throw new BadRequestException(apiResponse);
        }


        List<Role> roles = new ArrayList<>();
        roles.add(
                roleRepository.findByName(RoleName.ROLE_MERCHANT).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User newUser, String email, UserPrincipal currentUser) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));
        if (user.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            user.setBusinessName(newUser.getBusinessName());
            user.setBusinessDescription(newUser.getBusinessDescription());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setLogo(newUser.getLogo());
            user.setDocument(newUser.getDocument());


            return userRepository.save(user);

        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to update profile of: " + email);
        throw new UnauthorizedException(apiResponse);

    }

    @Override
    public ApiResponse deleteUser(String email, UserPrincipal currentUser) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));
        if (!user.getId().equals(currentUser.getId()) || !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete profile of: " + email);
            throw new AccessDeniedException(apiResponse);
        }

        userRepository.deleteById(user.getId());

        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + email);
    }

    @Override
    public ApiResponse giveAdmin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("User role not set")));
        roles.add(
                roleRepository.findByName(RoleName.ROLE_MERCHANT).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);
        userRepository.save(user);
        return new ApiResponse(Boolean.TRUE, "You gave ADMIN role to user: " + email);
    }

    @Override
    public ApiResponse removeAdmin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));
        List<Role> roles = new ArrayList<>();
        roles.add(
                roleRepository.findByName(RoleName.ROLE_MERCHANT).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);
        userRepository.save(user);
        return new ApiResponse(Boolean.TRUE, "You took ADMIN role from user: " + email);
    }

    @Override
    public MerchantProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest) {
        User user = userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(currentUser.getEmail()));
        Document document = new Document(infoRequest.getDocumentName(), infoRequest.getDocumentType(), infoRequest.getDocument());
        if (user.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            user.setDocument(document);
            user.setLogo(infoRequest.getLogo());
            user.setBusinessDescription(infoRequest.getDescription());
            User updatedUser = userRepository.save(user);


            return new MerchantProfile(updatedUser.getId(), updatedUser.getEmail(),
                    updatedUser.getBusinessName(), updatedUser.getBusinessDescription(), updatedUser.getLogo(),
                    updatedUser.getCreatedAt(), updatedUser.getDocument());
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to update users profile", HttpStatus.FORBIDDEN);
        throw new AccessDeniedException(apiResponse);
    }
}
