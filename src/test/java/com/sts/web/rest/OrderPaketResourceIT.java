package com.sts.web.rest;

import com.sts.StstakipApp;
import com.sts.domain.OrderPaket;
import com.sts.domain.Company;
import com.sts.domain.PaketDetay;
import com.sts.repository.OrderPaketRepository;
import com.sts.repository.search.OrderPaketSearchRepository;
import com.sts.service.OrderPaketService;
import com.sts.service.dto.OrderPaketDTO;
import com.sts.service.mapper.OrderPaketMapper;
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
 * Integration tests for the {@link OrderPaketResource} REST controller.
 */
@SpringBootTest(classes = StstakipApp.class)
public class OrderPaketResourceIT {

    private static final Instant DEFAULT_ORDERED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDERED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_EXPIRED = false;
    private static final Boolean UPDATED_IS_EXPIRED = true;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OrderPaketRepository orderPaketRepository;

    @Autowired
    private OrderPaketMapper orderPaketMapper;

    @Autowired
    private OrderPaketService orderPaketService;

    /**
     * This repository is mocked in the com.sts.repository.search test package.
     *
     * @see com.sts.repository.search.OrderPaketSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrderPaketSearchRepository mockOrderPaketSearchRepository;

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

    private MockMvc restOrderPaketMockMvc;

    private OrderPaket orderPaket;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderPaketResource orderPaketResource = new OrderPaketResource(orderPaketService);
        this.restOrderPaketMockMvc = MockMvcBuilders.standaloneSetup(orderPaketResource)
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
    public static OrderPaket createEntity(EntityManager em) {
        OrderPaket orderPaket = new OrderPaket()
            .orderedDate(DEFAULT_ORDERED_DATE)
            .isExpired(DEFAULT_IS_EXPIRED)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        orderPaket.setCompany(company);
        // Add required entity
        PaketDetay paketDetay;
        if (TestUtil.findAll(em, PaketDetay.class).isEmpty()) {
            paketDetay = PaketDetayResourceIT.createEntity(em);
            em.persist(paketDetay);
            em.flush();
        } else {
            paketDetay = TestUtil.findAll(em, PaketDetay.class).get(0);
        }
        orderPaket.setPaketDetay(paketDetay);
        return orderPaket;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderPaket createUpdatedEntity(EntityManager em) {
        OrderPaket orderPaket = new OrderPaket()
            .orderedDate(UPDATED_ORDERED_DATE)
            .isExpired(UPDATED_IS_EXPIRED)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        orderPaket.setCompany(company);
        // Add required entity
        PaketDetay paketDetay;
        if (TestUtil.findAll(em, PaketDetay.class).isEmpty()) {
            paketDetay = PaketDetayResourceIT.createUpdatedEntity(em);
            em.persist(paketDetay);
            em.flush();
        } else {
            paketDetay = TestUtil.findAll(em, PaketDetay.class).get(0);
        }
        orderPaket.setPaketDetay(paketDetay);
        return orderPaket;
    }

    @BeforeEach
    public void initTest() {
        orderPaket = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderPaket() throws Exception {
        int databaseSizeBeforeCreate = orderPaketRepository.findAll().size();

        // Create the OrderPaket
        OrderPaketDTO orderPaketDTO = orderPaketMapper.toDto(orderPaket);
        restOrderPaketMockMvc.perform(post("/api/order-pakets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPaketDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderPaket in the database
        List<OrderPaket> orderPaketList = orderPaketRepository.findAll();
        assertThat(orderPaketList).hasSize(databaseSizeBeforeCreate + 1);
        OrderPaket testOrderPaket = orderPaketList.get(orderPaketList.size() - 1);
        assertThat(testOrderPaket.getOrderedDate()).isEqualTo(DEFAULT_ORDERED_DATE);
        assertThat(testOrderPaket.isIsExpired()).isEqualTo(DEFAULT_IS_EXPIRED);
        assertThat(testOrderPaket.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOrderPaket.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the OrderPaket in Elasticsearch
        verify(mockOrderPaketSearchRepository, times(1)).save(testOrderPaket);
    }

    @Test
    @Transactional
    public void createOrderPaketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderPaketRepository.findAll().size();

        // Create the OrderPaket with an existing ID
        orderPaket.setId(1L);
        OrderPaketDTO orderPaketDTO = orderPaketMapper.toDto(orderPaket);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderPaketMockMvc.perform(post("/api/order-pakets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPaketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPaket in the database
        List<OrderPaket> orderPaketList = orderPaketRepository.findAll();
        assertThat(orderPaketList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrderPaket in Elasticsearch
        verify(mockOrderPaketSearchRepository, times(0)).save(orderPaket);
    }


    @Test
    @Transactional
    public void checkOrderedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderPaketRepository.findAll().size();
        // set the field null
        orderPaket.setOrderedDate(null);

        // Create the OrderPaket, which fails.
        OrderPaketDTO orderPaketDTO = orderPaketMapper.toDto(orderPaket);

        restOrderPaketMockMvc.perform(post("/api/order-pakets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPaketDTO)))
            .andExpect(status().isBadRequest());

        List<OrderPaket> orderPaketList = orderPaketRepository.findAll();
        assertThat(orderPaketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderPaketRepository.findAll().size();
        // set the field null
        orderPaket.setStartDate(null);

        // Create the OrderPaket, which fails.
        OrderPaketDTO orderPaketDTO = orderPaketMapper.toDto(orderPaket);

        restOrderPaketMockMvc.perform(post("/api/order-pakets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPaketDTO)))
            .andExpect(status().isBadRequest());

        List<OrderPaket> orderPaketList = orderPaketRepository.findAll();
        assertThat(orderPaketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderPaketRepository.findAll().size();
        // set the field null
        orderPaket.setEndDate(null);

        // Create the OrderPaket, which fails.
        OrderPaketDTO orderPaketDTO = orderPaketMapper.toDto(orderPaket);

        restOrderPaketMockMvc.perform(post("/api/order-pakets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPaketDTO)))
            .andExpect(status().isBadRequest());

        List<OrderPaket> orderPaketList = orderPaketRepository.findAll();
        assertThat(orderPaketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderPakets() throws Exception {
        // Initialize the database
        orderPaketRepository.saveAndFlush(orderPaket);

        // Get all the orderPaketList
        restOrderPaketMockMvc.perform(get("/api/order-pakets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderPaket.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderedDate").value(hasItem(DEFAULT_ORDERED_DATE.toString())))
            .andExpect(jsonPath("$.[*].isExpired").value(hasItem(DEFAULT_IS_EXPIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderPaket() throws Exception {
        // Initialize the database
        orderPaketRepository.saveAndFlush(orderPaket);

        // Get the orderPaket
        restOrderPaketMockMvc.perform(get("/api/order-pakets/{id}", orderPaket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderPaket.getId().intValue()))
            .andExpect(jsonPath("$.orderedDate").value(DEFAULT_ORDERED_DATE.toString()))
            .andExpect(jsonPath("$.isExpired").value(DEFAULT_IS_EXPIRED.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderPaket() throws Exception {
        // Get the orderPaket
        restOrderPaketMockMvc.perform(get("/api/order-pakets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderPaket() throws Exception {
        // Initialize the database
        orderPaketRepository.saveAndFlush(orderPaket);

        int databaseSizeBeforeUpdate = orderPaketRepository.findAll().size();

        // Update the orderPaket
        OrderPaket updatedOrderPaket = orderPaketRepository.findById(orderPaket.getId()).get();
        // Disconnect from session so that the updates on updatedOrderPaket are not directly saved in db
        em.detach(updatedOrderPaket);
        updatedOrderPaket
            .orderedDate(UPDATED_ORDERED_DATE)
            .isExpired(UPDATED_IS_EXPIRED)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        OrderPaketDTO orderPaketDTO = orderPaketMapper.toDto(updatedOrderPaket);

        restOrderPaketMockMvc.perform(put("/api/order-pakets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPaketDTO)))
            .andExpect(status().isOk());

        // Validate the OrderPaket in the database
        List<OrderPaket> orderPaketList = orderPaketRepository.findAll();
        assertThat(orderPaketList).hasSize(databaseSizeBeforeUpdate);
        OrderPaket testOrderPaket = orderPaketList.get(orderPaketList.size() - 1);
        assertThat(testOrderPaket.getOrderedDate()).isEqualTo(UPDATED_ORDERED_DATE);
        assertThat(testOrderPaket.isIsExpired()).isEqualTo(UPDATED_IS_EXPIRED);
        assertThat(testOrderPaket.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOrderPaket.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the OrderPaket in Elasticsearch
        verify(mockOrderPaketSearchRepository, times(1)).save(testOrderPaket);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderPaket() throws Exception {
        int databaseSizeBeforeUpdate = orderPaketRepository.findAll().size();

        // Create the OrderPaket
        OrderPaketDTO orderPaketDTO = orderPaketMapper.toDto(orderPaket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderPaketMockMvc.perform(put("/api/order-pakets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPaketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPaket in the database
        List<OrderPaket> orderPaketList = orderPaketRepository.findAll();
        assertThat(orderPaketList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrderPaket in Elasticsearch
        verify(mockOrderPaketSearchRepository, times(0)).save(orderPaket);
    }

    @Test
    @Transactional
    public void deleteOrderPaket() throws Exception {
        // Initialize the database
        orderPaketRepository.saveAndFlush(orderPaket);

        int databaseSizeBeforeDelete = orderPaketRepository.findAll().size();

        // Delete the orderPaket
        restOrderPaketMockMvc.perform(delete("/api/order-pakets/{id}", orderPaket.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderPaket> orderPaketList = orderPaketRepository.findAll();
        assertThat(orderPaketList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrderPaket in Elasticsearch
        verify(mockOrderPaketSearchRepository, times(1)).deleteById(orderPaket.getId());
    }

    @Test
    @Transactional
    public void searchOrderPaket() throws Exception {
        // Initialize the database
        orderPaketRepository.saveAndFlush(orderPaket);
        when(mockOrderPaketSearchRepository.search(queryStringQuery("id:" + orderPaket.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(orderPaket), PageRequest.of(0, 1), 1));
        // Search the orderPaket
        restOrderPaketMockMvc.perform(get("/api/_search/order-pakets?query=id:" + orderPaket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderPaket.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderedDate").value(hasItem(DEFAULT_ORDERED_DATE.toString())))
            .andExpect(jsonPath("$.[*].isExpired").value(hasItem(DEFAULT_IS_EXPIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
}
