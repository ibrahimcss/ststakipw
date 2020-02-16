package com.sts.repository;

import com.sts.domain.OrderPaket;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderPaket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderPaketRepository extends JpaRepository<OrderPaket, Long> {

}
