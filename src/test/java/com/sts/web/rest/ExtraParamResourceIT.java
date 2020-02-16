package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.ExtraParam;
import com.sts.domain.Company;
import com.sts.repository.ExtraParamRepository;
import com.sts.repository.search.ExtraParamSearchRepository;
import com.sts.service.ExtraParamService;
import com.sts.service.dto.ExtraParamDTO;
import com.sts.service.mapper.ExtraParamMapper;
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
 * Integration tests for the {@link ExtraParamResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class ExtraParamResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ExtraParamRepository extraParamRepository;

    @Autowired
    private ExtraParamMapper extraParamMapper;

    @Autowired
    private ExtraParamService extraParamService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.ExtraParamSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExtraParamSearchRepository mockExtraParamSearchRepository;

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

    private MockMvc restExtraParamMockMvc;

    private ExtraParam extraParam;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExtraParamResource extraParamResource = new ExtraParamResource(extraParamService);
        this.restExtraParamMockMvc = MockMvcBuilders.standaloneSetup(extraParamResource)
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
    public static ExtraParam createEntity(EntityManager em) {
        ExtraParam extraParam = new ExtraParam()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        extraParam.setCompany(company);
        return extraParam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtraParam createUpdatedEntity(EntityManager em) {
        ExtraParam extraParam = new ExtraParam()
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        extraParam.setCompany(company);
        return extraParam;
    }

    @BeforeEach
    public void initTest() {
        extraParam = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtraParam() throws Exception {
        int databaseSizeBeforeCreate = extraParamRepository.findAll().size();

        // Create the ExtraParam
        ExtraParamDTO extraParamDTO = extraParamMapper.toDto(extraParam);
        restExtraParamMockMvc.perform(post("/api/extra-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraParamDTO)))
            .andExpect(status().isCreated());

        // Validate the ExtraParam in the database
        List<ExtraParam> extraParamList = extraParamRepository.findAll();
        assertThat(extraParamList).hasSize(databaseSizeBeforeCreate + 1);
        ExtraParam testExtraParam = extraParamList.get(extraParamList.size() - 1);
        assertThat(testExtraParam.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testExtraParam.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the ExtraParam in Elasticsearch
        verify(mockExtraParamSearchRepository, times(1)).save(testExtraParam);
    }

    @Test
    @Transactional
    public void createExtraParamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extraParamRepository.findAll().size();

        // Create the ExtraParam with an existing ID
        extraParam.setId(1L);
        ExtraParamDTO extraParamDTO = extraParamMapper.toDto(extraParam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtraParamMockMvc.perform(post("/api/extra-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtraParam in the database
        List<ExtraParam> extraParamList = extraParamRepository.findAll();
        assertThat(extraParamList).hasSize(databaseSizeBeforeCreate);

        // Validate the ExtraParam in Elasticsearch
        verify(mockExtraParamSearchRepository, times(0)).save(extraParam);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraParamRepository.findAll().size();
        // set the field null
        extraParam.setKey(null);

        // Create the ExtraParam, which fails.
        ExtraParamDTO extraParamDTO = extraParamMapper.toDto(extraParam);

        restExtraParamMockMvc.perform(post("/api/extra-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraParamDTO)))
            .andExpect(status().isBadRequest());

        List<ExtraParam> extraParamList = extraParamRepository.findAll();
        assertThat(extraParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraParamRepository.findAll().size();
        // set the field null
        extraParam.setValue(null);

        // Create the ExtraParam, which fails.
        ExtraParamDTO extraParamDTO = extraParamMapper.toDto(extraParam);

        restExtraParamMockMvc.perform(post("/api/extra-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraParamDTO)))
            .andExpect(status().isBadRequest());

        List<ExtraParam> extraParamList = extraParamRepository.findAll();
        assertThat(extraParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExtraParams() throws Exception {
        // Initialize the database
        extraParamRepository.saveAndFlush(extraParam);

        // Get all the extraParamList
        restExtraParamMockMvc.perform(get("/api/extra-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extraParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getExtraParam() throws Exception {
        // Initialize the database
        extraParamRepository.saveAndFlush(extraParam);

        // Get the extraParam
        restExtraParamMockMvc.perform(get("/api/extra-params/{id}", extraParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(extraParam.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingExtraParam() throws Exception {
        // Get the extraParam
        restExtraParamMockMvc.perform(get("/api/extra-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtraParam() throws Exception {
        // Initialize the database
        extraParamRepository.saveAndFlush(extraParam);

        int databaseSizeBeforeUpdate = extraParamRepository.findAll().size();

        // Update the extraParam
        ExtraParam updatedExtraParam = extraParamRepository.findById(extraParam.getId()).get();
        // Disconnect from session so that the updates on updatedExtraParam are not directly saved in db
        em.detach(updatedExtraParam);
        updatedExtraParam
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        ExtraParamDTO extraParamDTO = extraParamMapper.toDto(updatedExtraParam);

        restExtraParamMockMvc.perform(put("/api/extra-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraParamDTO)))
            .andExpect(status().isOk());

        // Validate the ExtraParam in the database
        List<ExtraParam> extraParamList = extraParamRepository.findAll();
        assertThat(extraParamList).hasSize(databaseSizeBeforeUpdate);
        ExtraParam testExtraParam = extraParamList.get(extraParamList.size() - 1);
        assertThat(testExtraParam.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testExtraParam.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the ExtraParam in Elasticsearch
        verify(mockExtraParamSearchRepository, times(1)).save(testExtraParam);
    }

    @Test
    @Transactional
    public void updateNonExistingExtraParam() throws Exception {
        int databaseSizeBeforeUpdate = extraParamRepository.findAll().size();

        // Create the ExtraParam
        ExtraParamDTO extraParamDTO = extraParamMapper.toDto(extraParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtraParamMockMvc.perform(put("/api/extra-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extraParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtraParam in the database
        List<ExtraParam> extraParamList = extraParamRepository.findAll();
        assertThat(extraParamList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ExtraParam in Elasticsearch
        verify(mockExtraParamSearchRepository, times(0)).save(extraParam);
    }

    @Test
    @Transactional
    public void deleteExtraParam() throws Exception {
        // Initialize the database
        extraParamRepository.saveAndFlush(extraParam);

        int databaseSizeBeforeDelete = extraParamRepository.findAll().size();

        // Delete the extraParam
        restExtraParamMockMvc.perform(delete("/api/extra-params/{id}", extraParam.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExtraParam> extraParamList = extraParamRepository.findAll();
        assertThat(extraParamList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ExtraParam in Elasticsearch
        verify(mockExtraParamSearchRepository, times(1)).deleteById(extraParam.getId());
    }

    @Test
    @Transactional
    public void searchExtraParam() throws Exception {
        // Initialize the database
        extraParamRepository.saveAndFlush(extraParam);
        when(mockExtraParamSearchRepository.search(queryStringQuery("id:" + extraParam.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(extraParam), PageRequest.of(0, 1), 1));
        // Search the extraParam
        restExtraParamMockMvc.perform(get("/api/_search/extra-params?query=id:" + extraParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extraParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
}
