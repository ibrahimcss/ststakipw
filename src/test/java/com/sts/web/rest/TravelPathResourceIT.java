package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.TravelPath;
import com.sts.domain.Company;
import com.sts.domain.User;
import com.sts.repository.TravelPathRepository;
import com.sts.repository.search.TravelPathSearchRepository;
import com.sts.service.TravelPathService;
import com.sts.service.dto.TravelPathDTO;
import com.sts.service.mapper.TravelPathMapper;
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
 * Integration tests for the {@link TravelPathResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class TravelPathResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEPARTURE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPARTURE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DEPART_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DEPART_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_DEPART_LON = -180D;
    private static final Double UPDATED_DEPART_LON = -179D;

    private static final Double DEFAULT_DEPART_LAT = -90D;
    private static final Double UPDATED_DEPART_LAT = -89D;

    private static final Instant DEFAULT_ARRIVAL_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARRIVAL_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ARRIVAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ARRIVAL_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_ARRIVAL_LON = -180D;
    private static final Double UPDATED_ARRIVAL_LON = -179D;

    private static final Double DEFAULT_ARRIVAL_LAT = -90D;
    private static final Double UPDATED_ARRIVAL_LAT = -89D;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private TravelPathRepository travelPathRepository;

    @Autowired
    private TravelPathMapper travelPathMapper;

    @Autowired
    private TravelPathService travelPathService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.TravelPathSearchRepositoryMockConfiguration
     */
    @Autowired
    private TravelPathSearchRepository mockTravelPathSearchRepository;

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

    private MockMvc restTravelPathMockMvc;

    private TravelPath travelPath;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TravelPathResource travelPathResource = new TravelPathResource(travelPathService);
        this.restTravelPathMockMvc = MockMvcBuilders.standaloneSetup(travelPathResource)
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
    public static TravelPath createEntity(EntityManager em) {
        TravelPath travelPath = new TravelPath()
            .name(DEFAULT_NAME)
            .departureTime(DEFAULT_DEPARTURE_TIME)
            .departAddress(DEFAULT_DEPART_ADDRESS)
            .departLon(DEFAULT_DEPART_LON)
            .departLat(DEFAULT_DEPART_LAT)
            .arrivalTime(DEFAULT_ARRIVAL_TIME)
            .arrivalAddress(DEFAULT_ARRIVAL_ADDRESS)
            .arrivalLon(DEFAULT_ARRIVAL_LON)
            .arrivalLat(DEFAULT_ARRIVAL_LAT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
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
        travelPath.setCompany(company);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        travelPath.setEmployee(user);
        return travelPath;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TravelPath createUpdatedEntity(EntityManager em) {
        TravelPath travelPath = new TravelPath()
            .name(UPDATED_NAME)
            .departureTime(UPDATED_DEPARTURE_TIME)
            .departAddress(UPDATED_DEPART_ADDRESS)
            .departLon(UPDATED_DEPART_LON)
            .departLat(UPDATED_DEPART_LAT)
            .arrivalTime(UPDATED_ARRIVAL_TIME)
            .arrivalAddress(UPDATED_ARRIVAL_ADDRESS)
            .arrivalLon(UPDATED_ARRIVAL_LON)
            .arrivalLat(UPDATED_ARRIVAL_LAT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
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
        travelPath.setCompany(company);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        travelPath.setEmployee(user);
        return travelPath;
    }

    @BeforeEach
    public void initTest() {
        travelPath = createEntity(em);
    }

    @Test
    @Transactional
    public void createTravelPath() throws Exception {
        int databaseSizeBeforeCreate = travelPathRepository.findAll().size();

        // Create the TravelPath
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);
        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isCreated());

        // Validate the TravelPath in the database
        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeCreate + 1);
        TravelPath testTravelPath = travelPathList.get(travelPathList.size() - 1);
        assertThat(testTravelPath.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTravelPath.getDepartureTime()).isEqualTo(DEFAULT_DEPARTURE_TIME);
        assertThat(testTravelPath.getDepartAddress()).isEqualTo(DEFAULT_DEPART_ADDRESS);
        assertThat(testTravelPath.getDepartLon()).isEqualTo(DEFAULT_DEPART_LON);
        assertThat(testTravelPath.getDepartLat()).isEqualTo(DEFAULT_DEPART_LAT);
        assertThat(testTravelPath.getArrivalTime()).isEqualTo(DEFAULT_ARRIVAL_TIME);
        assertThat(testTravelPath.getArrivalAddress()).isEqualTo(DEFAULT_ARRIVAL_ADDRESS);
        assertThat(testTravelPath.getArrivalLon()).isEqualTo(DEFAULT_ARRIVAL_LON);
        assertThat(testTravelPath.getArrivalLat()).isEqualTo(DEFAULT_ARRIVAL_LAT);
        assertThat(testTravelPath.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTravelPath.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTravelPath.isEnabled()).isEqualTo(DEFAULT_ENABLED);

        // Validate the TravelPath in Elasticsearch
        verify(mockTravelPathSearchRepository, times(1)).save(testTravelPath);
    }

    @Test
    @Transactional
    public void createTravelPathWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = travelPathRepository.findAll().size();

        // Create the TravelPath with an existing ID
        travelPath.setId(1L);
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TravelPath in the database
        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeCreate);

        // Validate the TravelPath in Elasticsearch
        verify(mockTravelPathSearchRepository, times(0)).save(travelPath);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setName(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartureTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setDepartureTime(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setDepartAddress(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartLonIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setDepartLon(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setDepartLat(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArrivalTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setArrivalTime(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArrivalAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setArrivalAddress(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArrivalLonIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setArrivalLon(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArrivalLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setArrivalLat(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setStartDate(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setEndDate(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelPathRepository.findAll().size();
        // set the field null
        travelPath.setEnabled(null);

        // Create the TravelPath, which fails.
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        restTravelPathMockMvc.perform(post("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTravelPaths() throws Exception {
        // Initialize the database
        travelPathRepository.saveAndFlush(travelPath);

        // Get all the travelPathList
        restTravelPathMockMvc.perform(get("/api/travel-paths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travelPath.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].departureTime").value(hasItem(DEFAULT_DEPARTURE_TIME.toString())))
            .andExpect(jsonPath("$.[*].departAddress").value(hasItem(DEFAULT_DEPART_ADDRESS)))
            .andExpect(jsonPath("$.[*].departLon").value(hasItem(DEFAULT_DEPART_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].departLat").value(hasItem(DEFAULT_DEPART_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].arrivalTime").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].arrivalAddress").value(hasItem(DEFAULT_ARRIVAL_ADDRESS)))
            .andExpect(jsonPath("$.[*].arrivalLon").value(hasItem(DEFAULT_ARRIVAL_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].arrivalLat").value(hasItem(DEFAULT_ARRIVAL_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTravelPath() throws Exception {
        // Initialize the database
        travelPathRepository.saveAndFlush(travelPath);

        // Get the travelPath
        restTravelPathMockMvc.perform(get("/api/travel-paths/{id}", travelPath.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(travelPath.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.departureTime").value(DEFAULT_DEPARTURE_TIME.toString()))
            .andExpect(jsonPath("$.departAddress").value(DEFAULT_DEPART_ADDRESS))
            .andExpect(jsonPath("$.departLon").value(DEFAULT_DEPART_LON.doubleValue()))
            .andExpect(jsonPath("$.departLat").value(DEFAULT_DEPART_LAT.doubleValue()))
            .andExpect(jsonPath("$.arrivalTime").value(DEFAULT_ARRIVAL_TIME.toString()))
            .andExpect(jsonPath("$.arrivalAddress").value(DEFAULT_ARRIVAL_ADDRESS))
            .andExpect(jsonPath("$.arrivalLon").value(DEFAULT_ARRIVAL_LON.doubleValue()))
            .andExpect(jsonPath("$.arrivalLat").value(DEFAULT_ARRIVAL_LAT.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTravelPath() throws Exception {
        // Get the travelPath
        restTravelPathMockMvc.perform(get("/api/travel-paths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravelPath() throws Exception {
        // Initialize the database
        travelPathRepository.saveAndFlush(travelPath);

        int databaseSizeBeforeUpdate = travelPathRepository.findAll().size();

        // Update the travelPath
        TravelPath updatedTravelPath = travelPathRepository.findById(travelPath.getId()).get();
        // Disconnect from session so that the updates on updatedTravelPath are not directly saved in db
        em.detach(updatedTravelPath);
        updatedTravelPath
            .name(UPDATED_NAME)
            .departureTime(UPDATED_DEPARTURE_TIME)
            .departAddress(UPDATED_DEPART_ADDRESS)
            .departLon(UPDATED_DEPART_LON)
            .departLat(UPDATED_DEPART_LAT)
            .arrivalTime(UPDATED_ARRIVAL_TIME)
            .arrivalAddress(UPDATED_ARRIVAL_ADDRESS)
            .arrivalLon(UPDATED_ARRIVAL_LON)
            .arrivalLat(UPDATED_ARRIVAL_LAT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .enabled(UPDATED_ENABLED);
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(updatedTravelPath);

        restTravelPathMockMvc.perform(put("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isOk());

        // Validate the TravelPath in the database
        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeUpdate);
        TravelPath testTravelPath = travelPathList.get(travelPathList.size() - 1);
        assertThat(testTravelPath.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTravelPath.getDepartureTime()).isEqualTo(UPDATED_DEPARTURE_TIME);
        assertThat(testTravelPath.getDepartAddress()).isEqualTo(UPDATED_DEPART_ADDRESS);
        assertThat(testTravelPath.getDepartLon()).isEqualTo(UPDATED_DEPART_LON);
        assertThat(testTravelPath.getDepartLat()).isEqualTo(UPDATED_DEPART_LAT);
        assertThat(testTravelPath.getArrivalTime()).isEqualTo(UPDATED_ARRIVAL_TIME);
        assertThat(testTravelPath.getArrivalAddress()).isEqualTo(UPDATED_ARRIVAL_ADDRESS);
        assertThat(testTravelPath.getArrivalLon()).isEqualTo(UPDATED_ARRIVAL_LON);
        assertThat(testTravelPath.getArrivalLat()).isEqualTo(UPDATED_ARRIVAL_LAT);
        assertThat(testTravelPath.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTravelPath.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTravelPath.isEnabled()).isEqualTo(UPDATED_ENABLED);

        // Validate the TravelPath in Elasticsearch
        verify(mockTravelPathSearchRepository, times(1)).save(testTravelPath);
    }

    @Test
    @Transactional
    public void updateNonExistingTravelPath() throws Exception {
        int databaseSizeBeforeUpdate = travelPathRepository.findAll().size();

        // Create the TravelPath
        TravelPathDTO travelPathDTO = travelPathMapper.toDto(travelPath);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravelPathMockMvc.perform(put("/api/travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(travelPathDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TravelPath in the database
        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TravelPath in Elasticsearch
        verify(mockTravelPathSearchRepository, times(0)).save(travelPath);
    }

    @Test
    @Transactional
    public void deleteTravelPath() throws Exception {
        // Initialize the database
        travelPathRepository.saveAndFlush(travelPath);

        int databaseSizeBeforeDelete = travelPathRepository.findAll().size();

        // Delete the travelPath
        restTravelPathMockMvc.perform(delete("/api/travel-paths/{id}", travelPath.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TravelPath> travelPathList = travelPathRepository.findAll();
        assertThat(travelPathList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TravelPath in Elasticsearch
        verify(mockTravelPathSearchRepository, times(1)).deleteById(travelPath.getId());
    }

    @Test
    @Transactional
    public void searchTravelPath() throws Exception {
        // Initialize the database
        travelPathRepository.saveAndFlush(travelPath);
        when(mockTravelPathSearchRepository.search(queryStringQuery("id:" + travelPath.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(travelPath), PageRequest.of(0, 1), 1));
        // Search the travelPath
        restTravelPathMockMvc.perform(get("/api/_search/travel-paths?query=id:" + travelPath.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travelPath.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].departureTime").value(hasItem(DEFAULT_DEPARTURE_TIME.toString())))
            .andExpect(jsonPath("$.[*].departAddress").value(hasItem(DEFAULT_DEPART_ADDRESS)))
            .andExpect(jsonPath("$.[*].departLon").value(hasItem(DEFAULT_DEPART_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].departLat").value(hasItem(DEFAULT_DEPART_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].arrivalTime").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].arrivalAddress").value(hasItem(DEFAULT_ARRIVAL_ADDRESS)))
            .andExpect(jsonPath("$.[*].arrivalLon").value(hasItem(DEFAULT_ARRIVAL_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].arrivalLat").value(hasItem(DEFAULT_ARRIVAL_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
}
