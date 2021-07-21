package com.fanshawe.shippingservicesproject.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Size(max = 20)
        private String username;

        @NotBlank
        @Size(max = 50)
        @Email
        private String email;

        @NotBlank
        @Size(max = 120)
        private String password;

        private String firstname;

        private String lastname;

        private String companyname;

        private String registration;

        public User(String username, String email, String password, String firstname, String lastname) {
                this.username = username;
                this.email = email;
                this.password = password;
                this.firstname = firstname;
                this.lastname = lastname;
        }

        public User(String username, String email, String password, String firstname, String lastname, String companyname, String registration) {
                this.username = username;
                this.email = email;
                this.password = password;
                this.firstname = firstname;
                this.lastname = lastname;
                this.companyname = companyname;
                this.registration = registration;
        }

        public User() {

        }


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
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

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getFirstname() {
                return firstname;
        }

        public void setFirstname(String firstname) {
                this.firstname = firstname;
        }

        public String getLastname() {
                return lastname;
        }

        public void setLastname(String lastname) {
                this.lastname = lastname;
        }

        public String getCompanyname() {
                return companyname;
        }

        public void setCompanyname(String companyname) {
                this.companyname = companyname;
        }

        public String getRegistration() {
                return registration;
        }

        public void setRegistration(String registration) {
                this.registration = registration;
        }
}
