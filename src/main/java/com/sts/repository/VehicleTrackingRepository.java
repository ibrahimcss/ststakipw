package com.sts.repository;

import com.sts.domain.VehicleTracking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VehicleTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleTrackingRepository extends JpaRepository<VehicleTracking, Long>, JpaSpecificationExecutor<VehicleTracking> {

}
