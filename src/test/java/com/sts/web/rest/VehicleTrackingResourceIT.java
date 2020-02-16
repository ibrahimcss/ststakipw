package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.VehicleTracking;
import com.sts.domain.Vehicle;
import com.sts.domain.Company;
import com.sts.repository.VehicleTrackingRepository;
import com.sts.repository.search.VehicleTrackingSearchRepository;
import com.sts.service.VehicleTrackingService;
import com.sts.service.dto.VehicleTrackingDTO;
import com.sts.service.mapper.VehicleTrackingMapper;
import com.sts.web.rest.errors.ExceptionTranslator;
import com.sts.service.dto.VehicleTrackingCriteria;
import com.sts.service.VehicleTrackingQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link VehicleTrackingResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class VehicleTrackingResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_LON = -180D;
    private static final Double UPDATED_LON = -179D;
    private static final Double SMALLER_LON = -180D - 1D;

    private static final Double DEFAULT_LAT = -90D;
    private static final Double UPDATED_LAT = -89D;
    private static final Double SMALLER_LAT = -90D - 1D;

    @Autowired
    private VehicleTrackingRepository vehicleTrackingRepository;

    @Autowired
    private VehicleTrackingMapper vehicleTrackingMapper;

    @Autowired
    private VehicleTrackingService vehicleTrackingService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.VehicleTrackingSearchRepositoryMockConfiguration
     */
    @Autowired
    private VehicleTrackingSearchRepository mockVehicleTrackingSearchRepository;

    @Autowired
    private VehicleTrackingQueryService vehicleTrackingQueryService;

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

    private MockMvc restVehicleTrackingMockMvc;

    private VehicleTracking vehicleTracking;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleTrackingResource vehicleTrackingResource = new VehicleTrackingResource(vehicleTrackingService, vehicleTrackingQueryService);
        this.restVehicleTrackingMockMvc = MockMvcBuilders.standaloneSetup(vehicleTrackingResource)
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
    public static VehicleTracking createEntity(EntityManager em) {
        VehicleTracking vehicleTracking = new VehicleTracking()
            .createdAt(DEFAULT_CREATED_AT)
            .lon(DEFAULT_LON)
            .lat(DEFAULT_LAT);
        // Add required entity
        Vehicle vehicle;
        if (TestUtil.findAll(em, Vehicle.class).isEmpty()) {
            vehicle = VehicleResourceIT.createEntity(em);
            em.persist(vehicle);
            em.flush();
        } else {
            vehicle = TestUtil.findAll(em, Vehicle.class).get(0);
        }
        vehicleTracking.setVehicle(vehicle);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        vehicleTracking.setCompany(company);
        return vehicleTracking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleTracking createUpdatedEntity(EntityManager em) {
        VehicleTracking vehicleTracking = new VehicleTracking()
            .createdAt(UPDATED_CREATED_AT)
            .lon(UPDATED_LON)
            .lat(UPDATED_LAT);
        // Add required entity
        Vehicle vehicle;
        if (TestUtil.findAll(em, Vehicle.class).isEmpty()) {
            vehicle = VehicleResourceIT.createUpdatedEntity(em);
            em.persist(vehicle);
            em.flush();
        } else {
            vehicle = TestUtil.findAll(em, Vehicle.class).get(0);
        }
        vehicleTracking.setVehicle(vehicle);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        vehicleTracking.setCompany(company);
        return vehicleTracking;
    }

    @BeforeEach
    public void initTest() {
        vehicleTracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleTracking() throws Exception {
        int databaseSizeBeforeCreate = vehicleTrackingRepository.findAll().size();

        // Create the VehicleTracking
        VehicleTrackingDTO vehicleTrackingDTO = vehicleTrackingMapper.toDto(vehicleTracking);
        restVehicleTrackingMockMvc.perform(post("/api/vehicle-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTrackingDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleTracking in the database
        List<VehicleTracking> vehicleTrackingList = vehicleTrackingRepository.findAll();
        assertThat(vehicleTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleTracking testVehicleTracking = vehicleTrackingList.get(vehicleTrackingList.size() - 1);
        assertThat(testVehicleTracking.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testVehicleTracking.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testVehicleTracking.getLat()).isEqualTo(DEFAULT_LAT);

        // Validate the VehicleTracking in Elasticsearch
        verify(mockVehicleTrackingSearchRepository, times(1)).save(testVehicleTracking);
    }

    @Test
    @Transactional
    public void createVehicleTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleTrackingRepository.findAll().size();

        // Create the VehicleTracking with an existing ID
        vehicleTracking.setId(1L);
        VehicleTrackingDTO vehicleTrackingDTO = vehicleTrackingMapper.toDto(vehicleTracking);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleTrackingMockMvc.perform(post("/api/vehicle-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleTracking in the database
        List<VehicleTracking> vehicleTrackingList = vehicleTrackingRepository.findAll();
        assertThat(vehicleTrackingList).hasSize(databaseSizeBeforeCreate);

        // Validate the VehicleTracking in Elasticsearch
        verify(mockVehicleTrackingSearchRepository, times(0)).save(vehicleTracking);
    }


    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTrackingRepository.findAll().size();
        // set the field null
        vehicleTracking.setCreatedAt(null);

        // Create the VehicleTracking, which fails.
        VehicleTrackingDTO vehicleTrackingDTO = vehicleTrackingMapper.toDto(vehicleTracking);

        restVehicleTrackingMockMvc.perform(post("/api/vehicle-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTracking> vehicleTrackingList = vehicleTrackingRepository.findAll();
        assertThat(vehicleTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLonIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTrackingRepository.findAll().size();
        // set the field null
        vehicleTracking.setLon(null);

        // Create the VehicleTracking, which fails.
        VehicleTrackingDTO vehicleTrackingDTO = vehicleTrackingMapper.toDto(vehicleTracking);

        restVehicleTrackingMockMvc.perform(post("/api/vehicle-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTracking> vehicleTrackingList = vehicleTrackingRepository.findAll();
        assertThat(vehicleTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTrackingRepository.findAll().size();
        // set the field null
        vehicleTracking.setLat(null);

        // Create the VehicleTracking, which fails.
        VehicleTrackingDTO vehicleTrackingDTO = vehicleTrackingMapper.toDto(vehicleTracking);

        restVehicleTrackingMockMvc.perform(post("/api/vehicle-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTracking> vehicleTrackingList = vehicleTrackingRepository.findAll();
        assertThat(vehicleTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackings() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList
        restVehicleTrackingMockMvc.perform(get("/api/vehicle-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getVehicleTracking() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get the vehicleTracking
        restVehicleTrackingMockMvc.perform(get("/api/vehicle-trackings/{id}", vehicleTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleTracking.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()));
    }


    @Test
    @Transactional
    public void getVehicleTrackingsByIdFiltering() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        Long id = vehicleTracking.getId();

        defaultVehicleTrackingShouldBeFound("id.equals=" + id);
        defaultVehicleTrackingShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleTrackingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleTrackingShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleTrackingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleTrackingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVehicleTrackingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where createdAt equals to DEFAULT_CREATED_AT
        defaultVehicleTrackingShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the vehicleTrackingList where createdAt equals to UPDATED_CREATED_AT
        defaultVehicleTrackingShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where createdAt not equals to DEFAULT_CREATED_AT
        defaultVehicleTrackingShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the vehicleTrackingList where createdAt not equals to UPDATED_CREATED_AT
        defaultVehicleTrackingShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultVehicleTrackingShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the vehicleTrackingList where createdAt equals to UPDATED_CREATED_AT
        defaultVehicleTrackingShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where createdAt is not null
        defaultVehicleTrackingShouldBeFound("createdAt.specified=true");

        // Get all the vehicleTrackingList where createdAt is null
        defaultVehicleTrackingShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLonIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lon equals to DEFAULT_LON
        defaultVehicleTrackingShouldBeFound("lon.equals=" + DEFAULT_LON);

        // Get all the vehicleTrackingList where lon equals to UPDATED_LON
        defaultVehicleTrackingShouldNotBeFound("lon.equals=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lon not equals to DEFAULT_LON
        defaultVehicleTrackingShouldNotBeFound("lon.notEquals=" + DEFAULT_LON);

        // Get all the vehicleTrackingList where lon not equals to UPDATED_LON
        defaultVehicleTrackingShouldBeFound("lon.notEquals=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLonIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lon in DEFAULT_LON or UPDATED_LON
        defaultVehicleTrackingShouldBeFound("lon.in=" + DEFAULT_LON + "," + UPDATED_LON);

        // Get all the vehicleTrackingList where lon equals to UPDATED_LON
        defaultVehicleTrackingShouldNotBeFound("lon.in=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLonIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lon is not null
        defaultVehicleTrackingShouldBeFound("lon.specified=true");

        // Get all the vehicleTrackingList where lon is null
        defaultVehicleTrackingShouldNotBeFound("lon.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lon is greater than or equal to DEFAULT_LON
        defaultVehicleTrackingShouldBeFound("lon.greaterThanOrEqual=" + DEFAULT_LON);

        // Get all the vehicleTrackingList where lon is greater than or equal to (DEFAULT_LON + 1)
        defaultVehicleTrackingShouldNotBeFound("lon.greaterThanOrEqual=" + (DEFAULT_LON + 1));
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lon is less than or equal to DEFAULT_LON
        defaultVehicleTrackingShouldBeFound("lon.lessThanOrEqual=" + DEFAULT_LON);

        // Get all the vehicleTrackingList where lon is less than or equal to SMALLER_LON
        defaultVehicleTrackingShouldNotBeFound("lon.lessThanOrEqual=" + SMALLER_LON);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLonIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lon is less than DEFAULT_LON
        defaultVehicleTrackingShouldNotBeFound("lon.lessThan=" + DEFAULT_LON);

        // Get all the vehicleTrackingList where lon is less than (DEFAULT_LON + 1)
        defaultVehicleTrackingShouldBeFound("lon.lessThan=" + (DEFAULT_LON + 1));
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lon is greater than DEFAULT_LON
        defaultVehicleTrackingShouldNotBeFound("lon.greaterThan=" + DEFAULT_LON);

        // Get all the vehicleTrackingList where lon is greater than SMALLER_LON
        defaultVehicleTrackingShouldBeFound("lon.greaterThan=" + SMALLER_LON);
    }


    @Test
    @Transactional
    public void getAllVehicleTrackingsByLatIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lat equals to DEFAULT_LAT
        defaultVehicleTrackingShouldBeFound("lat.equals=" + DEFAULT_LAT);

        // Get all the vehicleTrackingList where lat equals to UPDATED_LAT
        defaultVehicleTrackingShouldNotBeFound("lat.equals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lat not equals to DEFAULT_LAT
        defaultVehicleTrackingShouldNotBeFound("lat.notEquals=" + DEFAULT_LAT);

        // Get all the vehicleTrackingList where lat not equals to UPDATED_LAT
        defaultVehicleTrackingShouldBeFound("lat.notEquals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLatIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lat in DEFAULT_LAT or UPDATED_LAT
        defaultVehicleTrackingShouldBeFound("lat.in=" + DEFAULT_LAT + "," + UPDATED_LAT);

        // Get all the vehicleTrackingList where lat equals to UPDATED_LAT
        defaultVehicleTrackingShouldNotBeFound("lat.in=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lat is not null
        defaultVehicleTrackingShouldBeFound("lat.specified=true");

        // Get all the vehicleTrackingList where lat is null
        defaultVehicleTrackingShouldNotBeFound("lat.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lat is greater than or equal to DEFAULT_LAT
        defaultVehicleTrackingShouldBeFound("lat.greaterThanOrEqual=" + DEFAULT_LAT);

        // Get all the vehicleTrackingList where lat is greater than or equal to (DEFAULT_LAT + 1)
        defaultVehicleTrackingShouldNotBeFound("lat.greaterThanOrEqual=" + (DEFAULT_LAT + 1));
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lat is less than or equal to DEFAULT_LAT
        defaultVehicleTrackingShouldBeFound("lat.lessThanOrEqual=" + DEFAULT_LAT);

        // Get all the vehicleTrackingList where lat is less than or equal to SMALLER_LAT
        defaultVehicleTrackingShouldNotBeFound("lat.lessThanOrEqual=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLatIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lat is less than DEFAULT_LAT
        defaultVehicleTrackingShouldNotBeFound("lat.lessThan=" + DEFAULT_LAT);

        // Get all the vehicleTrackingList where lat is less than (DEFAULT_LAT + 1)
        defaultVehicleTrackingShouldBeFound("lat.lessThan=" + (DEFAULT_LAT + 1));
    }

    @Test
    @Transactional
    public void getAllVehicleTrackingsByLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        // Get all the vehicleTrackingList where lat is greater than DEFAULT_LAT
        defaultVehicleTrackingShouldNotBeFound("lat.greaterThan=" + DEFAULT_LAT);

        // Get all the vehicleTrackingList where lat is greater than SMALLER_LAT
        defaultVehicleTrackingShouldBeFound("lat.greaterThan=" + SMALLER_LAT);
    }


    @Test
    @Transactional
    public void getAllVehicleTrackingsByVehicleIsEqualToSomething() throws Exception {
        // Get already existing entity
        Vehicle vehicle = vehicleTracking.getVehicle();
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);
        Long vehicleId = vehicle.getId();

        // Get all the vehicleTrackingList where vehicle equals to vehicleId
        defaultVehicleTrackingShouldBeFound("vehicleId.equals=" + vehicleId);

        // Get all the vehicleTrackingList where vehicle equals to vehicleId + 1
        defaultVehicleTrackingShouldNotBeFound("vehicleId.equals=" + (vehicleId + 1));
    }


    @Test
    @Transactional
    public void getAllVehicleTrackingsByCompanyIsEqualToSomething() throws Exception {
        // Get already existing entity
        Company company = vehicleTracking.getCompany();
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);
        Long companyId = company.getId();

        // Get all the vehicleTrackingList where company equals to companyId
        defaultVehicleTrackingShouldBeFound("companyId.equals=" + companyId);

        // Get all the vehicleTrackingList where company equals to companyId + 1
        defaultVehicleTrackingShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleTrackingShouldBeFound(String filter) throws Exception {
        restVehicleTrackingMockMvc.perform(get("/api/vehicle-trackings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())));

        // Check, that the count call also returns 1
        restVehicleTrackingMockMvc.perform(get("/api/vehicle-trackings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleTrackingShouldNotBeFound(String filter) throws Exception {
        restVehicleTrackingMockMvc.perform(get("/api/vehicle-trackings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleTrackingMockMvc.perform(get("/api/vehicle-trackings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVehicleTracking() throws Exception {
        // Get the vehicleTracking
        restVehicleTrackingMockMvc.perform(get("/api/vehicle-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleTracking() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        int databaseSizeBeforeUpdate = vehicleTrackingRepository.findAll().size();

        // Update the vehicleTracking
        VehicleTracking updatedVehicleTracking = vehicleTrackingRepository.findById(vehicleTracking.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleTracking are not directly saved in db
        em.detach(updatedVehicleTracking);
        updatedVehicleTracking
            .createdAt(UPDATED_CREATED_AT)
            .lon(UPDATED_LON)
            .lat(UPDATED_LAT);
        VehicleTrackingDTO vehicleTrackingDTO = vehicleTrackingMapper.toDto(updatedVehicleTracking);

        restVehicleTrackingMockMvc.perform(put("/api/vehicle-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTrackingDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleTracking in the database
        List<VehicleTracking> vehicleTrackingList = vehicleTrackingRepository.findAll();
        assertThat(vehicleTrackingList).hasSize(databaseSizeBeforeUpdate);
        VehicleTracking testVehicleTracking = vehicleTrackingList.get(vehicleTrackingList.size() - 1);
        assertThat(testVehicleTracking.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testVehicleTracking.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testVehicleTracking.getLat()).isEqualTo(UPDATED_LAT);

        // Validate the VehicleTracking in Elasticsearch
        verify(mockVehicleTrackingSearchRepository, times(1)).save(testVehicleTracking);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleTracking() throws Exception {
        int databaseSizeBeforeUpdate = vehicleTrackingRepository.findAll().size();

        // Create the VehicleTracking
        VehicleTrackingDTO vehicleTrackingDTO = vehicleTrackingMapper.toDto(vehicleTracking);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleTrackingMockMvc.perform(put("/api/vehicle-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleTracking in the database
        List<VehicleTracking> vehicleTrackingList = vehicleTrackingRepository.findAll();
        assertThat(vehicleTrackingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VehicleTracking in Elasticsearch
        verify(mockVehicleTrackingSearchRepository, times(0)).save(vehicleTracking);
    }

    @Test
    @Transactional
    public void deleteVehicleTracking() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);

        int databaseSizeBeforeDelete = vehicleTrackingRepository.findAll().size();

        // Delete the vehicleTracking
        restVehicleTrackingMockMvc.perform(delete("/api/vehicle-trackings/{id}", vehicleTracking.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleTracking> vehicleTrackingList = vehicleTrackingRepository.findAll();
        assertThat(vehicleTrackingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VehicleTracking in Elasticsearch
        verify(mockVehicleTrackingSearchRepository, times(1)).deleteById(vehicleTracking.getId());
    }

    @Test
    @Transactional
    public void searchVehicleTracking() throws Exception {
        // Initialize the database
        vehicleTrackingRepository.saveAndFlush(vehicleTracking);
        when(mockVehicleTrackingSearchRepository.search(queryStringQuery("id:" + vehicleTracking.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(vehicleTracking), PageRequest.of(0, 1), 1));
        // Search the vehicleTracking
        restVehicleTrackingMockMvc.perform(get("/api/_search/vehicle-trackings?query=id:" + vehicleTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())));
    }
}
