package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnimalTipoDeAlteracao;
import br.com.ngzorro.repository.AnimalTipoDeAlteracaoRepository;
import br.com.ngzorro.service.AnimalTipoDeAlteracaoService;
import br.com.ngzorro.service.dto.AnimalTipoDeAlteracaoDTO;
import br.com.ngzorro.service.mapper.AnimalTipoDeAlteracaoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalTipoDeAlteracaoCriteria;
import br.com.ngzorro.service.AnimalTipoDeAlteracaoQueryService;

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
 * Integration tests for the {@link AnimalTipoDeAlteracaoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalTipoDeAlteracaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private AnimalTipoDeAlteracaoRepository animalTipoDeAlteracaoRepository;

    @Autowired
    private AnimalTipoDeAlteracaoMapper animalTipoDeAlteracaoMapper;

    @Autowired
    private AnimalTipoDeAlteracaoService animalTipoDeAlteracaoService;

    @Autowired
    private AnimalTipoDeAlteracaoQueryService animalTipoDeAlteracaoQueryService;

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

    private MockMvc restAnimalTipoDeAlteracaoMockMvc;

    private AnimalTipoDeAlteracao animalTipoDeAlteracao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalTipoDeAlteracaoResource animalTipoDeAlteracaoResource = new AnimalTipoDeAlteracaoResource(animalTipoDeAlteracaoService, animalTipoDeAlteracaoQueryService);
        this.restAnimalTipoDeAlteracaoMockMvc = MockMvcBuilders.standaloneSetup(animalTipoDeAlteracaoResource)
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
    public static AnimalTipoDeAlteracao createEntity(EntityManager em) {
        AnimalTipoDeAlteracao animalTipoDeAlteracao = new AnimalTipoDeAlteracao()
            .descricao(DEFAULT_DESCRICAO);
        return animalTipoDeAlteracao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalTipoDeAlteracao createUpdatedEntity(EntityManager em) {
        AnimalTipoDeAlteracao animalTipoDeAlteracao = new AnimalTipoDeAlteracao()
            .descricao(UPDATED_DESCRICAO);
        return animalTipoDeAlteracao;
    }

    @BeforeEach
    public void initTest() {
        animalTipoDeAlteracao = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalTipoDeAlteracao() throws Exception {
        int databaseSizeBeforeCreate = animalTipoDeAlteracaoRepository.findAll().size();

        // Create the AnimalTipoDeAlteracao
        AnimalTipoDeAlteracaoDTO animalTipoDeAlteracaoDTO = animalTipoDeAlteracaoMapper.toDto(animalTipoDeAlteracao);
        restAnimalTipoDeAlteracaoMockMvc.perform(post("/api/animal-tipo-de-alteracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalTipoDeAlteracaoDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalTipoDeAlteracao in the database
        List<AnimalTipoDeAlteracao> animalTipoDeAlteracaoList = animalTipoDeAlteracaoRepository.findAll();
        assertThat(animalTipoDeAlteracaoList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalTipoDeAlteracao testAnimalTipoDeAlteracao = animalTipoDeAlteracaoList.get(animalTipoDeAlteracaoList.size() - 1);
        assertThat(testAnimalTipoDeAlteracao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createAnimalTipoDeAlteracaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalTipoDeAlteracaoRepository.findAll().size();

        // Create the AnimalTipoDeAlteracao with an existing ID
        animalTipoDeAlteracao.setId(1L);
        AnimalTipoDeAlteracaoDTO animalTipoDeAlteracaoDTO = animalTipoDeAlteracaoMapper.toDto(animalTipoDeAlteracao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalTipoDeAlteracaoMockMvc.perform(post("/api/animal-tipo-de-alteracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalTipoDeAlteracaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalTipoDeAlteracao in the database
        List<AnimalTipoDeAlteracao> animalTipoDeAlteracaoList = animalTipoDeAlteracaoRepository.findAll();
        assertThat(animalTipoDeAlteracaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalTipoDeAlteracaos() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        // Get all the animalTipoDeAlteracaoList
        restAnimalTipoDeAlteracaoMockMvc.perform(get("/api/animal-tipo-de-alteracaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalTipoDeAlteracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getAnimalTipoDeAlteracao() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        // Get the animalTipoDeAlteracao
        restAnimalTipoDeAlteracaoMockMvc.perform(get("/api/animal-tipo-de-alteracaos/{id}", animalTipoDeAlteracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalTipoDeAlteracao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getAnimalTipoDeAlteracaosByIdFiltering() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        Long id = animalTipoDeAlteracao.getId();

        defaultAnimalTipoDeAlteracaoShouldBeFound("id.equals=" + id);
        defaultAnimalTipoDeAlteracaoShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalTipoDeAlteracaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalTipoDeAlteracaoShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalTipoDeAlteracaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalTipoDeAlteracaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalTipoDeAlteracaosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        // Get all the animalTipoDeAlteracaoList where descricao equals to DEFAULT_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the animalTipoDeAlteracaoList where descricao equals to UPDATED_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnimalTipoDeAlteracaosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        // Get all the animalTipoDeAlteracaoList where descricao not equals to DEFAULT_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the animalTipoDeAlteracaoList where descricao not equals to UPDATED_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnimalTipoDeAlteracaosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        // Get all the animalTipoDeAlteracaoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the animalTipoDeAlteracaoList where descricao equals to UPDATED_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnimalTipoDeAlteracaosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        // Get all the animalTipoDeAlteracaoList where descricao is not null
        defaultAnimalTipoDeAlteracaoShouldBeFound("descricao.specified=true");

        // Get all the animalTipoDeAlteracaoList where descricao is null
        defaultAnimalTipoDeAlteracaoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalTipoDeAlteracaosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        // Get all the animalTipoDeAlteracaoList where descricao contains DEFAULT_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the animalTipoDeAlteracaoList where descricao contains UPDATED_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnimalTipoDeAlteracaosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        // Get all the animalTipoDeAlteracaoList where descricao does not contain DEFAULT_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the animalTipoDeAlteracaoList where descricao does not contain UPDATED_DESCRICAO
        defaultAnimalTipoDeAlteracaoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalTipoDeAlteracaoShouldBeFound(String filter) throws Exception {
        restAnimalTipoDeAlteracaoMockMvc.perform(get("/api/animal-tipo-de-alteracaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalTipoDeAlteracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restAnimalTipoDeAlteracaoMockMvc.perform(get("/api/animal-tipo-de-alteracaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalTipoDeAlteracaoShouldNotBeFound(String filter) throws Exception {
        restAnimalTipoDeAlteracaoMockMvc.perform(get("/api/animal-tipo-de-alteracaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalTipoDeAlteracaoMockMvc.perform(get("/api/animal-tipo-de-alteracaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimalTipoDeAlteracao() throws Exception {
        // Get the animalTipoDeAlteracao
        restAnimalTipoDeAlteracaoMockMvc.perform(get("/api/animal-tipo-de-alteracaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalTipoDeAlteracao() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        int databaseSizeBeforeUpdate = animalTipoDeAlteracaoRepository.findAll().size();

        // Update the animalTipoDeAlteracao
        AnimalTipoDeAlteracao updatedAnimalTipoDeAlteracao = animalTipoDeAlteracaoRepository.findById(animalTipoDeAlteracao.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalTipoDeAlteracao are not directly saved in db
        em.detach(updatedAnimalTipoDeAlteracao);
        updatedAnimalTipoDeAlteracao
            .descricao(UPDATED_DESCRICAO);
        AnimalTipoDeAlteracaoDTO animalTipoDeAlteracaoDTO = animalTipoDeAlteracaoMapper.toDto(updatedAnimalTipoDeAlteracao);

        restAnimalTipoDeAlteracaoMockMvc.perform(put("/api/animal-tipo-de-alteracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalTipoDeAlteracaoDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalTipoDeAlteracao in the database
        List<AnimalTipoDeAlteracao> animalTipoDeAlteracaoList = animalTipoDeAlteracaoRepository.findAll();
        assertThat(animalTipoDeAlteracaoList).hasSize(databaseSizeBeforeUpdate);
        AnimalTipoDeAlteracao testAnimalTipoDeAlteracao = animalTipoDeAlteracaoList.get(animalTipoDeAlteracaoList.size() - 1);
        assertThat(testAnimalTipoDeAlteracao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalTipoDeAlteracao() throws Exception {
        int databaseSizeBeforeUpdate = animalTipoDeAlteracaoRepository.findAll().size();

        // Create the AnimalTipoDeAlteracao
        AnimalTipoDeAlteracaoDTO animalTipoDeAlteracaoDTO = animalTipoDeAlteracaoMapper.toDto(animalTipoDeAlteracao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalTipoDeAlteracaoMockMvc.perform(put("/api/animal-tipo-de-alteracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalTipoDeAlteracaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalTipoDeAlteracao in the database
        List<AnimalTipoDeAlteracao> animalTipoDeAlteracaoList = animalTipoDeAlteracaoRepository.findAll();
        assertThat(animalTipoDeAlteracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalTipoDeAlteracao() throws Exception {
        // Initialize the database
        animalTipoDeAlteracaoRepository.saveAndFlush(animalTipoDeAlteracao);

        int databaseSizeBeforeDelete = animalTipoDeAlteracaoRepository.findAll().size();

        // Delete the animalTipoDeAlteracao
        restAnimalTipoDeAlteracaoMockMvc.perform(delete("/api/animal-tipo-de-alteracaos/{id}", animalTipoDeAlteracao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalTipoDeAlteracao> animalTipoDeAlteracaoList = animalTipoDeAlteracaoRepository.findAll();
        assertThat(animalTipoDeAlteracaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
