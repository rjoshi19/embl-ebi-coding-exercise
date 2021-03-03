package com.embl.techtest.security.controller;

import com.embl.techtest.security.component.JwtTokenUtil;
import com.embl.techtest.security.model.JwtRequest;
import com.embl.techtest.security.model.JwtResponse;
import com.embl.techtest.security.service.JwtUserDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@Api(value = "Authentication for APIs", tags = "Authenticate", description = "REST Controller for Auth Token Generation API")
public class JwtAuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationController.class);

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                       JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @ApiOperation(value = "Generate JWT Auth Token", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the token"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        LOGGER.info("Creating Auth Token");
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        LOGGER.info("Auth Token Successfully Created.");
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            LOGGER.error("User is disabled");
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            LOGGER.error("Invalid Credentials");
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
