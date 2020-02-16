package com.sts.repository;

import com.sts.domain.WorksIn;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the WorksIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorksInRepository extends JpaRepository<WorksIn, Long> {

    @Query("select worksIn from WorksIn worksIn where worksIn.employee.login = ?#{principal.username}")
    List<WorksIn> findByEmployeeIsCurrentUser();

}
