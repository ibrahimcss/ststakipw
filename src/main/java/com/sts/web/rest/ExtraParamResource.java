package com.sts.web.rest;

import com.sts.service.ExtraParamService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.ExtraParamDTO;

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
 * REST controller for managing {@link com.sts.domain.ExtraParam}.
 */
@RestController
@RequestMapping("/api")
public class ExtraParamResource {

    private final Logger log = LoggerFactory.getLogger(ExtraParamResource.class);

    private static final String ENTITY_NAME = "extraParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExtraParamService extraParamService;

    public ExtraParamResource(ExtraParamService extraParamService) {
        this.extraParamService = extraParamService;
    }

    /**
     * {@code POST  /extra-params} : Create a new extraParam.
     *
     * @param extraParamDTO the extraParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new extraParamDTO, or with status {@code 400 (Bad Request)} if the extraParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/extra-params")
    public ResponseEntity<ExtraParamDTO> createExtraParam(@Valid @RequestBody ExtraParamDTO extraParamDTO) throws URISyntaxException {
        log.debug("REST request to save ExtraParam : {}", extraParamDTO);
        if (extraParamDTO.getId() != null) {
            throw new BadRequestAlertException("A new extraParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExtraParamDTO result = extraParamService.save(extraParamDTO);
        return ResponseEntity.created(new URI("/api/extra-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /extra-params} : Updates an existing extraParam.
     *
     * @param extraParamDTO the extraParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extraParamDTO,
     * or with status {@code 400 (Bad Request)} if the extraParamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the extraParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/extra-params")
    public ResponseEntity<ExtraParamDTO> updateExtraParam(@Valid @RequestBody ExtraParamDTO extraParamDTO) throws URISyntaxException {
        log.debug("REST request to update ExtraParam : {}", extraParamDTO);
        if (extraParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExtraParamDTO result = extraParamService.save(extraParamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, extraParamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /extra-params} : get all the extraParams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of extraParams in body.
     */
    @GetMapping("/extra-params")
    public ResponseEntity<List<ExtraParamDTO>> getAllExtraParams(Pageable pageable) {
        log.debug("REST request to get a page of ExtraParams");
        Page<ExtraParamDTO> page = extraParamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /extra-params/:id} : get the "id" extraParam.
     *
     * @param id the id of the extraParamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the extraParamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/extra-params/{id}")
    public ResponseEntity<ExtraParamDTO> getExtraParam(@PathVariable Long id) {
        log.debug("REST request to get ExtraParam : {}", id);
        Optional<ExtraParamDTO> extraParamDTO = extraParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extraParamDTO);
    }

    /**
     * {@code DELETE  /extra-params/:id} : delete the "id" extraParam.
     *
     * @param id the id of the extraParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/extra-params/{id}")
    public ResponseEntity<Void> deleteExtraParam(@PathVariable Long id) {
        log.debug("REST request to delete ExtraParam : {}", id);
        extraParamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/extra-params?query=:query} : search for the extraParam corresponding
     * to the query.
     *
     * @param query the query of the extraParam search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/extra-params")
    public ResponseEntity<List<ExtraParamDTO>> searchExtraParams(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ExtraParams for query {}", query);
        Page<ExtraParamDTO> page = extraParamService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
