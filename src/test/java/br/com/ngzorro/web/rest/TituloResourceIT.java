package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Titulo;
import br.com.ngzorro.domain.Tutor;
import br.com.ngzorro.domain.Fornecedor;
import br.com.ngzorro.repository.TituloRepository;
import br.com.ngzorro.service.TituloService;
import br.com.ngzorro.service.dto.TituloDTO;
import br.com.ngzorro.service.mapper.TituloMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.TituloCriteria;
import br.com.ngzorro.service.TituloQueryService;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.ngzorro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.ngzorro.domain.enumeration.TipoTitulo;
/**
 * Integration tests for the {@link TituloResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class TituloResourceIT {

    private static final Boolean DEFAULT_IS_PAGO = false;
    private static final Boolean UPDATED_IS_PAGO = true;

    private static final TipoTitulo DEFAULT_TIPO = TipoTitulo.RECEITA;
    private static final TipoTitulo UPDATED_TIPO = TipoTitulo.DESPESA;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DATA_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_EMISSAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_EMISSAO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_PAGAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PAGAMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_PAGAMENTO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_VENCIMENTO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private TituloMapper tituloMapper;

    @Autowired
    private TituloService tituloService;

    @Autowired
    private TituloQueryService tituloQueryService;

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

    private MockMvc restTituloMockMvc;

    private Titulo titulo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TituloResource tituloResource = new TituloResource(tituloService, tituloQueryService);
        this.restTituloMockMvc = MockMvcBuilders.standaloneSetup(tituloResource)
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
    public static Titulo createEntity(EntityManager em) {
        Titulo titulo = new Titulo()
            .isPago(DEFAULT_IS_PAGO)
            .tipo(DEFAULT_TIPO)
            .descricao(DEFAULT_DESCRICAO)
            .valor(DEFAULT_VALOR)
            .dataEmissao(DEFAULT_DATA_EMISSAO)
            .dataPagamento(DEFAULT_DATA_PAGAMENTO)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO);
        return titulo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Titulo createUpdatedEntity(EntityManager em) {
        Titulo titulo = new Titulo()
            .isPago(UPDATED_IS_PAGO)
            .tipo(UPDATED_TIPO)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO);
        return titulo;
    }

    @BeforeEach
    public void initTest() {
        titulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTitulo() throws Exception {
        int databaseSizeBeforeCreate = tituloRepository.findAll().size();

        // Create the Titulo
        TituloDTO tituloDTO = tituloMapper.toDto(titulo);
        restTituloMockMvc.perform(post("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloDTO)))
            .andExpect(status().isCreated());

        // Validate the Titulo in the database
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeCreate + 1);
        Titulo testTitulo = tituloList.get(tituloList.size() - 1);
        assertThat(testTitulo.isIsPago()).isEqualTo(DEFAULT_IS_PAGO);
        assertThat(testTitulo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTitulo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTitulo.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testTitulo.getDataEmissao()).isEqualTo(DEFAULT_DATA_EMISSAO);
        assertThat(testTitulo.getDataPagamento()).isEqualTo(DEFAULT_DATA_PAGAMENTO);
        assertThat(testTitulo.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void createTituloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tituloRepository.findAll().size();

        // Create the Titulo with an existing ID
        titulo.setId(1L);
        TituloDTO tituloDTO = tituloMapper.toDto(titulo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTituloMockMvc.perform(post("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Titulo in the database
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTitulos() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList
        restTituloMockMvc.perform(get("/api/titulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(titulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].isPago").value(hasItem(DEFAULT_IS_PAGO.booleanValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].dataEmissao").value(hasItem(DEFAULT_DATA_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getTitulo() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get the titulo
        restTituloMockMvc.perform(get("/api/titulos/{id}", titulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(titulo.getId().intValue()))
            .andExpect(jsonPath("$.isPago").value(DEFAULT_IS_PAGO.booleanValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.dataEmissao").value(DEFAULT_DATA_EMISSAO.toString()))
            .andExpect(jsonPath("$.dataPagamento").value(DEFAULT_DATA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()));
    }


    @Test
    @Transactional
    public void getTitulosByIdFiltering() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        Long id = titulo.getId();

        defaultTituloShouldBeFound("id.equals=" + id);
        defaultTituloShouldNotBeFound("id.notEquals=" + id);

        defaultTituloShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTituloShouldNotBeFound("id.greaterThan=" + id);

        defaultTituloShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTituloShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTitulosByIsPagoIsEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where isPago equals to DEFAULT_IS_PAGO
        defaultTituloShouldBeFound("isPago.equals=" + DEFAULT_IS_PAGO);

        // Get all the tituloList where isPago equals to UPDATED_IS_PAGO
        defaultTituloShouldNotBeFound("isPago.equals=" + UPDATED_IS_PAGO);
    }

    @Test
    @Transactional
    public void getAllTitulosByIsPagoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where isPago not equals to DEFAULT_IS_PAGO
        defaultTituloShouldNotBeFound("isPago.notEquals=" + DEFAULT_IS_PAGO);

        // Get all the tituloList where isPago not equals to UPDATED_IS_PAGO
        defaultTituloShouldBeFound("isPago.notEquals=" + UPDATED_IS_PAGO);
    }

    @Test
    @Transactional
    public void getAllTitulosByIsPagoIsInShouldWork() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where isPago in DEFAULT_IS_PAGO or UPDATED_IS_PAGO
        defaultTituloShouldBeFound("isPago.in=" + DEFAULT_IS_PAGO + "," + UPDATED_IS_PAGO);

        // Get all the tituloList where isPago equals to UPDATED_IS_PAGO
        defaultTituloShouldNotBeFound("isPago.in=" + UPDATED_IS_PAGO);
    }

    @Test
    @Transactional
    public void getAllTitulosByIsPagoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where isPago is not null
        defaultTituloShouldBeFound("isPago.specified=true");

        // Get all the tituloList where isPago is null
        defaultTituloShouldNotBeFound("isPago.specified=false");
    }

    @Test
    @Transactional
    public void getAllTitulosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where tipo equals to DEFAULT_TIPO
        defaultTituloShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the tituloList where tipo equals to UPDATED_TIPO
        defaultTituloShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllTitulosByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where tipo not equals to DEFAULT_TIPO
        defaultTituloShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the tituloList where tipo not equals to UPDATED_TIPO
        defaultTituloShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllTitulosByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultTituloShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the tituloList where tipo equals to UPDATED_TIPO
        defaultTituloShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllTitulosByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where tipo is not null
        defaultTituloShouldBeFound("tipo.specified=true");

        // Get all the tituloList where tipo is null
        defaultTituloShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTitulosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where valor equals to DEFAULT_VALOR
        defaultTituloShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the tituloList where valor equals to UPDATED_VALOR
        defaultTituloShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllTitulosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where valor not equals to DEFAULT_VALOR
        defaultTituloShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the tituloList where valor not equals to UPDATED_VALOR
        defaultTituloShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllTitulosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultTituloShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the tituloList where valor equals to UPDATED_VALOR
        defaultTituloShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllTitulosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where valor is not null
        defaultTituloShouldBeFound("valor.specified=true");

        // Get all the tituloList where valor is null
        defaultTituloShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllTitulosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where valor is greater than or equal to DEFAULT_VALOR
        defaultTituloShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the tituloList where valor is greater than or equal to UPDATED_VALOR
        defaultTituloShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllTitulosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where valor is less than or equal to DEFAULT_VALOR
        defaultTituloShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the tituloList where valor is less than or equal to SMALLER_VALOR
        defaultTituloShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllTitulosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where valor is less than DEFAULT_VALOR
        defaultTituloShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the tituloList where valor is less than UPDATED_VALOR
        defaultTituloShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllTitulosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where valor is greater than DEFAULT_VALOR
        defaultTituloShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the tituloList where valor is greater than SMALLER_VALOR
        defaultTituloShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllTitulosByDataEmissaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataEmissao equals to DEFAULT_DATA_EMISSAO
        defaultTituloShouldBeFound("dataEmissao.equals=" + DEFAULT_DATA_EMISSAO);

        // Get all the tituloList where dataEmissao equals to UPDATED_DATA_EMISSAO
        defaultTituloShouldNotBeFound("dataEmissao.equals=" + UPDATED_DATA_EMISSAO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataEmissaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataEmissao not equals to DEFAULT_DATA_EMISSAO
        defaultTituloShouldNotBeFound("dataEmissao.notEquals=" + DEFAULT_DATA_EMISSAO);

        // Get all the tituloList where dataEmissao not equals to UPDATED_DATA_EMISSAO
        defaultTituloShouldBeFound("dataEmissao.notEquals=" + UPDATED_DATA_EMISSAO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataEmissaoIsInShouldWork() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataEmissao in DEFAULT_DATA_EMISSAO or UPDATED_DATA_EMISSAO
        defaultTituloShouldBeFound("dataEmissao.in=" + DEFAULT_DATA_EMISSAO + "," + UPDATED_DATA_EMISSAO);

        // Get all the tituloList where dataEmissao equals to UPDATED_DATA_EMISSAO
        defaultTituloShouldNotBeFound("dataEmissao.in=" + UPDATED_DATA_EMISSAO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataEmissaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataEmissao is not null
        defaultTituloShouldBeFound("dataEmissao.specified=true");

        // Get all the tituloList where dataEmissao is null
        defaultTituloShouldNotBeFound("dataEmissao.specified=false");
    }

    @Test
    @Transactional
    public void getAllTitulosByDataEmissaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataEmissao is greater than or equal to DEFAULT_DATA_EMISSAO
        defaultTituloShouldBeFound("dataEmissao.greaterThanOrEqual=" + DEFAULT_DATA_EMISSAO);

        // Get all the tituloList where dataEmissao is greater than or equal to UPDATED_DATA_EMISSAO
        defaultTituloShouldNotBeFound("dataEmissao.greaterThanOrEqual=" + UPDATED_DATA_EMISSAO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataEmissaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataEmissao is less than or equal to DEFAULT_DATA_EMISSAO
        defaultTituloShouldBeFound("dataEmissao.lessThanOrEqual=" + DEFAULT_DATA_EMISSAO);

        // Get all the tituloList where dataEmissao is less than or equal to SMALLER_DATA_EMISSAO
        defaultTituloShouldNotBeFound("dataEmissao.lessThanOrEqual=" + SMALLER_DATA_EMISSAO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataEmissaoIsLessThanSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataEmissao is less than DEFAULT_DATA_EMISSAO
        defaultTituloShouldNotBeFound("dataEmissao.lessThan=" + DEFAULT_DATA_EMISSAO);

        // Get all the tituloList where dataEmissao is less than UPDATED_DATA_EMISSAO
        defaultTituloShouldBeFound("dataEmissao.lessThan=" + UPDATED_DATA_EMISSAO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataEmissaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataEmissao is greater than DEFAULT_DATA_EMISSAO
        defaultTituloShouldNotBeFound("dataEmissao.greaterThan=" + DEFAULT_DATA_EMISSAO);

        // Get all the tituloList where dataEmissao is greater than SMALLER_DATA_EMISSAO
        defaultTituloShouldBeFound("dataEmissao.greaterThan=" + SMALLER_DATA_EMISSAO);
    }


    @Test
    @Transactional
    public void getAllTitulosByDataPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataPagamento equals to DEFAULT_DATA_PAGAMENTO
        defaultTituloShouldBeFound("dataPagamento.equals=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the tituloList where dataPagamento equals to UPDATED_DATA_PAGAMENTO
        defaultTituloShouldNotBeFound("dataPagamento.equals=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataPagamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataPagamento not equals to DEFAULT_DATA_PAGAMENTO
        defaultTituloShouldNotBeFound("dataPagamento.notEquals=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the tituloList where dataPagamento not equals to UPDATED_DATA_PAGAMENTO
        defaultTituloShouldBeFound("dataPagamento.notEquals=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataPagamento in DEFAULT_DATA_PAGAMENTO or UPDATED_DATA_PAGAMENTO
        defaultTituloShouldBeFound("dataPagamento.in=" + DEFAULT_DATA_PAGAMENTO + "," + UPDATED_DATA_PAGAMENTO);

        // Get all the tituloList where dataPagamento equals to UPDATED_DATA_PAGAMENTO
        defaultTituloShouldNotBeFound("dataPagamento.in=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataPagamento is not null
        defaultTituloShouldBeFound("dataPagamento.specified=true");

        // Get all the tituloList where dataPagamento is null
        defaultTituloShouldNotBeFound("dataPagamento.specified=false");
    }

    @Test
    @Transactional
    public void getAllTitulosByDataPagamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataPagamento is greater than or equal to DEFAULT_DATA_PAGAMENTO
        defaultTituloShouldBeFound("dataPagamento.greaterThanOrEqual=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the tituloList where dataPagamento is greater than or equal to UPDATED_DATA_PAGAMENTO
        defaultTituloShouldNotBeFound("dataPagamento.greaterThanOrEqual=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataPagamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataPagamento is less than or equal to DEFAULT_DATA_PAGAMENTO
        defaultTituloShouldBeFound("dataPagamento.lessThanOrEqual=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the tituloList where dataPagamento is less than or equal to SMALLER_DATA_PAGAMENTO
        defaultTituloShouldNotBeFound("dataPagamento.lessThanOrEqual=" + SMALLER_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataPagamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataPagamento is less than DEFAULT_DATA_PAGAMENTO
        defaultTituloShouldNotBeFound("dataPagamento.lessThan=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the tituloList where dataPagamento is less than UPDATED_DATA_PAGAMENTO
        defaultTituloShouldBeFound("dataPagamento.lessThan=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataPagamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataPagamento is greater than DEFAULT_DATA_PAGAMENTO
        defaultTituloShouldNotBeFound("dataPagamento.greaterThan=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the tituloList where dataPagamento is greater than SMALLER_DATA_PAGAMENTO
        defaultTituloShouldBeFound("dataPagamento.greaterThan=" + SMALLER_DATA_PAGAMENTO);
    }


    @Test
    @Transactional
    public void getAllTitulosByDataVencimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataVencimento equals to DEFAULT_DATA_VENCIMENTO
        defaultTituloShouldBeFound("dataVencimento.equals=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the tituloList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultTituloShouldNotBeFound("dataVencimento.equals=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataVencimentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataVencimento not equals to DEFAULT_DATA_VENCIMENTO
        defaultTituloShouldNotBeFound("dataVencimento.notEquals=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the tituloList where dataVencimento not equals to UPDATED_DATA_VENCIMENTO
        defaultTituloShouldBeFound("dataVencimento.notEquals=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataVencimentoIsInShouldWork() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataVencimento in DEFAULT_DATA_VENCIMENTO or UPDATED_DATA_VENCIMENTO
        defaultTituloShouldBeFound("dataVencimento.in=" + DEFAULT_DATA_VENCIMENTO + "," + UPDATED_DATA_VENCIMENTO);

        // Get all the tituloList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultTituloShouldNotBeFound("dataVencimento.in=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataVencimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataVencimento is not null
        defaultTituloShouldBeFound("dataVencimento.specified=true");

        // Get all the tituloList where dataVencimento is null
        defaultTituloShouldNotBeFound("dataVencimento.specified=false");
    }

    @Test
    @Transactional
    public void getAllTitulosByDataVencimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataVencimento is greater than or equal to DEFAULT_DATA_VENCIMENTO
        defaultTituloShouldBeFound("dataVencimento.greaterThanOrEqual=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the tituloList where dataVencimento is greater than or equal to UPDATED_DATA_VENCIMENTO
        defaultTituloShouldNotBeFound("dataVencimento.greaterThanOrEqual=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataVencimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataVencimento is less than or equal to DEFAULT_DATA_VENCIMENTO
        defaultTituloShouldBeFound("dataVencimento.lessThanOrEqual=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the tituloList where dataVencimento is less than or equal to SMALLER_DATA_VENCIMENTO
        defaultTituloShouldNotBeFound("dataVencimento.lessThanOrEqual=" + SMALLER_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataVencimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataVencimento is less than DEFAULT_DATA_VENCIMENTO
        defaultTituloShouldNotBeFound("dataVencimento.lessThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the tituloList where dataVencimento is less than UPDATED_DATA_VENCIMENTO
        defaultTituloShouldBeFound("dataVencimento.lessThan=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllTitulosByDataVencimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList where dataVencimento is greater than DEFAULT_DATA_VENCIMENTO
        defaultTituloShouldNotBeFound("dataVencimento.greaterThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the tituloList where dataVencimento is greater than SMALLER_DATA_VENCIMENTO
        defaultTituloShouldBeFound("dataVencimento.greaterThan=" + SMALLER_DATA_VENCIMENTO);
    }


    @Test
    @Transactional
    public void getAllTitulosByTutorIsEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);
        Tutor tutor = TutorResourceIT.createEntity(em);
        em.persist(tutor);
        em.flush();
        titulo.setTutor(tutor);
        tituloRepository.saveAndFlush(titulo);
        Long tutorId = tutor.getId();

        // Get all the tituloList where tutor equals to tutorId
        defaultTituloShouldBeFound("tutorId.equals=" + tutorId);

        // Get all the tituloList where tutor equals to tutorId + 1
        defaultTituloShouldNotBeFound("tutorId.equals=" + (tutorId + 1));
    }


    @Test
    @Transactional
    public void getAllTitulosByFornecedorIsEqualToSomething() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);
        Fornecedor fornecedor = FornecedorResourceIT.createEntity(em);
        em.persist(fornecedor);
        em.flush();
        titulo.setFornecedor(fornecedor);
        tituloRepository.saveAndFlush(titulo);
        Long fornecedorId = fornecedor.getId();

        // Get all the tituloList where fornecedor equals to fornecedorId
        defaultTituloShouldBeFound("fornecedorId.equals=" + fornecedorId);

        // Get all the tituloList where fornecedor equals to fornecedorId + 1
        defaultTituloShouldNotBeFound("fornecedorId.equals=" + (fornecedorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTituloShouldBeFound(String filter) throws Exception {
        restTituloMockMvc.perform(get("/api/titulos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(titulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].isPago").value(hasItem(DEFAULT_IS_PAGO.booleanValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].dataEmissao").value(hasItem(DEFAULT_DATA_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())));

        // Check, that the count call also returns 1
        restTituloMockMvc.perform(get("/api/titulos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTituloShouldNotBeFound(String filter) throws Exception {
        restTituloMockMvc.perform(get("/api/titulos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTituloMockMvc.perform(get("/api/titulos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTitulo() throws Exception {
        // Get the titulo
        restTituloMockMvc.perform(get("/api/titulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTitulo() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        int databaseSizeBeforeUpdate = tituloRepository.findAll().size();

        // Update the titulo
        Titulo updatedTitulo = tituloRepository.findById(titulo.getId()).get();
        // Disconnect from session so that the updates on updatedTitulo are not directly saved in db
        em.detach(updatedTitulo);
        updatedTitulo
            .isPago(UPDATED_IS_PAGO)
            .tipo(UPDATED_TIPO)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO);
        TituloDTO tituloDTO = tituloMapper.toDto(updatedTitulo);

        restTituloMockMvc.perform(put("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloDTO)))
            .andExpect(status().isOk());

        // Validate the Titulo in the database
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeUpdate);
        Titulo testTitulo = tituloList.get(tituloList.size() - 1);
        assertThat(testTitulo.isIsPago()).isEqualTo(UPDATED_IS_PAGO);
        assertThat(testTitulo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTitulo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTitulo.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testTitulo.getDataEmissao()).isEqualTo(UPDATED_DATA_EMISSAO);
        assertThat(testTitulo.getDataPagamento()).isEqualTo(UPDATED_DATA_PAGAMENTO);
        assertThat(testTitulo.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingTitulo() throws Exception {
        int databaseSizeBeforeUpdate = tituloRepository.findAll().size();

        // Create the Titulo
        TituloDTO tituloDTO = tituloMapper.toDto(titulo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTituloMockMvc.perform(put("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Titulo in the database
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTitulo() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        int databaseSizeBeforeDelete = tituloRepository.findAll().size();

        // Delete the titulo
        restTituloMockMvc.perform(delete("/api/titulos/{id}", titulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
