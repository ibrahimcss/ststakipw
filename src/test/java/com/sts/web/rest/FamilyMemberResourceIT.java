package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.FamilyMember;
import com.sts.domain.CustomerOf;
import com.sts.domain.Student;
import com.sts.repository.FamilyMemberRepository;
import com.sts.repository.search.FamilyMemberSearchRepository;
import com.sts.service.FamilyMemberService;
import com.sts.service.dto.FamilyMemberDTO;
import com.sts.service.mapper.FamilyMemberMapper;
import com.sts.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.sts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FamilyMemberResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class FamilyMemberResourceIT {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private FamilyMemberMapper familyMemberMapper;

    @Autowired
    private FamilyMemberService familyMemberService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.FamilyMemberSearchRepositoryMockConfiguration
     */
    @Autowired
    private FamilyMemberSearchRepository mockFamilyMemberSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFamilyMemberMockMvc;

    private FamilyMember familyMember;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamilyMemberResource familyMemberResource = new FamilyMemberResource(familyMemberService);
        this.restFamilyMemberMockMvc = MockMvcBuilders.standaloneSetup(familyMemberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyMember createEntity(EntityManager em) {
        FamilyMember familyMember = new FamilyMember();
        // Add required entity
        CustomerOf customerOf;
        if (TestUtil.findAll(em, CustomerOf.class).isEmpty()) {
            customerOf = CustomerOfResourceIT.createEntity(em);
            em.persist(customerOf);
            em.flush();
        } else {
            customerOf = TestUtil.findAll(em, CustomerOf.class).get(0);
        }
        familyMember.setParent(customerOf);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        familyMember.setChild(student);
        return familyMember;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyMember createUpdatedEntity(EntityManager em) {
        FamilyMember familyMember = new FamilyMember();
        // Add required entity
        CustomerOf customerOf;
        if (TestUtil.findAll(em, CustomerOf.class).isEmpty()) {
            customerOf = CustomerOfResourceIT.createUpdatedEntity(em);
            em.persist(customerOf);
            em.flush();
        } else {
            customerOf = TestUtil.findAll(em, CustomerOf.class).get(0);
        }
        familyMember.setParent(customerOf);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createUpdatedEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        familyMember.setChild(student);
        return familyMember;
    }

    @BeforeEach
    public void initTest() {
        familyMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilyMember() throws Exception {
        int databaseSizeBeforeCreate = familyMemberRepository.findAll().size();

        // Create the FamilyMember
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);
        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO)))
            .andExpect(status().isCreated());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeCreate + 1);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(1)).save(testFamilyMember);
    }

    @Test
    @Transactional
    public void createFamilyMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familyMemberRepository.findAll().size();

        // Create the FamilyMember with an existing ID
        familyMember.setId(1L);
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeCreate);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(0)).save(familyMember);
    }


    @Test
    @Transactional
    public void getAllFamilyMembers() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        // Get all the familyMemberList
        restFamilyMemberMockMvc.perform(get("/api/family-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyMember.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        // Get the familyMember
        restFamilyMemberMockMvc.perform(get("/api/family-members/{id}", familyMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(familyMember.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFamilyMember() throws Exception {
        // Get the familyMember
        restFamilyMemberMockMvc.perform(get("/api/family-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();

        // Update the familyMember
        FamilyMember updatedFamilyMember = familyMemberRepository.findById(familyMember.getId()).get();
        // Disconnect from session so that the updates on updatedFamilyMember are not directly saved in db
        em.detach(updatedFamilyMember);
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(updatedFamilyMember);

        restFamilyMemberMockMvc.perform(put("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO)))
            .andExpect(status().isOk());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(1)).save(testFamilyMember);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilyMember() throws Exception {
        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();

        // Create the FamilyMember
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyMemberMockMvc.perform(put("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(0)).save(familyMember);
    }

    @Test
    @Transactional
    public void deleteFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        int databaseSizeBeforeDelete = familyMemberRepository.findAll().size();

        // Delete the familyMember
        restFamilyMemberMockMvc.perform(delete("/api/family-members/{id}", familyMember.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(1)).deleteById(familyMember.getId());
    }

    @Test
    @Transactional
    public void searchFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);
        when(mockFamilyMemberSearchRepository.search(queryStringQuery("id:" + familyMember.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(familyMember), PageRequest.of(0, 1), 1));
        // Search the familyMember
        restFamilyMemberMockMvc.perform(get("/api/_search/family-members?query=id:" + familyMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyMember.getId().intValue())));
    }
}
