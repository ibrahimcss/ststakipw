package com.sts.service;

import com.sts.domain.OrderPaket;
import com.sts.repository.OrderPaketRepository;
import com.sts.repository.search.OrderPaketSearchRepository;
import com.sts.service.dto.OrderPaketDTO;
import com.sts.service.mapper.OrderPaketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link OrderPaket}.
 */
@Service
@Transactional
public class OrderPaketService {

    private final Logger log = LoggerFactory.getLogger(OrderPaketService.class);

    private final OrderPaketRepository orderPaketRepository;

    private final OrderPaketMapper orderPaketMapper;

    private final OrderPaketSearchRepository orderPaketSearchRepository;

    public OrderPaketService(OrderPaketRepository orderPaketRepository, OrderPaketMapper orderPaketMapper, OrderPaketSearchRepository orderPaketSearchRepository) {
        this.orderPaketRepository = orderPaketRepository;
        this.orderPaketMapper = orderPaketMapper;
        this.orderPaketSearchRepository = orderPaketSearchRepository;
    }

    /**
     * Save a orderPaket.
     *
     * @param orderPaketDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderPaketDTO save(OrderPaketDTO orderPaketDTO) {
        log.debug("Request to save OrderPaket : {}", orderPaketDTO);
        OrderPaket orderPaket = orderPaketMapper.toEntity(orderPaketDTO);
        orderPaket = orderPaketRepository.save(orderPaket);
        OrderPaketDTO result = orderPaketMapper.toDto(orderPaket);
        orderPaketSearchRepository.save(orderPaket);
        return result;
    }

    /**
     * Get all the orderPakets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderPaketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderPakets");
        return orderPaketRepository.findAll(pageable)
            .map(orderPaketMapper::toDto);
    }

    /**
     * Get one orderPaket by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderPaketDTO> findOne(Long id) {
        log.debug("Request to get OrderPaket : {}", id);
        return orderPaketRepository.findById(id)
            .map(orderPaketMapper::toDto);
    }

    /**
     * Delete the orderPaket by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderPaket : {}", id);
        orderPaketRepository.deleteById(id);
        orderPaketSearchRepository.deleteById(id);
    }

    /**
     * Search for the orderPaket corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderPaketDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrderPakets for query {}", query);
        return orderPaketSearchRepository.search(queryStringQuery(query), pageable)
            .map(orderPaketMapper::toDto);
    }
}
