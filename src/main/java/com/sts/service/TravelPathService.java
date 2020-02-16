package com.sts.service;

import com.sts.domain.TravelPath;
import com.sts.repository.TravelPathRepository;
import com.sts.repository.search.TravelPathSearchRepository;
import com.sts.service.dto.TravelPathDTO;
import com.sts.service.mapper.TravelPathMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TravelPath}.
 */
@Service
@Transactional
public class TravelPathService {

    private final Logger log = LoggerFactory.getLogger(TravelPathService.class);

    private final TravelPathRepository travelPathRepository;

    private final TravelPathMapper travelPathMapper;

    private final TravelPathSearchRepository travelPathSearchRepository;

    public TravelPathService(TravelPathRepository travelPathRepository, TravelPathMapper travelPathMapper, TravelPathSearchRepository travelPathSearchRepository) {
        this.travelPathRepository = travelPathRepository;
        this.travelPathMapper = travelPathMapper;
        this.travelPathSearchRepository = travelPathSearchRepository;
    }

    /**
     * Save a travelPath.
     *
     * @param travelPathDTO the entity to save.
     * @return the persisted entity.
     */
    public TravelPathDTO save(TravelPathDTO travelPathDTO) {
        log.debug("Request to save TravelPath : {}", travelPathDTO);
        TravelPath travelPath = travelPathMapper.toEntity(travelPathDTO);
        travelPath = travelPathRepository.save(travelPath);
        TravelPathDTO result = travelPathMapper.toDto(travelPath);
        travelPathSearchRepository.save(travelPath);
        return result;
    }

    /**
     * Get all the travelPaths.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TravelPathDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TravelPaths");
        return travelPathRepository.findAll(pageable)
            .map(travelPathMapper::toDto);
    }

    /**
     * Get one travelPath by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TravelPathDTO> findOne(Long id) {
        log.debug("Request to get TravelPath : {}", id);
        return travelPathRepository.findById(id)
            .map(travelPathMapper::toDto);
    }

    /**
     * Delete the travelPath by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TravelPath : {}", id);
        travelPathRepository.deleteById(id);
        travelPathSearchRepository.deleteById(id);
    }

    /**
     * Search for the travelPath corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TravelPathDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TravelPaths for query {}", query);
        return travelPathSearchRepository.search(queryStringQuery(query), pageable)
            .map(travelPathMapper::toDto);
    }
}
