package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.PaketDetay;
import com.sts.repository.PaketDetayRepository;
import com.sts.repository.search.PaketDetaySearchRepository;
import com.sts.service.PaketDetayService;
import com.sts.service.dto.PaketDetayDTO;
import com.sts.service.mapper.PaketDetayMapper;
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
import java.util.Collections;
import java.util.List;

import static com.sts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sts.domain.enumeration.PaketEnum;
/**
 * Integration tests for the {@link PaketDetayResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class PaketDetayResourceIT {

    private static final PaketEnum DEFAULT_NAME = PaketEnum.Basic;
    private static final PaketEnum UPDATED_NAME = PaketEnum.Silver;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final Integer DEFAULT_VEHICLE_QUOTA = 5;
    private static final Integer UPDATED_VEHICLE_QUOTA = 6;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    @Autowired
    private PaketDetayRepository paketDetayRepository;

    @Autowired
    private PaketDetayMapper paketDetayMapper;

    @Autowired
    private PaketDetayService paketDetayService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.PaketDetaySearchRepositoryMockConfiguration
     */
    @Autowired
    private PaketDetaySearchRepository mockPaketDetaySearchRepository;

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

    private MockMvc restPaketDetayMockMvc;

    private PaketDetay paketDetay;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaketDetayResource paketDetayResource = new PaketDetayResource(paketDetayService);
        this.restPaketDetayMockMvc = MockMvcBuilders.standaloneSetup(paketDetayResource)
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
    public static PaketDetay createEntity(EntityManager em) {
        PaketDetay paketDetay = new PaketDetay()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .vehicleQuota(DEFAULT_VEHICLE_QUOTA)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .enabled(DEFAULT_ENABLED)
            .year(DEFAULT_YEAR);
        return paketDetay;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaketDetay createUpdatedEntity(EntityManager em) {
        PaketDetay paketDetay = new PaketDetay()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .vehicleQuota(UPDATED_VEHICLE_QUOTA)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .enabled(UPDATED_ENABLED)
            .year(UPDATED_YEAR);
        return paketDetay;
    }

    @BeforeEach
    public void initTest() {
        paketDetay = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaketDetay() throws Exception {
        int databaseSizeBeforeCreate = paketDetayRepository.findAll().size();

        // Create the PaketDetay
        PaketDetayDTO paketDetayDTO = paketDetayMapper.toDto(paketDetay);
        restPaketDetayMockMvc.perform(post("/api/paket-detays")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paketDetayDTO)))
            .andExpect(status().isCreated());

        // Validate the PaketDetay in the database
        List<PaketDetay> paketDetayList = paketDetayRepository.findAll();
        assertThat(paketDetayList).hasSize(databaseSizeBeforeCreate + 1);
        PaketDetay testPaketDetay = paketDetayList.get(paketDetayList.size() - 1);
        assertThat(testPaketDetay.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaketDetay.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaketDetay.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPaketDetay.getVehicleQuota()).isEqualTo(DEFAULT_VEHICLE_QUOTA);
        assertThat(testPaketDetay.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPaketDetay.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPaketDetay.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testPaketDetay.getYear()).isEqualTo(DEFAULT_YEAR);

        // Validate the PaketDetay in Elasticsearch
        verify(mockPaketDetaySearchRepository, times(1)).save(testPaketDetay);
    }

    @Test
    @Transactional
    public void createPaketDetayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paketDetayRepository.findAll().size();

        // Create the PaketDetay with an existing ID
        paketDetay.setId(1L);
        PaketDetayDTO paketDetayDTO = paketDetayMapper.toDto(paketDetay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaketDetayMockMvc.perform(post("/api/paket-detays")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paketDetayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaketDetay in the database
        List<PaketDetay> paketDetayList = paketDetayRepository.findAll();
        assertThat(paketDetayList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaketDetay in Elasticsearch
        verify(mockPaketDetaySearchRepository, times(0)).save(paketDetay);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paketDetayRepository.findAll().size();
        // set the field null
        paketDetay.setName(null);

        // Create the PaketDetay, which fails.
        PaketDetayDTO paketDetayDTO = paketDetayMapper.toDto(paketDetay);

        restPaketDetayMockMvc.perform(post("/api/paket-detays")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paketDetayDTO)))
            .andExpect(status().isBadRequest());

        List<PaketDetay> paketDetayList = paketDetayRepository.findAll();
        assertThat(paketDetayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = paketDetayRepository.findAll().size();
        // set the field null
        paketDetay.setDescription(null);

        // Create the PaketDetay, which fails.
        PaketDetayDTO paketDetayDTO = paketDetayMapper.toDto(paketDetay);

        restPaketDetayMockMvc.perform(post("/api/paket-detays")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paketDetayDTO)))
            .andExpect(status().isBadRequest());

        List<PaketDetay> paketDetayList = paketDetayRepository.findAll();
        assertThat(paketDetayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = paketDetayRepository.findAll().size();
        // set the field null
        paketDetay.setYear(null);

        // Create the PaketDetay, which fails.
        PaketDetayDTO paketDetayDTO = paketDetayMapper.toDto(paketDetay);

        restPaketDetayMockMvc.perform(post("/api/paket-detays")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paketDetayDTO)))
            .andExpect(status().isBadRequest());

        List<PaketDetay> paketDetayList = paketDetayRepository.findAll();
        assertThat(paketDetayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaketDetays() throws Exception {
        // Initialize the database
        paketDetayRepository.saveAndFlush(paketDetay);

        // Get all the paketDetayList
        restPaketDetayMockMvc.perform(get("/api/paket-detays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paketDetay.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].vehicleQuota").value(hasItem(DEFAULT_VEHICLE_QUOTA)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }
    
    @Test
    @Transactional
    public void getPaketDetay() throws Exception {
        // Initialize the database
        paketDetayRepository.saveAndFlush(paketDetay);

        // Get the paketDetay
        restPaketDetayMockMvc.perform(get("/api/paket-detays/{id}", paketDetay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paketDetay.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.vehicleQuota").value(DEFAULT_VEHICLE_QUOTA))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR));
    }

    @Test
    @Transactional
    public void getNonExistingPaketDetay() throws Exception {
        // Get the paketDetay
        restPaketDetayMockMvc.perform(get("/api/paket-detays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaketDetay() throws Exception {
        // Initialize the database
        paketDetayRepository.saveAndFlush(paketDetay);

        int databaseSizeBeforeUpdate = paketDetayRepository.findAll().size();

        // Update the paketDetay
        PaketDetay updatedPaketDetay = paketDetayRepository.findById(paketDetay.getId()).get();
        // Disconnect from session so that the updates on updatedPaketDetay are not directly saved in db
        em.detach(updatedPaketDetay);
        updatedPaketDetay
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .vehicleQuota(UPDATED_VEHICLE_QUOTA)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .enabled(UPDATED_ENABLED)
            .year(UPDATED_YEAR);
        PaketDetayDTO paketDetayDTO = paketDetayMapper.toDto(updatedPaketDetay);

        restPaketDetayMockMvc.perform(put("/api/paket-detays")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paketDetayDTO)))
            .andExpect(status().isOk());

        // Validate the PaketDetay in the database
        List<PaketDetay> paketDetayList = paketDetayRepository.findAll();
        assertThat(paketDetayList).hasSize(databaseSizeBeforeUpdate);
        PaketDetay testPaketDetay = paketDetayList.get(paketDetayList.size() - 1);
        assertThat(testPaketDetay.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaketDetay.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaketDetay.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPaketDetay.getVehicleQuota()).isEqualTo(UPDATED_VEHICLE_QUOTA);
        assertThat(testPaketDetay.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPaketDetay.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPaketDetay.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testPaketDetay.getYear()).isEqualTo(UPDATED_YEAR);

        // Validate the PaketDetay in Elasticsearch
        verify(mockPaketDetaySearchRepository, times(1)).save(testPaketDetay);
    }

    @Test
    @Transactional
    public void updateNonExistingPaketDetay() throws Exception {
        int databaseSizeBeforeUpdate = paketDetayRepository.findAll().size();

        // Create the PaketDetay
        PaketDetayDTO paketDetayDTO = paketDetayMapper.toDto(paketDetay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaketDetayMockMvc.perform(put("/api/paket-detays")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paketDetayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaketDetay in the database
        List<PaketDetay> paketDetayList = paketDetayRepository.findAll();
        assertThat(paketDetayList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaketDetay in Elasticsearch
        verify(mockPaketDetaySearchRepository, times(0)).save(paketDetay);
    }

    @Test
    @Transactional
    public void deletePaketDetay() throws Exception {
        // Initialize the database
        paketDetayRepository.saveAndFlush(paketDetay);

        int databaseSizeBeforeDelete = paketDetayRepository.findAll().size();

        // Delete the paketDetay
        restPaketDetayMockMvc.perform(delete("/api/paket-detays/{id}", paketDetay.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaketDetay> paketDetayList = paketDetayRepository.findAll();
        assertThat(paketDetayList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaketDetay in Elasticsearch
        verify(mockPaketDetaySearchRepository, times(1)).deleteById(paketDetay.getId());
    }

    @Test
    @Transactional
    public void searchPaketDetay() throws Exception {
        // Initialize the database
        paketDetayRepository.saveAndFlush(paketDetay);
        when(mockPaketDetaySearchRepository.search(queryStringQuery("id:" + paketDetay.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paketDetay), PageRequest.of(0, 1), 1));
        // Search the paketDetay
        restPaketDetayMockMvc.perform(get("/api/_search/paket-detays?query=id:" + paketDetay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paketDetay.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].vehicleQuota").value(hasItem(DEFAULT_VEHICLE_QUOTA)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }
}
