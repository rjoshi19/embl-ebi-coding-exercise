package com.embl.techtest.security.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    @ApiModelProperty(notes = "Username for token generation")
    private String username;

    @ApiModelProperty(notes = "Password for token generation")
    private String password;

    public JwtRequest() {

    }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
