package com.sts.repository;

import com.sts.domain.StudentTracking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StudentTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentTrackingRepository extends JpaRepository<StudentTracking, Long> {

}
