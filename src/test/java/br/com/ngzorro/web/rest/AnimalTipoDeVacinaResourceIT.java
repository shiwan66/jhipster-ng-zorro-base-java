package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnimalTipoDeVacina;
import br.com.ngzorro.repository.AnimalTipoDeVacinaRepository;
import br.com.ngzorro.service.AnimalTipoDeVacinaService;
import br.com.ngzorro.service.dto.AnimalTipoDeVacinaDTO;
import br.com.ngzorro.service.mapper.AnimalTipoDeVacinaMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalTipoDeVacinaCriteria;
import br.com.ngzorro.service.AnimalTipoDeVacinaQueryService;

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
import java.util.List;

import static br.com.ngzorro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AnimalTipoDeVacinaResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalTipoDeVacinaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private AnimalTipoDeVacinaRepository animalTipoDeVacinaRepository;

    @Autowired
    private AnimalTipoDeVacinaMapper animalTipoDeVacinaMapper;

    @Autowired
    private AnimalTipoDeVacinaService animalTipoDeVacinaService;

    @Autowired
    private AnimalTipoDeVacinaQueryService animalTipoDeVacinaQueryService;

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

    private MockMvc restAnimalTipoDeVacinaMockMvc;

    private AnimalTipoDeVacina animalTipoDeVacina;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalTipoDeVacinaResource animalTipoDeVacinaResource = new AnimalTipoDeVacinaResource(animalTipoDeVacinaService, animalTipoDeVacinaQueryService);
        this.restAnimalTipoDeVacinaMockMvc = MockMvcBuilders.standaloneSetup(animalTipoDeVacinaResource)
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
    public static AnimalTipoDeVacina createEntity(EntityManager em) {
        AnimalTipoDeVacina animalTipoDeVacina = new AnimalTipoDeVacina()
            .descricao(DEFAULT_DESCRICAO);
        return animalTipoDeVacina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalTipoDeVacina createUpdatedEntity(EntityManager em) {
        AnimalTipoDeVacina animalTipoDeVacina = new AnimalTipoDeVacina()
            .descricao(UPDATED_DESCRICAO);
        return animalTipoDeVacina;
    }

    @BeforeEach
    public void initTest() {
        animalTipoDeVacina = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalTipoDeVacina() throws Exception {
        int databaseSizeBeforeCreate = animalTipoDeVacinaRepository.findAll().size();

        // Create the AnimalTipoDeVacina
        AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO = animalTipoDeVacinaMapper.toDto(animalTipoDeVacina);
        restAnimalTipoDeVacinaMockMvc.perform(post("/api/animal-tipo-de-vacinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalTipoDeVacinaDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalTipoDeVacina in the database
        List<AnimalTipoDeVacina> animalTipoDeVacinaList = animalTipoDeVacinaRepository.findAll();
        assertThat(animalTipoDeVacinaList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalTipoDeVacina testAnimalTipoDeVacina = animalTipoDeVacinaList.get(animalTipoDeVacinaList.size() - 1);
        assertThat(testAnimalTipoDeVacina.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createAnimalTipoDeVacinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalTipoDeVacinaRepository.findAll().size();

        // Create the AnimalTipoDeVacina with an existing ID
        animalTipoDeVacina.setId(1L);
        AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO = animalTipoDeVacinaMapper.toDto(animalTipoDeVacina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalTipoDeVacinaMockMvc.perform(post("/api/animal-tipo-de-vacinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalTipoDeVacinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalTipoDeVacina in the database
        List<AnimalTipoDeVacina> animalTipoDeVacinaList = animalTipoDeVacinaRepository.findAll();
        assertThat(animalTipoDeVacinaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalTipoDeVacinas() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        // Get all the animalTipoDeVacinaList
        restAnimalTipoDeVacinaMockMvc.perform(get("/api/animal-tipo-de-vacinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalTipoDeVacina.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getAnimalTipoDeVacina() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        // Get the animalTipoDeVacina
        restAnimalTipoDeVacinaMockMvc.perform(get("/api/animal-tipo-de-vacinas/{id}", animalTipoDeVacina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalTipoDeVacina.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getAnimalTipoDeVacinasByIdFiltering() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        Long id = animalTipoDeVacina.getId();

        defaultAnimalTipoDeVacinaShouldBeFound("id.equals=" + id);
        defaultAnimalTipoDeVacinaShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalTipoDeVacinaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalTipoDeVacinaShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalTipoDeVacinaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalTipoDeVacinaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalTipoDeVacinasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        // Get all the animalTipoDeVacinaList where descricao equals to DEFAULT_DESCRICAO
        defaultAnimalTipoDeVacinaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the animalTipoDeVacinaList where descricao equals to UPDATED_DESCRICAO
        defaultAnimalTipoDeVacinaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnimalTipoDeVacinasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        // Get all the animalTipoDeVacinaList where descricao not equals to DEFAULT_DESCRICAO
        defaultAnimalTipoDeVacinaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the animalTipoDeVacinaList where descricao not equals to UPDATED_DESCRICAO
        defaultAnimalTipoDeVacinaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnimalTipoDeVacinasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        // Get all the animalTipoDeVacinaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAnimalTipoDeVacinaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the animalTipoDeVacinaList where descricao equals to UPDATED_DESCRICAO
        defaultAnimalTipoDeVacinaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnimalTipoDeVacinasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        // Get all the animalTipoDeVacinaList where descricao is not null
        defaultAnimalTipoDeVacinaShouldBeFound("descricao.specified=true");

        // Get all the animalTipoDeVacinaList where descricao is null
        defaultAnimalTipoDeVacinaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalTipoDeVacinasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        // Get all the animalTipoDeVacinaList where descricao contains DEFAULT_DESCRICAO
        defaultAnimalTipoDeVacinaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the animalTipoDeVacinaList where descricao contains UPDATED_DESCRICAO
        defaultAnimalTipoDeVacinaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnimalTipoDeVacinasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        // Get all the animalTipoDeVacinaList where descricao does not contain DEFAULT_DESCRICAO
        defaultAnimalTipoDeVacinaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the animalTipoDeVacinaList where descricao does not contain UPDATED_DESCRICAO
        defaultAnimalTipoDeVacinaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalTipoDeVacinaShouldBeFound(String filter) throws Exception {
        restAnimalTipoDeVacinaMockMvc.perform(get("/api/animal-tipo-de-vacinas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalTipoDeVacina.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restAnimalTipoDeVacinaMockMvc.perform(get("/api/animal-tipo-de-vacinas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalTipoDeVacinaShouldNotBeFound(String filter) throws Exception {
        restAnimalTipoDeVacinaMockMvc.perform(get("/api/animal-tipo-de-vacinas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalTipoDeVacinaMockMvc.perform(get("/api/animal-tipo-de-vacinas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimalTipoDeVacina() throws Exception {
        // Get the animalTipoDeVacina
        restAnimalTipoDeVacinaMockMvc.perform(get("/api/animal-tipo-de-vacinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalTipoDeVacina() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        int databaseSizeBeforeUpdate = animalTipoDeVacinaRepository.findAll().size();

        // Update the animalTipoDeVacina
        AnimalTipoDeVacina updatedAnimalTipoDeVacina = animalTipoDeVacinaRepository.findById(animalTipoDeVacina.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalTipoDeVacina are not directly saved in db
        em.detach(updatedAnimalTipoDeVacina);
        updatedAnimalTipoDeVacina
            .descricao(UPDATED_DESCRICAO);
        AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO = animalTipoDeVacinaMapper.toDto(updatedAnimalTipoDeVacina);

        restAnimalTipoDeVacinaMockMvc.perform(put("/api/animal-tipo-de-vacinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalTipoDeVacinaDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalTipoDeVacina in the database
        List<AnimalTipoDeVacina> animalTipoDeVacinaList = animalTipoDeVacinaRepository.findAll();
        assertThat(animalTipoDeVacinaList).hasSize(databaseSizeBeforeUpdate);
        AnimalTipoDeVacina testAnimalTipoDeVacina = animalTipoDeVacinaList.get(animalTipoDeVacinaList.size() - 1);
        assertThat(testAnimalTipoDeVacina.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalTipoDeVacina() throws Exception {
        int databaseSizeBeforeUpdate = animalTipoDeVacinaRepository.findAll().size();

        // Create the AnimalTipoDeVacina
        AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO = animalTipoDeVacinaMapper.toDto(animalTipoDeVacina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalTipoDeVacinaMockMvc.perform(put("/api/animal-tipo-de-vacinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalTipoDeVacinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalTipoDeVacina in the database
        List<AnimalTipoDeVacina> animalTipoDeVacinaList = animalTipoDeVacinaRepository.findAll();
        assertThat(animalTipoDeVacinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalTipoDeVacina() throws Exception {
        // Initialize the database
        animalTipoDeVacinaRepository.saveAndFlush(animalTipoDeVacina);

        int databaseSizeBeforeDelete = animalTipoDeVacinaRepository.findAll().size();

        // Delete the animalTipoDeVacina
        restAnimalTipoDeVacinaMockMvc.perform(delete("/api/animal-tipo-de-vacinas/{id}", animalTipoDeVacina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalTipoDeVacina> animalTipoDeVacinaList = animalTipoDeVacinaRepository.findAll();
        assertThat(animalTipoDeVacinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
