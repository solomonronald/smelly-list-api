package com.smellylist.api.auth.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = UserCredential.TABLE_NAME)
public class UserCredential {
    public static final String TABLE_NAME = "user_credential";

    @Id
    @Column(name = "public_id", nullable = false)
    private String publicId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "role_mask")
    private int roleMask;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    public UserCredential() {
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleMask() {
        return roleMask;
    }

    public void setRoleMask(int roleMask) {
        this.roleMask = roleMask;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
