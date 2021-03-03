package com.embl.techtest.security.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;

    @ApiModelProperty(notes = "Token Generated for Auth")
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
