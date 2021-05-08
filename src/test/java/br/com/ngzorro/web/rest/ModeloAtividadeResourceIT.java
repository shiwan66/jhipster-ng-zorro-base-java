package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.ModeloAtividade;
import br.com.ngzorro.domain.Atividade;
import br.com.ngzorro.repository.ModeloAtividadeRepository;
import br.com.ngzorro.service.ModeloAtividadeService;
import br.com.ngzorro.service.dto.ModeloAtividadeDTO;
import br.com.ngzorro.service.mapper.ModeloAtividadeMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.ModeloAtividadeCriteria;
import br.com.ngzorro.service.ModeloAtividadeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static br.com.ngzorro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ModeloAtividadeResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class ModeloAtividadeResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ModeloAtividadeRepository modeloAtividadeRepository;

    @Mock
    private ModeloAtividadeRepository modeloAtividadeRepositoryMock;

    @Autowired
    private ModeloAtividadeMapper modeloAtividadeMapper;

    @Mock
    private ModeloAtividadeService modeloAtividadeServiceMock;

    @Autowired
    private ModeloAtividadeService modeloAtividadeService;

    @Autowired
    private ModeloAtividadeQueryService modeloAtividadeQueryService;

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

    private MockMvc restModeloAtividadeMockMvc;

    private ModeloAtividade modeloAtividade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModeloAtividadeResource modeloAtividadeResource = new ModeloAtividadeResource(modeloAtividadeService, modeloAtividadeQueryService);
        this.restModeloAtividadeMockMvc = MockMvcBuilders.standaloneSetup(modeloAtividadeResource)
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
    public static ModeloAtividade createEntity(EntityManager em) {
        ModeloAtividade modeloAtividade = new ModeloAtividade()
            .descricao(DEFAULT_DESCRICAO);
        return modeloAtividade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModeloAtividade createUpdatedEntity(EntityManager em) {
        ModeloAtividade modeloAtividade = new ModeloAtividade()
            .descricao(UPDATED_DESCRICAO);
        return modeloAtividade;
    }

    @BeforeEach
    public void initTest() {
        modeloAtividade = createEntity(em);
    }

    @Test
    @Transactional
    public void createModeloAtividade() throws Exception {
        int databaseSizeBeforeCreate = modeloAtividadeRepository.findAll().size();

        // Create the ModeloAtividade
        ModeloAtividadeDTO modeloAtividadeDTO = modeloAtividadeMapper.toDto(modeloAtividade);
        restModeloAtividadeMockMvc.perform(post("/api/modelo-atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloAtividadeDTO)))
            .andExpect(status().isCreated());

        // Validate the ModeloAtividade in the database
        List<ModeloAtividade> modeloAtividadeList = modeloAtividadeRepository.findAll();
        assertThat(modeloAtividadeList).hasSize(databaseSizeBeforeCreate + 1);
        ModeloAtividade testModeloAtividade = modeloAtividadeList.get(modeloAtividadeList.size() - 1);
        assertThat(testModeloAtividade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createModeloAtividadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modeloAtividadeRepository.findAll().size();

        // Create the ModeloAtividade with an existing ID
        modeloAtividade.setId(1L);
        ModeloAtividadeDTO modeloAtividadeDTO = modeloAtividadeMapper.toDto(modeloAtividade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeloAtividadeMockMvc.perform(post("/api/modelo-atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloAtividadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModeloAtividade in the database
        List<ModeloAtividade> modeloAtividadeList = modeloAtividadeRepository.findAll();
        assertThat(modeloAtividadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllModeloAtividades() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        // Get all the modeloAtividadeList
        restModeloAtividadeMockMvc.perform(get("/api/modelo-atividades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeloAtividade.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllModeloAtividadesWithEagerRelationshipsIsEnabled() throws Exception {
        ModeloAtividadeResource modeloAtividadeResource = new ModeloAtividadeResource(modeloAtividadeServiceMock, modeloAtividadeQueryService);
        when(modeloAtividadeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restModeloAtividadeMockMvc = MockMvcBuilders.standaloneSetup(modeloAtividadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restModeloAtividadeMockMvc.perform(get("/api/modelo-atividades?eagerload=true"))
        .andExpect(status().isOk());

        verify(modeloAtividadeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllModeloAtividadesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ModeloAtividadeResource modeloAtividadeResource = new ModeloAtividadeResource(modeloAtividadeServiceMock, modeloAtividadeQueryService);
            when(modeloAtividadeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restModeloAtividadeMockMvc = MockMvcBuilders.standaloneSetup(modeloAtividadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restModeloAtividadeMockMvc.perform(get("/api/modelo-atividades?eagerload=true"))
        .andExpect(status().isOk());

            verify(modeloAtividadeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getModeloAtividade() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        // Get the modeloAtividade
        restModeloAtividadeMockMvc.perform(get("/api/modelo-atividades/{id}", modeloAtividade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modeloAtividade.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getModeloAtividadesByIdFiltering() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        Long id = modeloAtividade.getId();

        defaultModeloAtividadeShouldBeFound("id.equals=" + id);
        defaultModeloAtividadeShouldNotBeFound("id.notEquals=" + id);

        defaultModeloAtividadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultModeloAtividadeShouldNotBeFound("id.greaterThan=" + id);

        defaultModeloAtividadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultModeloAtividadeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllModeloAtividadesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        // Get all the modeloAtividadeList where descricao equals to DEFAULT_DESCRICAO
        defaultModeloAtividadeShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the modeloAtividadeList where descricao equals to UPDATED_DESCRICAO
        defaultModeloAtividadeShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllModeloAtividadesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        // Get all the modeloAtividadeList where descricao not equals to DEFAULT_DESCRICAO
        defaultModeloAtividadeShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the modeloAtividadeList where descricao not equals to UPDATED_DESCRICAO
        defaultModeloAtividadeShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllModeloAtividadesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        // Get all the modeloAtividadeList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultModeloAtividadeShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the modeloAtividadeList where descricao equals to UPDATED_DESCRICAO
        defaultModeloAtividadeShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllModeloAtividadesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        // Get all the modeloAtividadeList where descricao is not null
        defaultModeloAtividadeShouldBeFound("descricao.specified=true");

        // Get all the modeloAtividadeList where descricao is null
        defaultModeloAtividadeShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllModeloAtividadesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        // Get all the modeloAtividadeList where descricao contains DEFAULT_DESCRICAO
        defaultModeloAtividadeShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the modeloAtividadeList where descricao contains UPDATED_DESCRICAO
        defaultModeloAtividadeShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllModeloAtividadesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        // Get all the modeloAtividadeList where descricao does not contain DEFAULT_DESCRICAO
        defaultModeloAtividadeShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the modeloAtividadeList where descricao does not contain UPDATED_DESCRICAO
        defaultModeloAtividadeShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllModeloAtividadesByAtividadeIsEqualToSomething() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);
        Atividade atividade = AtividadeResourceIT.createEntity(em);
        em.persist(atividade);
        em.flush();
        modeloAtividade.addAtividade(atividade);
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);
        Long atividadeId = atividade.getId();

        // Get all the modeloAtividadeList where atividade equals to atividadeId
        defaultModeloAtividadeShouldBeFound("atividadeId.equals=" + atividadeId);

        // Get all the modeloAtividadeList where atividade equals to atividadeId + 1
        defaultModeloAtividadeShouldNotBeFound("atividadeId.equals=" + (atividadeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultModeloAtividadeShouldBeFound(String filter) throws Exception {
        restModeloAtividadeMockMvc.perform(get("/api/modelo-atividades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeloAtividade.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restModeloAtividadeMockMvc.perform(get("/api/modelo-atividades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultModeloAtividadeShouldNotBeFound(String filter) throws Exception {
        restModeloAtividadeMockMvc.perform(get("/api/modelo-atividades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restModeloAtividadeMockMvc.perform(get("/api/modelo-atividades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingModeloAtividade() throws Exception {
        // Get the modeloAtividade
        restModeloAtividadeMockMvc.perform(get("/api/modelo-atividades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModeloAtividade() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        int databaseSizeBeforeUpdate = modeloAtividadeRepository.findAll().size();

        // Update the modeloAtividade
        ModeloAtividade updatedModeloAtividade = modeloAtividadeRepository.findById(modeloAtividade.getId()).get();
        // Disconnect from session so that the updates on updatedModeloAtividade are not directly saved in db
        em.detach(updatedModeloAtividade);
        updatedModeloAtividade
            .descricao(UPDATED_DESCRICAO);
        ModeloAtividadeDTO modeloAtividadeDTO = modeloAtividadeMapper.toDto(updatedModeloAtividade);

        restModeloAtividadeMockMvc.perform(put("/api/modelo-atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloAtividadeDTO)))
            .andExpect(status().isOk());

        // Validate the ModeloAtividade in the database
        List<ModeloAtividade> modeloAtividadeList = modeloAtividadeRepository.findAll();
        assertThat(modeloAtividadeList).hasSize(databaseSizeBeforeUpdate);
        ModeloAtividade testModeloAtividade = modeloAtividadeList.get(modeloAtividadeList.size() - 1);
        assertThat(testModeloAtividade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingModeloAtividade() throws Exception {
        int databaseSizeBeforeUpdate = modeloAtividadeRepository.findAll().size();

        // Create the ModeloAtividade
        ModeloAtividadeDTO modeloAtividadeDTO = modeloAtividadeMapper.toDto(modeloAtividade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeloAtividadeMockMvc.perform(put("/api/modelo-atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloAtividadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModeloAtividade in the database
        List<ModeloAtividade> modeloAtividadeList = modeloAtividadeRepository.findAll();
        assertThat(modeloAtividadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModeloAtividade() throws Exception {
        // Initialize the database
        modeloAtividadeRepository.saveAndFlush(modeloAtividade);

        int databaseSizeBeforeDelete = modeloAtividadeRepository.findAll().size();

        // Delete the modeloAtividade
        restModeloAtividadeMockMvc.perform(delete("/api/modelo-atividades/{id}", modeloAtividade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModeloAtividade> modeloAtividadeList = modeloAtividadeRepository.findAll();
        assertThat(modeloAtividadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
