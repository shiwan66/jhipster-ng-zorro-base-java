package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnimalAlteracao;
import br.com.ngzorro.domain.AnimalTipoDeAlteracao;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.repository.AnimalAlteracaoRepository;
import br.com.ngzorro.service.AnimalAlteracaoService;
import br.com.ngzorro.service.dto.AnimalAlteracaoDTO;
import br.com.ngzorro.service.mapper.AnimalAlteracaoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalAlteracaoCriteria;
import br.com.ngzorro.service.AnimalAlteracaoQueryService;

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
 * Integration tests for the {@link AnimalAlteracaoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalAlteracaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_ALTERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ALTERACAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_ALTERACAO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AnimalAlteracaoRepository animalAlteracaoRepository;

    @Autowired
    private AnimalAlteracaoMapper animalAlteracaoMapper;

    @Autowired
    private AnimalAlteracaoService animalAlteracaoService;

    @Autowired
    private AnimalAlteracaoQueryService animalAlteracaoQueryService;

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

    private MockMvc restAnimalAlteracaoMockMvc;

    private AnimalAlteracao animalAlteracao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalAlteracaoResource animalAlteracaoResource = new AnimalAlteracaoResource(animalAlteracaoService, animalAlteracaoQueryService);
        this.restAnimalAlteracaoMockMvc = MockMvcBuilders.standaloneSetup(animalAlteracaoResource)
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
    public static AnimalAlteracao createEntity(EntityManager em) {
        AnimalAlteracao animalAlteracao = new AnimalAlteracao()
            .descricao(DEFAULT_DESCRICAO)
            .dataAlteracao(DEFAULT_DATA_ALTERACAO);
        return animalAlteracao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalAlteracao createUpdatedEntity(EntityManager em) {
        AnimalAlteracao animalAlteracao = new AnimalAlteracao()
            .descricao(UPDATED_DESCRICAO)
            .dataAlteracao(UPDATED_DATA_ALTERACAO);
        return animalAlteracao;
    }

    @BeforeEach
    public void initTest() {
        animalAlteracao = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalAlteracao() throws Exception {
        int databaseSizeBeforeCreate = animalAlteracaoRepository.findAll().size();

        // Create the AnimalAlteracao
        AnimalAlteracaoDTO animalAlteracaoDTO = animalAlteracaoMapper.toDto(animalAlteracao);
        restAnimalAlteracaoMockMvc.perform(post("/api/animal-alteracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalAlteracaoDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalAlteracao in the database
        List<AnimalAlteracao> animalAlteracaoList = animalAlteracaoRepository.findAll();
        assertThat(animalAlteracaoList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalAlteracao testAnimalAlteracao = animalAlteracaoList.get(animalAlteracaoList.size() - 1);
        assertThat(testAnimalAlteracao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAnimalAlteracao.getDataAlteracao()).isEqualTo(DEFAULT_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void createAnimalAlteracaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalAlteracaoRepository.findAll().size();

        // Create the AnimalAlteracao with an existing ID
        animalAlteracao.setId(1L);
        AnimalAlteracaoDTO animalAlteracaoDTO = animalAlteracaoMapper.toDto(animalAlteracao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalAlteracaoMockMvc.perform(post("/api/animal-alteracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalAlteracaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalAlteracao in the database
        List<AnimalAlteracao> animalAlteracaoList = animalAlteracaoRepository.findAll();
        assertThat(animalAlteracaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalAlteracaos() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get all the animalAlteracaoList
        restAnimalAlteracaoMockMvc.perform(get("/api/animal-alteracaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalAlteracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getAnimalAlteracao() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get the animalAlteracao
        restAnimalAlteracaoMockMvc.perform(get("/api/animal-alteracaos/{id}", animalAlteracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalAlteracao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.dataAlteracao").value(DEFAULT_DATA_ALTERACAO.toString()));
    }


    @Test
    @Transactional
    public void getAnimalAlteracaosByIdFiltering() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        Long id = animalAlteracao.getId();

        defaultAnimalAlteracaoShouldBeFound("id.equals=" + id);
        defaultAnimalAlteracaoShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalAlteracaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalAlteracaoShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalAlteracaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalAlteracaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalAlteracaosByDataAlteracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get all the animalAlteracaoList where dataAlteracao equals to DEFAULT_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldBeFound("dataAlteracao.equals=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalAlteracaoList where dataAlteracao equals to UPDATED_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldNotBeFound("dataAlteracao.equals=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalAlteracaosByDataAlteracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get all the animalAlteracaoList where dataAlteracao not equals to DEFAULT_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldNotBeFound("dataAlteracao.notEquals=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalAlteracaoList where dataAlteracao not equals to UPDATED_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldBeFound("dataAlteracao.notEquals=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalAlteracaosByDataAlteracaoIsInShouldWork() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get all the animalAlteracaoList where dataAlteracao in DEFAULT_DATA_ALTERACAO or UPDATED_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldBeFound("dataAlteracao.in=" + DEFAULT_DATA_ALTERACAO + "," + UPDATED_DATA_ALTERACAO);

        // Get all the animalAlteracaoList where dataAlteracao equals to UPDATED_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldNotBeFound("dataAlteracao.in=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalAlteracaosByDataAlteracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get all the animalAlteracaoList where dataAlteracao is not null
        defaultAnimalAlteracaoShouldBeFound("dataAlteracao.specified=true");

        // Get all the animalAlteracaoList where dataAlteracao is null
        defaultAnimalAlteracaoShouldNotBeFound("dataAlteracao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnimalAlteracaosByDataAlteracaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get all the animalAlteracaoList where dataAlteracao is greater than or equal to DEFAULT_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldBeFound("dataAlteracao.greaterThanOrEqual=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalAlteracaoList where dataAlteracao is greater than or equal to UPDATED_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldNotBeFound("dataAlteracao.greaterThanOrEqual=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalAlteracaosByDataAlteracaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get all the animalAlteracaoList where dataAlteracao is less than or equal to DEFAULT_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldBeFound("dataAlteracao.lessThanOrEqual=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalAlteracaoList where dataAlteracao is less than or equal to SMALLER_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldNotBeFound("dataAlteracao.lessThanOrEqual=" + SMALLER_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalAlteracaosByDataAlteracaoIsLessThanSomething() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get all the animalAlteracaoList where dataAlteracao is less than DEFAULT_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldNotBeFound("dataAlteracao.lessThan=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalAlteracaoList where dataAlteracao is less than UPDATED_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldBeFound("dataAlteracao.lessThan=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalAlteracaosByDataAlteracaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        // Get all the animalAlteracaoList where dataAlteracao is greater than DEFAULT_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldNotBeFound("dataAlteracao.greaterThan=" + DEFAULT_DATA_ALTERACAO);

        // Get all the animalAlteracaoList where dataAlteracao is greater than SMALLER_DATA_ALTERACAO
        defaultAnimalAlteracaoShouldBeFound("dataAlteracao.greaterThan=" + SMALLER_DATA_ALTERACAO);
    }


    @Test
    @Transactional
    public void getAllAnimalAlteracaosByAnimalTipoDeAlteracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);
        AnimalTipoDeAlteracao animalTipoDeAlteracao = AnimalTipoDeAlteracaoResourceIT.createEntity(em);
        em.persist(animalTipoDeAlteracao);
        em.flush();
        animalAlteracao.setAnimalTipoDeAlteracao(animalTipoDeAlteracao);
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);
        Long animalTipoDeAlteracaoId = animalTipoDeAlteracao.getId();

        // Get all the animalAlteracaoList where animalTipoDeAlteracao equals to animalTipoDeAlteracaoId
        defaultAnimalAlteracaoShouldBeFound("animalTipoDeAlteracaoId.equals=" + animalTipoDeAlteracaoId);

        // Get all the animalAlteracaoList where animalTipoDeAlteracao equals to animalTipoDeAlteracaoId + 1
        defaultAnimalAlteracaoShouldNotBeFound("animalTipoDeAlteracaoId.equals=" + (animalTipoDeAlteracaoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalAlteracaosByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);
        Animal animal = AnimalResourceIT.createEntity(em);
        em.persist(animal);
        em.flush();
        animalAlteracao.setAnimal(animal);
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);
        Long animalId = animal.getId();

        // Get all the animalAlteracaoList where animal equals to animalId
        defaultAnimalAlteracaoShouldBeFound("animalId.equals=" + animalId);

        // Get all the animalAlteracaoList where animal equals to animalId + 1
        defaultAnimalAlteracaoShouldNotBeFound("animalId.equals=" + (animalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalAlteracaoShouldBeFound(String filter) throws Exception {
        restAnimalAlteracaoMockMvc.perform(get("/api/animal-alteracaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalAlteracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())));

        // Check, that the count call also returns 1
        restAnimalAlteracaoMockMvc.perform(get("/api/animal-alteracaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalAlteracaoShouldNotBeFound(String filter) throws Exception {
        restAnimalAlteracaoMockMvc.perform(get("/api/animal-alteracaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalAlteracaoMockMvc.perform(get("/api/animal-alteracaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimalAlteracao() throws Exception {
        // Get the animalAlteracao
        restAnimalAlteracaoMockMvc.perform(get("/api/animal-alteracaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalAlteracao() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        int databaseSizeBeforeUpdate = animalAlteracaoRepository.findAll().size();

        // Update the animalAlteracao
        AnimalAlteracao updatedAnimalAlteracao = animalAlteracaoRepository.findById(animalAlteracao.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalAlteracao are not directly saved in db
        em.detach(updatedAnimalAlteracao);
        updatedAnimalAlteracao
            .descricao(UPDATED_DESCRICAO)
            .dataAlteracao(UPDATED_DATA_ALTERACAO);
        AnimalAlteracaoDTO animalAlteracaoDTO = animalAlteracaoMapper.toDto(updatedAnimalAlteracao);

        restAnimalAlteracaoMockMvc.perform(put("/api/animal-alteracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalAlteracaoDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalAlteracao in the database
        List<AnimalAlteracao> animalAlteracaoList = animalAlteracaoRepository.findAll();
        assertThat(animalAlteracaoList).hasSize(databaseSizeBeforeUpdate);
        AnimalAlteracao testAnimalAlteracao = animalAlteracaoList.get(animalAlteracaoList.size() - 1);
        assertThat(testAnimalAlteracao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAnimalAlteracao.getDataAlteracao()).isEqualTo(UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalAlteracao() throws Exception {
        int databaseSizeBeforeUpdate = animalAlteracaoRepository.findAll().size();

        // Create the AnimalAlteracao
        AnimalAlteracaoDTO animalAlteracaoDTO = animalAlteracaoMapper.toDto(animalAlteracao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalAlteracaoMockMvc.perform(put("/api/animal-alteracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalAlteracaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalAlteracao in the database
        List<AnimalAlteracao> animalAlteracaoList = animalAlteracaoRepository.findAll();
        assertThat(animalAlteracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalAlteracao() throws Exception {
        // Initialize the database
        animalAlteracaoRepository.saveAndFlush(animalAlteracao);

        int databaseSizeBeforeDelete = animalAlteracaoRepository.findAll().size();

        // Delete the animalAlteracao
        restAnimalAlteracaoMockMvc.perform(delete("/api/animal-alteracaos/{id}", animalAlteracao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalAlteracao> animalAlteracaoList = animalAlteracaoRepository.findAll();
        assertThat(animalAlteracaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
