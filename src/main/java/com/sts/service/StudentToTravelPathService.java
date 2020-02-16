package com.sts.service;

import com.sts.domain.StudentToTravelPath;
import com.sts.repository.StudentToTravelPathRepository;
import com.sts.repository.search.StudentToTravelPathSearchRepository;
import com.sts.service.dto.StudentToTravelPathDTO;
import com.sts.service.mapper.StudentToTravelPathMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link StudentToTravelPath}.
 */
@Service
@Transactional
public class StudentToTravelPathService {

    private final Logger log = LoggerFactory.getLogger(StudentToTravelPathService.class);

    private final StudentToTravelPathRepository studentToTravelPathRepository;

    private final StudentToTravelPathMapper studentToTravelPathMapper;

    private final StudentToTravelPathSearchRepository studentToTravelPathSearchRepository;

    public StudentToTravelPathService(StudentToTravelPathRepository studentToTravelPathRepository, StudentToTravelPathMapper studentToTravelPathMapper, StudentToTravelPathSearchRepository studentToTravelPathSearchRepository) {
        this.studentToTravelPathRepository = studentToTravelPathRepository;
        this.studentToTravelPathMapper = studentToTravelPathMapper;
        this.studentToTravelPathSearchRepository = studentToTravelPathSearchRepository;
    }

    /**
     * Save a studentToTravelPath.
     *
     * @param studentToTravelPathDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentToTravelPathDTO save(StudentToTravelPathDTO studentToTravelPathDTO) {
        log.debug("Request to save StudentToTravelPath : {}", studentToTravelPathDTO);
        StudentToTravelPath studentToTravelPath = studentToTravelPathMapper.toEntity(studentToTravelPathDTO);
        studentToTravelPath = studentToTravelPathRepository.save(studentToTravelPath);
        StudentToTravelPathDTO result = studentToTravelPathMapper.toDto(studentToTravelPath);
        studentToTravelPathSearchRepository.save(studentToTravelPath);
        return result;
    }

    /**
     * Get all the studentToTravelPaths.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentToTravelPathDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentToTravelPaths");
        return studentToTravelPathRepository.findAll(pageable)
            .map(studentToTravelPathMapper::toDto);
    }

    /**
     * Get one studentToTravelPath by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentToTravelPathDTO> findOne(Long id) {
        log.debug("Request to get StudentToTravelPath : {}", id);
        return studentToTravelPathRepository.findById(id)
            .map(studentToTravelPathMapper::toDto);
    }

    /**
     * Delete the studentToTravelPath by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentToTravelPath : {}", id);
        studentToTravelPathRepository.deleteById(id);
        studentToTravelPathSearchRepository.deleteById(id);
    }

    /**
     * Search for the studentToTravelPath corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentToTravelPathDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentToTravelPaths for query {}", query);
        return studentToTravelPathSearchRepository.search(queryStringQuery(query), pageable)
            .map(studentToTravelPathMapper::toDto);
    }
}
