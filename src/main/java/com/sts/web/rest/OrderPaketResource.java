package com.sts.web.rest;

import com.sts.service.OrderPaketService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.OrderPaketDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.sts.domain.OrderPaket}.
 */
@RestController
@RequestMapping("/api")
public class OrderPaketResource {

    private final Logger log = LoggerFactory.getLogger(OrderPaketResource.class);

    private static final String ENTITY_NAME = "orderPaket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderPaketService orderPaketService;

    public OrderPaketResource(OrderPaketService orderPaketService) {
        this.orderPaketService = orderPaketService;
    }

    /**
     * {@code POST  /order-pakets} : Create a new orderPaket.
     *
     * @param orderPaketDTO the orderPaketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderPaketDTO, or with status {@code 400 (Bad Request)} if the orderPaket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-pakets")
    public ResponseEntity<OrderPaketDTO> createOrderPaket(@Valid @RequestBody OrderPaketDTO orderPaketDTO) throws URISyntaxException {
        log.debug("REST request to save OrderPaket : {}", orderPaketDTO);
        if (orderPaketDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderPaket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderPaketDTO result = orderPaketService.save(orderPaketDTO);
        return ResponseEntity.created(new URI("/api/order-pakets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-pakets} : Updates an existing orderPaket.
     *
     * @param orderPaketDTO the orderPaketDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderPaketDTO,
     * or with status {@code 400 (Bad Request)} if the orderPaketDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderPaketDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-pakets")
    public ResponseEntity<OrderPaketDTO> updateOrderPaket(@Valid @RequestBody OrderPaketDTO orderPaketDTO) throws URISyntaxException {
        log.debug("REST request to update OrderPaket : {}", orderPaketDTO);
        if (orderPaketDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderPaketDTO result = orderPaketService.save(orderPaketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderPaketDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-pakets} : get all the orderPakets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderPakets in body.
     */
    @GetMapping("/order-pakets")
    public ResponseEntity<List<OrderPaketDTO>> getAllOrderPakets(Pageable pageable) {
        log.debug("REST request to get a page of OrderPakets");
        Page<OrderPaketDTO> page = orderPaketService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-pakets/:id} : get the "id" orderPaket.
     *
     * @param id the id of the orderPaketDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderPaketDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-pakets/{id}")
    public ResponseEntity<OrderPaketDTO> getOrderPaket(@PathVariable Long id) {
        log.debug("REST request to get OrderPaket : {}", id);
        Optional<OrderPaketDTO> orderPaketDTO = orderPaketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderPaketDTO);
    }

    /**
     * {@code DELETE  /order-pakets/:id} : delete the "id" orderPaket.
     *
     * @param id the id of the orderPaketDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-pakets/{id}")
    public ResponseEntity<Void> deleteOrderPaket(@PathVariable Long id) {
        log.debug("REST request to delete OrderPaket : {}", id);
        orderPaketService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/order-pakets?query=:query} : search for the orderPaket corresponding
     * to the query.
     *
     * @param query the query of the orderPaket search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/order-pakets")
    public ResponseEntity<List<OrderPaketDTO>> searchOrderPakets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrderPakets for query {}", query);
        Page<OrderPaketDTO> page = orderPaketService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
