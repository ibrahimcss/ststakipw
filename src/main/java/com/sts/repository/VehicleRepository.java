package com.sts.repository;

import com.sts.domain.Vehicle;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Vehicle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, QuerydslPredicateExecutor<Vehicle> {

    @Query("select vehicle from Vehicle vehicle where vehicle.credential.login = ?#{principal.username}")
    List<Vehicle> findByCredentialIsCurrentUser();

}
