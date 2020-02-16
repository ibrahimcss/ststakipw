package com.sts.service;

import com.sts.domain.FamilyMember;
import com.sts.repository.FamilyMemberRepository;
import com.sts.repository.search.FamilyMemberSearchRepository;
import com.sts.service.dto.FamilyMemberDTO;
import com.sts.service.mapper.FamilyMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FamilyMember}.
 */
@Service
@Transactional
public class FamilyMemberService {

    private final Logger log = LoggerFactory.getLogger(FamilyMemberService.class);

    private final FamilyMemberRepository familyMemberRepository;

    private final FamilyMemberMapper familyMemberMapper;

    private final FamilyMemberSearchRepository familyMemberSearchRepository;

    public FamilyMemberService(FamilyMemberRepository familyMemberRepository, FamilyMemberMapper familyMemberMapper, FamilyMemberSearchRepository familyMemberSearchRepository) {
        this.familyMemberRepository = familyMemberRepository;
        this.familyMemberMapper = familyMemberMapper;
        this.familyMemberSearchRepository = familyMemberSearchRepository;
    }

    /**
     * Save a familyMember.
     *
     * @param familyMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public FamilyMemberDTO save(FamilyMemberDTO familyMemberDTO) {
        log.debug("Request to save FamilyMember : {}", familyMemberDTO);
        FamilyMember familyMember = familyMemberMapper.toEntity(familyMemberDTO);
        familyMember = familyMemberRepository.save(familyMember);
        FamilyMemberDTO result = familyMemberMapper.toDto(familyMember);
        familyMemberSearchRepository.save(familyMember);
        return result;
    }

    /**
     * Get all the familyMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FamilyMemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FamilyMembers");
        return familyMemberRepository.findAll(pageable)
            .map(familyMemberMapper::toDto);
    }

    /**
     * Get one familyMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FamilyMemberDTO> findOne(Long id) {
        log.debug("Request to get FamilyMember : {}", id);
        return familyMemberRepository.findById(id)
            .map(familyMemberMapper::toDto);
    }

    /**
     * Delete the familyMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FamilyMember : {}", id);
        familyMemberRepository.deleteById(id);
        familyMemberSearchRepository.deleteById(id);
    }

    /**
     * Search for the familyMember corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FamilyMemberDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FamilyMembers for query {}", query);
        return familyMemberSearchRepository.search(queryStringQuery(query), pageable)
            .map(familyMemberMapper::toDto);
    }
}
