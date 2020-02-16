package com.sts.repository;

import com.sts.domain.PaketDetay;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaketDetay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaketDetayRepository extends JpaRepository<PaketDetay, Long> {

}
