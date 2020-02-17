package com.sts.repository;

import com.sts.domain.WorksIn;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the WorksIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorksInRepository extends JpaRepository<WorksIn, Long>, QuerydslPredicateExecutor<WorksIn> {

    @Query("select worksIn from WorksIn worksIn where worksIn.employee.login = ?#{principal.username}")
    WorksIn findByEmployeeIsCurrentUserOrderByIdIdAsc();

    WorksIn findByEmployeeId(Long id);
}
