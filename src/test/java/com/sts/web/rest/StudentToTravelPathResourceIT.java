package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.StudentToTravelPath;
import com.sts.domain.Student;
import com.sts.domain.TravelPath;
import com.sts.repository.StudentToTravelPathRepository;
import com.sts.repository.search.StudentToTravelPathSearchRepository;
import com.sts.service.StudentToTravelPathService;
import com.sts.service.dto.StudentToTravelPathDTO;
import com.sts.service.mapper.StudentToTravelPathMapper;
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
 * Integration tests for the {@link StudentToTravelPathResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class StudentToTravelPathResourceIT {

    private static final Instant DEFAULT_BOARDING_TIME_OF_ARRIVAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOARDING_TIME_OF_ARRIVAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LANDING_TIME_OF_ARRIVAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LANDING_TIME_OF_ARRIVAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BOARDING_TIME_OF_RETURN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOARDING_TIME_OF_RETURN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LANDING_TIME_OF_RETURN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LANDING_TIME_OF_RETURN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private StudentToTravelPathRepository studentToTravelPathRepository;

    @Autowired
    private StudentToTravelPathMapper studentToTravelPathMapper;

    @Autowired
    private StudentToTravelPathService studentToTravelPathService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.StudentToTravelPathSearchRepositoryMockConfiguration
     */
    @Autowired
    private StudentToTravelPathSearchRepository mockStudentToTravelPathSearchRepository;

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

    private MockMvc restStudentToTravelPathMockMvc;

    private StudentToTravelPath studentToTravelPath;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentToTravelPathResource studentToTravelPathResource = new StudentToTravelPathResource(studentToTravelPathService);
        this.restStudentToTravelPathMockMvc = MockMvcBuilders.standaloneSetup(studentToTravelPathResource)
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
    public static StudentToTravelPath createEntity(EntityManager em) {
        StudentToTravelPath studentToTravelPath = new StudentToTravelPath()
            .boardingTimeOfArrival(DEFAULT_BOARDING_TIME_OF_ARRIVAL)
            .landingTimeOfArrival(DEFAULT_LANDING_TIME_OF_ARRIVAL)
            .boardingTimeOfReturn(DEFAULT_BOARDING_TIME_OF_RETURN)
            .landingTimeOfReturn(DEFAULT_LANDING_TIME_OF_RETURN)
            .enabled(DEFAULT_ENABLED);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        studentToTravelPath.setStudent(student);
        // Add required entity
        TravelPath travelPath;
        if (TestUtil.findAll(em, TravelPath.class).isEmpty()) {
            travelPath = TravelPathResourceIT.createEntity(em);
            em.persist(travelPath);
            em.flush();
        } else {
            travelPath = TestUtil.findAll(em, TravelPath.class).get(0);
        }
        studentToTravelPath.setTravelPath(travelPath);
        return studentToTravelPath;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentToTravelPath createUpdatedEntity(EntityManager em) {
        StudentToTravelPath studentToTravelPath = new StudentToTravelPath()
            .boardingTimeOfArrival(UPDATED_BOARDING_TIME_OF_ARRIVAL)
            .landingTimeOfArrival(UPDATED_LANDING_TIME_OF_ARRIVAL)
            .boardingTimeOfReturn(UPDATED_BOARDING_TIME_OF_RETURN)
            .landingTimeOfReturn(UPDATED_LANDING_TIME_OF_RETURN)
            .enabled(UPDATED_ENABLED);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createUpdatedEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        studentToTravelPath.setStudent(student);
        // Add required entity
        TravelPath travelPath;
        if (TestUtil.findAll(em, TravelPath.class).isEmpty()) {
            travelPath = TravelPathResourceIT.createUpdatedEntity(em);
            em.persist(travelPath);
            em.flush();
        } else {
            travelPath = TestUtil.findAll(em, TravelPath.class).get(0);
        }
        studentToTravelPath.setTravelPath(travelPath);
        return studentToTravelPath;
    }

    @BeforeEach
    public void initTest() {
        studentToTravelPath = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentToTravelPath() throws Exception {
        int databaseSizeBeforeCreate = studentToTravelPathRepository.findAll().size();

        // Create the StudentToTravelPath
        StudentToTravelPathDTO studentToTravelPathDTO = studentToTravelPathMapper.toDto(studentToTravelPath);
        restStudentToTravelPathMockMvc.perform(post("/api/student-to-travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentToTravelPathDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentToTravelPath in the database
        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeCreate + 1);
        StudentToTravelPath testStudentToTravelPath = studentToTravelPathList.get(studentToTravelPathList.size() - 1);
        assertThat(testStudentToTravelPath.getBoardingTimeOfArrival()).isEqualTo(DEFAULT_BOARDING_TIME_OF_ARRIVAL);
        assertThat(testStudentToTravelPath.getLandingTimeOfArrival()).isEqualTo(DEFAULT_LANDING_TIME_OF_ARRIVAL);
        assertThat(testStudentToTravelPath.getBoardingTimeOfReturn()).isEqualTo(DEFAULT_BOARDING_TIME_OF_RETURN);
        assertThat(testStudentToTravelPath.getLandingTimeOfReturn()).isEqualTo(DEFAULT_LANDING_TIME_OF_RETURN);
        assertThat(testStudentToTravelPath.isEnabled()).isEqualTo(DEFAULT_ENABLED);

        // Validate the StudentToTravelPath in Elasticsearch
        verify(mockStudentToTravelPathSearchRepository, times(1)).save(testStudentToTravelPath);
    }

    @Test
    @Transactional
    public void createStudentToTravelPathWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentToTravelPathRepository.findAll().size();

        // Create the StudentToTravelPath with an existing ID
        studentToTravelPath.setId(1L);
        StudentToTravelPathDTO studentToTravelPathDTO = studentToTravelPathMapper.toDto(studentToTravelPath);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentToTravelPathMockMvc.perform(post("/api/student-to-travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentToTravelPathDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentToTravelPath in the database
        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeCreate);

        // Validate the StudentToTravelPath in Elasticsearch
        verify(mockStudentToTravelPathSearchRepository, times(0)).save(studentToTravelPath);
    }


    @Test
    @Transactional
    public void checkBoardingTimeOfArrivalIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentToTravelPathRepository.findAll().size();
        // set the field null
        studentToTravelPath.setBoardingTimeOfArrival(null);

        // Create the StudentToTravelPath, which fails.
        StudentToTravelPathDTO studentToTravelPathDTO = studentToTravelPathMapper.toDto(studentToTravelPath);

        restStudentToTravelPathMockMvc.perform(post("/api/student-to-travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentToTravelPathDTO)))
            .andExpect(status().isBadRequest());

        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLandingTimeOfArrivalIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentToTravelPathRepository.findAll().size();
        // set the field null
        studentToTravelPath.setLandingTimeOfArrival(null);

        // Create the StudentToTravelPath, which fails.
        StudentToTravelPathDTO studentToTravelPathDTO = studentToTravelPathMapper.toDto(studentToTravelPath);

        restStudentToTravelPathMockMvc.perform(post("/api/student-to-travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentToTravelPathDTO)))
            .andExpect(status().isBadRequest());

        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoardingTimeOfReturnIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentToTravelPathRepository.findAll().size();
        // set the field null
        studentToTravelPath.setBoardingTimeOfReturn(null);

        // Create the StudentToTravelPath, which fails.
        StudentToTravelPathDTO studentToTravelPathDTO = studentToTravelPathMapper.toDto(studentToTravelPath);

        restStudentToTravelPathMockMvc.perform(post("/api/student-to-travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentToTravelPathDTO)))
            .andExpect(status().isBadRequest());

        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLandingTimeOfReturnIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentToTravelPathRepository.findAll().size();
        // set the field null
        studentToTravelPath.setLandingTimeOfReturn(null);

        // Create the StudentToTravelPath, which fails.
        StudentToTravelPathDTO studentToTravelPathDTO = studentToTravelPathMapper.toDto(studentToTravelPath);

        restStudentToTravelPathMockMvc.perform(post("/api/student-to-travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentToTravelPathDTO)))
            .andExpect(status().isBadRequest());

        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentToTravelPathRepository.findAll().size();
        // set the field null
        studentToTravelPath.setEnabled(null);

        // Create the StudentToTravelPath, which fails.
        StudentToTravelPathDTO studentToTravelPathDTO = studentToTravelPathMapper.toDto(studentToTravelPath);

        restStudentToTravelPathMockMvc.perform(post("/api/student-to-travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentToTravelPathDTO)))
            .andExpect(status().isBadRequest());

        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentToTravelPaths() throws Exception {
        // Initialize the database
        studentToTravelPathRepository.saveAndFlush(studentToTravelPath);

        // Get all the studentToTravelPathList
        restStudentToTravelPathMockMvc.perform(get("/api/student-to-travel-paths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentToTravelPath.getId().intValue())))
            .andExpect(jsonPath("$.[*].boardingTimeOfArrival").value(hasItem(DEFAULT_BOARDING_TIME_OF_ARRIVAL.toString())))
            .andExpect(jsonPath("$.[*].landingTimeOfArrival").value(hasItem(DEFAULT_LANDING_TIME_OF_ARRIVAL.toString())))
            .andExpect(jsonPath("$.[*].boardingTimeOfReturn").value(hasItem(DEFAULT_BOARDING_TIME_OF_RETURN.toString())))
            .andExpect(jsonPath("$.[*].landingTimeOfReturn").value(hasItem(DEFAULT_LANDING_TIME_OF_RETURN.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStudentToTravelPath() throws Exception {
        // Initialize the database
        studentToTravelPathRepository.saveAndFlush(studentToTravelPath);

        // Get the studentToTravelPath
        restStudentToTravelPathMockMvc.perform(get("/api/student-to-travel-paths/{id}", studentToTravelPath.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentToTravelPath.getId().intValue()))
            .andExpect(jsonPath("$.boardingTimeOfArrival").value(DEFAULT_BOARDING_TIME_OF_ARRIVAL.toString()))
            .andExpect(jsonPath("$.landingTimeOfArrival").value(DEFAULT_LANDING_TIME_OF_ARRIVAL.toString()))
            .andExpect(jsonPath("$.boardingTimeOfReturn").value(DEFAULT_BOARDING_TIME_OF_RETURN.toString()))
            .andExpect(jsonPath("$.landingTimeOfReturn").value(DEFAULT_LANDING_TIME_OF_RETURN.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentToTravelPath() throws Exception {
        // Get the studentToTravelPath
        restStudentToTravelPathMockMvc.perform(get("/api/student-to-travel-paths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentToTravelPath() throws Exception {
        // Initialize the database
        studentToTravelPathRepository.saveAndFlush(studentToTravelPath);

        int databaseSizeBeforeUpdate = studentToTravelPathRepository.findAll().size();

        // Update the studentToTravelPath
        StudentToTravelPath updatedStudentToTravelPath = studentToTravelPathRepository.findById(studentToTravelPath.getId()).get();
        // Disconnect from session so that the updates on updatedStudentToTravelPath are not directly saved in db
        em.detach(updatedStudentToTravelPath);
        updatedStudentToTravelPath
            .boardingTimeOfArrival(UPDATED_BOARDING_TIME_OF_ARRIVAL)
            .landingTimeOfArrival(UPDATED_LANDING_TIME_OF_ARRIVAL)
            .boardingTimeOfReturn(UPDATED_BOARDING_TIME_OF_RETURN)
            .landingTimeOfReturn(UPDATED_LANDING_TIME_OF_RETURN)
            .enabled(UPDATED_ENABLED);
        StudentToTravelPathDTO studentToTravelPathDTO = studentToTravelPathMapper.toDto(updatedStudentToTravelPath);

        restStudentToTravelPathMockMvc.perform(put("/api/student-to-travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentToTravelPathDTO)))
            .andExpect(status().isOk());

        // Validate the StudentToTravelPath in the database
        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeUpdate);
        StudentToTravelPath testStudentToTravelPath = studentToTravelPathList.get(studentToTravelPathList.size() - 1);
        assertThat(testStudentToTravelPath.getBoardingTimeOfArrival()).isEqualTo(UPDATED_BOARDING_TIME_OF_ARRIVAL);
        assertThat(testStudentToTravelPath.getLandingTimeOfArrival()).isEqualTo(UPDATED_LANDING_TIME_OF_ARRIVAL);
        assertThat(testStudentToTravelPath.getBoardingTimeOfReturn()).isEqualTo(UPDATED_BOARDING_TIME_OF_RETURN);
        assertThat(testStudentToTravelPath.getLandingTimeOfReturn()).isEqualTo(UPDATED_LANDING_TIME_OF_RETURN);
        assertThat(testStudentToTravelPath.isEnabled()).isEqualTo(UPDATED_ENABLED);

        // Validate the StudentToTravelPath in Elasticsearch
        verify(mockStudentToTravelPathSearchRepository, times(1)).save(testStudentToTravelPath);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentToTravelPath() throws Exception {
        int databaseSizeBeforeUpdate = studentToTravelPathRepository.findAll().size();

        // Create the StudentToTravelPath
        StudentToTravelPathDTO studentToTravelPathDTO = studentToTravelPathMapper.toDto(studentToTravelPath);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentToTravelPathMockMvc.perform(put("/api/student-to-travel-paths")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentToTravelPathDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentToTravelPath in the database
        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StudentToTravelPath in Elasticsearch
        verify(mockStudentToTravelPathSearchRepository, times(0)).save(studentToTravelPath);
    }

    @Test
    @Transactional
    public void deleteStudentToTravelPath() throws Exception {
        // Initialize the database
        studentToTravelPathRepository.saveAndFlush(studentToTravelPath);

        int databaseSizeBeforeDelete = studentToTravelPathRepository.findAll().size();

        // Delete the studentToTravelPath
        restStudentToTravelPathMockMvc.perform(delete("/api/student-to-travel-paths/{id}", studentToTravelPath.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentToTravelPath> studentToTravelPathList = studentToTravelPathRepository.findAll();
        assertThat(studentToTravelPathList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StudentToTravelPath in Elasticsearch
        verify(mockStudentToTravelPathSearchRepository, times(1)).deleteById(studentToTravelPath.getId());
    }

    @Test
    @Transactional
    public void searchStudentToTravelPath() throws Exception {
        // Initialize the database
        studentToTravelPathRepository.saveAndFlush(studentToTravelPath);
        when(mockStudentToTravelPathSearchRepository.search(queryStringQuery("id:" + studentToTravelPath.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(studentToTravelPath), PageRequest.of(0, 1), 1));
        // Search the studentToTravelPath
        restStudentToTravelPathMockMvc.perform(get("/api/_search/student-to-travel-paths?query=id:" + studentToTravelPath.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentToTravelPath.getId().intValue())))
            .andExpect(jsonPath("$.[*].boardingTimeOfArrival").value(hasItem(DEFAULT_BOARDING_TIME_OF_ARRIVAL.toString())))
            .andExpect(jsonPath("$.[*].landingTimeOfArrival").value(hasItem(DEFAULT_LANDING_TIME_OF_ARRIVAL.toString())))
            .andExpect(jsonPath("$.[*].boardingTimeOfReturn").value(hasItem(DEFAULT_BOARDING_TIME_OF_RETURN.toString())))
            .andExpect(jsonPath("$.[*].landingTimeOfReturn").value(hasItem(DEFAULT_LANDING_TIME_OF_RETURN.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
}
