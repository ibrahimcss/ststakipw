package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.WorksIn;
import com.sts.domain.User;
import com.sts.domain.Company;
import com.sts.repository.WorksInRepository;
import com.sts.repository.search.WorksInSearchRepository;
import com.sts.service.WorksInService;
import com.sts.service.dto.WorksInDTO;
import com.sts.service.mapper.WorksInMapper;
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
 * Integration tests for the {@link WorksInResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class WorksInResourceIT {

    private static final Long DEFAULT_TC_ID = 10000000000L;
    private static final Long UPDATED_TC_ID = 10000000001L;

    private static final String DEFAULT_PHONE = "+906778097353";
    private static final String UPDATED_PHONE = "+902150950549";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private WorksInRepository worksInRepository;

    @Autowired
    private WorksInMapper worksInMapper;

    @Autowired
    private WorksInService worksInService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.WorksInSearchRepositoryMockConfiguration
     */
    @Autowired
    private WorksInSearchRepository mockWorksInSearchRepository;

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

    private MockMvc restWorksInMockMvc;

    private WorksIn worksIn;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorksInResource worksInResource = new WorksInResource(worksInService);
        this.restWorksInMockMvc = MockMvcBuilders.standaloneSetup(worksInResource)
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
    public static WorksIn createEntity(EntityManager em) {
        WorksIn worksIn = new WorksIn()
            .tcId(DEFAULT_TC_ID)
            .phone(DEFAULT_PHONE)
            .enabled(DEFAULT_ENABLED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        worksIn.setEmployee(user);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        worksIn.setCompany(company);
        return worksIn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorksIn createUpdatedEntity(EntityManager em) {
        WorksIn worksIn = new WorksIn()
            .tcId(UPDATED_TC_ID)
            .phone(UPDATED_PHONE)
            .enabled(UPDATED_ENABLED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        worksIn.setEmployee(user);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        worksIn.setCompany(company);
        return worksIn;
    }

    @BeforeEach
    public void initTest() {
        worksIn = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorksIn() throws Exception {
        int databaseSizeBeforeCreate = worksInRepository.findAll().size();

        // Create the WorksIn
        WorksInDTO worksInDTO = worksInMapper.toDto(worksIn);
        restWorksInMockMvc.perform(post("/api/works-ins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worksInDTO)))
            .andExpect(status().isCreated());

        // Validate the WorksIn in the database
        List<WorksIn> worksInList = worksInRepository.findAll();
        assertThat(worksInList).hasSize(databaseSizeBeforeCreate + 1);
        WorksIn testWorksIn = worksInList.get(worksInList.size() - 1);
        assertThat(testWorksIn.getTcId()).isEqualTo(DEFAULT_TC_ID);
        assertThat(testWorksIn.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testWorksIn.isEnabled()).isEqualTo(DEFAULT_ENABLED);

        // Validate the WorksIn in Elasticsearch
        verify(mockWorksInSearchRepository, times(1)).save(testWorksIn);
    }

    @Test
    @Transactional
    public void createWorksInWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = worksInRepository.findAll().size();

        // Create the WorksIn with an existing ID
        worksIn.setId(1L);
        WorksInDTO worksInDTO = worksInMapper.toDto(worksIn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorksInMockMvc.perform(post("/api/works-ins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worksInDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorksIn in the database
        List<WorksIn> worksInList = worksInRepository.findAll();
        assertThat(worksInList).hasSize(databaseSizeBeforeCreate);

        // Validate the WorksIn in Elasticsearch
        verify(mockWorksInSearchRepository, times(0)).save(worksIn);
    }


    @Test
    @Transactional
    public void checkTcIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = worksInRepository.findAll().size();
        // set the field null
        worksIn.setTcId(null);

        // Create the WorksIn, which fails.
        WorksInDTO worksInDTO = worksInMapper.toDto(worksIn);

        restWorksInMockMvc.perform(post("/api/works-ins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worksInDTO)))
            .andExpect(status().isBadRequest());

        List<WorksIn> worksInList = worksInRepository.findAll();
        assertThat(worksInList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = worksInRepository.findAll().size();
        // set the field null
        worksIn.setPhone(null);

        // Create the WorksIn, which fails.
        WorksInDTO worksInDTO = worksInMapper.toDto(worksIn);

        restWorksInMockMvc.perform(post("/api/works-ins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worksInDTO)))
            .andExpect(status().isBadRequest());

        List<WorksIn> worksInList = worksInRepository.findAll();
        assertThat(worksInList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = worksInRepository.findAll().size();
        // set the field null
        worksIn.setEnabled(null);

        // Create the WorksIn, which fails.
        WorksInDTO worksInDTO = worksInMapper.toDto(worksIn);

        restWorksInMockMvc.perform(post("/api/works-ins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worksInDTO)))
            .andExpect(status().isBadRequest());

        List<WorksIn> worksInList = worksInRepository.findAll();
        assertThat(worksInList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorksIns() throws Exception {
        // Initialize the database
        worksInRepository.saveAndFlush(worksIn);

        // Get all the worksInList
        restWorksInMockMvc.perform(get("/api/works-ins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(worksIn.getId().intValue())))
            .andExpect(jsonPath("$.[*].tcId").value(hasItem(DEFAULT_TC_ID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getWorksIn() throws Exception {
        // Initialize the database
        worksInRepository.saveAndFlush(worksIn);

        // Get the worksIn
        restWorksInMockMvc.perform(get("/api/works-ins/{id}", worksIn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(worksIn.getId().intValue()))
            .andExpect(jsonPath("$.tcId").value(DEFAULT_TC_ID.intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWorksIn() throws Exception {
        // Get the worksIn
        restWorksInMockMvc.perform(get("/api/works-ins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorksIn() throws Exception {
        // Initialize the database
        worksInRepository.saveAndFlush(worksIn);

        int databaseSizeBeforeUpdate = worksInRepository.findAll().size();

        // Update the worksIn
        WorksIn updatedWorksIn = worksInRepository.findById(worksIn.getId()).get();
        // Disconnect from session so that the updates on updatedWorksIn are not directly saved in db
        em.detach(updatedWorksIn);
        updatedWorksIn
            .tcId(UPDATED_TC_ID)
            .phone(UPDATED_PHONE)
            .enabled(UPDATED_ENABLED);
        WorksInDTO worksInDTO = worksInMapper.toDto(updatedWorksIn);

        restWorksInMockMvc.perform(put("/api/works-ins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worksInDTO)))
            .andExpect(status().isOk());

        // Validate the WorksIn in the database
        List<WorksIn> worksInList = worksInRepository.findAll();
        assertThat(worksInList).hasSize(databaseSizeBeforeUpdate);
        WorksIn testWorksIn = worksInList.get(worksInList.size() - 1);
        assertThat(testWorksIn.getTcId()).isEqualTo(UPDATED_TC_ID);
        assertThat(testWorksIn.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testWorksIn.isEnabled()).isEqualTo(UPDATED_ENABLED);

        // Validate the WorksIn in Elasticsearch
        verify(mockWorksInSearchRepository, times(1)).save(testWorksIn);
    }

    @Test
    @Transactional
    public void updateNonExistingWorksIn() throws Exception {
        int databaseSizeBeforeUpdate = worksInRepository.findAll().size();

        // Create the WorksIn
        WorksInDTO worksInDTO = worksInMapper.toDto(worksIn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorksInMockMvc.perform(put("/api/works-ins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(worksInDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorksIn in the database
        List<WorksIn> worksInList = worksInRepository.findAll();
        assertThat(worksInList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WorksIn in Elasticsearch
        verify(mockWorksInSearchRepository, times(0)).save(worksIn);
    }

    @Test
    @Transactional
    public void deleteWorksIn() throws Exception {
        // Initialize the database
        worksInRepository.saveAndFlush(worksIn);

        int databaseSizeBeforeDelete = worksInRepository.findAll().size();

        // Delete the worksIn
        restWorksInMockMvc.perform(delete("/api/works-ins/{id}", worksIn.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorksIn> worksInList = worksInRepository.findAll();
        assertThat(worksInList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WorksIn in Elasticsearch
        verify(mockWorksInSearchRepository, times(1)).deleteById(worksIn.getId());
    }

    @Test
    @Transactional
    public void searchWorksIn() throws Exception {
        // Initialize the database
        worksInRepository.saveAndFlush(worksIn);
        when(mockWorksInSearchRepository.search(queryStringQuery("id:" + worksIn.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(worksIn), PageRequest.of(0, 1), 1));
        // Search the worksIn
        restWorksInMockMvc.perform(get("/api/_search/works-ins?query=id:" + worksIn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(worksIn.getId().intValue())))
            .andExpect(jsonPath("$.[*].tcId").value(hasItem(DEFAULT_TC_ID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
}
