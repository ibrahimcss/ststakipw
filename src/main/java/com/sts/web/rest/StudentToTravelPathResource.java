package com.sts.web.rest;

import com.sts.service.StudentToTravelPathService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.StudentToTravelPathDTO;

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
 * REST controller for managing {@link com.sts.domain.StudentToTravelPath}.
 */
@RestController
@RequestMapping("/api")
public class StudentToTravelPathResource {

    private final Logger log = LoggerFactory.getLogger(StudentToTravelPathResource.class);

    private static final String ENTITY_NAME = "studentToTravelPath";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentToTravelPathService studentToTravelPathService;

    public StudentToTravelPathResource(StudentToTravelPathService studentToTravelPathService) {
        this.studentToTravelPathService = studentToTravelPathService;
    }

    /**
     * {@code POST  /student-to-travel-paths} : Create a new studentToTravelPath.
     *
     * @param studentToTravelPathDTO the studentToTravelPathDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentToTravelPathDTO, or with status {@code 400 (Bad Request)} if the studentToTravelPath has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-to-travel-paths")
    public ResponseEntity<StudentToTravelPathDTO> createStudentToTravelPath(@Valid @RequestBody StudentToTravelPathDTO studentToTravelPathDTO) throws URISyntaxException {
        log.debug("REST request to save StudentToTravelPath : {}", studentToTravelPathDTO);
        if (studentToTravelPathDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentToTravelPath cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentToTravelPathDTO result = studentToTravelPathService.save(studentToTravelPathDTO);
        return ResponseEntity.created(new URI("/api/student-to-travel-paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-to-travel-paths} : Updates an existing studentToTravelPath.
     *
     * @param studentToTravelPathDTO the studentToTravelPathDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentToTravelPathDTO,
     * or with status {@code 400 (Bad Request)} if the studentToTravelPathDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentToTravelPathDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-to-travel-paths")
    public ResponseEntity<StudentToTravelPathDTO> updateStudentToTravelPath(@Valid @RequestBody StudentToTravelPathDTO studentToTravelPathDTO) throws URISyntaxException {
        log.debug("REST request to update StudentToTravelPath : {}", studentToTravelPathDTO);
        if (studentToTravelPathDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentToTravelPathDTO result = studentToTravelPathService.save(studentToTravelPathDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentToTravelPathDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /student-to-travel-paths} : get all the studentToTravelPaths.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentToTravelPaths in body.
     */
    @GetMapping("/student-to-travel-paths")
    public ResponseEntity<List<StudentToTravelPathDTO>> getAllStudentToTravelPaths(Pageable pageable) {
        log.debug("REST request to get a page of StudentToTravelPaths");
        Page<StudentToTravelPathDTO> page = studentToTravelPathService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /student-to-travel-paths/:id} : get the "id" studentToTravelPath.
     *
     * @param id the id of the studentToTravelPathDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentToTravelPathDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-to-travel-paths/{id}")
    public ResponseEntity<StudentToTravelPathDTO> getStudentToTravelPath(@PathVariable Long id) {
        log.debug("REST request to get StudentToTravelPath : {}", id);
        Optional<StudentToTravelPathDTO> studentToTravelPathDTO = studentToTravelPathService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentToTravelPathDTO);
    }

    /**
     * {@code DELETE  /student-to-travel-paths/:id} : delete the "id" studentToTravelPath.
     *
     * @param id the id of the studentToTravelPathDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-to-travel-paths/{id}")
    public ResponseEntity<Void> deleteStudentToTravelPath(@PathVariable Long id) {
        log.debug("REST request to delete StudentToTravelPath : {}", id);
        studentToTravelPathService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/student-to-travel-paths?query=:query} : search for the studentToTravelPath corresponding
     * to the query.
     *
     * @param query the query of the studentToTravelPath search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/student-to-travel-paths")
    public ResponseEntity<List<StudentToTravelPathDTO>> searchStudentToTravelPaths(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StudentToTravelPaths for query {}", query);
        Page<StudentToTravelPathDTO> page = studentToTravelPathService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
