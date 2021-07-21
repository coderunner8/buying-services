package com.fanshawe.shippingservicesproject.service;

import com.fanshawe.shippingservicesproject.model.User;
import com.fanshawe.shippingservicesproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      // return new User("admin","password",new ArrayList<>());

        Optional<User> user=userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return user.map(UserVerify::new).get();
    }
}
