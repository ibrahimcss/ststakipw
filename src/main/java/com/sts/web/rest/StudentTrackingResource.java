package com.sts.web.rest;

import com.sts.service.StudentTrackingService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.StudentTrackingDTO;

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
 * REST controller for managing {@link com.sts.domain.StudentTracking}.
 */
@RestController
@RequestMapping("/api")
public class StudentTrackingResource {

    private final Logger log = LoggerFactory.getLogger(StudentTrackingResource.class);

    private static final String ENTITY_NAME = "studentTracking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentTrackingService studentTrackingService;

    public StudentTrackingResource(StudentTrackingService studentTrackingService) {
        this.studentTrackingService = studentTrackingService;
    }

    /**
     * {@code POST  /student-trackings} : Create a new studentTracking.
     *
     * @param studentTrackingDTO the studentTrackingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentTrackingDTO, or with status {@code 400 (Bad Request)} if the studentTracking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-trackings")
    public ResponseEntity<StudentTrackingDTO> createStudentTracking(@Valid @RequestBody StudentTrackingDTO studentTrackingDTO) throws URISyntaxException {
        log.debug("REST request to save StudentTracking : {}", studentTrackingDTO);
        if (studentTrackingDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentTrackingDTO result = studentTrackingService.save(studentTrackingDTO);
        return ResponseEntity.created(new URI("/api/student-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-trackings} : Updates an existing studentTracking.
     *
     * @param studentTrackingDTO the studentTrackingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentTrackingDTO,
     * or with status {@code 400 (Bad Request)} if the studentTrackingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentTrackingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-trackings")
    public ResponseEntity<StudentTrackingDTO> updateStudentTracking(@Valid @RequestBody StudentTrackingDTO studentTrackingDTO) throws URISyntaxException {
        log.debug("REST request to update StudentTracking : {}", studentTrackingDTO);
        if (studentTrackingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentTrackingDTO result = studentTrackingService.save(studentTrackingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentTrackingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /student-trackings} : get all the studentTrackings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentTrackings in body.
     */
    @GetMapping("/student-trackings")
    public ResponseEntity<List<StudentTrackingDTO>> getAllStudentTrackings(Pageable pageable) {
        log.debug("REST request to get a page of StudentTrackings");
        Page<StudentTrackingDTO> page = studentTrackingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /student-trackings/:id} : get the "id" studentTracking.
     *
     * @param id the id of the studentTrackingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentTrackingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-trackings/{id}")
    public ResponseEntity<StudentTrackingDTO> getStudentTracking(@PathVariable Long id) {
        log.debug("REST request to get StudentTracking : {}", id);
        Optional<StudentTrackingDTO> studentTrackingDTO = studentTrackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentTrackingDTO);
    }

    /**
     * {@code DELETE  /student-trackings/:id} : delete the "id" studentTracking.
     *
     * @param id the id of the studentTrackingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-trackings/{id}")
    public ResponseEntity<Void> deleteStudentTracking(@PathVariable Long id) {
        log.debug("REST request to delete StudentTracking : {}", id);
        studentTrackingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/student-trackings?query=:query} : search for the studentTracking corresponding
     * to the query.
     *
     * @param query the query of the studentTracking search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/student-trackings")
    public ResponseEntity<List<StudentTrackingDTO>> searchStudentTrackings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StudentTrackings for query {}", query);
        Page<StudentTrackingDTO> page = studentTrackingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
