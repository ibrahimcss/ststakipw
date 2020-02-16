package com.sts.service;

import com.sts.domain.ExtraParam;
import com.sts.repository.ExtraParamRepository;
import com.sts.repository.search.ExtraParamSearchRepository;
import com.sts.service.dto.ExtraParamDTO;
import com.sts.service.mapper.ExtraParamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ExtraParam}.
 */
@Service
@Transactional
public class ExtraParamService {

    private final Logger log = LoggerFactory.getLogger(ExtraParamService.class);

    private final ExtraParamRepository extraParamRepository;

    private final ExtraParamMapper extraParamMapper;

    private final ExtraParamSearchRepository extraParamSearchRepository;

    public ExtraParamService(ExtraParamRepository extraParamRepository, ExtraParamMapper extraParamMapper, ExtraParamSearchRepository extraParamSearchRepository) {
        this.extraParamRepository = extraParamRepository;
        this.extraParamMapper = extraParamMapper;
        this.extraParamSearchRepository = extraParamSearchRepository;
    }

    /**
     * Save a extraParam.
     *
     * @param extraParamDTO the entity to save.
     * @return the persisted entity.
     */
    public ExtraParamDTO save(ExtraParamDTO extraParamDTO) {
        log.debug("Request to save ExtraParam : {}", extraParamDTO);
        ExtraParam extraParam = extraParamMapper.toEntity(extraParamDTO);
        extraParam = extraParamRepository.save(extraParam);
        ExtraParamDTO result = extraParamMapper.toDto(extraParam);
        extraParamSearchRepository.save(extraParam);
        return result;
    }

    /**
     * Get all the extraParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtraParamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExtraParams");
        return extraParamRepository.findAll(pageable)
            .map(extraParamMapper::toDto);
    }

    /**
     * Get one extraParam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExtraParamDTO> findOne(Long id) {
        log.debug("Request to get ExtraParam : {}", id);
        return extraParamRepository.findById(id)
            .map(extraParamMapper::toDto);
    }

    /**
     * Delete the extraParam by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExtraParam : {}", id);
        extraParamRepository.deleteById(id);
        extraParamSearchRepository.deleteById(id);
    }

    /**
     * Search for the extraParam corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtraParamDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ExtraParams for query {}", query);
        return extraParamSearchRepository.search(queryStringQuery(query), pageable)
            .map(extraParamMapper::toDto);
    }
}
