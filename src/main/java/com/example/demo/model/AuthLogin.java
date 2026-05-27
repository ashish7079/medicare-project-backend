package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "login")
public class AuthLogin {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Column(unique = true)
    private String emailId;

    private Long mobileNo;

    private Integer age;

    private String password;

    private String role;

    private boolean premium;

    private String profileImage;

    private String avatar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(
            String userName
    ) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(
            String emailId
    ) {
        this.emailId = emailId;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(
            Long mobileNo
    ) {
        this.mobileNo = mobileNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(
            Integer age
    ) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(
            String password
    ) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(
            String role
    ) {
        this.role = role;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(
            boolean premium
    ) {
        this.premium = premium;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(
            String profileImage
    ) {
        this.profileImage =
                profileImage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(
            String avatar
    ) {
        this.avatar = avatar;
    }
}