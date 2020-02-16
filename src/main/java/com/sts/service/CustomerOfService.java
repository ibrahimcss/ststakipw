package com.sts.service;

import com.sts.domain.CustomerOf;
import com.sts.repository.CustomerOfRepository;
import com.sts.repository.search.CustomerOfSearchRepository;
import com.sts.service.dto.CustomerOfDTO;
import com.sts.service.mapper.CustomerOfMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CustomerOf}.
 */
@Service
@Transactional
public class CustomerOfService {

    private final Logger log = LoggerFactory.getLogger(CustomerOfService.class);

    private final CustomerOfRepository customerOfRepository;

    private final CustomerOfMapper customerOfMapper;

    private final CustomerOfSearchRepository customerOfSearchRepository;

    public CustomerOfService(CustomerOfRepository customerOfRepository, CustomerOfMapper customerOfMapper, CustomerOfSearchRepository customerOfSearchRepository) {
        this.customerOfRepository = customerOfRepository;
        this.customerOfMapper = customerOfMapper;
        this.customerOfSearchRepository = customerOfSearchRepository;
    }

    /**
     * Save a customerOf.
     *
     * @param customerOfDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerOfDTO save(CustomerOfDTO customerOfDTO) {
        log.debug("Request to save CustomerOf : {}", customerOfDTO);
        CustomerOf customerOf = customerOfMapper.toEntity(customerOfDTO);
        customerOf = customerOfRepository.save(customerOf);
        CustomerOfDTO result = customerOfMapper.toDto(customerOf);
        customerOfSearchRepository.save(customerOf);
        return result;
    }

    /**
     * Get all the customerOfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerOfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerOfs");
        return customerOfRepository.findAll(pageable)
            .map(customerOfMapper::toDto);
    }

    /**
     * Get one customerOf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerOfDTO> findOne(Long id) {
        log.debug("Request to get CustomerOf : {}", id);
        return customerOfRepository.findById(id)
            .map(customerOfMapper::toDto);
    }

    /**
     * Delete the customerOf by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerOf : {}", id);
        customerOfRepository.deleteById(id);
        customerOfSearchRepository.deleteById(id);
    }

    /**
     * Search for the customerOf corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerOfDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CustomerOfs for query {}", query);
        return customerOfSearchRepository.search(queryStringQuery(query), pageable)
            .map(customerOfMapper::toDto);
    }
}
