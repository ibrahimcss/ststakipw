package com.sts.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.sts.domain.VehicleTracking;
import com.sts.domain.*; // for static metamodels
import com.sts.repository.VehicleTrackingRepository;
import com.sts.repository.search.VehicleTrackingSearchRepository;
import com.sts.service.dto.VehicleTrackingCriteria;
import com.sts.service.dto.VehicleTrackingDTO;
import com.sts.service.mapper.VehicleTrackingMapper;

/**
 * Service for executing complex queries for {@link VehicleTracking} entities in the database.
 * The main input is a {@link VehicleTrackingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleTrackingDTO} or a {@link Page} of {@link VehicleTrackingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleTrackingQueryService extends QueryService<VehicleTracking> {

    private final Logger log = LoggerFactory.getLogger(VehicleTrackingQueryService.class);

    private final VehicleTrackingRepository vehicleTrackingRepository;

    private final VehicleTrackingMapper vehicleTrackingMapper;

    private final VehicleTrackingSearchRepository vehicleTrackingSearchRepository;

    public VehicleTrackingQueryService(VehicleTrackingRepository vehicleTrackingRepository, VehicleTrackingMapper vehicleTrackingMapper, VehicleTrackingSearchRepository vehicleTrackingSearchRepository) {
        this.vehicleTrackingRepository = vehicleTrackingRepository;
        this.vehicleTrackingMapper = vehicleTrackingMapper;
        this.vehicleTrackingSearchRepository = vehicleTrackingSearchRepository;
    }

    /**
     * Return a {@link List} of {@link VehicleTrackingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleTrackingDTO> findByCriteria(VehicleTrackingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleTracking> specification = createSpecification(criteria);
        return vehicleTrackingMapper.toDto(vehicleTrackingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleTrackingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleTrackingDTO> findByCriteria(VehicleTrackingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleTracking> specification = createSpecification(criteria);
        return vehicleTrackingRepository.findAll(specification, page)
            .map(vehicleTrackingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleTrackingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleTracking> specification = createSpecification(criteria);
        return vehicleTrackingRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleTrackingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleTracking> createSpecification(VehicleTrackingCriteria criteria) {
        Specification<VehicleTracking> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleTracking_.id));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), VehicleTracking_.createdAt));
            }
            if (criteria.getLon() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLon(), VehicleTracking_.lon));
            }
            if (criteria.getLat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLat(), VehicleTracking_.lat));
            }
            if (criteria.getVehicleId() != null) {
                specification = specification.and(buildSpecification(criteria.getVehicleId(),
                    root -> root.join(VehicleTracking_.vehicle, JoinType.LEFT).get(Vehicle_.id)));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(VehicleTracking_.company, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}
