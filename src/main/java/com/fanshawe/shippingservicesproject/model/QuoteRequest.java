package com.fanshawe.shippingservicesproject.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(	name = "quoteRequest")
public class QuoteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commodity;

    private String count;

    private String weight;

    private String dimensions;

    private String countryorigin;

    private String provinceorigin;

    private String cityorigin;

    private String countrydes;

    private String provincedes;

    private String citydes;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User userInfo;

    @OneToMany(mappedBy = "quoteRequest",targetEntity = QuotePrice.class,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuotePrice> quotePrices;

    public QuoteRequest(String commodity, String count, String weight, String dimensions, String countryorigin, String provinceorigin, String cityorigin, String countrydes, String provincedes, String citydes, LocalDate createdDate, User userInfo) {
        this.commodity = commodity;
        this.count = count;
        this.weight = weight;
        this.dimensions = dimensions;
        this.countryorigin = countryorigin;
        this.provinceorigin = provinceorigin;
        this.cityorigin = cityorigin;
        this.countrydes = countrydes;
        this.provincedes = provincedes;
        this.citydes = citydes;
        this.createdDate = createdDate;
        this.userInfo = userInfo;
    }

    public QuoteRequest(String commodity, String count, String weight, String dimensions, String countryorigin, String provinceorigin, String cityorigin, String countrydes, String provincedes, String citydes, User userInfo) {
        this.commodity = commodity;
        this.count = count;
        this.weight = weight;
        this.dimensions = dimensions;
        this.countryorigin = countryorigin;
        this.provinceorigin = provinceorigin;
        this.cityorigin = cityorigin;
        this.countrydes = countrydes;
        this.provincedes = provincedes;
        this.citydes = citydes;
        this.userInfo = userInfo;
    }

    public QuoteRequest() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getCountryorigin() {
        return countryorigin;
    }

    public void setCountryorigin(String countryorigin) {
        this.countryorigin = countryorigin;
    }

    public String getProvinceorigin() {
        return provinceorigin;
    }

    public void setProvinceorigin(String provinceorigin) {
        this.provinceorigin = provinceorigin;
    }

    public String getCityorigin() {
        return cityorigin;
    }

    public void setCityorigin(String cityorigin) {
        this.cityorigin = cityorigin;
    }

    public String getCountrydes() {
        return countrydes;
    }

    public void setCountrydes(String countrydes) {
        this.countrydes = countrydes;
    }

    public String getProvincedes() {
        return provincedes;
    }

    public void setProvincedes(String provincedes) {
        this.provincedes = provincedes;
    }

    public String getCitydes() {
        return citydes;
    }

    public void setCitydes(String citydes) {
        this.citydes = citydes;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    /*public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }*/

    /*public List<QuotePrice> getQuotePrices() {
        return quotePrices;
    }

    public void setQuotePrices(List<QuotePrice> quotePrices) {
        this.quotePrices = quotePrices;
    }*/
}
