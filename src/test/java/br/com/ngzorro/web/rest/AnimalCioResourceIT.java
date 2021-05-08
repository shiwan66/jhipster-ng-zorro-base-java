package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnimalCio;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.repository.AnimalCioRepository;
import br.com.ngzorro.service.AnimalCioService;
import br.com.ngzorro.service.dto.AnimalCioDTO;
import br.com.ngzorro.service.mapper.AnimalCioMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalCioCriteria;
import br.com.ngzorro.service.AnimalCioQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.ngzorro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AnimalCioResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalCioResourceIT {

    private static final LocalDate DEFAULT_DATA_DO_CIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DO_CIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_DO_CIO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private AnimalCioRepository animalCioRepository;

    @Autowired
    private AnimalCioMapper animalCioMapper;

    @Autowired
    private AnimalCioService animalCioService;

    @Autowired
    private AnimalCioQueryService animalCioQueryService;

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

    private MockMvc restAnimalCioMockMvc;

    private AnimalCio animalCio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalCioResource animalCioResource = new AnimalCioResource(animalCioService, animalCioQueryService);
        this.restAnimalCioMockMvc = MockMvcBuilders.standaloneSetup(animalCioResource)
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
    public static AnimalCio createEntity(EntityManager em) {
        AnimalCio animalCio = new AnimalCio()
            .dataDoCio(DEFAULT_DATA_DO_CIO)
            .observacao(DEFAULT_OBSERVACAO);
        return animalCio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalCio createUpdatedEntity(EntityManager em) {
        AnimalCio animalCio = new AnimalCio()
            .dataDoCio(UPDATED_DATA_DO_CIO)
            .observacao(UPDATED_OBSERVACAO);
        return animalCio;
    }

    @BeforeEach
    public void initTest() {
        animalCio = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalCio() throws Exception {
        int databaseSizeBeforeCreate = animalCioRepository.findAll().size();

        // Create the AnimalCio
        AnimalCioDTO animalCioDTO = animalCioMapper.toDto(animalCio);
        restAnimalCioMockMvc.perform(post("/api/animal-cios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCioDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalCio in the database
        List<AnimalCio> animalCioList = animalCioRepository.findAll();
        assertThat(animalCioList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalCio testAnimalCio = animalCioList.get(animalCioList.size() - 1);
        assertThat(testAnimalCio.getDataDoCio()).isEqualTo(DEFAULT_DATA_DO_CIO);
        assertThat(testAnimalCio.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    public void createAnimalCioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalCioRepository.findAll().size();

        // Create the AnimalCio with an existing ID
        animalCio.setId(1L);
        AnimalCioDTO animalCioDTO = animalCioMapper.toDto(animalCio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalCioMockMvc.perform(post("/api/animal-cios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalCio in the database
        List<AnimalCio> animalCioList = animalCioRepository.findAll();
        assertThat(animalCioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalCios() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get all the animalCioList
        restAnimalCioMockMvc.perform(get("/api/animal-cios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalCio.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDoCio").value(hasItem(DEFAULT_DATA_DO_CIO.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getAnimalCio() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get the animalCio
        restAnimalCioMockMvc.perform(get("/api/animal-cios/{id}", animalCio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalCio.getId().intValue()))
            .andExpect(jsonPath("$.dataDoCio").value(DEFAULT_DATA_DO_CIO.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()));
    }


    @Test
    @Transactional
    public void getAnimalCiosByIdFiltering() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        Long id = animalCio.getId();

        defaultAnimalCioShouldBeFound("id.equals=" + id);
        defaultAnimalCioShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalCioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalCioShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalCioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalCioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalCiosByDataDoCioIsEqualToSomething() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get all the animalCioList where dataDoCio equals to DEFAULT_DATA_DO_CIO
        defaultAnimalCioShouldBeFound("dataDoCio.equals=" + DEFAULT_DATA_DO_CIO);

        // Get all the animalCioList where dataDoCio equals to UPDATED_DATA_DO_CIO
        defaultAnimalCioShouldNotBeFound("dataDoCio.equals=" + UPDATED_DATA_DO_CIO);
    }

    @Test
    @Transactional
    public void getAllAnimalCiosByDataDoCioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get all the animalCioList where dataDoCio not equals to DEFAULT_DATA_DO_CIO
        defaultAnimalCioShouldNotBeFound("dataDoCio.notEquals=" + DEFAULT_DATA_DO_CIO);

        // Get all the animalCioList where dataDoCio not equals to UPDATED_DATA_DO_CIO
        defaultAnimalCioShouldBeFound("dataDoCio.notEquals=" + UPDATED_DATA_DO_CIO);
    }

    @Test
    @Transactional
    public void getAllAnimalCiosByDataDoCioIsInShouldWork() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get all the animalCioList where dataDoCio in DEFAULT_DATA_DO_CIO or UPDATED_DATA_DO_CIO
        defaultAnimalCioShouldBeFound("dataDoCio.in=" + DEFAULT_DATA_DO_CIO + "," + UPDATED_DATA_DO_CIO);

        // Get all the animalCioList where dataDoCio equals to UPDATED_DATA_DO_CIO
        defaultAnimalCioShouldNotBeFound("dataDoCio.in=" + UPDATED_DATA_DO_CIO);
    }

    @Test
    @Transactional
    public void getAllAnimalCiosByDataDoCioIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get all the animalCioList where dataDoCio is not null
        defaultAnimalCioShouldBeFound("dataDoCio.specified=true");

        // Get all the animalCioList where dataDoCio is null
        defaultAnimalCioShouldNotBeFound("dataDoCio.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnimalCiosByDataDoCioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get all the animalCioList where dataDoCio is greater than or equal to DEFAULT_DATA_DO_CIO
        defaultAnimalCioShouldBeFound("dataDoCio.greaterThanOrEqual=" + DEFAULT_DATA_DO_CIO);

        // Get all the animalCioList where dataDoCio is greater than or equal to UPDATED_DATA_DO_CIO
        defaultAnimalCioShouldNotBeFound("dataDoCio.greaterThanOrEqual=" + UPDATED_DATA_DO_CIO);
    }

    @Test
    @Transactional
    public void getAllAnimalCiosByDataDoCioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get all the animalCioList where dataDoCio is less than or equal to DEFAULT_DATA_DO_CIO
        defaultAnimalCioShouldBeFound("dataDoCio.lessThanOrEqual=" + DEFAULT_DATA_DO_CIO);

        // Get all the animalCioList where dataDoCio is less than or equal to SMALLER_DATA_DO_CIO
        defaultAnimalCioShouldNotBeFound("dataDoCio.lessThanOrEqual=" + SMALLER_DATA_DO_CIO);
    }

    @Test
    @Transactional
    public void getAllAnimalCiosByDataDoCioIsLessThanSomething() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get all the animalCioList where dataDoCio is less than DEFAULT_DATA_DO_CIO
        defaultAnimalCioShouldNotBeFound("dataDoCio.lessThan=" + DEFAULT_DATA_DO_CIO);

        // Get all the animalCioList where dataDoCio is less than UPDATED_DATA_DO_CIO
        defaultAnimalCioShouldBeFound("dataDoCio.lessThan=" + UPDATED_DATA_DO_CIO);
    }

    @Test
    @Transactional
    public void getAllAnimalCiosByDataDoCioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        // Get all the animalCioList where dataDoCio is greater than DEFAULT_DATA_DO_CIO
        defaultAnimalCioShouldNotBeFound("dataDoCio.greaterThan=" + DEFAULT_DATA_DO_CIO);

        // Get all the animalCioList where dataDoCio is greater than SMALLER_DATA_DO_CIO
        defaultAnimalCioShouldBeFound("dataDoCio.greaterThan=" + SMALLER_DATA_DO_CIO);
    }


    @Test
    @Transactional
    public void getAllAnimalCiosByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);
        Animal animal = AnimalResourceIT.createEntity(em);
        em.persist(animal);
        em.flush();
        animalCio.setAnimal(animal);
        animalCioRepository.saveAndFlush(animalCio);
        Long animalId = animal.getId();

        // Get all the animalCioList where animal equals to animalId
        defaultAnimalCioShouldBeFound("animalId.equals=" + animalId);

        // Get all the animalCioList where animal equals to animalId + 1
        defaultAnimalCioShouldNotBeFound("animalId.equals=" + (animalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalCioShouldBeFound(String filter) throws Exception {
        restAnimalCioMockMvc.perform(get("/api/animal-cios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalCio.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDoCio").value(hasItem(DEFAULT_DATA_DO_CIO.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));

        // Check, that the count call also returns 1
        restAnimalCioMockMvc.perform(get("/api/animal-cios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalCioShouldNotBeFound(String filter) throws Exception {
        restAnimalCioMockMvc.perform(get("/api/animal-cios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalCioMockMvc.perform(get("/api/animal-cios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimalCio() throws Exception {
        // Get the animalCio
        restAnimalCioMockMvc.perform(get("/api/animal-cios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalCio() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        int databaseSizeBeforeUpdate = animalCioRepository.findAll().size();

        // Update the animalCio
        AnimalCio updatedAnimalCio = animalCioRepository.findById(animalCio.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalCio are not directly saved in db
        em.detach(updatedAnimalCio);
        updatedAnimalCio
            .dataDoCio(UPDATED_DATA_DO_CIO)
            .observacao(UPDATED_OBSERVACAO);
        AnimalCioDTO animalCioDTO = animalCioMapper.toDto(updatedAnimalCio);

        restAnimalCioMockMvc.perform(put("/api/animal-cios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCioDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalCio in the database
        List<AnimalCio> animalCioList = animalCioRepository.findAll();
        assertThat(animalCioList).hasSize(databaseSizeBeforeUpdate);
        AnimalCio testAnimalCio = animalCioList.get(animalCioList.size() - 1);
        assertThat(testAnimalCio.getDataDoCio()).isEqualTo(UPDATED_DATA_DO_CIO);
        assertThat(testAnimalCio.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalCio() throws Exception {
        int databaseSizeBeforeUpdate = animalCioRepository.findAll().size();

        // Create the AnimalCio
        AnimalCioDTO animalCioDTO = animalCioMapper.toDto(animalCio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalCioMockMvc.perform(put("/api/animal-cios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalCio in the database
        List<AnimalCio> animalCioList = animalCioRepository.findAll();
        assertThat(animalCioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalCio() throws Exception {
        // Initialize the database
        animalCioRepository.saveAndFlush(animalCio);

        int databaseSizeBeforeDelete = animalCioRepository.findAll().size();

        // Delete the animalCio
        restAnimalCioMockMvc.perform(delete("/api/animal-cios/{id}", animalCio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalCio> animalCioList = animalCioRepository.findAll();
        assertThat(animalCioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
