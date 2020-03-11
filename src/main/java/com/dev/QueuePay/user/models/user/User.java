package com.dev.QueuePay.user.models.user;

import com.dev.QueuePay.user.models.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

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
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    private UUID id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(generator = "UUID")
   @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID userId;

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


//    @OneToOne
//    @Column(name = "logo")
//    private DatabaseFile logo;
//
//    @OneToOne
//    @Column(name = "CAC")
//    private DatabaseFile CAC;



    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    List<Role> roles;

    private String verifyEmailToken;


    @JsonIgnoreProperties
   @Enumerated(EnumType.STRING)
    private EmailVerificationStatus emailVerificationStatus = EmailVerificationStatus.UNVERIFIED;



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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businessDescription='" + businessDescription + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", verifyEmailToken='" + verifyEmailToken + '\'' +
                ", emailVerificationStatus=" + emailVerificationStatus +
                '}';
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
