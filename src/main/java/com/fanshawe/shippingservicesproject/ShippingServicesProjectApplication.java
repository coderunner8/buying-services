package com.fanshawe.shippingservicesproject;

import com.fanshawe.shippingservicesproject.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class ShippingServicesProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingServicesProjectApplication.class, args);
    }

}
