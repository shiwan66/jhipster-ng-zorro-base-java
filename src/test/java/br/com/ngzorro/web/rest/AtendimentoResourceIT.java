package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Atendimento;
import br.com.ngzorro.domain.Atividade;
import br.com.ngzorro.domain.Venda;
import br.com.ngzorro.domain.AnexoAtendimento;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.repository.AtendimentoRepository;
import br.com.ngzorro.service.AtendimentoService;
import br.com.ngzorro.service.dto.AtendimentoDTO;
import br.com.ngzorro.service.mapper.AtendimentoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AtendimentoCriteria;
import br.com.ngzorro.service.AtendimentoQueryService;

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

import br.com.ngzorro.domain.enumeration.AtendimentoSituacao;
/**
 * Integration tests for the {@link AtendimentoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AtendimentoResourceIT {

    private static final AtendimentoSituacao DEFAULT_SITUACAO = AtendimentoSituacao.EM_ANDAMENTO;
    private static final AtendimentoSituacao UPDATED_SITUACAO = AtendimentoSituacao.CONCLUIDO;

    private static final ZonedDateTime DEFAULT_DATA_DE_CHEGADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_DE_CHEGADA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_DE_CHEGADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATA_DE_SAIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_DE_SAIDA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_DE_SAIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private AtendimentoMapper atendimentoMapper;

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private AtendimentoQueryService atendimentoQueryService;

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

    private MockMvc restAtendimentoMockMvc;

    private Atendimento atendimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AtendimentoResource atendimentoResource = new AtendimentoResource(atendimentoService, atendimentoQueryService);
        this.restAtendimentoMockMvc = MockMvcBuilders.standaloneSetup(atendimentoResource)
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
    public static Atendimento createEntity(EntityManager em) {
        Atendimento atendimento = new Atendimento()
            .situacao(DEFAULT_SITUACAO)
            .dataDeChegada(DEFAULT_DATA_DE_CHEGADA)
            .dataDeSaida(DEFAULT_DATA_DE_SAIDA)
            .observacao(DEFAULT_OBSERVACAO);
        return atendimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atendimento createUpdatedEntity(EntityManager em) {
        Atendimento atendimento = new Atendimento()
            .situacao(UPDATED_SITUACAO)
            .dataDeChegada(UPDATED_DATA_DE_CHEGADA)
            .dataDeSaida(UPDATED_DATA_DE_SAIDA)
            .observacao(UPDATED_OBSERVACAO);
        return atendimento;
    }

    @BeforeEach
    public void initTest() {
        atendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtendimento() throws Exception {
        int databaseSizeBeforeCreate = atendimentoRepository.findAll().size();

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);
        restAtendimentoMockMvc.perform(post("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testAtendimento.getDataDeChegada()).isEqualTo(DEFAULT_DATA_DE_CHEGADA);
        assertThat(testAtendimento.getDataDeSaida()).isEqualTo(DEFAULT_DATA_DE_SAIDA);
        assertThat(testAtendimento.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    public void createAtendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atendimentoRepository.findAll().size();

        // Create the Atendimento with an existing ID
        atendimento.setId(1L);
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtendimentoMockMvc.perform(post("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAtendimentos() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList
        restAtendimentoMockMvc.perform(get("/api/atendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].dataDeChegada").value(hasItem(sameInstant(DEFAULT_DATA_DE_CHEGADA))))
            .andExpect(jsonPath("$.[*].dataDeSaida").value(hasItem(sameInstant(DEFAULT_DATA_DE_SAIDA))))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }
    
    @Test
    @Transactional
    public void getAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get the atendimento
        restAtendimentoMockMvc.perform(get("/api/atendimentos/{id}", atendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(atendimento.getId().intValue()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.dataDeChegada").value(sameInstant(DEFAULT_DATA_DE_CHEGADA)))
            .andExpect(jsonPath("$.dataDeSaida").value(sameInstant(DEFAULT_DATA_DE_SAIDA)))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }


    @Test
    @Transactional
    public void getAtendimentosByIdFiltering() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        Long id = atendimento.getId();

        defaultAtendimentoShouldBeFound("id.equals=" + id);
        defaultAtendimentoShouldNotBeFound("id.notEquals=" + id);

        defaultAtendimentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAtendimentoShouldNotBeFound("id.greaterThan=" + id);

        defaultAtendimentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAtendimentoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAtendimentosBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where situacao equals to DEFAULT_SITUACAO
        defaultAtendimentoShouldBeFound("situacao.equals=" + DEFAULT_SITUACAO);

        // Get all the atendimentoList where situacao equals to UPDATED_SITUACAO
        defaultAtendimentoShouldNotBeFound("situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    public void getAllAtendimentosBySituacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where situacao not equals to DEFAULT_SITUACAO
        defaultAtendimentoShouldNotBeFound("situacao.notEquals=" + DEFAULT_SITUACAO);

        // Get all the atendimentoList where situacao not equals to UPDATED_SITUACAO
        defaultAtendimentoShouldBeFound("situacao.notEquals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    public void getAllAtendimentosBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where situacao in DEFAULT_SITUACAO or UPDATED_SITUACAO
        defaultAtendimentoShouldBeFound("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO);

        // Get all the atendimentoList where situacao equals to UPDATED_SITUACAO
        defaultAtendimentoShouldNotBeFound("situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    public void getAllAtendimentosBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where situacao is not null
        defaultAtendimentoShouldBeFound("situacao.specified=true");

        // Get all the atendimentoList where situacao is null
        defaultAtendimentoShouldNotBeFound("situacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeChegadaIsEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeChegada equals to DEFAULT_DATA_DE_CHEGADA
        defaultAtendimentoShouldBeFound("dataDeChegada.equals=" + DEFAULT_DATA_DE_CHEGADA);

        // Get all the atendimentoList where dataDeChegada equals to UPDATED_DATA_DE_CHEGADA
        defaultAtendimentoShouldNotBeFound("dataDeChegada.equals=" + UPDATED_DATA_DE_CHEGADA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeChegadaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeChegada not equals to DEFAULT_DATA_DE_CHEGADA
        defaultAtendimentoShouldNotBeFound("dataDeChegada.notEquals=" + DEFAULT_DATA_DE_CHEGADA);

        // Get all the atendimentoList where dataDeChegada not equals to UPDATED_DATA_DE_CHEGADA
        defaultAtendimentoShouldBeFound("dataDeChegada.notEquals=" + UPDATED_DATA_DE_CHEGADA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeChegadaIsInShouldWork() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeChegada in DEFAULT_DATA_DE_CHEGADA or UPDATED_DATA_DE_CHEGADA
        defaultAtendimentoShouldBeFound("dataDeChegada.in=" + DEFAULT_DATA_DE_CHEGADA + "," + UPDATED_DATA_DE_CHEGADA);

        // Get all the atendimentoList where dataDeChegada equals to UPDATED_DATA_DE_CHEGADA
        defaultAtendimentoShouldNotBeFound("dataDeChegada.in=" + UPDATED_DATA_DE_CHEGADA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeChegadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeChegada is not null
        defaultAtendimentoShouldBeFound("dataDeChegada.specified=true");

        // Get all the atendimentoList where dataDeChegada is null
        defaultAtendimentoShouldNotBeFound("dataDeChegada.specified=false");
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeChegadaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeChegada is greater than or equal to DEFAULT_DATA_DE_CHEGADA
        defaultAtendimentoShouldBeFound("dataDeChegada.greaterThanOrEqual=" + DEFAULT_DATA_DE_CHEGADA);

        // Get all the atendimentoList where dataDeChegada is greater than or equal to UPDATED_DATA_DE_CHEGADA
        defaultAtendimentoShouldNotBeFound("dataDeChegada.greaterThanOrEqual=" + UPDATED_DATA_DE_CHEGADA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeChegadaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeChegada is less than or equal to DEFAULT_DATA_DE_CHEGADA
        defaultAtendimentoShouldBeFound("dataDeChegada.lessThanOrEqual=" + DEFAULT_DATA_DE_CHEGADA);

        // Get all the atendimentoList where dataDeChegada is less than or equal to SMALLER_DATA_DE_CHEGADA
        defaultAtendimentoShouldNotBeFound("dataDeChegada.lessThanOrEqual=" + SMALLER_DATA_DE_CHEGADA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeChegadaIsLessThanSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeChegada is less than DEFAULT_DATA_DE_CHEGADA
        defaultAtendimentoShouldNotBeFound("dataDeChegada.lessThan=" + DEFAULT_DATA_DE_CHEGADA);

        // Get all the atendimentoList where dataDeChegada is less than UPDATED_DATA_DE_CHEGADA
        defaultAtendimentoShouldBeFound("dataDeChegada.lessThan=" + UPDATED_DATA_DE_CHEGADA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeChegadaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeChegada is greater than DEFAULT_DATA_DE_CHEGADA
        defaultAtendimentoShouldNotBeFound("dataDeChegada.greaterThan=" + DEFAULT_DATA_DE_CHEGADA);

        // Get all the atendimentoList where dataDeChegada is greater than SMALLER_DATA_DE_CHEGADA
        defaultAtendimentoShouldBeFound("dataDeChegada.greaterThan=" + SMALLER_DATA_DE_CHEGADA);
    }


    @Test
    @Transactional
    public void getAllAtendimentosByDataDeSaidaIsEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeSaida equals to DEFAULT_DATA_DE_SAIDA
        defaultAtendimentoShouldBeFound("dataDeSaida.equals=" + DEFAULT_DATA_DE_SAIDA);

        // Get all the atendimentoList where dataDeSaida equals to UPDATED_DATA_DE_SAIDA
        defaultAtendimentoShouldNotBeFound("dataDeSaida.equals=" + UPDATED_DATA_DE_SAIDA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeSaidaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeSaida not equals to DEFAULT_DATA_DE_SAIDA
        defaultAtendimentoShouldNotBeFound("dataDeSaida.notEquals=" + DEFAULT_DATA_DE_SAIDA);

        // Get all the atendimentoList where dataDeSaida not equals to UPDATED_DATA_DE_SAIDA
        defaultAtendimentoShouldBeFound("dataDeSaida.notEquals=" + UPDATED_DATA_DE_SAIDA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeSaidaIsInShouldWork() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeSaida in DEFAULT_DATA_DE_SAIDA or UPDATED_DATA_DE_SAIDA
        defaultAtendimentoShouldBeFound("dataDeSaida.in=" + DEFAULT_DATA_DE_SAIDA + "," + UPDATED_DATA_DE_SAIDA);

        // Get all the atendimentoList where dataDeSaida equals to UPDATED_DATA_DE_SAIDA
        defaultAtendimentoShouldNotBeFound("dataDeSaida.in=" + UPDATED_DATA_DE_SAIDA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeSaidaIsNullOrNotNull() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeSaida is not null
        defaultAtendimentoShouldBeFound("dataDeSaida.specified=true");

        // Get all the atendimentoList where dataDeSaida is null
        defaultAtendimentoShouldNotBeFound("dataDeSaida.specified=false");
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeSaidaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeSaida is greater than or equal to DEFAULT_DATA_DE_SAIDA
        defaultAtendimentoShouldBeFound("dataDeSaida.greaterThanOrEqual=" + DEFAULT_DATA_DE_SAIDA);

        // Get all the atendimentoList where dataDeSaida is greater than or equal to UPDATED_DATA_DE_SAIDA
        defaultAtendimentoShouldNotBeFound("dataDeSaida.greaterThanOrEqual=" + UPDATED_DATA_DE_SAIDA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeSaidaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeSaida is less than or equal to DEFAULT_DATA_DE_SAIDA
        defaultAtendimentoShouldBeFound("dataDeSaida.lessThanOrEqual=" + DEFAULT_DATA_DE_SAIDA);

        // Get all the atendimentoList where dataDeSaida is less than or equal to SMALLER_DATA_DE_SAIDA
        defaultAtendimentoShouldNotBeFound("dataDeSaida.lessThanOrEqual=" + SMALLER_DATA_DE_SAIDA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeSaidaIsLessThanSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeSaida is less than DEFAULT_DATA_DE_SAIDA
        defaultAtendimentoShouldNotBeFound("dataDeSaida.lessThan=" + DEFAULT_DATA_DE_SAIDA);

        // Get all the atendimentoList where dataDeSaida is less than UPDATED_DATA_DE_SAIDA
        defaultAtendimentoShouldBeFound("dataDeSaida.lessThan=" + UPDATED_DATA_DE_SAIDA);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByDataDeSaidaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where dataDeSaida is greater than DEFAULT_DATA_DE_SAIDA
        defaultAtendimentoShouldNotBeFound("dataDeSaida.greaterThan=" + DEFAULT_DATA_DE_SAIDA);

        // Get all the atendimentoList where dataDeSaida is greater than SMALLER_DATA_DE_SAIDA
        defaultAtendimentoShouldBeFound("dataDeSaida.greaterThan=" + SMALLER_DATA_DE_SAIDA);
    }


    @Test
    @Transactional
    public void getAllAtendimentosByObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where observacao equals to DEFAULT_OBSERVACAO
        defaultAtendimentoShouldBeFound("observacao.equals=" + DEFAULT_OBSERVACAO);

        // Get all the atendimentoList where observacao equals to UPDATED_OBSERVACAO
        defaultAtendimentoShouldNotBeFound("observacao.equals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByObservacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where observacao not equals to DEFAULT_OBSERVACAO
        defaultAtendimentoShouldNotBeFound("observacao.notEquals=" + DEFAULT_OBSERVACAO);

        // Get all the atendimentoList where observacao not equals to UPDATED_OBSERVACAO
        defaultAtendimentoShouldBeFound("observacao.notEquals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByObservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where observacao in DEFAULT_OBSERVACAO or UPDATED_OBSERVACAO
        defaultAtendimentoShouldBeFound("observacao.in=" + DEFAULT_OBSERVACAO + "," + UPDATED_OBSERVACAO);

        // Get all the atendimentoList where observacao equals to UPDATED_OBSERVACAO
        defaultAtendimentoShouldNotBeFound("observacao.in=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByObservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where observacao is not null
        defaultAtendimentoShouldBeFound("observacao.specified=true");

        // Get all the atendimentoList where observacao is null
        defaultAtendimentoShouldNotBeFound("observacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAtendimentosByObservacaoContainsSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where observacao contains DEFAULT_OBSERVACAO
        defaultAtendimentoShouldBeFound("observacao.contains=" + DEFAULT_OBSERVACAO);

        // Get all the atendimentoList where observacao contains UPDATED_OBSERVACAO
        defaultAtendimentoShouldNotBeFound("observacao.contains=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAtendimentosByObservacaoNotContainsSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList where observacao does not contain DEFAULT_OBSERVACAO
        defaultAtendimentoShouldNotBeFound("observacao.doesNotContain=" + DEFAULT_OBSERVACAO);

        // Get all the atendimentoList where observacao does not contain UPDATED_OBSERVACAO
        defaultAtendimentoShouldBeFound("observacao.doesNotContain=" + UPDATED_OBSERVACAO);
    }


    @Test
    @Transactional
    public void getAllAtendimentosByAtividadeIsEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);
        Atividade atividade = AtividadeResourceIT.createEntity(em);
        em.persist(atividade);
        em.flush();
        atendimento.addAtividade(atividade);
        atendimentoRepository.saveAndFlush(atendimento);
        Long atividadeId = atividade.getId();

        // Get all the atendimentoList where atividade equals to atividadeId
        defaultAtendimentoShouldBeFound("atividadeId.equals=" + atividadeId);

        // Get all the atendimentoList where atividade equals to atividadeId + 1
        defaultAtendimentoShouldNotBeFound("atividadeId.equals=" + (atividadeId + 1));
    }


    @Test
    @Transactional
    public void getAllAtendimentosByVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);
        Venda venda = VendaResourceIT.createEntity(em);
        em.persist(venda);
        em.flush();
        atendimento.addVenda(venda);
        atendimentoRepository.saveAndFlush(atendimento);
        Long vendaId = venda.getId();

        // Get all the atendimentoList where venda equals to vendaId
        defaultAtendimentoShouldBeFound("vendaId.equals=" + vendaId);

        // Get all the atendimentoList where venda equals to vendaId + 1
        defaultAtendimentoShouldNotBeFound("vendaId.equals=" + (vendaId + 1));
    }


    @Test
    @Transactional
    public void getAllAtendimentosByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);
        AnexoAtendimento anexo = AnexoAtendimentoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        atendimento.addAnexo(anexo);
        atendimentoRepository.saveAndFlush(atendimento);
        Long anexoId = anexo.getId();

        // Get all the atendimentoList where anexo equals to anexoId
        defaultAtendimentoShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the atendimentoList where anexo equals to anexoId + 1
        defaultAtendimentoShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllAtendimentosByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);
        Animal animal = AnimalResourceIT.createEntity(em);
        em.persist(animal);
        em.flush();
        atendimento.setAnimal(animal);
        atendimentoRepository.saveAndFlush(atendimento);
        Long animalId = animal.getId();

        // Get all the atendimentoList where animal equals to animalId
        defaultAtendimentoShouldBeFound("animalId.equals=" + animalId);

        // Get all the atendimentoList where animal equals to animalId + 1
        defaultAtendimentoShouldNotBeFound("animalId.equals=" + (animalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAtendimentoShouldBeFound(String filter) throws Exception {
        restAtendimentoMockMvc.perform(get("/api/atendimentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].dataDeChegada").value(hasItem(sameInstant(DEFAULT_DATA_DE_CHEGADA))))
            .andExpect(jsonPath("$.[*].dataDeSaida").value(hasItem(sameInstant(DEFAULT_DATA_DE_SAIDA))))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));

        // Check, that the count call also returns 1
        restAtendimentoMockMvc.perform(get("/api/atendimentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAtendimentoShouldNotBeFound(String filter) throws Exception {
        restAtendimentoMockMvc.perform(get("/api/atendimentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAtendimentoMockMvc.perform(get("/api/atendimentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAtendimento() throws Exception {
        // Get the atendimento
        restAtendimentoMockMvc.perform(get("/api/atendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();

        // Update the atendimento
        Atendimento updatedAtendimento = atendimentoRepository.findById(atendimento.getId()).get();
        // Disconnect from session so that the updates on updatedAtendimento are not directly saved in db
        em.detach(updatedAtendimento);
        updatedAtendimento
            .situacao(UPDATED_SITUACAO)
            .dataDeChegada(UPDATED_DATA_DE_CHEGADA)
            .dataDeSaida(UPDATED_DATA_DE_SAIDA)
            .observacao(UPDATED_OBSERVACAO);
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(updatedAtendimento);

        restAtendimentoMockMvc.perform(put("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isOk());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testAtendimento.getDataDeChegada()).isEqualTo(UPDATED_DATA_DE_CHEGADA);
        assertThat(testAtendimento.getDataDeSaida()).isEqualTo(UPDATED_DATA_DE_SAIDA);
        assertThat(testAtendimento.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtendimentoMockMvc.perform(put("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        int databaseSizeBeforeDelete = atendimentoRepository.findAll().size();

        // Delete the atendimento
        restAtendimentoMockMvc.perform(delete("/api/atendimentos/{id}", atendimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
