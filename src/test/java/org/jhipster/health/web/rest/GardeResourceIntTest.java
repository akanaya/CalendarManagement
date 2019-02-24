package org.jhipster.health.web.rest;

import org.jhipster.health.TwentyOnePointsApp;

import org.jhipster.health.domain.Garde;
import org.jhipster.health.repository.GardeRepository;
import org.jhipster.health.repository.search.GardeSearchRepository;
import org.jhipster.health.service.GardeService;
import org.jhipster.health.web.rest.errors.ExceptionTranslator;
import org.jhipster.health.service.dto.GardeCriteria;
import org.jhipster.health.service.GardeQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static org.jhipster.health.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GardeResource REST controller.
 *
 * @see GardeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TwentyOnePointsApp.class)
public class GardeResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_SELLER_RESIDANT = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_RESIDANT = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER_RESIDENT = "AAAAAAAAAA";
    private static final String UPDATED_BUYER_RESIDENT = "BBBBBBBBBB";

    @Autowired
    private GardeRepository gardeRepository;
    
    @Autowired
    private GardeService gardeService;

    /**
     * This repository is mocked in the org.jhipster.health.repository.search test package.
     *
     * @see org.jhipster.health.repository.search.GardeSearchRepositoryMockConfiguration
     */
    @Autowired
    private GardeSearchRepository mockGardeSearchRepository;

    @Autowired
    private GardeQueryService gardeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGardeMockMvc;

    private Garde garde;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GardeResource gardeResource = new GardeResource(gardeService, gardeQueryService);
        this.restGardeMockMvc = MockMvcBuilders.standaloneSetup(gardeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Garde createEntity(EntityManager em) {
        Garde garde = new Garde()
            .date(DEFAULT_DATE)
            .amount(DEFAULT_AMOUNT)
            .seller_residant(DEFAULT_SELLER_RESIDANT)
            .buyer_resident(DEFAULT_BUYER_RESIDENT);
        return garde;
    }

    @Before
    public void initTest() {
        garde = createEntity(em);
    }

    @Test
    @Transactional
    public void createGarde() throws Exception {
        int databaseSizeBeforeCreate = gardeRepository.findAll().size();

        // Create the Garde
        restGardeMockMvc.perform(post("/api/gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garde)))
            .andExpect(status().isCreated());

        // Validate the Garde in the database
        List<Garde> gardeList = gardeRepository.findAll();
        assertThat(gardeList).hasSize(databaseSizeBeforeCreate + 1);
        Garde testGarde = gardeList.get(gardeList.size() - 1);
        assertThat(testGarde.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testGarde.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testGarde.getSeller_residant()).isEqualTo(DEFAULT_SELLER_RESIDANT);
        assertThat(testGarde.getBuyer_resident()).isEqualTo(DEFAULT_BUYER_RESIDENT);

        // Validate the Garde in Elasticsearch
        verify(mockGardeSearchRepository, times(1)).save(testGarde);
    }

    @Test
    @Transactional
    public void createGardeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gardeRepository.findAll().size();

        // Create the Garde with an existing ID
        garde.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGardeMockMvc.perform(post("/api/gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garde)))
            .andExpect(status().isBadRequest());

        // Validate the Garde in the database
        List<Garde> gardeList = gardeRepository.findAll();
        assertThat(gardeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Garde in Elasticsearch
        verify(mockGardeSearchRepository, times(0)).save(garde);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = gardeRepository.findAll().size();
        // set the field null
        garde.setDate(null);

        // Create the Garde, which fails.

        restGardeMockMvc.perform(post("/api/gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garde)))
            .andExpect(status().isBadRequest());

        List<Garde> gardeList = gardeRepository.findAll();
        assertThat(gardeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = gardeRepository.findAll().size();
        // set the field null
        garde.setAmount(null);

        // Create the Garde, which fails.

        restGardeMockMvc.perform(post("/api/gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garde)))
            .andExpect(status().isBadRequest());

        List<Garde> gardeList = gardeRepository.findAll();
        assertThat(gardeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGardes() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList
        restGardeMockMvc.perform(get("/api/gardes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garde.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].seller_residant").value(hasItem(DEFAULT_SELLER_RESIDANT.toString())))
            .andExpect(jsonPath("$.[*].buyer_resident").value(hasItem(DEFAULT_BUYER_RESIDENT.toString())));
    }
    
    @Test
    @Transactional
    public void getGarde() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get the garde
        restGardeMockMvc.perform(get("/api/gardes/{id}", garde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(garde.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.seller_residant").value(DEFAULT_SELLER_RESIDANT.toString()))
            .andExpect(jsonPath("$.buyer_resident").value(DEFAULT_BUYER_RESIDENT.toString()));
    }

    @Test
    @Transactional
    public void getAllGardesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where date equals to DEFAULT_DATE
        defaultGardeShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the gardeList where date equals to UPDATED_DATE
        defaultGardeShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGardesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where date in DEFAULT_DATE or UPDATED_DATE
        defaultGardeShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the gardeList where date equals to UPDATED_DATE
        defaultGardeShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGardesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where date is not null
        defaultGardeShouldBeFound("date.specified=true");

        // Get all the gardeList where date is null
        defaultGardeShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllGardesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where date greater than or equals to DEFAULT_DATE
        defaultGardeShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the gardeList where date greater than or equals to UPDATED_DATE
        defaultGardeShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGardesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where date less than or equals to DEFAULT_DATE
        defaultGardeShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the gardeList where date less than or equals to UPDATED_DATE
        defaultGardeShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllGardesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where amount equals to DEFAULT_AMOUNT
        defaultGardeShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the gardeList where amount equals to UPDATED_AMOUNT
        defaultGardeShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllGardesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultGardeShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the gardeList where amount equals to UPDATED_AMOUNT
        defaultGardeShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllGardesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where amount is not null
        defaultGardeShouldBeFound("amount.specified=true");

        // Get all the gardeList where amount is null
        defaultGardeShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllGardesBySeller_residantIsEqualToSomething() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where seller_residant equals to DEFAULT_SELLER_RESIDANT
        defaultGardeShouldBeFound("seller_residant.equals=" + DEFAULT_SELLER_RESIDANT);

        // Get all the gardeList where seller_residant equals to UPDATED_SELLER_RESIDANT
        defaultGardeShouldNotBeFound("seller_residant.equals=" + UPDATED_SELLER_RESIDANT);
    }

    @Test
    @Transactional
    public void getAllGardesBySeller_residantIsInShouldWork() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where seller_residant in DEFAULT_SELLER_RESIDANT or UPDATED_SELLER_RESIDANT
        defaultGardeShouldBeFound("seller_residant.in=" + DEFAULT_SELLER_RESIDANT + "," + UPDATED_SELLER_RESIDANT);

        // Get all the gardeList where seller_residant equals to UPDATED_SELLER_RESIDANT
        defaultGardeShouldNotBeFound("seller_residant.in=" + UPDATED_SELLER_RESIDANT);
    }

    @Test
    @Transactional
    public void getAllGardesBySeller_residantIsNullOrNotNull() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where seller_residant is not null
        defaultGardeShouldBeFound("seller_residant.specified=true");

        // Get all the gardeList where seller_residant is null
        defaultGardeShouldNotBeFound("seller_residant.specified=false");
    }

    @Test
    @Transactional
    public void getAllGardesByBuyer_residentIsEqualToSomething() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where buyer_resident equals to DEFAULT_BUYER_RESIDENT
        defaultGardeShouldBeFound("buyer_resident.equals=" + DEFAULT_BUYER_RESIDENT);

        // Get all the gardeList where buyer_resident equals to UPDATED_BUYER_RESIDENT
        defaultGardeShouldNotBeFound("buyer_resident.equals=" + UPDATED_BUYER_RESIDENT);
    }

    @Test
    @Transactional
    public void getAllGardesByBuyer_residentIsInShouldWork() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where buyer_resident in DEFAULT_BUYER_RESIDENT or UPDATED_BUYER_RESIDENT
        defaultGardeShouldBeFound("buyer_resident.in=" + DEFAULT_BUYER_RESIDENT + "," + UPDATED_BUYER_RESIDENT);

        // Get all the gardeList where buyer_resident equals to UPDATED_BUYER_RESIDENT
        defaultGardeShouldNotBeFound("buyer_resident.in=" + UPDATED_BUYER_RESIDENT);
    }

    @Test
    @Transactional
    public void getAllGardesByBuyer_residentIsNullOrNotNull() throws Exception {
        // Initialize the database
        gardeRepository.saveAndFlush(garde);

        // Get all the gardeList where buyer_resident is not null
        defaultGardeShouldBeFound("buyer_resident.specified=true");

        // Get all the gardeList where buyer_resident is null
        defaultGardeShouldNotBeFound("buyer_resident.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGardeShouldBeFound(String filter) throws Exception {
        restGardeMockMvc.perform(get("/api/gardes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garde.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].seller_residant").value(hasItem(DEFAULT_SELLER_RESIDANT.toString())))
            .andExpect(jsonPath("$.[*].buyer_resident").value(hasItem(DEFAULT_BUYER_RESIDENT.toString())));

        // Check, that the count call also returns 1
        restGardeMockMvc.perform(get("/api/gardes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGardeShouldNotBeFound(String filter) throws Exception {
        restGardeMockMvc.perform(get("/api/gardes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGardeMockMvc.perform(get("/api/gardes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGarde() throws Exception {
        // Get the garde
        restGardeMockMvc.perform(get("/api/gardes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGarde() throws Exception {
        // Initialize the database
        gardeService.save(garde);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockGardeSearchRepository);

        int databaseSizeBeforeUpdate = gardeRepository.findAll().size();

        // Update the garde
        Garde updatedGarde = gardeRepository.findById(garde.getId()).get();
        // Disconnect from session so that the updates on updatedGarde are not directly saved in db
        em.detach(updatedGarde);
        updatedGarde
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .seller_residant(UPDATED_SELLER_RESIDANT)
            .buyer_resident(UPDATED_BUYER_RESIDENT);

        restGardeMockMvc.perform(put("/api/gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGarde)))
            .andExpect(status().isOk());

        // Validate the Garde in the database
        List<Garde> gardeList = gardeRepository.findAll();
        assertThat(gardeList).hasSize(databaseSizeBeforeUpdate);
        Garde testGarde = gardeList.get(gardeList.size() - 1);
        assertThat(testGarde.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testGarde.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testGarde.getSeller_residant()).isEqualTo(UPDATED_SELLER_RESIDANT);
        assertThat(testGarde.getBuyer_resident()).isEqualTo(UPDATED_BUYER_RESIDENT);

        // Validate the Garde in Elasticsearch
        verify(mockGardeSearchRepository, times(1)).save(testGarde);
    }

    @Test
    @Transactional
    public void updateNonExistingGarde() throws Exception {
        int databaseSizeBeforeUpdate = gardeRepository.findAll().size();

        // Create the Garde

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGardeMockMvc.perform(put("/api/gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garde)))
            .andExpect(status().isBadRequest());

        // Validate the Garde in the database
        List<Garde> gardeList = gardeRepository.findAll();
        assertThat(gardeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Garde in Elasticsearch
        verify(mockGardeSearchRepository, times(0)).save(garde);
    }

    @Test
    @Transactional
    public void deleteGarde() throws Exception {
        // Initialize the database
        gardeService.save(garde);

        int databaseSizeBeforeDelete = gardeRepository.findAll().size();

        // Get the garde
        restGardeMockMvc.perform(delete("/api/gardes/{id}", garde.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Garde> gardeList = gardeRepository.findAll();
        assertThat(gardeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Garde in Elasticsearch
        verify(mockGardeSearchRepository, times(1)).deleteById(garde.getId());
    }

    @Test
    @Transactional
    public void searchGarde() throws Exception {
        // Initialize the database
        gardeService.save(garde);
        when(mockGardeSearchRepository.search(queryStringQuery("id:" + garde.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(garde), PageRequest.of(0, 1), 1));
        // Search the garde
        restGardeMockMvc.perform(get("/api/_search/gardes?query=id:" + garde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garde.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].seller_residant").value(hasItem(DEFAULT_SELLER_RESIDANT.toString())))
            .andExpect(jsonPath("$.[*].buyer_resident").value(hasItem(DEFAULT_BUYER_RESIDENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Garde.class);
        Garde garde1 = new Garde();
        garde1.setId(1L);
        Garde garde2 = new Garde();
        garde2.setId(garde1.getId());
        assertThat(garde1).isEqualTo(garde2);
        garde2.setId(2L);
        assertThat(garde1).isNotEqualTo(garde2);
        garde1.setId(null);
        assertThat(garde1).isNotEqualTo(garde2);
    }
}
