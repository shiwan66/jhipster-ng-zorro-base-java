package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Atividade;
import br.com.ngzorro.domain.Atendimento;
import br.com.ngzorro.domain.ModeloAtividade;
import br.com.ngzorro.repository.AtividadeRepository;
import br.com.ngzorro.service.AtividadeService;
import br.com.ngzorro.service.dto.AtividadeDTO;
import br.com.ngzorro.service.mapper.AtividadeMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AtividadeCriteria;
import br.com.ngzorro.service.AtividadeQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static br.com.ngzorro.web.rest.TestUtil.sameInstant;
import static br.com.ngzorro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AtividadeResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AtividadeResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_TERMINO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TERMINO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TERMINO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REALIZADO = false;
    private static final Boolean UPDATED_REALIZADO = true;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private AtividadeMapper atividadeMapper;

    @Autowired
    private AtividadeService atividadeService;

    @Autowired
    private AtividadeQueryService atividadeQueryService;

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

    private MockMvc restAtividadeMockMvc;

    private Atividade atividade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AtividadeResource atividadeResource = new AtividadeResource(atividadeService, atividadeQueryService);
        this.restAtividadeMockMvc = MockMvcBuilders.standaloneSetup(atividadeResource)
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
    public static Atividade createEntity(EntityManager em) {
        Atividade atividade = new Atividade()
            .titulo(DEFAULT_TITULO)
            .inicio(DEFAULT_INICIO)
            .termino(DEFAULT_TERMINO)
            .observacao(DEFAULT_OBSERVACAO)
            .realizado(DEFAULT_REALIZADO);
        return atividade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atividade createUpdatedEntity(EntityManager em) {
        Atividade atividade = new Atividade()
            .titulo(UPDATED_TITULO)
            .inicio(UPDATED_INICIO)
            .termino(UPDATED_TERMINO)
            .observacao(UPDATED_OBSERVACAO)
            .realizado(UPDATED_REALIZADO);
        return atividade;
    }

    @BeforeEach
    public void initTest() {
        atividade = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtividade() throws Exception {
        int databaseSizeBeforeCreate = atividadeRepository.findAll().size();

        // Create the Atividade
        AtividadeDTO atividadeDTO = atividadeMapper.toDto(atividade);
        restAtividadeMockMvc.perform(post("/api/atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atividadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Atividade in the database
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeCreate + 1);
        Atividade testAtividade = atividadeList.get(atividadeList.size() - 1);
        assertThat(testAtividade.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testAtividade.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testAtividade.getTermino()).isEqualTo(DEFAULT_TERMINO);
        assertThat(testAtividade.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testAtividade.isRealizado()).isEqualTo(DEFAULT_REALIZADO);
    }

    @Test
    @Transactional
    public void createAtividadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atividadeRepository.findAll().size();

        // Create the Atividade with an existing ID
        atividade.setId(1L);
        AtividadeDTO atividadeDTO = atividadeMapper.toDto(atividade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtividadeMockMvc.perform(post("/api/atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atividadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Atividade in the database
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAtividades() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList
        restAtividadeMockMvc.perform(get("/api/atividades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividade.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(sameInstant(DEFAULT_INICIO))))
            .andExpect(jsonPath("$.[*].termino").value(hasItem(sameInstant(DEFAULT_TERMINO))))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].realizado").value(hasItem(DEFAULT_REALIZADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAtividade() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get the atividade
        restAtividadeMockMvc.perform(get("/api/atividades/{id}", atividade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(atividade.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.inicio").value(sameInstant(DEFAULT_INICIO)))
            .andExpect(jsonPath("$.termino").value(sameInstant(DEFAULT_TERMINO)))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.realizado").value(DEFAULT_REALIZADO.booleanValue()));
    }


    @Test
    @Transactional
    public void getAtividadesByIdFiltering() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        Long id = atividade.getId();

        defaultAtividadeShouldBeFound("id.equals=" + id);
        defaultAtividadeShouldNotBeFound("id.notEquals=" + id);

        defaultAtividadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAtividadeShouldNotBeFound("id.greaterThan=" + id);

        defaultAtividadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAtividadeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAtividadesByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where titulo equals to DEFAULT_TITULO
        defaultAtividadeShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the atividadeList where titulo equals to UPDATED_TITULO
        defaultAtividadeShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where titulo not equals to DEFAULT_TITULO
        defaultAtividadeShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the atividadeList where titulo not equals to UPDATED_TITULO
        defaultAtividadeShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultAtividadeShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the atividadeList where titulo equals to UPDATED_TITULO
        defaultAtividadeShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where titulo is not null
        defaultAtividadeShouldBeFound("titulo.specified=true");

        // Get all the atividadeList where titulo is null
        defaultAtividadeShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllAtividadesByTituloContainsSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where titulo contains DEFAULT_TITULO
        defaultAtividadeShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the atividadeList where titulo contains UPDATED_TITULO
        defaultAtividadeShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where titulo does not contain DEFAULT_TITULO
        defaultAtividadeShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the atividadeList where titulo does not contain UPDATED_TITULO
        defaultAtividadeShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllAtividadesByInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where inicio equals to DEFAULT_INICIO
        defaultAtividadeShouldBeFound("inicio.equals=" + DEFAULT_INICIO);

        // Get all the atividadeList where inicio equals to UPDATED_INICIO
        defaultAtividadeShouldNotBeFound("inicio.equals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where inicio not equals to DEFAULT_INICIO
        defaultAtividadeShouldNotBeFound("inicio.notEquals=" + DEFAULT_INICIO);

        // Get all the atividadeList where inicio not equals to UPDATED_INICIO
        defaultAtividadeShouldBeFound("inicio.notEquals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByInicioIsInShouldWork() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where inicio in DEFAULT_INICIO or UPDATED_INICIO
        defaultAtividadeShouldBeFound("inicio.in=" + DEFAULT_INICIO + "," + UPDATED_INICIO);

        // Get all the atividadeList where inicio equals to UPDATED_INICIO
        defaultAtividadeShouldNotBeFound("inicio.in=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where inicio is not null
        defaultAtividadeShouldBeFound("inicio.specified=true");

        // Get all the atividadeList where inicio is null
        defaultAtividadeShouldNotBeFound("inicio.specified=false");
    }

    @Test
    @Transactional
    public void getAllAtividadesByInicioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where inicio is greater than or equal to DEFAULT_INICIO
        defaultAtividadeShouldBeFound("inicio.greaterThanOrEqual=" + DEFAULT_INICIO);

        // Get all the atividadeList where inicio is greater than or equal to UPDATED_INICIO
        defaultAtividadeShouldNotBeFound("inicio.greaterThanOrEqual=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByInicioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where inicio is less than or equal to DEFAULT_INICIO
        defaultAtividadeShouldBeFound("inicio.lessThanOrEqual=" + DEFAULT_INICIO);

        // Get all the atividadeList where inicio is less than or equal to SMALLER_INICIO
        defaultAtividadeShouldNotBeFound("inicio.lessThanOrEqual=" + SMALLER_INICIO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByInicioIsLessThanSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where inicio is less than DEFAULT_INICIO
        defaultAtividadeShouldNotBeFound("inicio.lessThan=" + DEFAULT_INICIO);

        // Get all the atividadeList where inicio is less than UPDATED_INICIO
        defaultAtividadeShouldBeFound("inicio.lessThan=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByInicioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where inicio is greater than DEFAULT_INICIO
        defaultAtividadeShouldNotBeFound("inicio.greaterThan=" + DEFAULT_INICIO);

        // Get all the atividadeList where inicio is greater than SMALLER_INICIO
        defaultAtividadeShouldBeFound("inicio.greaterThan=" + SMALLER_INICIO);
    }


    @Test
    @Transactional
    public void getAllAtividadesByTerminoIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where termino equals to DEFAULT_TERMINO
        defaultAtividadeShouldBeFound("termino.equals=" + DEFAULT_TERMINO);

        // Get all the atividadeList where termino equals to UPDATED_TERMINO
        defaultAtividadeShouldNotBeFound("termino.equals=" + UPDATED_TERMINO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTerminoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where termino not equals to DEFAULT_TERMINO
        defaultAtividadeShouldNotBeFound("termino.notEquals=" + DEFAULT_TERMINO);

        // Get all the atividadeList where termino not equals to UPDATED_TERMINO
        defaultAtividadeShouldBeFound("termino.notEquals=" + UPDATED_TERMINO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTerminoIsInShouldWork() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where termino in DEFAULT_TERMINO or UPDATED_TERMINO
        defaultAtividadeShouldBeFound("termino.in=" + DEFAULT_TERMINO + "," + UPDATED_TERMINO);

        // Get all the atividadeList where termino equals to UPDATED_TERMINO
        defaultAtividadeShouldNotBeFound("termino.in=" + UPDATED_TERMINO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTerminoIsNullOrNotNull() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where termino is not null
        defaultAtividadeShouldBeFound("termino.specified=true");

        // Get all the atividadeList where termino is null
        defaultAtividadeShouldNotBeFound("termino.specified=false");
    }

    @Test
    @Transactional
    public void getAllAtividadesByTerminoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where termino is greater than or equal to DEFAULT_TERMINO
        defaultAtividadeShouldBeFound("termino.greaterThanOrEqual=" + DEFAULT_TERMINO);

        // Get all the atividadeList where termino is greater than or equal to UPDATED_TERMINO
        defaultAtividadeShouldNotBeFound("termino.greaterThanOrEqual=" + UPDATED_TERMINO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTerminoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where termino is less than or equal to DEFAULT_TERMINO
        defaultAtividadeShouldBeFound("termino.lessThanOrEqual=" + DEFAULT_TERMINO);

        // Get all the atividadeList where termino is less than or equal to SMALLER_TERMINO
        defaultAtividadeShouldNotBeFound("termino.lessThanOrEqual=" + SMALLER_TERMINO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTerminoIsLessThanSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where termino is less than DEFAULT_TERMINO
        defaultAtividadeShouldNotBeFound("termino.lessThan=" + DEFAULT_TERMINO);

        // Get all the atividadeList where termino is less than UPDATED_TERMINO
        defaultAtividadeShouldBeFound("termino.lessThan=" + UPDATED_TERMINO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByTerminoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where termino is greater than DEFAULT_TERMINO
        defaultAtividadeShouldNotBeFound("termino.greaterThan=" + DEFAULT_TERMINO);

        // Get all the atividadeList where termino is greater than SMALLER_TERMINO
        defaultAtividadeShouldBeFound("termino.greaterThan=" + SMALLER_TERMINO);
    }


    @Test
    @Transactional
    public void getAllAtividadesByObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where observacao equals to DEFAULT_OBSERVACAO
        defaultAtividadeShouldBeFound("observacao.equals=" + DEFAULT_OBSERVACAO);

        // Get all the atividadeList where observacao equals to UPDATED_OBSERVACAO
        defaultAtividadeShouldNotBeFound("observacao.equals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByObservacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where observacao not equals to DEFAULT_OBSERVACAO
        defaultAtividadeShouldNotBeFound("observacao.notEquals=" + DEFAULT_OBSERVACAO);

        // Get all the atividadeList where observacao not equals to UPDATED_OBSERVACAO
        defaultAtividadeShouldBeFound("observacao.notEquals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByObservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where observacao in DEFAULT_OBSERVACAO or UPDATED_OBSERVACAO
        defaultAtividadeShouldBeFound("observacao.in=" + DEFAULT_OBSERVACAO + "," + UPDATED_OBSERVACAO);

        // Get all the atividadeList where observacao equals to UPDATED_OBSERVACAO
        defaultAtividadeShouldNotBeFound("observacao.in=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByObservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where observacao is not null
        defaultAtividadeShouldBeFound("observacao.specified=true");

        // Get all the atividadeList where observacao is null
        defaultAtividadeShouldNotBeFound("observacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAtividadesByObservacaoContainsSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where observacao contains DEFAULT_OBSERVACAO
        defaultAtividadeShouldBeFound("observacao.contains=" + DEFAULT_OBSERVACAO);

        // Get all the atividadeList where observacao contains UPDATED_OBSERVACAO
        defaultAtividadeShouldNotBeFound("observacao.contains=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByObservacaoNotContainsSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where observacao does not contain DEFAULT_OBSERVACAO
        defaultAtividadeShouldNotBeFound("observacao.doesNotContain=" + DEFAULT_OBSERVACAO);

        // Get all the atividadeList where observacao does not contain UPDATED_OBSERVACAO
        defaultAtividadeShouldBeFound("observacao.doesNotContain=" + UPDATED_OBSERVACAO);
    }


    @Test
    @Transactional
    public void getAllAtividadesByRealizadoIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where realizado equals to DEFAULT_REALIZADO
        defaultAtividadeShouldBeFound("realizado.equals=" + DEFAULT_REALIZADO);

        // Get all the atividadeList where realizado equals to UPDATED_REALIZADO
        defaultAtividadeShouldNotBeFound("realizado.equals=" + UPDATED_REALIZADO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByRealizadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where realizado not equals to DEFAULT_REALIZADO
        defaultAtividadeShouldNotBeFound("realizado.notEquals=" + DEFAULT_REALIZADO);

        // Get all the atividadeList where realizado not equals to UPDATED_REALIZADO
        defaultAtividadeShouldBeFound("realizado.notEquals=" + UPDATED_REALIZADO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByRealizadoIsInShouldWork() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where realizado in DEFAULT_REALIZADO or UPDATED_REALIZADO
        defaultAtividadeShouldBeFound("realizado.in=" + DEFAULT_REALIZADO + "," + UPDATED_REALIZADO);

        // Get all the atividadeList where realizado equals to UPDATED_REALIZADO
        defaultAtividadeShouldNotBeFound("realizado.in=" + UPDATED_REALIZADO);
    }

    @Test
    @Transactional
    public void getAllAtividadesByRealizadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList where realizado is not null
        defaultAtividadeShouldBeFound("realizado.specified=true");

        // Get all the atividadeList where realizado is null
        defaultAtividadeShouldNotBeFound("realizado.specified=false");
    }

    @Test
    @Transactional
    public void getAllAtividadesByAtendimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);
        Atendimento atendimento = AtendimentoResourceIT.createEntity(em);
        em.persist(atendimento);
        em.flush();
        atividade.setAtendimento(atendimento);
        atividadeRepository.saveAndFlush(atividade);
        Long atendimentoId = atendimento.getId();

        // Get all the atividadeList where atendimento equals to atendimentoId
        defaultAtividadeShouldBeFound("atendimentoId.equals=" + atendimentoId);

        // Get all the atividadeList where atendimento equals to atendimentoId + 1
        defaultAtividadeShouldNotBeFound("atendimentoId.equals=" + (atendimentoId + 1));
    }


    @Test
    @Transactional
    public void getAllAtividadesByModeloAtividadeIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);
        ModeloAtividade modeloAtividade = ModeloAtividadeResourceIT.createEntity(em);
        em.persist(modeloAtividade);
        em.flush();
        atividade.addModeloAtividade(modeloAtividade);
        atividadeRepository.saveAndFlush(atividade);
        Long modeloAtividadeId = modeloAtividade.getId();

        // Get all the atividadeList where modeloAtividade equals to modeloAtividadeId
        defaultAtividadeShouldBeFound("modeloAtividadeId.equals=" + modeloAtividadeId);

        // Get all the atividadeList where modeloAtividade equals to modeloAtividadeId + 1
        defaultAtividadeShouldNotBeFound("modeloAtividadeId.equals=" + (modeloAtividadeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAtividadeShouldBeFound(String filter) throws Exception {
        restAtividadeMockMvc.perform(get("/api/atividades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividade.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(sameInstant(DEFAULT_INICIO))))
            .andExpect(jsonPath("$.[*].termino").value(hasItem(sameInstant(DEFAULT_TERMINO))))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].realizado").value(hasItem(DEFAULT_REALIZADO.booleanValue())));

        // Check, that the count call also returns 1
        restAtividadeMockMvc.perform(get("/api/atividades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAtividadeShouldNotBeFound(String filter) throws Exception {
        restAtividadeMockMvc.perform(get("/api/atividades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAtividadeMockMvc.perform(get("/api/atividades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAtividade() throws Exception {
        // Get the atividade
        restAtividadeMockMvc.perform(get("/api/atividades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtividade() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        int databaseSizeBeforeUpdate = atividadeRepository.findAll().size();

        // Update the atividade
        Atividade updatedAtividade = atividadeRepository.findById(atividade.getId()).get();
        // Disconnect from session so that the updates on updatedAtividade are not directly saved in db
        em.detach(updatedAtividade);
        updatedAtividade
            .titulo(UPDATED_TITULO)
            .inicio(UPDATED_INICIO)
            .termino(UPDATED_TERMINO)
            .observacao(UPDATED_OBSERVACAO)
            .realizado(UPDATED_REALIZADO);
        AtividadeDTO atividadeDTO = atividadeMapper.toDto(updatedAtividade);

        restAtividadeMockMvc.perform(put("/api/atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atividadeDTO)))
            .andExpect(status().isOk());

        // Validate the Atividade in the database
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeUpdate);
        Atividade testAtividade = atividadeList.get(atividadeList.size() - 1);
        assertThat(testAtividade.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testAtividade.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testAtividade.getTermino()).isEqualTo(UPDATED_TERMINO);
        assertThat(testAtividade.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testAtividade.isRealizado()).isEqualTo(UPDATED_REALIZADO);
    }

    @Test
    @Transactional
    public void updateNonExistingAtividade() throws Exception {
        int databaseSizeBeforeUpdate = atividadeRepository.findAll().size();

        // Create the Atividade
        AtividadeDTO atividadeDTO = atividadeMapper.toDto(atividade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtividadeMockMvc.perform(put("/api/atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atividadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Atividade in the database
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAtividade() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        int databaseSizeBeforeDelete = atividadeRepository.findAll().size();

        // Delete the atividade
        restAtividadeMockMvc.perform(delete("/api/atividades/{id}", atividade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
