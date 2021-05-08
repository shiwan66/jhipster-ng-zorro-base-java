package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnimalCarrapaticida;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.repository.AnimalCarrapaticidaRepository;
import br.com.ngzorro.service.AnimalCarrapaticidaService;
import br.com.ngzorro.service.dto.AnimalCarrapaticidaDTO;
import br.com.ngzorro.service.mapper.AnimalCarrapaticidaMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalCarrapaticidaCriteria;
import br.com.ngzorro.service.AnimalCarrapaticidaQueryService;

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
 * Integration tests for the {@link AnimalCarrapaticidaResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalCarrapaticidaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_APLICACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_APLICACAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_APLICACAO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AnimalCarrapaticidaRepository animalCarrapaticidaRepository;

    @Autowired
    private AnimalCarrapaticidaMapper animalCarrapaticidaMapper;

    @Autowired
    private AnimalCarrapaticidaService animalCarrapaticidaService;

    @Autowired
    private AnimalCarrapaticidaQueryService animalCarrapaticidaQueryService;

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

    private MockMvc restAnimalCarrapaticidaMockMvc;

    private AnimalCarrapaticida animalCarrapaticida;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalCarrapaticidaResource animalCarrapaticidaResource = new AnimalCarrapaticidaResource(animalCarrapaticidaService, animalCarrapaticidaQueryService);
        this.restAnimalCarrapaticidaMockMvc = MockMvcBuilders.standaloneSetup(animalCarrapaticidaResource)
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
    public static AnimalCarrapaticida createEntity(EntityManager em) {
        AnimalCarrapaticida animalCarrapaticida = new AnimalCarrapaticida()
            .nome(DEFAULT_NOME)
            .dataAplicacao(DEFAULT_DATA_APLICACAO);
        return animalCarrapaticida;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalCarrapaticida createUpdatedEntity(EntityManager em) {
        AnimalCarrapaticida animalCarrapaticida = new AnimalCarrapaticida()
            .nome(UPDATED_NOME)
            .dataAplicacao(UPDATED_DATA_APLICACAO);
        return animalCarrapaticida;
    }

    @BeforeEach
    public void initTest() {
        animalCarrapaticida = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalCarrapaticida() throws Exception {
        int databaseSizeBeforeCreate = animalCarrapaticidaRepository.findAll().size();

        // Create the AnimalCarrapaticida
        AnimalCarrapaticidaDTO animalCarrapaticidaDTO = animalCarrapaticidaMapper.toDto(animalCarrapaticida);
        restAnimalCarrapaticidaMockMvc.perform(post("/api/animal-carrapaticidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCarrapaticidaDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalCarrapaticida in the database
        List<AnimalCarrapaticida> animalCarrapaticidaList = animalCarrapaticidaRepository.findAll();
        assertThat(animalCarrapaticidaList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalCarrapaticida testAnimalCarrapaticida = animalCarrapaticidaList.get(animalCarrapaticidaList.size() - 1);
        assertThat(testAnimalCarrapaticida.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAnimalCarrapaticida.getDataAplicacao()).isEqualTo(DEFAULT_DATA_APLICACAO);
    }

    @Test
    @Transactional
    public void createAnimalCarrapaticidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalCarrapaticidaRepository.findAll().size();

        // Create the AnimalCarrapaticida with an existing ID
        animalCarrapaticida.setId(1L);
        AnimalCarrapaticidaDTO animalCarrapaticidaDTO = animalCarrapaticidaMapper.toDto(animalCarrapaticida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalCarrapaticidaMockMvc.perform(post("/api/animal-carrapaticidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCarrapaticidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalCarrapaticida in the database
        List<AnimalCarrapaticida> animalCarrapaticidaList = animalCarrapaticidaRepository.findAll();
        assertThat(animalCarrapaticidaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalCarrapaticidas() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList
        restAnimalCarrapaticidaMockMvc.perform(get("/api/animal-carrapaticidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalCarrapaticida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataAplicacao").value(hasItem(DEFAULT_DATA_APLICACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getAnimalCarrapaticida() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get the animalCarrapaticida
        restAnimalCarrapaticidaMockMvc.perform(get("/api/animal-carrapaticidas/{id}", animalCarrapaticida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalCarrapaticida.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataAplicacao").value(DEFAULT_DATA_APLICACAO.toString()));
    }


    @Test
    @Transactional
    public void getAnimalCarrapaticidasByIdFiltering() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        Long id = animalCarrapaticida.getId();

        defaultAnimalCarrapaticidaShouldBeFound("id.equals=" + id);
        defaultAnimalCarrapaticidaShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalCarrapaticidaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalCarrapaticidaShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalCarrapaticidaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalCarrapaticidaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where nome equals to DEFAULT_NOME
        defaultAnimalCarrapaticidaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the animalCarrapaticidaList where nome equals to UPDATED_NOME
        defaultAnimalCarrapaticidaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where nome not equals to DEFAULT_NOME
        defaultAnimalCarrapaticidaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the animalCarrapaticidaList where nome not equals to UPDATED_NOME
        defaultAnimalCarrapaticidaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAnimalCarrapaticidaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the animalCarrapaticidaList where nome equals to UPDATED_NOME
        defaultAnimalCarrapaticidaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where nome is not null
        defaultAnimalCarrapaticidaShouldBeFound("nome.specified=true");

        // Get all the animalCarrapaticidaList where nome is null
        defaultAnimalCarrapaticidaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByNomeContainsSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where nome contains DEFAULT_NOME
        defaultAnimalCarrapaticidaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the animalCarrapaticidaList where nome contains UPDATED_NOME
        defaultAnimalCarrapaticidaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where nome does not contain DEFAULT_NOME
        defaultAnimalCarrapaticidaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the animalCarrapaticidaList where nome does not contain UPDATED_NOME
        defaultAnimalCarrapaticidaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByDataAplicacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where dataAplicacao equals to DEFAULT_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldBeFound("dataAplicacao.equals=" + DEFAULT_DATA_APLICACAO);

        // Get all the animalCarrapaticidaList where dataAplicacao equals to UPDATED_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldNotBeFound("dataAplicacao.equals=" + UPDATED_DATA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByDataAplicacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where dataAplicacao not equals to DEFAULT_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldNotBeFound("dataAplicacao.notEquals=" + DEFAULT_DATA_APLICACAO);

        // Get all the animalCarrapaticidaList where dataAplicacao not equals to UPDATED_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldBeFound("dataAplicacao.notEquals=" + UPDATED_DATA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByDataAplicacaoIsInShouldWork() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where dataAplicacao in DEFAULT_DATA_APLICACAO or UPDATED_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldBeFound("dataAplicacao.in=" + DEFAULT_DATA_APLICACAO + "," + UPDATED_DATA_APLICACAO);

        // Get all the animalCarrapaticidaList where dataAplicacao equals to UPDATED_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldNotBeFound("dataAplicacao.in=" + UPDATED_DATA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByDataAplicacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where dataAplicacao is not null
        defaultAnimalCarrapaticidaShouldBeFound("dataAplicacao.specified=true");

        // Get all the animalCarrapaticidaList where dataAplicacao is null
        defaultAnimalCarrapaticidaShouldNotBeFound("dataAplicacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByDataAplicacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where dataAplicacao is greater than or equal to DEFAULT_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldBeFound("dataAplicacao.greaterThanOrEqual=" + DEFAULT_DATA_APLICACAO);

        // Get all the animalCarrapaticidaList where dataAplicacao is greater than or equal to UPDATED_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldNotBeFound("dataAplicacao.greaterThanOrEqual=" + UPDATED_DATA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByDataAplicacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where dataAplicacao is less than or equal to DEFAULT_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldBeFound("dataAplicacao.lessThanOrEqual=" + DEFAULT_DATA_APLICACAO);

        // Get all the animalCarrapaticidaList where dataAplicacao is less than or equal to SMALLER_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldNotBeFound("dataAplicacao.lessThanOrEqual=" + SMALLER_DATA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByDataAplicacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where dataAplicacao is less than DEFAULT_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldNotBeFound("dataAplicacao.lessThan=" + DEFAULT_DATA_APLICACAO);

        // Get all the animalCarrapaticidaList where dataAplicacao is less than UPDATED_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldBeFound("dataAplicacao.lessThan=" + UPDATED_DATA_APLICACAO);
    }

    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByDataAplicacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        // Get all the animalCarrapaticidaList where dataAplicacao is greater than DEFAULT_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldNotBeFound("dataAplicacao.greaterThan=" + DEFAULT_DATA_APLICACAO);

        // Get all the animalCarrapaticidaList where dataAplicacao is greater than SMALLER_DATA_APLICACAO
        defaultAnimalCarrapaticidaShouldBeFound("dataAplicacao.greaterThan=" + SMALLER_DATA_APLICACAO);
    }


    @Test
    @Transactional
    public void getAllAnimalCarrapaticidasByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);
        Animal animal = AnimalResourceIT.createEntity(em);
        em.persist(animal);
        em.flush();
        animalCarrapaticida.setAnimal(animal);
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);
        Long animalId = animal.getId();

        // Get all the animalCarrapaticidaList where animal equals to animalId
        defaultAnimalCarrapaticidaShouldBeFound("animalId.equals=" + animalId);

        // Get all the animalCarrapaticidaList where animal equals to animalId + 1
        defaultAnimalCarrapaticidaShouldNotBeFound("animalId.equals=" + (animalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalCarrapaticidaShouldBeFound(String filter) throws Exception {
        restAnimalCarrapaticidaMockMvc.perform(get("/api/animal-carrapaticidas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalCarrapaticida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataAplicacao").value(hasItem(DEFAULT_DATA_APLICACAO.toString())));

        // Check, that the count call also returns 1
        restAnimalCarrapaticidaMockMvc.perform(get("/api/animal-carrapaticidas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalCarrapaticidaShouldNotBeFound(String filter) throws Exception {
        restAnimalCarrapaticidaMockMvc.perform(get("/api/animal-carrapaticidas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalCarrapaticidaMockMvc.perform(get("/api/animal-carrapaticidas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimalCarrapaticida() throws Exception {
        // Get the animalCarrapaticida
        restAnimalCarrapaticidaMockMvc.perform(get("/api/animal-carrapaticidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalCarrapaticida() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        int databaseSizeBeforeUpdate = animalCarrapaticidaRepository.findAll().size();

        // Update the animalCarrapaticida
        AnimalCarrapaticida updatedAnimalCarrapaticida = animalCarrapaticidaRepository.findById(animalCarrapaticida.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalCarrapaticida are not directly saved in db
        em.detach(updatedAnimalCarrapaticida);
        updatedAnimalCarrapaticida
            .nome(UPDATED_NOME)
            .dataAplicacao(UPDATED_DATA_APLICACAO);
        AnimalCarrapaticidaDTO animalCarrapaticidaDTO = animalCarrapaticidaMapper.toDto(updatedAnimalCarrapaticida);

        restAnimalCarrapaticidaMockMvc.perform(put("/api/animal-carrapaticidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCarrapaticidaDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalCarrapaticida in the database
        List<AnimalCarrapaticida> animalCarrapaticidaList = animalCarrapaticidaRepository.findAll();
        assertThat(animalCarrapaticidaList).hasSize(databaseSizeBeforeUpdate);
        AnimalCarrapaticida testAnimalCarrapaticida = animalCarrapaticidaList.get(animalCarrapaticidaList.size() - 1);
        assertThat(testAnimalCarrapaticida.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAnimalCarrapaticida.getDataAplicacao()).isEqualTo(UPDATED_DATA_APLICACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalCarrapaticida() throws Exception {
        int databaseSizeBeforeUpdate = animalCarrapaticidaRepository.findAll().size();

        // Create the AnimalCarrapaticida
        AnimalCarrapaticidaDTO animalCarrapaticidaDTO = animalCarrapaticidaMapper.toDto(animalCarrapaticida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalCarrapaticidaMockMvc.perform(put("/api/animal-carrapaticidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCarrapaticidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalCarrapaticida in the database
        List<AnimalCarrapaticida> animalCarrapaticidaList = animalCarrapaticidaRepository.findAll();
        assertThat(animalCarrapaticidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalCarrapaticida() throws Exception {
        // Initialize the database
        animalCarrapaticidaRepository.saveAndFlush(animalCarrapaticida);

        int databaseSizeBeforeDelete = animalCarrapaticidaRepository.findAll().size();

        // Delete the animalCarrapaticida
        restAnimalCarrapaticidaMockMvc.perform(delete("/api/animal-carrapaticidas/{id}", animalCarrapaticida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalCarrapaticida> animalCarrapaticidaList = animalCarrapaticidaRepository.findAll();
        assertThat(animalCarrapaticidaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
