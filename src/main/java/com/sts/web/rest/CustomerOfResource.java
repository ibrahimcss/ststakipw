package com.sts.web.rest;

import com.sts.service.CustomerOfService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.CustomerOfDTO;

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
 * REST controller for managing {@link com.sts.domain.CustomerOf}.
 */
@RestController
@RequestMapping("/api")
public class CustomerOfResource {

    private final Logger log = LoggerFactory.getLogger(CustomerOfResource.class);

    private static final String ENTITY_NAME = "customerOf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerOfService customerOfService;

    public CustomerOfResource(CustomerOfService customerOfService) {
        this.customerOfService = customerOfService;
    }

    /**
     * {@code POST  /customer-ofs} : Create a new customerOf.
     *
     * @param customerOfDTO the customerOfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerOfDTO, or with status {@code 400 (Bad Request)} if the customerOf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-ofs")
    public ResponseEntity<CustomerOfDTO> createCustomerOf(@Valid @RequestBody CustomerOfDTO customerOfDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerOf : {}", customerOfDTO);
        if (customerOfDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerOf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerOfDTO result = customerOfService.save(customerOfDTO);
        return ResponseEntity.created(new URI("/api/customer-ofs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-ofs} : Updates an existing customerOf.
     *
     * @param customerOfDTO the customerOfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerOfDTO,
     * or with status {@code 400 (Bad Request)} if the customerOfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerOfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-ofs")
    public ResponseEntity<CustomerOfDTO> updateCustomerOf(@Valid @RequestBody CustomerOfDTO customerOfDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerOf : {}", customerOfDTO);
        if (customerOfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerOfDTO result = customerOfService.save(customerOfDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerOfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-ofs} : get all the customerOfs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerOfs in body.
     */
    @GetMapping("/customer-ofs")
    public ResponseEntity<List<CustomerOfDTO>> getAllCustomerOfs(Pageable pageable) {
        log.debug("REST request to get a page of CustomerOfs");
        Page<CustomerOfDTO> page = customerOfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-ofs/:id} : get the "id" customerOf.
     *
     * @param id the id of the customerOfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerOfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-ofs/{id}")
    public ResponseEntity<CustomerOfDTO> getCustomerOf(@PathVariable Long id) {
        log.debug("REST request to get CustomerOf : {}", id);
        Optional<CustomerOfDTO> customerOfDTO = customerOfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerOfDTO);
    }

    /**
     * {@code DELETE  /customer-ofs/:id} : delete the "id" customerOf.
     *
     * @param id the id of the customerOfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-ofs/{id}")
    public ResponseEntity<Void> deleteCustomerOf(@PathVariable Long id) {
        log.debug("REST request to delete CustomerOf : {}", id);
        customerOfService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/customer-ofs?query=:query} : search for the customerOf corresponding
     * to the query.
     *
     * @param query the query of the customerOf search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/customer-ofs")
    public ResponseEntity<List<CustomerOfDTO>> searchCustomerOfs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CustomerOfs for query {}", query);
        Page<CustomerOfDTO> page = customerOfService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
