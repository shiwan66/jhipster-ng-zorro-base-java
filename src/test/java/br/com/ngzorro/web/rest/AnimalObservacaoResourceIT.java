package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnimalObservacao;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.repository.AnimalObservacaoRepository;
import br.com.ngzorro.service.AnimalObservacaoService;
import br.com.ngzorro.service.dto.AnimalObservacaoDTO;
import br.com.ngzorro.service.mapper.AnimalObservacaoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalObservacaoCriteria;
import br.com.ngzorro.service.AnimalObservacaoQueryService;

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
 * Integration tests for the {@link AnimalObservacaoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalObservacaoResourceIT {

    private static final LocalDate DEFAULT_DATA_ALTERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ALTERACAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_ALTERACAO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private AnimalObservacaoRepository animalObservacaoRepository;

    @Autowired
    private AnimalObservacaoMapper animalObservacaoMapper;

    @Autowired
    private AnimalObservacaoService animalObservacaoService;

    @Autowired
    private AnimalObservacaoQueryService animalObservacaoQueryService;

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

    private MockMvc restAnimalObservacaoMockMvc;

    private AnimalObservacao animalObservacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalObservacaoResource animalObservacaoResource = new AnimalObservacaoResource(animalObservacaoService, animalObservacaoQueryService);
        this.restAnimalObservacaoMockMvc = MockMvcBuilders.standaloneSetup(animalObservacaoResource)
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
    public static AnimalObservacao createEntity(EntityManager em) {
        AnimalObservacao animalObservacao = new AnimalObservacao()
            .dataAlteracao(DEFAULT_DATA_ALTERACAO)
            .observacao(DEFAULT_OBSERVACAO);
        return animalObservacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalObservacao createUpdatedEntity(EntityManager em) {
        AnimalObservacao animalObservacao = new AnimalObservacao()
            .dataAlteracao(UPDATED_DATA_ALTERACAO)
            .observacao(UPDATED_OBSERVACAO);
        return animalObservacao;
    }

    @BeforeEach
    public void initTest() {
        animalObservacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalObservacao() throws Exception {
        int databaseSizeBeforeCreate = animalObservacaoRepository.findAll().size();

        // Create the AnimalObservacao
        AnimalObservacaoDTO animalObservacaoDTO = animalObservacaoMapper.toDto(animalObservacao);
        restAnimalObservacaoMockMvc.perform(post("/api/animal-observacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalObservacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalObservacao in the database
        List<AnimalObservacao> animalObservacaoList = animalObservacaoRepository.findAll();
        assertThat(animalObservacaoList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalObservacao testAnimalObservacao = animalObservacaoList.get(animalObservacaoList.size() - 1);
        assertThat(testAnimalObservacao.getDataAlteracao()).isEqualTo(DEFAULT_DATA_ALTERACAO);
        assertThat(testAnimalObservacao.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    public void createAnimalObservacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalObservacaoRepository.findAll().size();

        // Create the AnimalObservacao with an existing ID
        animalObservacao.setId(1L);
        AnimalObservacaoDTO animalObservacaoDTO = animalObservacaoMapper.toDto(animalObservacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalObservacaoMockMvc.perform(post("/api/animal-observacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalObservacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalObservacao in the database
        List<AnimalObservacao> animalObservacaoList = animalObservacaoRepository.findAll();
        assertThat(animalObservacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalObservacaos() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get all the animalObservacaoList
        restAnimalObservacaoMockMvc.perform(get("/api/animal-observacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalObservacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getAnimalObservacao() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get the animalObservacao
        restAnimalObservacaoMockMvc.perform(get("/api/animal-observacaos/{id}", animalObservacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalObservacao.getId().intValue()))
            .andExpect(jsonPath("$.dataAlteracao").value(DEFAULT_DATA_ALTERACAO.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()));
    }


    @Test
    @Transactional
    public void getAnimalObservacaosByIdFiltering() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        Long id = animalObservacao.getId();

        defaultAnimalObservacaoShouldBeFound("id.equals=" + id);
        defaultAnimalObservacaoShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalObservacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalObservacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalObservacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalObservacaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalObservacaosByDataAlteracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get all the animalObservacaoList where dataAlteracao equals to DEFAULT_DATA_ALTERACAO
        defaultAnimalObservacaoShouldBeFound("dataAlteracao.equals=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalObservacaoList where dataAlteracao equals to UPDATED_DATA_ALTERACAO
        defaultAnimalObservacaoShouldNotBeFound("dataAlteracao.equals=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalObservacaosByDataAlteracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get all the animalObservacaoList where dataAlteracao not equals to DEFAULT_DATA_ALTERACAO
        defaultAnimalObservacaoShouldNotBeFound("dataAlteracao.notEquals=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalObservacaoList where dataAlteracao not equals to UPDATED_DATA_ALTERACAO
        defaultAnimalObservacaoShouldBeFound("dataAlteracao.notEquals=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalObservacaosByDataAlteracaoIsInShouldWork() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get all the animalObservacaoList where dataAlteracao in DEFAULT_DATA_ALTERACAO or UPDATED_DATA_ALTERACAO
        defaultAnimalObservacaoShouldBeFound("dataAlteracao.in=" + DEFAULT_DATA_ALTERACAO + "," + UPDATED_DATA_ALTERACAO);

        // Get all the animalObservacaoList where dataAlteracao equals to UPDATED_DATA_ALTERACAO
        defaultAnimalObservacaoShouldNotBeFound("dataAlteracao.in=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalObservacaosByDataAlteracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get all the animalObservacaoList where dataAlteracao is not null
        defaultAnimalObservacaoShouldBeFound("dataAlteracao.specified=true");

        // Get all the animalObservacaoList where dataAlteracao is null
        defaultAnimalObservacaoShouldNotBeFound("dataAlteracao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnimalObservacaosByDataAlteracaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get all the animalObservacaoList where dataAlteracao is greater than or equal to DEFAULT_DATA_ALTERACAO
        defaultAnimalObservacaoShouldBeFound("dataAlteracao.greaterThanOrEqual=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalObservacaoList where dataAlteracao is greater than or equal to UPDATED_DATA_ALTERACAO
        defaultAnimalObservacaoShouldNotBeFound("dataAlteracao.greaterThanOrEqual=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalObservacaosByDataAlteracaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get all the animalObservacaoList where dataAlteracao is less than or equal to DEFAULT_DATA_ALTERACAO
        defaultAnimalObservacaoShouldBeFound("dataAlteracao.lessThanOrEqual=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalObservacaoList where dataAlteracao is less than or equal to SMALLER_DATA_ALTERACAO
        defaultAnimalObservacaoShouldNotBeFound("dataAlteracao.lessThanOrEqual=" + SMALLER_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalObservacaosByDataAlteracaoIsLessThanSomething() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get all the animalObservacaoList where dataAlteracao is less than DEFAULT_DATA_ALTERACAO
        defaultAnimalObservacaoShouldNotBeFound("dataAlteracao.lessThan=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalObservacaoList where dataAlteracao is less than UPDATED_DATA_ALTERACAO
        defaultAnimalObservacaoShouldBeFound("dataAlteracao.lessThan=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalObservacaosByDataAlteracaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        // Get all the animalObservacaoList where dataAlteracao is greater than DEFAULT_DATA_ALTERACAO
        defaultAnimalObservacaoShouldNotBeFound("dataAlteracao.greaterThan=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalObservacaoList where dataAlteracao is greater than SMALLER_DATA_ALTERACAO
        defaultAnimalObservacaoShouldBeFound("dataAlteracao.greaterThan=" + SMALLER_DATA_ALTERACAO);
    }


    @Test
    @Transactional
    public void getAllAnimalObservacaosByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);
        Animal animal = AnimalResourceIT.createEntity(em);
        em.persist(animal);
        em.flush();
        animalObservacao.setAnimal(animal);
        animalObservacaoRepository.saveAndFlush(animalObservacao);
        Long animalId = animal.getId();

        // Get all the animalObservacaoList where animal equals to animalId
        defaultAnimalObservacaoShouldBeFound("animalId.equals=" + animalId);

        // Get all the animalObservacaoList where animal equals to animalId + 1
        defaultAnimalObservacaoShouldNotBeFound("animalId.equals=" + (animalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalObservacaoShouldBeFound(String filter) throws Exception {
        restAnimalObservacaoMockMvc.perform(get("/api/animal-observacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalObservacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));

        // Check, that the count call also returns 1
        restAnimalObservacaoMockMvc.perform(get("/api/animal-observacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalObservacaoShouldNotBeFound(String filter) throws Exception {
        restAnimalObservacaoMockMvc.perform(get("/api/animal-observacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalObservacaoMockMvc.perform(get("/api/animal-observacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimalObservacao() throws Exception {
        // Get the animalObservacao
        restAnimalObservacaoMockMvc.perform(get("/api/animal-observacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalObservacao() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        int databaseSizeBeforeUpdate = animalObservacaoRepository.findAll().size();

        // Update the animalObservacao
        AnimalObservacao updatedAnimalObservacao = animalObservacaoRepository.findById(animalObservacao.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalObservacao are not directly saved in db
        em.detach(updatedAnimalObservacao);
        updatedAnimalObservacao
            .dataAlteracao(UPDATED_DATA_ALTERACAO)
            .observacao(UPDATED_OBSERVACAO);
        AnimalObservacaoDTO animalObservacaoDTO = animalObservacaoMapper.toDto(updatedAnimalObservacao);

        restAnimalObservacaoMockMvc.perform(put("/api/animal-observacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalObservacaoDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalObservacao in the database
        List<AnimalObservacao> animalObservacaoList = animalObservacaoRepository.findAll();
        assertThat(animalObservacaoList).hasSize(databaseSizeBeforeUpdate);
        AnimalObservacao testAnimalObservacao = animalObservacaoList.get(animalObservacaoList.size() - 1);
        assertThat(testAnimalObservacao.getDataAlteracao()).isEqualTo(UPDATED_DATA_ALTERACAO);
        assertThat(testAnimalObservacao.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalObservacao() throws Exception {
        int databaseSizeBeforeUpdate = animalObservacaoRepository.findAll().size();

        // Create the AnimalObservacao
        AnimalObservacaoDTO animalObservacaoDTO = animalObservacaoMapper.toDto(animalObservacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalObservacaoMockMvc.perform(put("/api/animal-observacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalObservacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalObservacao in the database
        List<AnimalObservacao> animalObservacaoList = animalObservacaoRepository.findAll();
        assertThat(animalObservacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalObservacao() throws Exception {
        // Initialize the database
        animalObservacaoRepository.saveAndFlush(animalObservacao);

        int databaseSizeBeforeDelete = animalObservacaoRepository.findAll().size();

        // Delete the animalObservacao
        restAnimalObservacaoMockMvc.perform(delete("/api/animal-observacaos/{id}", animalObservacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalObservacao> animalObservacaoList = animalObservacaoRepository.findAll();
        assertThat(animalObservacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
