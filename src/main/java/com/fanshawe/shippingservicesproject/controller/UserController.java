package com.fanshawe.shippingservicesproject.controller;

import com.fanshawe.shippingservicesproject.model.*;
import com.fanshawe.shippingservicesproject.repositories.QuoteRepository;
import com.fanshawe.shippingservicesproject.repositories.UserRepository;
import com.fanshawe.shippingservicesproject.service.MyUserDetailsService;
import com.fanshawe.shippingservicesproject.service.UserVerify;
import com.fanshawe.shippingservicesproject.utils.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fbb")
public class UserController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private QuoteRepository quoteRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        final UserDetails userDetails
                = myUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        UserVerify userVerify = (UserVerify) authentication.getPrincipal();

        System.out.println(loginRequest.getRole());
        System.out.println(userVerify.getRole());

        if (loginRequest.getRole().equals(userVerify.getRole())) {
            ((UserVerify) authentication.getPrincipal()).setUsername(userDetails.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(new LoginResponse(token, userVerify.getFirstname(), userVerify.getLastname(), userVerify.getEmail(), userVerify.getUsername(),userVerify.getRole()));

        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new LoginResponse("Error: User profile is incorrect, Select a correct Profile"));
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody BuyerRegistration buyerRegistration) {
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

            User user = new User(buyerRegistration.getUsername(), buyerRegistration.getEmail(), buyerRegistration.getPassword(),
                    buyerRegistration.getFirstname(), buyerRegistration.getLastname(),buyerRegistration.getCompany(),buyerRegistration.getRegistration(), buyerRegistration.getRole());
            userRepository.save(user);



        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/quote")
    public ResponseEntity<?> requestQuote(@RequestBody Quote quoteRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        ZoneId zonedId = ZoneId.of( "America/Montreal" );
        LocalDate today = LocalDate.now( zonedId );
        if(authentication.isAuthenticated()) {
            quoteRepository.save(new QuoteRequest(quoteRequest.getCommodity(), quoteRequest.getCount(),
                    quoteRequest.getWeight(), quoteRequest.getDimensions(), quoteRequest.getCountryorigin(),
                    quoteRequest.getProvinceorigin(), quoteRequest.getCityorigin(), quoteRequest.getCountrydes(),
                    quoteRequest.getProvincedes(), quoteRequest.getCitydes(), today,userRepository.getByUsername(username)));
            return ResponseEntity.ok(new MessageResponse("Your Quote has been forwarded to all Vendors"));
        }else{
            return ResponseEntity.badRequest().body("Error: User not authenticated");

        }
    }

    @GetMapping("/allquotes")
    public ResponseEntity<?> quotes(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            User user = userRepository.getByUsername(authentication.getName());
            return ResponseEntity.ok(quoteRepository.findQuoteRequestsByUserInfo(user));
        }else{
            return ResponseEntity.badRequest().body("Error: User not authenticated");

        }
    }

    @GetMapping("/priceList")
    public ResponseEntity<?> getpriceList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            User user = userRepository.getByUsername(authentication.getName());
            return ResponseEntity.ok(quoteRepository.findQRByPriceGiven(user.getId()));
        }else{
            return ResponseEntity.badRequest().body("Error: User not authenticated");

        }
    }

}
