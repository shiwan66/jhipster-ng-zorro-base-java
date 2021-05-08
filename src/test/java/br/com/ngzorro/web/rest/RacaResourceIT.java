package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Raca;
import br.com.ngzorro.repository.RacaRepository;
import br.com.ngzorro.service.RacaService;
import br.com.ngzorro.service.dto.RacaDTO;
import br.com.ngzorro.service.mapper.RacaMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.RacaCriteria;
import br.com.ngzorro.service.RacaQueryService;

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
 * Integration tests for the {@link RacaResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class RacaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private RacaRepository racaRepository;

    @Autowired
    private RacaMapper racaMapper;

    @Autowired
    private RacaService racaService;

    @Autowired
    private RacaQueryService racaQueryService;

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

    private MockMvc restRacaMockMvc;

    private Raca raca;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RacaResource racaResource = new RacaResource(racaService, racaQueryService);
        this.restRacaMockMvc = MockMvcBuilders.standaloneSetup(racaResource)
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
    public static Raca createEntity(EntityManager em) {
        Raca raca = new Raca()
            .nome(DEFAULT_NOME);
        return raca;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raca createUpdatedEntity(EntityManager em) {
        Raca raca = new Raca()
            .nome(UPDATED_NOME);
        return raca;
    }

    @BeforeEach
    public void initTest() {
        raca = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaca() throws Exception {
        int databaseSizeBeforeCreate = racaRepository.findAll().size();

        // Create the Raca
        RacaDTO racaDTO = racaMapper.toDto(raca);
        restRacaMockMvc.perform(post("/api/racas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racaDTO)))
            .andExpect(status().isCreated());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeCreate + 1);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createRacaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = racaRepository.findAll().size();

        // Create the Raca with an existing ID
        raca.setId(1L);
        RacaDTO racaDTO = racaMapper.toDto(raca);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacaMockMvc.perform(post("/api/racas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRacas() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList
        restRacaMockMvc.perform(get("/api/racas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get the raca
        restRacaMockMvc.perform(get("/api/racas/{id}", raca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(raca.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }


    @Test
    @Transactional
    public void getRacasByIdFiltering() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        Long id = raca.getId();

        defaultRacaShouldBeFound("id.equals=" + id);
        defaultRacaShouldNotBeFound("id.notEquals=" + id);

        defaultRacaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRacaShouldNotBeFound("id.greaterThan=" + id);

        defaultRacaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRacaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRacasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList where nome equals to DEFAULT_NOME
        defaultRacaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the racaList where nome equals to UPDATED_NOME
        defaultRacaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRacasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList where nome not equals to DEFAULT_NOME
        defaultRacaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the racaList where nome not equals to UPDATED_NOME
        defaultRacaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRacasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultRacaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the racaList where nome equals to UPDATED_NOME
        defaultRacaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRacasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList where nome is not null
        defaultRacaShouldBeFound("nome.specified=true");

        // Get all the racaList where nome is null
        defaultRacaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllRacasByNomeContainsSomething() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList where nome contains DEFAULT_NOME
        defaultRacaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the racaList where nome contains UPDATED_NOME
        defaultRacaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRacasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList where nome does not contain DEFAULT_NOME
        defaultRacaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the racaList where nome does not contain UPDATED_NOME
        defaultRacaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRacaShouldBeFound(String filter) throws Exception {
        restRacaMockMvc.perform(get("/api/racas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restRacaMockMvc.perform(get("/api/racas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRacaShouldNotBeFound(String filter) throws Exception {
        restRacaMockMvc.perform(get("/api/racas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRacaMockMvc.perform(get("/api/racas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRaca() throws Exception {
        // Get the raca
        restRacaMockMvc.perform(get("/api/racas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        int databaseSizeBeforeUpdate = racaRepository.findAll().size();

        // Update the raca
        Raca updatedRaca = racaRepository.findById(raca.getId()).get();
        // Disconnect from session so that the updates on updatedRaca are not directly saved in db
        em.detach(updatedRaca);
        updatedRaca
            .nome(UPDATED_NOME);
        RacaDTO racaDTO = racaMapper.toDto(updatedRaca);

        restRacaMockMvc.perform(put("/api/racas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racaDTO)))
            .andExpect(status().isOk());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingRaca() throws Exception {
        int databaseSizeBeforeUpdate = racaRepository.findAll().size();

        // Create the Raca
        RacaDTO racaDTO = racaMapper.toDto(raca);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacaMockMvc.perform(put("/api/racas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        int databaseSizeBeforeDelete = racaRepository.findAll().size();

        // Delete the raca
        restRacaMockMvc.perform(delete("/api/racas/{id}", raca.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
