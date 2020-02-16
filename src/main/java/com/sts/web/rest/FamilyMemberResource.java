package com.sts.web.rest;

import com.sts.service.FamilyMemberService;
import com.sts.web.rest.errors.BadRequestAlertException;
import com.sts.service.dto.FamilyMemberDTO;

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
 * REST controller for managing {@link com.sts.domain.FamilyMember}.
 */
@RestController
@RequestMapping("/api")
public class FamilyMemberResource {

    private final Logger log = LoggerFactory.getLogger(FamilyMemberResource.class);

    private static final String ENTITY_NAME = "familyMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyMemberService familyMemberService;

    public FamilyMemberResource(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    /**
     * {@code POST  /family-members} : Create a new familyMember.
     *
     * @param familyMemberDTO the familyMemberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyMemberDTO, or with status {@code 400 (Bad Request)} if the familyMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/family-members")
    public ResponseEntity<FamilyMemberDTO> createFamilyMember(@Valid @RequestBody FamilyMemberDTO familyMemberDTO) throws URISyntaxException {
        log.debug("REST request to save FamilyMember : {}", familyMemberDTO);
        if (familyMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new familyMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilyMemberDTO result = familyMemberService.save(familyMemberDTO);
        return ResponseEntity.created(new URI("/api/family-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /family-members} : Updates an existing familyMember.
     *
     * @param familyMemberDTO the familyMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyMemberDTO,
     * or with status {@code 400 (Bad Request)} if the familyMemberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/family-members")
    public ResponseEntity<FamilyMemberDTO> updateFamilyMember(@Valid @RequestBody FamilyMemberDTO familyMemberDTO) throws URISyntaxException {
        log.debug("REST request to update FamilyMember : {}", familyMemberDTO);
        if (familyMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamilyMemberDTO result = familyMemberService.save(familyMemberDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyMemberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /family-members} : get all the familyMembers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familyMembers in body.
     */
    @GetMapping("/family-members")
    public ResponseEntity<List<FamilyMemberDTO>> getAllFamilyMembers(Pageable pageable) {
        log.debug("REST request to get a page of FamilyMembers");
        Page<FamilyMemberDTO> page = familyMemberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /family-members/:id} : get the "id" familyMember.
     *
     * @param id the id of the familyMemberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyMemberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/family-members/{id}")
    public ResponseEntity<FamilyMemberDTO> getFamilyMember(@PathVariable Long id) {
        log.debug("REST request to get FamilyMember : {}", id);
        Optional<FamilyMemberDTO> familyMemberDTO = familyMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familyMemberDTO);
    }

    /**
     * {@code DELETE  /family-members/:id} : delete the "id" familyMember.
     *
     * @param id the id of the familyMemberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/family-members/{id}")
    public ResponseEntity<Void> deleteFamilyMember(@PathVariable Long id) {
        log.debug("REST request to delete FamilyMember : {}", id);
        familyMemberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/family-members?query=:query} : search for the familyMember corresponding
     * to the query.
     *
     * @param query the query of the familyMember search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/family-members")
    public ResponseEntity<List<FamilyMemberDTO>> searchFamilyMembers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FamilyMembers for query {}", query);
        Page<FamilyMemberDTO> page = familyMemberService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
