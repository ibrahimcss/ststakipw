package com.sts.web.rest;

import com.sts.service.TravelPathService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.TravelPathDTO;

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
 * REST controller for managing {@link com.sts.domain.TravelPath}.
 */
@RestController
@RequestMapping("/api")
public class TravelPathResource {

    private final Logger log = LoggerFactory.getLogger(TravelPathResource.class);

    private static final String ENTITY_NAME = "travelPath";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TravelPathService travelPathService;

    public TravelPathResource(TravelPathService travelPathService) {
        this.travelPathService = travelPathService;
    }

    /**
     * {@code POST  /travel-paths} : Create a new travelPath.
     *
     * @param travelPathDTO the travelPathDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new travelPathDTO, or with status {@code 400 (Bad Request)} if the travelPath has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/travel-paths")
    public ResponseEntity<TravelPathDTO> createTravelPath(@Valid @RequestBody TravelPathDTO travelPathDTO) throws URISyntaxException {
        log.debug("REST request to save TravelPath : {}", travelPathDTO);
        if (travelPathDTO.getId() != null) {
            throw new BadRequestAlertException("A new travelPath cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TravelPathDTO result = travelPathService.save(travelPathDTO);
        return ResponseEntity.created(new URI("/api/travel-paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /travel-paths} : Updates an existing travelPath.
     *
     * @param travelPathDTO the travelPathDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travelPathDTO,
     * or with status {@code 400 (Bad Request)} if the travelPathDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the travelPathDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/travel-paths")
    public ResponseEntity<TravelPathDTO> updateTravelPath(@Valid @RequestBody TravelPathDTO travelPathDTO) throws URISyntaxException {
        log.debug("REST request to update TravelPath : {}", travelPathDTO);
        if (travelPathDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TravelPathDTO result = travelPathService.save(travelPathDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, travelPathDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /travel-paths} : get all the travelPaths.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of travelPaths in body.
     */
    @GetMapping("/travel-paths")
    public ResponseEntity<List<TravelPathDTO>> getAllTravelPaths(Pageable pageable) {
        log.debug("REST request to get a page of TravelPaths");
        Page<TravelPathDTO> page = travelPathService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /travel-paths/:id} : get the "id" travelPath.
     *
     * @param id the id of the travelPathDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the travelPathDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/travel-paths/{id}")
    public ResponseEntity<TravelPathDTO> getTravelPath(@PathVariable Long id) {
        log.debug("REST request to get TravelPath : {}", id);
        Optional<TravelPathDTO> travelPathDTO = travelPathService.findOne(id);
        return ResponseUtil.wrapOrNotFound(travelPathDTO);
    }

    /**
     * {@code DELETE  /travel-paths/:id} : delete the "id" travelPath.
     *
     * @param id the id of the travelPathDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/travel-paths/{id}")
    public ResponseEntity<Void> deleteTravelPath(@PathVariable Long id) {
        log.debug("REST request to delete TravelPath : {}", id);
        travelPathService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/travel-paths?query=:query} : search for the travelPath corresponding
     * to the query.
     *
     * @param query the query of the travelPath search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/travel-paths")
    public ResponseEntity<List<TravelPathDTO>> searchTravelPaths(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TravelPaths for query {}", query);
        Page<TravelPathDTO> page = travelPathService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
