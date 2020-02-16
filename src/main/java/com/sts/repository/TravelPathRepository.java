package com.sts.repository;

import com.sts.domain.TravelPath;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the TravelPath entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TravelPathRepository extends JpaRepository<TravelPath, Long> {

    @Query("select travelPath from TravelPath travelPath where travelPath.employee.login = ?#{principal.username}")
    List<TravelPath> findByEmployeeIsCurrentUser();

}
