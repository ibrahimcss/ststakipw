package com.sts.repository;

import com.sts.domain.StudentToTravelPath;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StudentToTravelPath entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentToTravelPathRepository extends JpaRepository<StudentToTravelPath, Long> {

}
