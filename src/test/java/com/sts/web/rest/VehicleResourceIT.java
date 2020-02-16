package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.Vehicle;
import com.sts.domain.Company;
import com.sts.domain.User;
import com.sts.repository.VehicleRepository;
import com.sts.repository.search.VehicleSearchRepository;
import com.sts.service.VehicleService;
import com.sts.service.dto.VehicleDTO;
import com.sts.service.mapper.VehicleMapper;
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
 * Integration tests for the {@link VehicleResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class VehicleResourceIT {

    private static final String DEFAULT_LICENCE_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_PLATE = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final Integer DEFAULT_MODEL = 2000;
    private static final Integer UPDATED_MODEL = 2001;

    private static final Integer DEFAULT_QUOTA = 1;
    private static final Integer UPDATED_QUOTA = 2;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private VehicleService vehicleService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.VehicleSearchRepositoryMockConfiguration
     */
    @Autowired
    private VehicleSearchRepository mockVehicleSearchRepository;

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

    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleResource vehicleResource = new VehicleResource(vehicleService);
        this.restVehicleMockMvc = MockMvcBuilders.standaloneSetup(vehicleResource)
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
    public static Vehicle createEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .licencePlate(DEFAULT_LICENCE_PLATE)
            .brand(DEFAULT_BRAND)
            .model(DEFAULT_MODEL)
            .quota(DEFAULT_QUOTA)
            .enabled(DEFAULT_ENABLED);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        vehicle.setCompany(company);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        vehicle.setCredential(user);
        return vehicle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createUpdatedEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .licencePlate(UPDATED_LICENCE_PLATE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .quota(UPDATED_QUOTA)
            .enabled(UPDATED_ENABLED);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        vehicle.setCompany(company);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        vehicle.setCredential(user);
        return vehicle;
    }

    @BeforeEach
    public void initTest() {
        vehicle = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getLicencePlate()).isEqualTo(DEFAULT_LICENCE_PLATE);
        assertThat(testVehicle.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testVehicle.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehicle.getQuota()).isEqualTo(DEFAULT_QUOTA);
        assertThat(testVehicle.isEnabled()).isEqualTo(DEFAULT_ENABLED);

        // Validate the Vehicle in Elasticsearch
        verify(mockVehicleSearchRepository, times(1)).save(testVehicle);
    }

    @Test
    @Transactional
    public void createVehicleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle with an existing ID
        vehicle.setId(1L);
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);

        // Validate the Vehicle in Elasticsearch
        verify(mockVehicleSearchRepository, times(0)).save(vehicle);
    }


    @Test
    @Transactional
    public void checkLicencePlateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setLicencePlate(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setBrand(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setModel(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setQuota(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setEnabled(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].licencePlate").value(hasItem(DEFAULT_LICENCE_PLATE)))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].quota").value(hasItem(DEFAULT_QUOTA)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
            .andExpect(jsonPath("$.licencePlate").value(DEFAULT_LICENCE_PLATE))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.quota").value(DEFAULT_QUOTA))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).get();
        // Disconnect from session so that the updates on updatedVehicle are not directly saved in db
        em.detach(updatedVehicle);
        updatedVehicle
            .licencePlate(UPDATED_LICENCE_PLATE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .quota(UPDATED_QUOTA)
            .enabled(UPDATED_ENABLED);
        VehicleDTO vehicleDTO = vehicleMapper.toDto(updatedVehicle);

        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getLicencePlate()).isEqualTo(UPDATED_LICENCE_PLATE);
        assertThat(testVehicle.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testVehicle.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehicle.getQuota()).isEqualTo(UPDATED_QUOTA);
        assertThat(testVehicle.isEnabled()).isEqualTo(UPDATED_ENABLED);

        // Validate the Vehicle in Elasticsearch
        verify(mockVehicleSearchRepository, times(1)).save(testVehicle);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Vehicle in Elasticsearch
        verify(mockVehicleSearchRepository, times(0)).save(vehicle);
    }

    @Test
    @Transactional
    public void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Delete the vehicle
        restVehicleMockMvc.perform(delete("/api/vehicles/{id}", vehicle.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Vehicle in Elasticsearch
        verify(mockVehicleSearchRepository, times(1)).deleteById(vehicle.getId());
    }

    @Test
    @Transactional
    public void searchVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        when(mockVehicleSearchRepository.search(queryStringQuery("id:" + vehicle.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(vehicle), PageRequest.of(0, 1), 1));
        // Search the vehicle
        restVehicleMockMvc.perform(get("/api/_search/vehicles?query=id:" + vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].licencePlate").value(hasItem(DEFAULT_LICENCE_PLATE)))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].quota").value(hasItem(DEFAULT_QUOTA)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
}
