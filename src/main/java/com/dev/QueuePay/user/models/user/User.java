package com.dev.QueuePay.user.models.user;

import com.dev.QueuePay.user.models.DateAudit;
import com.dev.QueuePay.user.models.document.ProfileUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.cache.interceptor.CacheAspectSupport;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User extends DateAudit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @NotNull
    private String businessName;

    private String businessDescription;

    @NotBlank
    @NotNull
    @JsonIgnoreProperties
    @Size(min = 8)
    private String password;


//    @ElementCollection(fetch = FetchType.EAGER)
//    @Fetch(value = FetchMode.SUBSELECT)
//    List<Role> roles;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String verifyEmailToken;

    private String resetPasswordToken;


    @JsonIgnoreProperties
   @Enumerated(EnumType.STRING)
    private EmailVerificationStatus emailVerificationStatus = EmailVerificationStatus.UNVERIFIED;


    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
    private ProfileUpdate profileUpdate;



    public User() {
    }

    public User(String businessName, String email, String password) {
        super();
        this.businessName = businessName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getVerifyEmailToken() {
        return verifyEmailToken;
    }

    public void setVerifyEmailToken(String verifyEmailToken) {
        this.verifyEmailToken = verifyEmailToken;
    }

    public EmailVerificationStatus getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(EmailVerificationStatus emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}
