package com.fanshawe.shippingservicesproject.repositories;

import com.fanshawe.shippingservicesproject.model.QuoteRequest;
import com.fanshawe.shippingservicesproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteRequest, Long> {

    List<QuoteRequest> findQuoteRequestsByUserInfo(User user);

    QuoteRequest getById(Long id);


    /*
    * Used to display all the quotes to the Vendor.
    * */
    @Query("select qr from QuoteRequest qr\n" +
            "where qr.id not in (select r.id from QuotePrice p join QuoteRequest r\n" +
            "on p.quoteRequest.id = r.id and p.user.id= :user_id)")
    List<QuoteRequest> findQRByQuotePrice(@Param("user_id") Long id);


    /*
    * This Function is used by vendor to see list of items for which the price has given.
    * */
    @Query("select r,p from User u join QuoteRequest r\n" +
            "on u.id = r.userInfo.id \n" +
            "join QuotePrice p \n" +
            "on p.quoteRequest.id = r.id\n" +
            "where p.price not like 'NA' and p.user.id= :user_id")
    List<?> findQRByPrice(@Param("user_id") Long id);

    /*
     * This Function is used by buyer to view all the quotes that got the prices.
     * */
    @Query("select r,p,p.user from User u join QuoteRequest r\n" +
            "on u.id = r.userInfo.id \n" +
            "join QuotePrice p \n" +
            "on p.quoteRequest.id = r.id\n" +
            "where p.price not like 'NA' and r.userInfo.id= :user_id")
    List<?> findQRByPriceGiven(@Param("user_id") Long id);

}
