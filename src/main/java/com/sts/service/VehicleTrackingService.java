package com.sts.service;

import com.sts.domain.VehicleTracking;
import com.sts.repository.VehicleTrackingRepository;
import com.sts.repository.search.VehicleTrackingSearchRepository;
import com.sts.service.dto.VehicleTrackingDTO;
import com.sts.service.mapper.VehicleTrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link VehicleTracking}.
 */
@Service
@Transactional
public class VehicleTrackingService {

    private final Logger log = LoggerFactory.getLogger(VehicleTrackingService.class);

    private final VehicleTrackingRepository vehicleTrackingRepository;

    private final VehicleTrackingMapper vehicleTrackingMapper;

    private final VehicleTrackingSearchRepository vehicleTrackingSearchRepository;

    public VehicleTrackingService(VehicleTrackingRepository vehicleTrackingRepository, VehicleTrackingMapper vehicleTrackingMapper, VehicleTrackingSearchRepository vehicleTrackingSearchRepository) {
        this.vehicleTrackingRepository = vehicleTrackingRepository;
        this.vehicleTrackingMapper = vehicleTrackingMapper;
        this.vehicleTrackingSearchRepository = vehicleTrackingSearchRepository;
    }

    /**
     * Save a vehicleTracking.
     *
     * @param vehicleTrackingDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleTrackingDTO save(VehicleTrackingDTO vehicleTrackingDTO) {
        log.debug("Request to save VehicleTracking : {}", vehicleTrackingDTO);
        VehicleTracking vehicleTracking = vehicleTrackingMapper.toEntity(vehicleTrackingDTO);
        vehicleTracking = vehicleTrackingRepository.save(vehicleTracking);
        VehicleTrackingDTO result = vehicleTrackingMapper.toDto(vehicleTracking);
        vehicleTrackingSearchRepository.save(vehicleTracking);
        return result;
    }

    /**
     * Get all the vehicleTrackings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleTrackingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleTrackings");
        return vehicleTrackingRepository.findAll(pageable)
            .map(vehicleTrackingMapper::toDto);
    }

    /**
     * Get one vehicleTracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleTrackingDTO> findOne(Long id) {
        log.debug("Request to get VehicleTracking : {}", id);
        return vehicleTrackingRepository.findById(id)
            .map(vehicleTrackingMapper::toDto);
    }

    /**
     * Delete the vehicleTracking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleTracking : {}", id);
        vehicleTrackingRepository.deleteById(id);
        vehicleTrackingSearchRepository.deleteById(id);
    }

    /**
     * Search for the vehicleTracking corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleTrackingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VehicleTrackings for query {}", query);
        return vehicleTrackingSearchRepository.search(queryStringQuery(query), pageable)
            .map(vehicleTrackingMapper::toDto);
    }
}
