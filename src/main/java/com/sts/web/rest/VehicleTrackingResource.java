package com.sts.web.rest;

import com.sts.service.VehicleTrackingService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.VehicleTrackingDTO;
import com.sts.service.dto.VehicleTrackingCriteria;
import com.sts.service.VehicleTrackingQueryService;

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
 * REST controller for managing {@link com.sts.domain.VehicleTracking}.
 */
@RestController
@RequestMapping("/api")
public class VehicleTrackingResource {

    private final Logger log = LoggerFactory.getLogger(VehicleTrackingResource.class);

    private static final String ENTITY_NAME = "vehicleTracking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleTrackingService vehicleTrackingService;

    private final VehicleTrackingQueryService vehicleTrackingQueryService;

    public VehicleTrackingResource(VehicleTrackingService vehicleTrackingService, VehicleTrackingQueryService vehicleTrackingQueryService) {
        this.vehicleTrackingService = vehicleTrackingService;
        this.vehicleTrackingQueryService = vehicleTrackingQueryService;
    }

    /**
     * {@code POST  /vehicle-trackings} : Create a new vehicleTracking.
     *
     * @param vehicleTrackingDTO the vehicleTrackingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleTrackingDTO, or with status {@code 400 (Bad Request)} if the vehicleTracking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-trackings")
    public ResponseEntity<VehicleTrackingDTO> createVehicleTracking(@Valid @RequestBody VehicleTrackingDTO vehicleTrackingDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleTracking : {}", vehicleTrackingDTO);
        if (vehicleTrackingDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleTrackingDTO result = vehicleTrackingService.save(vehicleTrackingDTO);
        return ResponseEntity.created(new URI("/api/vehicle-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-trackings} : Updates an existing vehicleTracking.
     *
     * @param vehicleTrackingDTO the vehicleTrackingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleTrackingDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleTrackingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleTrackingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-trackings")
    public ResponseEntity<VehicleTrackingDTO> updateVehicleTracking(@Valid @RequestBody VehicleTrackingDTO vehicleTrackingDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleTracking : {}", vehicleTrackingDTO);
        if (vehicleTrackingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleTrackingDTO result = vehicleTrackingService.save(vehicleTrackingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleTrackingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicle-trackings} : get all the vehicleTrackings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleTrackings in body.
     */
    @GetMapping("/vehicle-trackings")
    public ResponseEntity<List<VehicleTrackingDTO>> getAllVehicleTrackings(VehicleTrackingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VehicleTrackings by criteria: {}", criteria);
        Page<VehicleTrackingDTO> page = vehicleTrackingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-trackings/count} : count all the vehicleTrackings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-trackings/count")
    public ResponseEntity<Long> countVehicleTrackings(VehicleTrackingCriteria criteria) {
        log.debug("REST request to count VehicleTrackings by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleTrackingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-trackings/:id} : get the "id" vehicleTracking.
     *
     * @param id the id of the vehicleTrackingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleTrackingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-trackings/{id}")
    public ResponseEntity<VehicleTrackingDTO> getVehicleTracking(@PathVariable Long id) {
        log.debug("REST request to get VehicleTracking : {}", id);
        Optional<VehicleTrackingDTO> vehicleTrackingDTO = vehicleTrackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleTrackingDTO);
    }

    /**
     * {@code DELETE  /vehicle-trackings/:id} : delete the "id" vehicleTracking.
     *
     * @param id the id of the vehicleTrackingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-trackings/{id}")
    public ResponseEntity<Void> deleteVehicleTracking(@PathVariable Long id) {
        log.debug("REST request to delete VehicleTracking : {}", id);
        vehicleTrackingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/vehicle-trackings?query=:query} : search for the vehicleTracking corresponding
     * to the query.
     *
     * @param query the query of the vehicleTracking search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/vehicle-trackings")
    public ResponseEntity<List<VehicleTrackingDTO>> searchVehicleTrackings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of VehicleTrackings for query {}", query);
        Page<VehicleTrackingDTO> page = vehicleTrackingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
