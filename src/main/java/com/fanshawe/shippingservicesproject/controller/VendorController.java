package com.fanshawe.shippingservicesproject.controller;

import com.fanshawe.shippingservicesproject.model.MessageResponse;
import com.fanshawe.shippingservicesproject.model.QuotePrice;
import com.fanshawe.shippingservicesproject.model.QuotePriceSetupRequest;
import com.fanshawe.shippingservicesproject.model.User;
import com.fanshawe.shippingservicesproject.repositories.QuotePriceRepository;
import com.fanshawe.shippingservicesproject.repositories.QuoteRepository;
import com.fanshawe.shippingservicesproject.repositories.UserRepository;
import com.fanshawe.shippingservicesproject.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping("vendor")
public class VendorController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private QuotePriceRepository quotePriceRepository;


    /*
     * Used to display all the quotes to the Vendor.
     * */
    @GetMapping("/requests")
    public ResponseEntity<?> ViewRequests(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            String username=authentication.getName();
            User user=userRepository.getByUsername(username);
            return ResponseEntity.ok(quoteRepository.findQRByQuotePrice(user.getId()));
        }
        else {
            return ResponseEntity.badRequest().body("Error: User not authenticated");
        }
    }

    /*
     * Used to post a price for a particular quote.
     * Its is used by vendor.
     * */
    @PostMapping("/priceEstimate")
    public ResponseEntity<?> PriceSet(@RequestBody QuotePriceSetupRequest quote){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        ZoneId zonedId = ZoneId.of( "America/Montreal" );
        LocalDate today = LocalDate.now( zonedId );
        if(authentication.isAuthenticated()){
        QuotePrice quotePrice=new QuotePrice(quote.getPrice(), quote.getComments(),today,userRepository.getByUsername(username),quoteRepository.getById(Long.valueOf(quote.getQuoteid())));
        quotePriceRepository.save(quotePrice);
        return ResponseEntity.ok(new MessageResponse("Price Successfully Sent to the Customer"));
        }
        else {
            return ResponseEntity.badRequest().body("Error: User not authenticated");
        }

    }

    /*
     * This Function is used by vendor to see list of items for which the price has given.
     * */
    @GetMapping("/priceRequests")
    public ResponseEntity<?> PriceRequests(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            String username=authentication.getName();
            User user=userRepository.getByUsername(username);
            return ResponseEntity.ok(quoteRepository.findQRByPrice(user.getId()));
        }
        else {
            return ResponseEntity.badRequest().body("Error: User not authenticated");
        }
    }
}
