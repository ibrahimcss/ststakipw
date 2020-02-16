package com.sts.web.rest;

import com.sts.service.WorksInService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.WorksInDTO;

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
 * REST controller for managing {@link com.sts.domain.WorksIn}.
 */
@RestController
@RequestMapping("/api")
public class WorksInResource {

    private final Logger log = LoggerFactory.getLogger(WorksInResource.class);

    private static final String ENTITY_NAME = "worksIn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorksInService worksInService;

    public WorksInResource(WorksInService worksInService) {
        this.worksInService = worksInService;
    }

    /**
     * {@code POST  /works-ins} : Create a new worksIn.
     *
     * @param worksInDTO the worksInDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new worksInDTO, or with status {@code 400 (Bad Request)} if the worksIn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/works-ins")
    public ResponseEntity<WorksInDTO> createWorksIn(@Valid @RequestBody WorksInDTO worksInDTO) throws URISyntaxException {
        log.debug("REST request to save WorksIn : {}", worksInDTO);
        if (worksInDTO.getId() != null) {
            throw new BadRequestAlertException("A new worksIn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorksInDTO result = worksInService.save(worksInDTO);
        return ResponseEntity.created(new URI("/api/works-ins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /works-ins} : Updates an existing worksIn.
     *
     * @param worksInDTO the worksInDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated worksInDTO,
     * or with status {@code 400 (Bad Request)} if the worksInDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the worksInDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/works-ins")
    public ResponseEntity<WorksInDTO> updateWorksIn(@Valid @RequestBody WorksInDTO worksInDTO) throws URISyntaxException {
        log.debug("REST request to update WorksIn : {}", worksInDTO);
        if (worksInDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorksInDTO result = worksInService.save(worksInDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, worksInDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /works-ins} : get all the worksIns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of worksIns in body.
     */
    @GetMapping("/works-ins")
    public ResponseEntity<List<WorksInDTO>> getAllWorksIns(Pageable pageable) {
        log.debug("REST request to get a page of WorksIns");
        Page<WorksInDTO> page = worksInService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /works-ins/:id} : get the "id" worksIn.
     *
     * @param id the id of the worksInDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the worksInDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/works-ins/{id}")
    public ResponseEntity<WorksInDTO> getWorksIn(@PathVariable Long id) {
        log.debug("REST request to get WorksIn : {}", id);
        Optional<WorksInDTO> worksInDTO = worksInService.findOne(id);
        return ResponseUtil.wrapOrNotFound(worksInDTO);
    }

    /**
     * {@code DELETE  /works-ins/:id} : delete the "id" worksIn.
     *
     * @param id the id of the worksInDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/works-ins/{id}")
    public ResponseEntity<Void> deleteWorksIn(@PathVariable Long id) {
        log.debug("REST request to delete WorksIn : {}", id);
        worksInService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/works-ins?query=:query} : search for the worksIn corresponding
     * to the query.
     *
     * @param query the query of the worksIn search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/works-ins")
    public ResponseEntity<List<WorksInDTO>> searchWorksIns(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of WorksIns for query {}", query);
        Page<WorksInDTO> page = worksInService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
