package com.fanshawe.shippingservicesproject.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(	name = "quotePrice")
public class QuotePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String price;

    private String comments;

    @Column(name = "price_given_date")
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="quote_id")
    private QuoteRequest quoteRequest;

    public QuotePrice( String price, String comments, User user, QuoteRequest quoteRequest) {
        this.price = price;
        this.comments = comments;
        this.user = user;
        this.quoteRequest = quoteRequest;
    }

    public QuotePrice(String price, String comments, LocalDate createdDate, User user, QuoteRequest quoteRequest) {
        this.price = price;
        this.comments = comments;
        this.createdDate = createdDate;
        this.user = user;
        this.quoteRequest = quoteRequest;
    }

    public QuotePrice() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    /*    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public QuoteRequest getQuoteRequest() {
        return quoteRequest;
    }

    public void setQuoteRequest(QuoteRequest quoteRequest) {
        this.quoteRequest = quoteRequest;
    }*/
}
