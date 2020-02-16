package com.sts.repository;

import com.sts.domain.ExtraParam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExtraParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraParamRepository extends JpaRepository<ExtraParam, Long> {

}
