package com.sts.service;

import com.sts.domain.StudentTracking;
import com.sts.repository.StudentTrackingRepository;
import com.sts.repository.search.StudentTrackingSearchRepository;
import com.sts.service.dto.StudentTrackingDTO;
import com.sts.service.mapper.StudentTrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link StudentTracking}.
 */
@Service
@Transactional
public class StudentTrackingService {

    private final Logger log = LoggerFactory.getLogger(StudentTrackingService.class);

    private final StudentTrackingRepository studentTrackingRepository;

    private final StudentTrackingMapper studentTrackingMapper;

    private final StudentTrackingSearchRepository studentTrackingSearchRepository;

    public StudentTrackingService(StudentTrackingRepository studentTrackingRepository, StudentTrackingMapper studentTrackingMapper, StudentTrackingSearchRepository studentTrackingSearchRepository) {
        this.studentTrackingRepository = studentTrackingRepository;
        this.studentTrackingMapper = studentTrackingMapper;
        this.studentTrackingSearchRepository = studentTrackingSearchRepository;
    }

    /**
     * Save a studentTracking.
     *
     * @param studentTrackingDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentTrackingDTO save(StudentTrackingDTO studentTrackingDTO) {
        log.debug("Request to save StudentTracking : {}", studentTrackingDTO);
        StudentTracking studentTracking = studentTrackingMapper.toEntity(studentTrackingDTO);
        studentTracking = studentTrackingRepository.save(studentTracking);
        StudentTrackingDTO result = studentTrackingMapper.toDto(studentTracking);
        studentTrackingSearchRepository.save(studentTracking);
        return result;
    }

    /**
     * Get all the studentTrackings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentTrackingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentTrackings");
        return studentTrackingRepository.findAll(pageable)
            .map(studentTrackingMapper::toDto);
    }

    /**
     * Get one studentTracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentTrackingDTO> findOne(Long id) {
        log.debug("Request to get StudentTracking : {}", id);
        return studentTrackingRepository.findById(id)
            .map(studentTrackingMapper::toDto);
    }

    /**
     * Delete the studentTracking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentTracking : {}", id);
        studentTrackingRepository.deleteById(id);
        studentTrackingSearchRepository.deleteById(id);
    }

    /**
     * Search for the studentTracking corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentTrackingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentTrackings for query {}", query);
        return studentTrackingSearchRepository.search(queryStringQuery(query), pageable)
            .map(studentTrackingMapper::toDto);
    }
}
