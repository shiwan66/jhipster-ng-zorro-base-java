package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnimalVacina;
import br.com.ngzorro.domain.AnimalTipoDeVacina;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.repository.AnimalVacinaRepository;
import br.com.ngzorro.service.AnimalVacinaService;
import br.com.ngzorro.service.dto.AnimalVacinaDTO;
import br.com.ngzorro.service.mapper.AnimalVacinaMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalVacinaCriteria;
import br.com.ngzorro.service.AnimalVacinaQueryService;

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
 * Integration tests for the {@link AnimalVacinaResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalVacinaResourceIT {

    private static final LocalDate DEFAULT_DATA_DA_APLICACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DA_APLICACAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_DA_APLICACAO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AnimalVacinaRepository animalVacinaRepository;

    @Autowired
    private AnimalVacinaMapper animalVacinaMapper;

    @Autowired
    private AnimalVacinaService animalVacinaService;

    @Autowired
    private AnimalVacinaQueryService animalVacinaQueryService;

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

    private MockMvc restAnimalVacinaMockMvc;

    private AnimalVacina animalVacina;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalVacinaResource animalVacinaResource = new AnimalVacinaResource(animalVacinaService, animalVacinaQueryService);
        this.restAnimalVacinaMockMvc = MockMvcBuilders.standaloneSetup(animalVacinaResource)
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
    public static AnimalVacina createEntity(EntityManager em) {
        AnimalVacina animalVacina = new AnimalVacina()
            .dataDaAplicacao(DEFAULT_DATA_DA_APLICACAO);
        return animalVacina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalVacina createUpdatedEntity(EntityManager em) {
        AnimalVacina animalVacina = new AnimalVacina()
            .dataDaAplicacao(UPDATED_DATA_DA_APLICACAO);
        return animalVacina;
    }

    @BeforeEach
    public void initTest() {
        animalVacina = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalVacina() throws Exception {
        int databaseSizeBeforeCreate = animalVacinaRepository.findAll().size();

        // Create the AnimalVacina
        AnimalVacinaDTO animalVacinaDTO = animalVacinaMapper.toDto(animalVacina);
        restAnimalVacinaMockMvc.perform(post("/api/animal-vacinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVacinaDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalVacina in the database
        List<AnimalVacina> animalVacinaList = animalVacinaRepository.findAll();
        assertThat(animalVacinaList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalVacina testAnimalVacina = animalVacinaList.get(animalVacinaList.size() - 1);
        assertThat(testAnimalVacina.getDataDaAplicacao()).isEqualTo(DEFAULT_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void createAnimalVacinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalVacinaRepository.findAll().size();

        // Create the AnimalVacina with an existing ID
        animalVacina.setId(1L);
        AnimalVacinaDTO animalVacinaDTO = animalVacinaMapper.toDto(animalVacina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalVacinaMockMvc.perform(post("/api/animal-vacinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVacinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalVacina in the database
        List<AnimalVacina> animalVacinaList = animalVacinaRepository.findAll();
        assertThat(animalVacinaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalVacinas() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get all the animalVacinaList
        restAnimalVacinaMockMvc.perform(get("/api/animal-vacinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalVacina.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDaAplicacao").value(hasItem(DEFAULT_DATA_DA_APLICACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getAnimalVacina() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get the animalVacina
        restAnimalVacinaMockMvc.perform(get("/api/animal-vacinas/{id}", animalVacina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalVacina.getId().intValue()))
            .andExpect(jsonPath("$.dataDaAplicacao").value(DEFAULT_DATA_DA_APLICACAO.toString()));
    }


    @Test
    @Transactional
    public void getAnimalVacinasByIdFiltering() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        Long id = animalVacina.getId();

        defaultAnimalVacinaShouldBeFound("id.equals=" + id);
        defaultAnimalVacinaShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalVacinaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalVacinaShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalVacinaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalVacinaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalVacinasByDataDaAplicacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get all the animalVacinaList where dataDaAplicacao equals to DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldBeFound("dataDaAplicacao.equals=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVacinaList where dataDaAplicacao equals to UPDATED_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldNotBeFound("dataDaAplicacao.equals=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVacinasByDataDaAplicacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get all the animalVacinaList where dataDaAplicacao not equals to DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldNotBeFound("dataDaAplicacao.notEquals=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVacinaList where dataDaAplicacao not equals to UPDATED_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldBeFound("dataDaAplicacao.notEquals=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVacinasByDataDaAplicacaoIsInShouldWork() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get all the animalVacinaList where dataDaAplicacao in DEFAULT_DATA_DA_APLICACAO or UPDATED_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldBeFound("dataDaAplicacao.in=" + DEFAULT_DATA_DA_APLICACAO + "," + UPDATED_DATA_DA_APLICACAO);

        // Get all the animalVacinaList where dataDaAplicacao equals to UPDATED_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldNotBeFound("dataDaAplicacao.in=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVacinasByDataDaAplicacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get all the animalVacinaList where dataDaAplicacao is not null
        defaultAnimalVacinaShouldBeFound("dataDaAplicacao.specified=true");

        // Get all the animalVacinaList where dataDaAplicacao is null
        defaultAnimalVacinaShouldNotBeFound("dataDaAplicacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnimalVacinasByDataDaAplicacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get all the animalVacinaList where dataDaAplicacao is greater than or equal to DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldBeFound("dataDaAplicacao.greaterThanOrEqual=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVacinaList where dataDaAplicacao is greater than or equal to UPDATED_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldNotBeFound("dataDaAplicacao.greaterThanOrEqual=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVacinasByDataDaAplicacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get all the animalVacinaList where dataDaAplicacao is less than or equal to DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldBeFound("dataDaAplicacao.lessThanOrEqual=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVacinaList where dataDaAplicacao is less than or equal to SMALLER_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldNotBeFound("dataDaAplicacao.lessThanOrEqual=" + SMALLER_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVacinasByDataDaAplicacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get all the animalVacinaList where dataDaAplicacao is less than DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldNotBeFound("dataDaAplicacao.lessThan=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVacinaList where dataDaAplicacao is less than UPDATED_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldBeFound("dataDaAplicacao.lessThan=" + UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalVacinasByDataDaAplicacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        // Get all the animalVacinaList where dataDaAplicacao is greater than DEFAULT_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldNotBeFound("dataDaAplicacao.greaterThan=" + DEFAULT_DATA_DA_APLICACAO);

        // Get all the animalVacinaList where dataDaAplicacao is greater than SMALLER_DATA_DA_APLICACAO
        defaultAnimalVacinaShouldBeFound("dataDaAplicacao.greaterThan=" + SMALLER_DATA_DA_APLICACAO);
    }


    @Test
    @Transactional
    public void getAllAnimalVacinasByAnimalTipoDeVacinaIsEqualToSomething() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);
        AnimalTipoDeVacina animalTipoDeVacina = AnimalTipoDeVacinaResourceIT.createEntity(em);
        em.persist(animalTipoDeVacina);
        em.flush();
        animalVacina.setAnimalTipoDeVacina(animalTipoDeVacina);
        animalVacinaRepository.saveAndFlush(animalVacina);
        Long animalTipoDeVacinaId = animalTipoDeVacina.getId();

        // Get all the animalVacinaList where animalTipoDeVacina equals to animalTipoDeVacinaId
        defaultAnimalVacinaShouldBeFound("animalTipoDeVacinaId.equals=" + animalTipoDeVacinaId);

        // Get all the animalVacinaList where animalTipoDeVacina equals to animalTipoDeVacinaId + 1
        defaultAnimalVacinaShouldNotBeFound("animalTipoDeVacinaId.equals=" + (animalTipoDeVacinaId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalVacinasByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);
        Animal animal = AnimalResourceIT.createEntity(em);
        em.persist(animal);
        em.flush();
        animalVacina.setAnimal(animal);
        animalVacinaRepository.saveAndFlush(animalVacina);
        Long animalId = animal.getId();

        // Get all the animalVacinaList where animal equals to animalId
        defaultAnimalVacinaShouldBeFound("animalId.equals=" + animalId);

        // Get all the animalVacinaList where animal equals to animalId + 1
        defaultAnimalVacinaShouldNotBeFound("animalId.equals=" + (animalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalVacinaShouldBeFound(String filter) throws Exception {
        restAnimalVacinaMockMvc.perform(get("/api/animal-vacinas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalVacina.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDaAplicacao").value(hasItem(DEFAULT_DATA_DA_APLICACAO.toString())));

        // Check, that the count call also returns 1
        restAnimalVacinaMockMvc.perform(get("/api/animal-vacinas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalVacinaShouldNotBeFound(String filter) throws Exception {
        restAnimalVacinaMockMvc.perform(get("/api/animal-vacinas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalVacinaMockMvc.perform(get("/api/animal-vacinas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimalVacina() throws Exception {
        // Get the animalVacina
        restAnimalVacinaMockMvc.perform(get("/api/animal-vacinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalVacina() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        int databaseSizeBeforeUpdate = animalVacinaRepository.findAll().size();

        // Update the animalVacina
        AnimalVacina updatedAnimalVacina = animalVacinaRepository.findById(animalVacina.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalVacina are not directly saved in db
        em.detach(updatedAnimalVacina);
        updatedAnimalVacina
            .dataDaAplicacao(UPDATED_DATA_DA_APLICACAO);
        AnimalVacinaDTO animalVacinaDTO = animalVacinaMapper.toDto(updatedAnimalVacina);

        restAnimalVacinaMockMvc.perform(put("/api/animal-vacinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVacinaDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalVacina in the database
        List<AnimalVacina> animalVacinaList = animalVacinaRepository.findAll();
        assertThat(animalVacinaList).hasSize(databaseSizeBeforeUpdate);
        AnimalVacina testAnimalVacina = animalVacinaList.get(animalVacinaList.size() - 1);
        assertThat(testAnimalVacina.getDataDaAplicacao()).isEqualTo(UPDATED_DATA_DA_APLICACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalVacina() throws Exception {
        int databaseSizeBeforeUpdate = animalVacinaRepository.findAll().size();

        // Create the AnimalVacina
        AnimalVacinaDTO animalVacinaDTO = animalVacinaMapper.toDto(animalVacina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalVacinaMockMvc.perform(put("/api/animal-vacinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVacinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalVacina in the database
        List<AnimalVacina> animalVacinaList = animalVacinaRepository.findAll();
        assertThat(animalVacinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalVacina() throws Exception {
        // Initialize the database
        animalVacinaRepository.saveAndFlush(animalVacina);

        int databaseSizeBeforeDelete = animalVacinaRepository.findAll().size();

        // Delete the animalVacina
        restAnimalVacinaMockMvc.perform(delete("/api/animal-vacinas/{id}", animalVacina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalVacina> animalVacinaList = animalVacinaRepository.findAll();
        assertThat(animalVacinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
