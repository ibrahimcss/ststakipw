package com.sts.web.rest;

import com.sts.service.PaketDetayService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.PaketDetayDTO;

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
 * REST controller for managing {@link com.sts.domain.PaketDetay}.
 */
@RestController
@RequestMapping("/api")
public class PaketDetayResource {

    private final Logger log = LoggerFactory.getLogger(PaketDetayResource.class);

    private static final String ENTITY_NAME = "paketDetay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaketDetayService paketDetayService;

    public PaketDetayResource(PaketDetayService paketDetayService) {
        this.paketDetayService = paketDetayService;
    }

    /**
     * {@code POST  /paket-detays} : Create a new paketDetay.
     *
     * @param paketDetayDTO the paketDetayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paketDetayDTO, or with status {@code 400 (Bad Request)} if the paketDetay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paket-detays")
    public ResponseEntity<PaketDetayDTO> createPaketDetay(@Valid @RequestBody PaketDetayDTO paketDetayDTO) throws URISyntaxException {
        log.debug("REST request to save PaketDetay : {}", paketDetayDTO);
        if (paketDetayDTO.getId() != null) {
            throw new BadRequestAlertException("A new paketDetay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaketDetayDTO result = paketDetayService.save(paketDetayDTO);
        return ResponseEntity.created(new URI("/api/paket-detays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paket-detays} : Updates an existing paketDetay.
     *
     * @param paketDetayDTO the paketDetayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paketDetayDTO,
     * or with status {@code 400 (Bad Request)} if the paketDetayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paketDetayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paket-detays")
    public ResponseEntity<PaketDetayDTO> updatePaketDetay(@Valid @RequestBody PaketDetayDTO paketDetayDTO) throws URISyntaxException {
        log.debug("REST request to update PaketDetay : {}", paketDetayDTO);
        if (paketDetayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaketDetayDTO result = paketDetayService.save(paketDetayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paketDetayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paket-detays} : get all the paketDetays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paketDetays in body.
     */
    @GetMapping("/paket-detays")
    public ResponseEntity<List<PaketDetayDTO>> getAllPaketDetays(Pageable pageable) {
        log.debug("REST request to get a page of PaketDetays");
        Page<PaketDetayDTO> page = paketDetayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paket-detays/:id} : get the "id" paketDetay.
     *
     * @param id the id of the paketDetayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paketDetayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paket-detays/{id}")
    public ResponseEntity<PaketDetayDTO> getPaketDetay(@PathVariable Long id) {
        log.debug("REST request to get PaketDetay : {}", id);
        Optional<PaketDetayDTO> paketDetayDTO = paketDetayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paketDetayDTO);
    }

    /**
     * {@code DELETE  /paket-detays/:id} : delete the "id" paketDetay.
     *
     * @param id the id of the paketDetayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paket-detays/{id}")
    public ResponseEntity<Void> deletePaketDetay(@PathVariable Long id) {
        log.debug("REST request to delete PaketDetay : {}", id);
        paketDetayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/paket-detays?query=:query} : search for the paketDetay corresponding
     * to the query.
     *
     * @param query the query of the paketDetay search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/paket-detays")
    public ResponseEntity<List<PaketDetayDTO>> searchPaketDetays(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PaketDetays for query {}", query);
        Page<PaketDetayDTO> page = paketDetayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
