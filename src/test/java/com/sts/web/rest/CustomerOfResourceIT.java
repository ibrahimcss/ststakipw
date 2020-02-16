package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.CustomerOf;
import com.sts.domain.User;
import com.sts.domain.Company;
import com.sts.repository.CustomerOfRepository;
import com.sts.repository.search.CustomerOfSearchRepository;
import com.sts.service.CustomerOfService;
import com.sts.service.dto.CustomerOfDTO;
import com.sts.service.mapper.CustomerOfMapper;
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

import com.sts.domain.enumeration.Sex;
/**
 * Integration tests for the {@link CustomerOfResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class CustomerOfResourceIT {

    private static final Long DEFAULT_TC_ID = 10000000000L;
    private static final Long UPDATED_TC_ID = 10000000001L;

    private static final String DEFAULT_PHONE = "+907258253484";
    private static final String UPDATED_PHONE = "+905962956870";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Sex DEFAULT_SEX = Sex.Male;
    private static final Sex UPDATED_SEX = Sex.Female;

    @Autowired
    private CustomerOfRepository customerOfRepository;

    @Autowired
    private CustomerOfMapper customerOfMapper;

    @Autowired
    private CustomerOfService customerOfService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.CustomerOfSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomerOfSearchRepository mockCustomerOfSearchRepository;

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

    private MockMvc restCustomerOfMockMvc;

    private CustomerOf customerOf;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerOfResource customerOfResource = new CustomerOfResource(customerOfService);
        this.restCustomerOfMockMvc = MockMvcBuilders.standaloneSetup(customerOfResource)
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
    public static CustomerOf createEntity(EntityManager em) {
        CustomerOf customerOf = new CustomerOf()
            .tcId(DEFAULT_TC_ID)
            .phone(DEFAULT_PHONE)
            .enabled(DEFAULT_ENABLED)
            .sex(DEFAULT_SEX);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        customerOf.setCustomer(user);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        customerOf.setCompany(company);
        return customerOf;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerOf createUpdatedEntity(EntityManager em) {
        CustomerOf customerOf = new CustomerOf()
            .tcId(UPDATED_TC_ID)
            .phone(UPDATED_PHONE)
            .enabled(UPDATED_ENABLED)
            .sex(UPDATED_SEX);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        customerOf.setCustomer(user);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        customerOf.setCompany(company);
        return customerOf;
    }

    @BeforeEach
    public void initTest() {
        customerOf = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerOf() throws Exception {
        int databaseSizeBeforeCreate = customerOfRepository.findAll().size();

        // Create the CustomerOf
        CustomerOfDTO customerOfDTO = customerOfMapper.toDto(customerOf);
        restCustomerOfMockMvc.perform(post("/api/customer-ofs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerOfDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerOf in the database
        List<CustomerOf> customerOfList = customerOfRepository.findAll();
        assertThat(customerOfList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOf testCustomerOf = customerOfList.get(customerOfList.size() - 1);
        assertThat(testCustomerOf.getTcId()).isEqualTo(DEFAULT_TC_ID);
        assertThat(testCustomerOf.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomerOf.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testCustomerOf.getSex()).isEqualTo(DEFAULT_SEX);

        // Validate the CustomerOf in Elasticsearch
        verify(mockCustomerOfSearchRepository, times(1)).save(testCustomerOf);
    }

    @Test
    @Transactional
    public void createCustomerOfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerOfRepository.findAll().size();

        // Create the CustomerOf with an existing ID
        customerOf.setId(1L);
        CustomerOfDTO customerOfDTO = customerOfMapper.toDto(customerOf);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerOfMockMvc.perform(post("/api/customer-ofs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerOfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerOf in the database
        List<CustomerOf> customerOfList = customerOfRepository.findAll();
        assertThat(customerOfList).hasSize(databaseSizeBeforeCreate);

        // Validate the CustomerOf in Elasticsearch
        verify(mockCustomerOfSearchRepository, times(0)).save(customerOf);
    }


    @Test
    @Transactional
    public void checkTcIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOfRepository.findAll().size();
        // set the field null
        customerOf.setTcId(null);

        // Create the CustomerOf, which fails.
        CustomerOfDTO customerOfDTO = customerOfMapper.toDto(customerOf);

        restCustomerOfMockMvc.perform(post("/api/customer-ofs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerOfDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerOf> customerOfList = customerOfRepository.findAll();
        assertThat(customerOfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOfRepository.findAll().size();
        // set the field null
        customerOf.setPhone(null);

        // Create the CustomerOf, which fails.
        CustomerOfDTO customerOfDTO = customerOfMapper.toDto(customerOf);

        restCustomerOfMockMvc.perform(post("/api/customer-ofs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerOfDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerOf> customerOfList = customerOfRepository.findAll();
        assertThat(customerOfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOfRepository.findAll().size();
        // set the field null
        customerOf.setEnabled(null);

        // Create the CustomerOf, which fails.
        CustomerOfDTO customerOfDTO = customerOfMapper.toDto(customerOf);

        restCustomerOfMockMvc.perform(post("/api/customer-ofs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerOfDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerOf> customerOfList = customerOfRepository.findAll();
        assertThat(customerOfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOfRepository.findAll().size();
        // set the field null
        customerOf.setSex(null);

        // Create the CustomerOf, which fails.
        CustomerOfDTO customerOfDTO = customerOfMapper.toDto(customerOf);

        restCustomerOfMockMvc.perform(post("/api/customer-ofs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerOfDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerOf> customerOfList = customerOfRepository.findAll();
        assertThat(customerOfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerOfs() throws Exception {
        // Initialize the database
        customerOfRepository.saveAndFlush(customerOf);

        // Get all the customerOfList
        restCustomerOfMockMvc.perform(get("/api/customer-ofs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOf.getId().intValue())))
            .andExpect(jsonPath("$.[*].tcId").value(hasItem(DEFAULT_TC_ID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerOf() throws Exception {
        // Initialize the database
        customerOfRepository.saveAndFlush(customerOf);

        // Get the customerOf
        restCustomerOfMockMvc.perform(get("/api/customer-ofs/{id}", customerOf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerOf.getId().intValue()))
            .andExpect(jsonPath("$.tcId").value(DEFAULT_TC_ID.intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerOf() throws Exception {
        // Get the customerOf
        restCustomerOfMockMvc.perform(get("/api/customer-ofs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOf() throws Exception {
        // Initialize the database
        customerOfRepository.saveAndFlush(customerOf);

        int databaseSizeBeforeUpdate = customerOfRepository.findAll().size();

        // Update the customerOf
        CustomerOf updatedCustomerOf = customerOfRepository.findById(customerOf.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerOf are not directly saved in db
        em.detach(updatedCustomerOf);
        updatedCustomerOf
            .tcId(UPDATED_TC_ID)
            .phone(UPDATED_PHONE)
            .enabled(UPDATED_ENABLED)
            .sex(UPDATED_SEX);
        CustomerOfDTO customerOfDTO = customerOfMapper.toDto(updatedCustomerOf);

        restCustomerOfMockMvc.perform(put("/api/customer-ofs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerOfDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerOf in the database
        List<CustomerOf> customerOfList = customerOfRepository.findAll();
        assertThat(customerOfList).hasSize(databaseSizeBeforeUpdate);
        CustomerOf testCustomerOf = customerOfList.get(customerOfList.size() - 1);
        assertThat(testCustomerOf.getTcId()).isEqualTo(UPDATED_TC_ID);
        assertThat(testCustomerOf.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomerOf.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testCustomerOf.getSex()).isEqualTo(UPDATED_SEX);

        // Validate the CustomerOf in Elasticsearch
        verify(mockCustomerOfSearchRepository, times(1)).save(testCustomerOf);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerOf() throws Exception {
        int databaseSizeBeforeUpdate = customerOfRepository.findAll().size();

        // Create the CustomerOf
        CustomerOfDTO customerOfDTO = customerOfMapper.toDto(customerOf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerOfMockMvc.perform(put("/api/customer-ofs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerOfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerOf in the database
        List<CustomerOf> customerOfList = customerOfRepository.findAll();
        assertThat(customerOfList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CustomerOf in Elasticsearch
        verify(mockCustomerOfSearchRepository, times(0)).save(customerOf);
    }

    @Test
    @Transactional
    public void deleteCustomerOf() throws Exception {
        // Initialize the database
        customerOfRepository.saveAndFlush(customerOf);

        int databaseSizeBeforeDelete = customerOfRepository.findAll().size();

        // Delete the customerOf
        restCustomerOfMockMvc.perform(delete("/api/customer-ofs/{id}", customerOf.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerOf> customerOfList = customerOfRepository.findAll();
        assertThat(customerOfList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CustomerOf in Elasticsearch
        verify(mockCustomerOfSearchRepository, times(1)).deleteById(customerOf.getId());
    }

    @Test
    @Transactional
    public void searchCustomerOf() throws Exception {
        // Initialize the database
        customerOfRepository.saveAndFlush(customerOf);
        when(mockCustomerOfSearchRepository.search(queryStringQuery("id:" + customerOf.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(customerOf), PageRequest.of(0, 1), 1));
        // Search the customerOf
        restCustomerOfMockMvc.perform(get("/api/_search/customer-ofs?query=id:" + customerOf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOf.getId().intValue())))
            .andExpect(jsonPath("$.[*].tcId").value(hasItem(DEFAULT_TC_ID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())));
    }
}
