package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "drinfo")
public class Upload {

    private String userName;

    private String degree;

    private String specialist;

    private String file1Path;

    private String file2Path;

    private Long authId;

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    public Upload() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(
            String userName
    ) {
        this.userName = userName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(
            String degree
    ) {
        this.degree = degree;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(
            String specialist
    ) {
        this.specialist = specialist;
    }

    public String getFile1Path() {
        return file1Path;
    }

    public void setFile1Path(
            String file1Path
    ) {
        this.file1Path = file1Path;
    }

    public String getFile2Path() {
        return file2Path;
    }

    public void setFile2Path(
            String file2Path
    ) {
        this.file2Path = file2Path;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(
            Long authId
    ) {
        this.authId = authId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}