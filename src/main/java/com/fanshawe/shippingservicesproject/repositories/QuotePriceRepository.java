package com.fanshawe.shippingservicesproject.repositories;

import com.fanshawe.shippingservicesproject.model.QuotePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotePriceRepository extends JpaRepository<QuotePrice, Long> {
}
