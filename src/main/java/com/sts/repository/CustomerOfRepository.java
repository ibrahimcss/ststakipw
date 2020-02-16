package com.sts.repository;

import com.sts.domain.CustomerOf;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CustomerOf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerOfRepository extends JpaRepository<CustomerOf, Long> {

    @Query("select customerOf from CustomerOf customerOf where customerOf.customer.login = ?#{principal.username}")
    List<CustomerOf> findByCustomerIsCurrentUser();

}
