package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnimalVermifugo;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.repository.AnimalVermifugoRepository;
import br.com.ngzorro.service.AnimalVermifugoService;
import br.com.ngzorro.service.dto.AnimalVermifugoDTO;
import br.com.ngzorro.service.mapper.AnimalVermifugoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalVermifugoCriteria;
import br.com.ngzorro.service.AnimalVermifugoQueryService;

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
 * Integration tests for the {@link AnimalVermifugoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalVermifugoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DA_APLICACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DA_APLICACAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_DA_APLICACAO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AnimalVermifugoRepository animalVermifugoRepository;

    @Autowired
    private AnimalVermifugoMapper animalVermifugoMapper;

    @Autowired
    private AnimalVermifugoService animalVermifugoService;

    @Autowired
    private AnimalVermifugoQueryService animalVermifugoQueryService;

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

    private MockMvc restAnimalVermifugoMockMvc;

    private AnimalVermifugo animalVermifugo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalVermifugoResource animalVermifugoResource = new AnimalVermifugoResource(animalVermifugoService, animalVermifugoQueryService);
        this.restAnimalVermifugoMockMvc = MockMvcBuilders.standaloneSetup(animalVermifugoResource)
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
    public static AnimalVermifugo createEntity(EntityManager em) {
        AnimalVermifugo animalVermifugo = new AnimalVermifugo()
            .nome(DEFAULT_NOME)
            .dataDaAplicacao(DEFAULT_DATA_DA_APLICACAO);
        return animalVermifugo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalVermifugo createUpdatedEntity(EntityManager em) {
        AnimalVermifugo animalVermifugo = new AnimalVermifugo()
            .nome(UPDATED_NOME)
            .dataDaAplicacao(UPDATED_DATA_DA_APLICACAO);
        return animalVermifugo;
    }

    @BeforeEach
    public void initTest() {
        animalVermifugo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalVermifugo() throws Exception {
        int databaseSizeBeforeCreate = animalVermifugoRepository.findAll().size();

        // Create the AnimalVermifugo
        AnimalVermifugoDTO animalVermifugoDTO = animalVermifugoMapper.toDto(animalVermifugo);
        restAnimalVermifugoMockMvc.perform(post("/api/animal-vermifugos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVermifugoDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalVermifugo in the database
        List<AnimalVermifugo> animalVermifugoList = animalVermifugoRepository.findAll();
        assertThat(animalVermifugoList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalVermifugo testAnimalVermifugo = animalVermifugoList.get(animalVermifugoList.size() - 1);
        assertThat(testAnimalVermifugo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAnimalVermifugo.getDataDaAplicacao()).isEqualTo(DEFAULT_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void createAnimalVermifugoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalVermifugoRepository.findAll().size();

        // Create the AnimalVermifugo with an existing ID
        animalVermifugo.setId(1L);
        AnimalVermifugoDTO animalVermifugoDTO = animalVermifugoMapper.toDto(animalVermifugo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalVermifugoMockMvc.perform(post("/api/animal-vermifugos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVermifugoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalVermifugo in the database
        List<AnimalVermifugo> animalVermifugoList = animalVermifugoRepository.findAll();
        assertThat(animalVermifugoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalVermifugos() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList
        restAnimalVermifugoMockMvc.perform(get("/api/animal-vermifugos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalVermifugo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataDaAplicacao").value(hasItem(DEFAULT_DATA_DA_APLICACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getAnimalVermifugo() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get the animalVermifugo
        restAnimalVermifugoMockMvc.perform(get("/api/animal-vermifugos/{id}", animalVermifugo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalVermifugo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataDaAplicacao").value(DEFAULT_DATA_DA_APLICACAO.toString()));
    }


    @Test
    @Transactional
    public void getAnimalVermifugosByIdFiltering() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        Long id = animalVermifugo.getId();

        defaultAnimalVermifugoShouldBeFound("id.equals=" + id);
        defaultAnimalVermifugoShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalVermifugoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalVermifugoShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalVermifugoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalVermifugoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalVermifugosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where nome equals to DEFAULT_NOME
        defaultAnimalVermifugoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the animalVermifugoList where nome equals to UPDATED_NOME
        defaultAnimalVermifugoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where nome not equals to DEFAULT_NOME
        defaultAnimalVermifugoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the animalVermifugoList where nome not equals to UPDATED_NOME
        defaultAnimalVermifugoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAnimalVermifugoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the animalVermifugoList where nome equals to UPDATED_NOME
        defaultAnimalVermifugoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where nome is not null
        defaultAnimalVermifugoShouldBeFound("nome.specified=true");

        // Get all the animalVermifugoList where nome is null
        defaultAnimalVermifugoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalVermifugosByNomeContainsSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where nome contains DEFAULT_NOME
        defaultAnimalVermifugoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the animalVermifugoList where nome contains UPDATED_NOME
        defaultAnimalVermifugoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where nome does not contain DEFAULT_NOME
        defaultAnimalVermifugoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the animalVermifugoList where nome does not contain UPDATED_NOME
        defaultAnimalVermifugoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAnimalVermifugosByDataDaAplicacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where dataDaAplicacao equals to DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldBeFound("dataDaAplicacao.equals=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVermifugoList where dataDaAplicacao equals to UPDATED_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldNotBeFound("dataDaAplicacao.equals=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByDataDaAplicacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where dataDaAplicacao not equals to DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldNotBeFound("dataDaAplicacao.notEquals=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVermifugoList where dataDaAplicacao not equals to UPDATED_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldBeFound("dataDaAplicacao.notEquals=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByDataDaAplicacaoIsInShouldWork() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where dataDaAplicacao in DEFAULT_DATA_DA_APLICACAO or UPDATED_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldBeFound("dataDaAplicacao.in=" + DEFAULT_DATA_DA_APLICACAO + "," + UPDATED_DATA_DA_APLICACAO);

        // Get all the animalVermifugoList where dataDaAplicacao equals to UPDATED_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldNotBeFound("dataDaAplicacao.in=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByDataDaAplicacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where dataDaAplicacao is not null
        defaultAnimalVermifugoShouldBeFound("dataDaAplicacao.specified=true");

        // Get all the animalVermifugoList where dataDaAplicacao is null
        defaultAnimalVermifugoShouldNotBeFound("dataDaAplicacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByDataDaAplicacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where dataDaAplicacao is greater than or equal to DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldBeFound("dataDaAplicacao.greaterThanOrEqual=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVermifugoList where dataDaAplicacao is greater than or equal to UPDATED_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldNotBeFound("dataDaAplicacao.greaterThanOrEqual=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByDataDaAplicacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where dataDaAplicacao is less than or equal to DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldBeFound("dataDaAplicacao.lessThanOrEqual=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVermifugoList where dataDaAplicacao is less than or equal to SMALLER_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldNotBeFound("dataDaAplicacao.lessThanOrEqual=" + SMALLER_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByDataDaAplicacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where dataDaAplicacao is less than DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldNotBeFound("dataDaAplicacao.lessThan=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVermifugoList where dataDaAplicacao is less than UPDATED_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldBeFound("dataDaAplicacao.lessThan=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVermifugosByDataDaAplicacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        // Get all the animalVermifugoList where dataDaAplicacao is greater than DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldNotBeFound("dataDaAplicacao.greaterThan=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVermifugoList where dataDaAplicacao is greater than SMALLER_DATA_DA_APLICACAO
        defaultAnimalVermifugoShouldBeFound("dataDaAplicacao.greaterThan=" + SMALLER_DATA_DA_APLICACAO);
    }


    @Test
    @Transactional
    public void getAllAnimalVermifugosByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);
        Animal animal = AnimalResourceIT.createEntity(em);
        em.persist(animal);
        em.flush();
        animalVermifugo.setAnimal(animal);
        animalVermifugoRepository.saveAndFlush(animalVermifugo);
        Long animalId = animal.getId();

        // Get all the animalVermifugoList where animal equals to animalId
        defaultAnimalVermifugoShouldBeFound("animalId.equals=" + animalId);

        // Get all the animalVermifugoList where animal equals to animalId + 1
        defaultAnimalVermifugoShouldNotBeFound("animalId.equals=" + (animalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalVermifugoShouldBeFound(String filter) throws Exception {
        restAnimalVermifugoMockMvc.perform(get("/api/animal-vermifugos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalVermifugo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataDaAplicacao").value(hasItem(DEFAULT_DATA_DA_APLICACAO.toString())));

        // Check, that the count call also returns 1
        restAnimalVermifugoMockMvc.perform(get("/api/animal-vermifugos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalVermifugoShouldNotBeFound(String filter) throws Exception {
        restAnimalVermifugoMockMvc.perform(get("/api/animal-vermifugos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalVermifugoMockMvc.perform(get("/api/animal-vermifugos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimalVermifugo() throws Exception {
        // Get the animalVermifugo
        restAnimalVermifugoMockMvc.perform(get("/api/animal-vermifugos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalVermifugo() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        int databaseSizeBeforeUpdate = animalVermifugoRepository.findAll().size();

        // Update the animalVermifugo
        AnimalVermifugo updatedAnimalVermifugo = animalVermifugoRepository.findById(animalVermifugo.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalVermifugo are not directly saved in db
        em.detach(updatedAnimalVermifugo);
        updatedAnimalVermifugo
            .nome(UPDATED_NOME)
            .dataDaAplicacao(UPDATED_DATA_DA_APLICACAO);
        AnimalVermifugoDTO animalVermifugoDTO = animalVermifugoMapper.toDto(updatedAnimalVermifugo);

        restAnimalVermifugoMockMvc.perform(put("/api/animal-vermifugos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVermifugoDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalVermifugo in the database
        List<AnimalVermifugo> animalVermifugoList = animalVermifugoRepository.findAll();
        assertThat(animalVermifugoList).hasSize(databaseSizeBeforeUpdate);
        AnimalVermifugo testAnimalVermifugo = animalVermifugoList.get(animalVermifugoList.size() - 1);
        assertThat(testAnimalVermifugo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAnimalVermifugo.getDataDaAplicacao()).isEqualTo(UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalVermifugo() throws Exception {
        int databaseSizeBeforeUpdate = animalVermifugoRepository.findAll().size();

        // Create the AnimalVermifugo
        AnimalVermifugoDTO animalVermifugoDTO = animalVermifugoMapper.toDto(animalVermifugo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalVermifugoMockMvc.perform(put("/api/animal-vermifugos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVermifugoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalVermifugo in the database
        List<AnimalVermifugo> animalVermifugoList = animalVermifugoRepository.findAll();
        assertThat(animalVermifugoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalVermifugo() throws Exception {
        // Initialize the database
        animalVermifugoRepository.saveAndFlush(animalVermifugo);

        int databaseSizeBeforeDelete = animalVermifugoRepository.findAll().size();

        // Delete the animalVermifugo
        restAnimalVermifugoMockMvc.perform(delete("/api/animal-vermifugos/{id}", animalVermifugo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalVermifugo> animalVermifugoList = animalVermifugoRepository.findAll();
        assertThat(animalVermifugoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
