package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.StudentTracking;
import com.sts.domain.StudentToTravelPath;
import com.sts.domain.Vehicle;
import com.sts.domain.Student;
import com.sts.repository.StudentTrackingRepository;
import com.sts.repository.search.StudentTrackingSearchRepository;
import com.sts.service.StudentTrackingService;
import com.sts.service.dto.StudentTrackingDTO;
import com.sts.service.mapper.StudentTrackingMapper;
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
import org.springframework.util.Base64Utils;
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

import com.sts.domain.enumeration.TrackingStep;
/**
 * Integration tests for the {@link StudentTrackingResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class StudentTrackingResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_LON = -180D;
    private static final Double UPDATED_LON = -179D;

    private static final Double DEFAULT_LAT = -90D;
    private static final Double UPDATED_LAT = -89D;

    private static final TrackingStep DEFAULT_T_STEP = TrackingStep.ArrivalBoarding;
    private static final TrackingStep UPDATED_T_STEP = TrackingStep.ArrivalLanding;

    @Autowired
    private StudentTrackingRepository studentTrackingRepository;

    @Autowired
    private StudentTrackingMapper studentTrackingMapper;

    @Autowired
    private StudentTrackingService studentTrackingService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.StudentTrackingSearchRepositoryMockConfiguration
     */
    @Autowired
    private StudentTrackingSearchRepository mockStudentTrackingSearchRepository;

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

    private MockMvc restStudentTrackingMockMvc;

    private StudentTracking studentTracking;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentTrackingResource studentTrackingResource = new StudentTrackingResource(studentTrackingService);
        this.restStudentTrackingMockMvc = MockMvcBuilders.standaloneSetup(studentTrackingResource)
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
    public static StudentTracking createEntity(EntityManager em) {
        StudentTracking studentTracking = new StudentTracking()
            .createdAt(DEFAULT_CREATED_AT)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .lon(DEFAULT_LON)
            .lat(DEFAULT_LAT)
            .tStep(DEFAULT_T_STEP);
        // Add required entity
        StudentToTravelPath studentToTravelPath;
        if (TestUtil.findAll(em, StudentToTravelPath.class).isEmpty()) {
            studentToTravelPath = StudentToTravelPathResourceIT.createEntity(em);
            em.persist(studentToTravelPath);
            em.flush();
        } else {
            studentToTravelPath = TestUtil.findAll(em, StudentToTravelPath.class).get(0);
        }
        studentTracking.setStudentToTravelPath(studentToTravelPath);
        // Add required entity
        Vehicle vehicle;
        if (TestUtil.findAll(em, Vehicle.class).isEmpty()) {
            vehicle = VehicleResourceIT.createEntity(em);
            em.persist(vehicle);
            em.flush();
        } else {
            vehicle = TestUtil.findAll(em, Vehicle.class).get(0);
        }
        studentTracking.setVehicle(vehicle);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        studentTracking.setStudent(student);
        return studentTracking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentTracking createUpdatedEntity(EntityManager em) {
        StudentTracking studentTracking = new StudentTracking()
            .createdAt(UPDATED_CREATED_AT)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .lon(UPDATED_LON)
            .lat(UPDATED_LAT)
            .tStep(UPDATED_T_STEP);
        // Add required entity
        StudentToTravelPath studentToTravelPath;
        if (TestUtil.findAll(em, StudentToTravelPath.class).isEmpty()) {
            studentToTravelPath = StudentToTravelPathResourceIT.createUpdatedEntity(em);
            em.persist(studentToTravelPath);
            em.flush();
        } else {
            studentToTravelPath = TestUtil.findAll(em, StudentToTravelPath.class).get(0);
        }
        studentTracking.setStudentToTravelPath(studentToTravelPath);
        // Add required entity
        Vehicle vehicle;
        if (TestUtil.findAll(em, Vehicle.class).isEmpty()) {
            vehicle = VehicleResourceIT.createUpdatedEntity(em);
            em.persist(vehicle);
            em.flush();
        } else {
            vehicle = TestUtil.findAll(em, Vehicle.class).get(0);
        }
        studentTracking.setVehicle(vehicle);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createUpdatedEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        studentTracking.setStudent(student);
        return studentTracking;
    }

    @BeforeEach
    public void initTest() {
        studentTracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentTracking() throws Exception {
        int databaseSizeBeforeCreate = studentTrackingRepository.findAll().size();

        // Create the StudentTracking
        StudentTrackingDTO studentTrackingDTO = studentTrackingMapper.toDto(studentTracking);
        restStudentTrackingMockMvc.perform(post("/api/student-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentTrackingDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentTracking in the database
        List<StudentTracking> studentTrackingList = studentTrackingRepository.findAll();
        assertThat(studentTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        StudentTracking testStudentTracking = studentTrackingList.get(studentTrackingList.size() - 1);
        assertThat(testStudentTracking.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testStudentTracking.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testStudentTracking.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testStudentTracking.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testStudentTracking.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testStudentTracking.gettStep()).isEqualTo(DEFAULT_T_STEP);

        // Validate the StudentTracking in Elasticsearch
        verify(mockStudentTrackingSearchRepository, times(1)).save(testStudentTracking);
    }

    @Test
    @Transactional
    public void createStudentTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentTrackingRepository.findAll().size();

        // Create the StudentTracking with an existing ID
        studentTracking.setId(1L);
        StudentTrackingDTO studentTrackingDTO = studentTrackingMapper.toDto(studentTracking);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentTrackingMockMvc.perform(post("/api/student-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentTracking in the database
        List<StudentTracking> studentTrackingList = studentTrackingRepository.findAll();
        assertThat(studentTrackingList).hasSize(databaseSizeBeforeCreate);

        // Validate the StudentTracking in Elasticsearch
        verify(mockStudentTrackingSearchRepository, times(0)).save(studentTracking);
    }


    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentTrackingRepository.findAll().size();
        // set the field null
        studentTracking.setCreatedAt(null);

        // Create the StudentTracking, which fails.
        StudentTrackingDTO studentTrackingDTO = studentTrackingMapper.toDto(studentTracking);

        restStudentTrackingMockMvc.perform(post("/api/student-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<StudentTracking> studentTrackingList = studentTrackingRepository.findAll();
        assertThat(studentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLonIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentTrackingRepository.findAll().size();
        // set the field null
        studentTracking.setLon(null);

        // Create the StudentTracking, which fails.
        StudentTrackingDTO studentTrackingDTO = studentTrackingMapper.toDto(studentTracking);

        restStudentTrackingMockMvc.perform(post("/api/student-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<StudentTracking> studentTrackingList = studentTrackingRepository.findAll();
        assertThat(studentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentTrackingRepository.findAll().size();
        // set the field null
        studentTracking.setLat(null);

        // Create the StudentTracking, which fails.
        StudentTrackingDTO studentTrackingDTO = studentTrackingMapper.toDto(studentTracking);

        restStudentTrackingMockMvc.perform(post("/api/student-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<StudentTracking> studentTrackingList = studentTrackingRepository.findAll();
        assertThat(studentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checktStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentTrackingRepository.findAll().size();
        // set the field null
        studentTracking.settStep(null);

        // Create the StudentTracking, which fails.
        StudentTrackingDTO studentTrackingDTO = studentTrackingMapper.toDto(studentTracking);

        restStudentTrackingMockMvc.perform(post("/api/student-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<StudentTracking> studentTrackingList = studentTrackingRepository.findAll();
        assertThat(studentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentTrackings() throws Exception {
        // Initialize the database
        studentTrackingRepository.saveAndFlush(studentTracking);

        // Get all the studentTrackingList
        restStudentTrackingMockMvc.perform(get("/api/student-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].tStep").value(hasItem(DEFAULT_T_STEP.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentTracking() throws Exception {
        // Initialize the database
        studentTrackingRepository.saveAndFlush(studentTracking);

        // Get the studentTracking
        restStudentTrackingMockMvc.perform(get("/api/student-trackings/{id}", studentTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentTracking.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.tStep").value(DEFAULT_T_STEP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentTracking() throws Exception {
        // Get the studentTracking
        restStudentTrackingMockMvc.perform(get("/api/student-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentTracking() throws Exception {
        // Initialize the database
        studentTrackingRepository.saveAndFlush(studentTracking);

        int databaseSizeBeforeUpdate = studentTrackingRepository.findAll().size();

        // Update the studentTracking
        StudentTracking updatedStudentTracking = studentTrackingRepository.findById(studentTracking.getId()).get();
        // Disconnect from session so that the updates on updatedStudentTracking are not directly saved in db
        em.detach(updatedStudentTracking);
        updatedStudentTracking
            .createdAt(UPDATED_CREATED_AT)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .lon(UPDATED_LON)
            .lat(UPDATED_LAT)
            .tStep(UPDATED_T_STEP);
        StudentTrackingDTO studentTrackingDTO = studentTrackingMapper.toDto(updatedStudentTracking);

        restStudentTrackingMockMvc.perform(put("/api/student-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentTrackingDTO)))
            .andExpect(status().isOk());

        // Validate the StudentTracking in the database
        List<StudentTracking> studentTrackingList = studentTrackingRepository.findAll();
        assertThat(studentTrackingList).hasSize(databaseSizeBeforeUpdate);
        StudentTracking testStudentTracking = studentTrackingList.get(studentTrackingList.size() - 1);
        assertThat(testStudentTracking.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStudentTracking.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testStudentTracking.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testStudentTracking.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testStudentTracking.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testStudentTracking.gettStep()).isEqualTo(UPDATED_T_STEP);

        // Validate the StudentTracking in Elasticsearch
        verify(mockStudentTrackingSearchRepository, times(1)).save(testStudentTracking);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentTracking() throws Exception {
        int databaseSizeBeforeUpdate = studentTrackingRepository.findAll().size();

        // Create the StudentTracking
        StudentTrackingDTO studentTrackingDTO = studentTrackingMapper.toDto(studentTracking);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentTrackingMockMvc.perform(put("/api/student-trackings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentTracking in the database
        List<StudentTracking> studentTrackingList = studentTrackingRepository.findAll();
        assertThat(studentTrackingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StudentTracking in Elasticsearch
        verify(mockStudentTrackingSearchRepository, times(0)).save(studentTracking);
    }

    @Test
    @Transactional
    public void deleteStudentTracking() throws Exception {
        // Initialize the database
        studentTrackingRepository.saveAndFlush(studentTracking);

        int databaseSizeBeforeDelete = studentTrackingRepository.findAll().size();

        // Delete the studentTracking
        restStudentTrackingMockMvc.perform(delete("/api/student-trackings/{id}", studentTracking.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentTracking> studentTrackingList = studentTrackingRepository.findAll();
        assertThat(studentTrackingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StudentTracking in Elasticsearch
        verify(mockStudentTrackingSearchRepository, times(1)).deleteById(studentTracking.getId());
    }

    @Test
    @Transactional
    public void searchStudentTracking() throws Exception {
        // Initialize the database
        studentTrackingRepository.saveAndFlush(studentTracking);
        when(mockStudentTrackingSearchRepository.search(queryStringQuery("id:" + studentTracking.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(studentTracking), PageRequest.of(0, 1), 1));
        // Search the studentTracking
        restStudentTrackingMockMvc.perform(get("/api/_search/student-trackings?query=id:" + studentTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].tStep").value(hasItem(DEFAULT_T_STEP.toString())));
    }
}
