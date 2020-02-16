package com.sts.service;

import com.sts.domain.WorksIn;
import com.sts.repository.WorksInRepository;
import com.sts.repository.search.WorksInSearchRepository;
import com.sts.service.dto.WorksInDTO;
import com.sts.service.mapper.WorksInMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link WorksIn}.
 */
@Service
@Transactional
public class WorksInService {

    private final Logger log = LoggerFactory.getLogger(WorksInService.class);

    private final WorksInRepository worksInRepository;

    private final WorksInMapper worksInMapper;

    private final WorksInSearchRepository worksInSearchRepository;

    public WorksInService(WorksInRepository worksInRepository, WorksInMapper worksInMapper, WorksInSearchRepository worksInSearchRepository) {
        this.worksInRepository = worksInRepository;
        this.worksInMapper = worksInMapper;
        this.worksInSearchRepository = worksInSearchRepository;
    }

    /**
     * Save a worksIn.
     *
     * @param worksInDTO the entity to save.
     * @return the persisted entity.
     */
    public WorksInDTO save(WorksInDTO worksInDTO) {
        log.debug("Request to save WorksIn : {}", worksInDTO);
        WorksIn worksIn = worksInMapper.toEntity(worksInDTO);
        worksIn = worksInRepository.save(worksIn);
        WorksInDTO result = worksInMapper.toDto(worksIn);
        worksInSearchRepository.save(worksIn);
        return result;
    }

    /**
     * Get all the worksIns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorksInDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorksIns");
        return worksInRepository.findAll(pageable)
            .map(worksInMapper::toDto);
    }

    /**
     * Get one worksIn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorksInDTO> findOne(Long id) {
        log.debug("Request to get WorksIn : {}", id);
        return worksInRepository.findById(id)
            .map(worksInMapper::toDto);
    }

    /**
     * Delete the worksIn by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorksIn : {}", id);
        worksInRepository.deleteById(id);
        worksInSearchRepository.deleteById(id);
    }

    /**
     * Search for the worksIn corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorksInDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WorksIns for query {}", query);
        return worksInSearchRepository.search(queryStringQuery(query), pageable)
            .map(worksInMapper::toDto);
    }
}
