package com.sts.service;

import com.sts.domain.PaketDetay;
import com.sts.repository.PaketDetayRepository;
import com.sts.repository.search.PaketDetaySearchRepository;
import com.sts.service.dto.PaketDetayDTO;
import com.sts.service.mapper.PaketDetayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PaketDetay}.
 */
@Service
@Transactional
public class PaketDetayService {

    private final Logger log = LoggerFactory.getLogger(PaketDetayService.class);

    private final PaketDetayRepository paketDetayRepository;

    private final PaketDetayMapper paketDetayMapper;

    private final PaketDetaySearchRepository paketDetaySearchRepository;

    public PaketDetayService(PaketDetayRepository paketDetayRepository, PaketDetayMapper paketDetayMapper, PaketDetaySearchRepository paketDetaySearchRepository) {
        this.paketDetayRepository = paketDetayRepository;
        this.paketDetayMapper = paketDetayMapper;
        this.paketDetaySearchRepository = paketDetaySearchRepository;
    }

    /**
     * Save a paketDetay.
     *
     * @param paketDetayDTO the entity to save.
     * @return the persisted entity.
     */
    public PaketDetayDTO save(PaketDetayDTO paketDetayDTO) {
        log.debug("Request to save PaketDetay : {}", paketDetayDTO);
        PaketDetay paketDetay = paketDetayMapper.toEntity(paketDetayDTO);
        paketDetay = paketDetayRepository.save(paketDetay);
        PaketDetayDTO result = paketDetayMapper.toDto(paketDetay);
        paketDetaySearchRepository.save(paketDetay);
        return result;
    }

    /**
     * Get all the paketDetays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaketDetayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaketDetays");
        return paketDetayRepository.findAll(pageable)
            .map(paketDetayMapper::toDto);
    }

    /**
     * Get one paketDetay by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaketDetayDTO> findOne(Long id) {
        log.debug("Request to get PaketDetay : {}", id);
        return paketDetayRepository.findById(id)
            .map(paketDetayMapper::toDto);
    }

    /**
     * Delete the paketDetay by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaketDetay : {}", id);
        paketDetayRepository.deleteById(id);
        paketDetaySearchRepository.deleteById(id);
    }

    /**
     * Search for the paketDetay corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaketDetayDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaketDetays for query {}", query);
        return paketDetaySearchRepository.search(queryStringQuery(query), pageable)
            .map(paketDetayMapper::toDto);
    }
}
