package com.fanshawe.shippingservicesproject.controller;

import com.fanshawe.shippingservicesproject.model.*;
import com.fanshawe.shippingservicesproject.repositories.UserRepository;
import com.fanshawe.shippingservicesproject.service.MyUserDetailsService;
import com.fanshawe.shippingservicesproject.utils.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public LoginResponse authenticate(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }

        final UserDetails userDetails
                = myUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return new LoginResponse(token);
    }

    @PostMapping("/register/buyer")
    public ResponseEntity<?> register(@Valid @RequestBody BuyerRegistration buyerRegistration){
        if (userRepository.existsByUsername(buyerRegistration.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(buyerRegistration.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user=new User(buyerRegistration.getUsername(),buyerRegistration.getEmail(),buyerRegistration.getPassword(),
                buyerRegistration.getFirstname(),buyerRegistration.getLastname());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
