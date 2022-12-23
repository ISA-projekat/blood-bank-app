package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.config.JwtTokenUtil;
import com.bloodbank.bloodbankapp.dto.JwtRequest;
import com.bloodbank.bloodbankapp.dto.JwtResponse;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.service.JwtUserDetailsService;
import com.bloodbank.bloodbankapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final long id = userDetailsService.getUserId(userDetails.getUsername());

        final boolean firstTime = userDetailsService.getUserFirstTime(userDetails.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails, id, firstTime);

        return ResponseEntity.ok(new JwtResponse(token, jwtTokenUtil.getExpiresIn()));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
