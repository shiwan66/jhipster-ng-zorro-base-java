package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Venda;
import br.com.ngzorro.domain.VendaConsumo;
import br.com.ngzorro.domain.Atendimento;
import br.com.ngzorro.repository.VendaRepository;
import br.com.ngzorro.service.VendaService;
import br.com.ngzorro.service.dto.VendaDTO;
import br.com.ngzorro.service.mapper.VendaMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.VendaCriteria;
import br.com.ngzorro.service.VendaQueryService;

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

import br.com.ngzorro.domain.enumeration.TipoSituacaoDoLancamento;
/**
 * Integration tests for the {@link VendaResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class VendaResourceIT {

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_DA_COMPRA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_DA_COMPRA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_DA_COMPRA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATA_DO_PAGAMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_DO_PAGAMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_DO_PAGAMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_DESCONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_DESCONTO = new BigDecimal(2);
    private static final BigDecimal SMALLER_DESCONTO = new BigDecimal(1 - 1);

    private static final TipoSituacaoDoLancamento DEFAULT_SITUACAO = TipoSituacaoDoLancamento.PAGO;
    private static final TipoSituacaoDoLancamento UPDATED_SITUACAO = TipoSituacaoDoLancamento.PAGAMENTOPENDENTE;

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL = new BigDecimal(1 - 1);

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaMapper vendaMapper;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private VendaQueryService vendaQueryService;

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

    private MockMvc restVendaMockMvc;

    private Venda venda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendaResource vendaResource = new VendaResource(vendaService, vendaQueryService);
        this.restVendaMockMvc = MockMvcBuilders.standaloneSetup(vendaResource)
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
    public static Venda createEntity(EntityManager em) {
        Venda venda = new Venda()
            .observacao(DEFAULT_OBSERVACAO)
            .dataDaCompra(DEFAULT_DATA_DA_COMPRA)
            .dataDoPagamento(DEFAULT_DATA_DO_PAGAMENTO)
            .desconto(DEFAULT_DESCONTO)
            .situacao(DEFAULT_SITUACAO)
            .valorTotal(DEFAULT_VALOR_TOTAL);
        return venda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venda createUpdatedEntity(EntityManager em) {
        Venda venda = new Venda()
            .observacao(UPDATED_OBSERVACAO)
            .dataDaCompra(UPDATED_DATA_DA_COMPRA)
            .dataDoPagamento(UPDATED_DATA_DO_PAGAMENTO)
            .desconto(UPDATED_DESCONTO)
            .situacao(UPDATED_SITUACAO)
            .valorTotal(UPDATED_VALOR_TOTAL);
        return venda;
    }

    @BeforeEach
    public void initTest() {
        venda = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenda() throws Exception {
        int databaseSizeBeforeCreate = vendaRepository.findAll().size();

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);
        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isCreated());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate + 1);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testVenda.getDataDaCompra()).isEqualTo(DEFAULT_DATA_DA_COMPRA);
        assertThat(testVenda.getDataDoPagamento()).isEqualTo(DEFAULT_DATA_DO_PAGAMENTO);
        assertThat(testVenda.getDesconto()).isEqualTo(DEFAULT_DESCONTO);
        assertThat(testVenda.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testVenda.getValorTotal()).isEqualTo(DEFAULT_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void createVendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendaRepository.findAll().size();

        // Create the Venda with an existing ID
        venda.setId(1L);
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVendas() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venda.getId().intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].dataDaCompra").value(hasItem(sameInstant(DEFAULT_DATA_DA_COMPRA))))
            .andExpect(jsonPath("$.[*].dataDoPagamento").value(hasItem(sameInstant(DEFAULT_DATA_DO_PAGAMENTO))))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.intValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get the venda
        restVendaMockMvc.perform(get("/api/vendas/{id}", venda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venda.getId().intValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.dataDaCompra").value(sameInstant(DEFAULT_DATA_DA_COMPRA)))
            .andExpect(jsonPath("$.dataDoPagamento").value(sameInstant(DEFAULT_DATA_DO_PAGAMENTO)))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.intValue()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.valorTotal").value(DEFAULT_VALOR_TOTAL.intValue()));
    }


    @Test
    @Transactional
    public void getVendasByIdFiltering() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        Long id = venda.getId();

        defaultVendaShouldBeFound("id.equals=" + id);
        defaultVendaShouldNotBeFound("id.notEquals=" + id);

        defaultVendaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVendaShouldNotBeFound("id.greaterThan=" + id);

        defaultVendaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVendaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVendasByDataDaCompraIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDaCompra equals to DEFAULT_DATA_DA_COMPRA
        defaultVendaShouldBeFound("dataDaCompra.equals=" + DEFAULT_DATA_DA_COMPRA);

        // Get all the vendaList where dataDaCompra equals to UPDATED_DATA_DA_COMPRA
        defaultVendaShouldNotBeFound("dataDaCompra.equals=" + UPDATED_DATA_DA_COMPRA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDaCompraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDaCompra not equals to DEFAULT_DATA_DA_COMPRA
        defaultVendaShouldNotBeFound("dataDaCompra.notEquals=" + DEFAULT_DATA_DA_COMPRA);

        // Get all the vendaList where dataDaCompra not equals to UPDATED_DATA_DA_COMPRA
        defaultVendaShouldBeFound("dataDaCompra.notEquals=" + UPDATED_DATA_DA_COMPRA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDaCompraIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDaCompra in DEFAULT_DATA_DA_COMPRA or UPDATED_DATA_DA_COMPRA
        defaultVendaShouldBeFound("dataDaCompra.in=" + DEFAULT_DATA_DA_COMPRA + "," + UPDATED_DATA_DA_COMPRA);

        // Get all the vendaList where dataDaCompra equals to UPDATED_DATA_DA_COMPRA
        defaultVendaShouldNotBeFound("dataDaCompra.in=" + UPDATED_DATA_DA_COMPRA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDaCompraIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDaCompra is not null
        defaultVendaShouldBeFound("dataDaCompra.specified=true");

        // Get all the vendaList where dataDaCompra is null
        defaultVendaShouldNotBeFound("dataDaCompra.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByDataDaCompraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDaCompra is greater than or equal to DEFAULT_DATA_DA_COMPRA
        defaultVendaShouldBeFound("dataDaCompra.greaterThanOrEqual=" + DEFAULT_DATA_DA_COMPRA);

        // Get all the vendaList where dataDaCompra is greater than or equal to UPDATED_DATA_DA_COMPRA
        defaultVendaShouldNotBeFound("dataDaCompra.greaterThanOrEqual=" + UPDATED_DATA_DA_COMPRA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDaCompraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDaCompra is less than or equal to DEFAULT_DATA_DA_COMPRA
        defaultVendaShouldBeFound("dataDaCompra.lessThanOrEqual=" + DEFAULT_DATA_DA_COMPRA);

        // Get all the vendaList where dataDaCompra is less than or equal to SMALLER_DATA_DA_COMPRA
        defaultVendaShouldNotBeFound("dataDaCompra.lessThanOrEqual=" + SMALLER_DATA_DA_COMPRA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDaCompraIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDaCompra is less than DEFAULT_DATA_DA_COMPRA
        defaultVendaShouldNotBeFound("dataDaCompra.lessThan=" + DEFAULT_DATA_DA_COMPRA);

        // Get all the vendaList where dataDaCompra is less than UPDATED_DATA_DA_COMPRA
        defaultVendaShouldBeFound("dataDaCompra.lessThan=" + UPDATED_DATA_DA_COMPRA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDaCompraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDaCompra is greater than DEFAULT_DATA_DA_COMPRA
        defaultVendaShouldNotBeFound("dataDaCompra.greaterThan=" + DEFAULT_DATA_DA_COMPRA);

        // Get all the vendaList where dataDaCompra is greater than SMALLER_DATA_DA_COMPRA
        defaultVendaShouldBeFound("dataDaCompra.greaterThan=" + SMALLER_DATA_DA_COMPRA);
    }


    @Test
    @Transactional
    public void getAllVendasByDataDoPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDoPagamento equals to DEFAULT_DATA_DO_PAGAMENTO
        defaultVendaShouldBeFound("dataDoPagamento.equals=" + DEFAULT_DATA_DO_PAGAMENTO);

        // Get all the vendaList where dataDoPagamento equals to UPDATED_DATA_DO_PAGAMENTO
        defaultVendaShouldNotBeFound("dataDoPagamento.equals=" + UPDATED_DATA_DO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDoPagamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDoPagamento not equals to DEFAULT_DATA_DO_PAGAMENTO
        defaultVendaShouldNotBeFound("dataDoPagamento.notEquals=" + DEFAULT_DATA_DO_PAGAMENTO);

        // Get all the vendaList where dataDoPagamento not equals to UPDATED_DATA_DO_PAGAMENTO
        defaultVendaShouldBeFound("dataDoPagamento.notEquals=" + UPDATED_DATA_DO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDoPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDoPagamento in DEFAULT_DATA_DO_PAGAMENTO or UPDATED_DATA_DO_PAGAMENTO
        defaultVendaShouldBeFound("dataDoPagamento.in=" + DEFAULT_DATA_DO_PAGAMENTO + "," + UPDATED_DATA_DO_PAGAMENTO);

        // Get all the vendaList where dataDoPagamento equals to UPDATED_DATA_DO_PAGAMENTO
        defaultVendaShouldNotBeFound("dataDoPagamento.in=" + UPDATED_DATA_DO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDoPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDoPagamento is not null
        defaultVendaShouldBeFound("dataDoPagamento.specified=true");

        // Get all the vendaList where dataDoPagamento is null
        defaultVendaShouldNotBeFound("dataDoPagamento.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByDataDoPagamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDoPagamento is greater than or equal to DEFAULT_DATA_DO_PAGAMENTO
        defaultVendaShouldBeFound("dataDoPagamento.greaterThanOrEqual=" + DEFAULT_DATA_DO_PAGAMENTO);

        // Get all the vendaList where dataDoPagamento is greater than or equal to UPDATED_DATA_DO_PAGAMENTO
        defaultVendaShouldNotBeFound("dataDoPagamento.greaterThanOrEqual=" + UPDATED_DATA_DO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDoPagamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDoPagamento is less than or equal to DEFAULT_DATA_DO_PAGAMENTO
        defaultVendaShouldBeFound("dataDoPagamento.lessThanOrEqual=" + DEFAULT_DATA_DO_PAGAMENTO);

        // Get all the vendaList where dataDoPagamento is less than or equal to SMALLER_DATA_DO_PAGAMENTO
        defaultVendaShouldNotBeFound("dataDoPagamento.lessThanOrEqual=" + SMALLER_DATA_DO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDoPagamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDoPagamento is less than DEFAULT_DATA_DO_PAGAMENTO
        defaultVendaShouldNotBeFound("dataDoPagamento.lessThan=" + DEFAULT_DATA_DO_PAGAMENTO);

        // Get all the vendaList where dataDoPagamento is less than UPDATED_DATA_DO_PAGAMENTO
        defaultVendaShouldBeFound("dataDoPagamento.lessThan=" + UPDATED_DATA_DO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDataDoPagamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataDoPagamento is greater than DEFAULT_DATA_DO_PAGAMENTO
        defaultVendaShouldNotBeFound("dataDoPagamento.greaterThan=" + DEFAULT_DATA_DO_PAGAMENTO);

        // Get all the vendaList where dataDoPagamento is greater than SMALLER_DATA_DO_PAGAMENTO
        defaultVendaShouldBeFound("dataDoPagamento.greaterThan=" + SMALLER_DATA_DO_PAGAMENTO);
    }


    @Test
    @Transactional
    public void getAllVendasByDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where desconto equals to DEFAULT_DESCONTO
        defaultVendaShouldBeFound("desconto.equals=" + DEFAULT_DESCONTO);

        // Get all the vendaList where desconto equals to UPDATED_DESCONTO
        defaultVendaShouldNotBeFound("desconto.equals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDescontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where desconto not equals to DEFAULT_DESCONTO
        defaultVendaShouldNotBeFound("desconto.notEquals=" + DEFAULT_DESCONTO);

        // Get all the vendaList where desconto not equals to UPDATED_DESCONTO
        defaultVendaShouldBeFound("desconto.notEquals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where desconto in DEFAULT_DESCONTO or UPDATED_DESCONTO
        defaultVendaShouldBeFound("desconto.in=" + DEFAULT_DESCONTO + "," + UPDATED_DESCONTO);

        // Get all the vendaList where desconto equals to UPDATED_DESCONTO
        defaultVendaShouldNotBeFound("desconto.in=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where desconto is not null
        defaultVendaShouldBeFound("desconto.specified=true");

        // Get all the vendaList where desconto is null
        defaultVendaShouldNotBeFound("desconto.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where desconto is greater than or equal to DEFAULT_DESCONTO
        defaultVendaShouldBeFound("desconto.greaterThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the vendaList where desconto is greater than or equal to UPDATED_DESCONTO
        defaultVendaShouldNotBeFound("desconto.greaterThanOrEqual=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where desconto is less than or equal to DEFAULT_DESCONTO
        defaultVendaShouldBeFound("desconto.lessThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the vendaList where desconto is less than or equal to SMALLER_DESCONTO
        defaultVendaShouldNotBeFound("desconto.lessThanOrEqual=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where desconto is less than DEFAULT_DESCONTO
        defaultVendaShouldNotBeFound("desconto.lessThan=" + DEFAULT_DESCONTO);

        // Get all the vendaList where desconto is less than UPDATED_DESCONTO
        defaultVendaShouldBeFound("desconto.lessThan=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllVendasByDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where desconto is greater than DEFAULT_DESCONTO
        defaultVendaShouldNotBeFound("desconto.greaterThan=" + DEFAULT_DESCONTO);

        // Get all the vendaList where desconto is greater than SMALLER_DESCONTO
        defaultVendaShouldBeFound("desconto.greaterThan=" + SMALLER_DESCONTO);
    }


    @Test
    @Transactional
    public void getAllVendasBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where situacao equals to DEFAULT_SITUACAO
        defaultVendaShouldBeFound("situacao.equals=" + DEFAULT_SITUACAO);

        // Get all the vendaList where situacao equals to UPDATED_SITUACAO
        defaultVendaShouldNotBeFound("situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    public void getAllVendasBySituacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where situacao not equals to DEFAULT_SITUACAO
        defaultVendaShouldNotBeFound("situacao.notEquals=" + DEFAULT_SITUACAO);

        // Get all the vendaList where situacao not equals to UPDATED_SITUACAO
        defaultVendaShouldBeFound("situacao.notEquals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    public void getAllVendasBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where situacao in DEFAULT_SITUACAO or UPDATED_SITUACAO
        defaultVendaShouldBeFound("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO);

        // Get all the vendaList where situacao equals to UPDATED_SITUACAO
        defaultVendaShouldNotBeFound("situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    public void getAllVendasBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where situacao is not null
        defaultVendaShouldBeFound("situacao.specified=true");

        // Get all the vendaList where situacao is null
        defaultVendaShouldNotBeFound("situacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByValorTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotal equals to DEFAULT_VALOR_TOTAL
        defaultVendaShouldBeFound("valorTotal.equals=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultVendaShouldNotBeFound("valorTotal.equals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendasByValorTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotal not equals to DEFAULT_VALOR_TOTAL
        defaultVendaShouldNotBeFound("valorTotal.notEquals=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaList where valorTotal not equals to UPDATED_VALOR_TOTAL
        defaultVendaShouldBeFound("valorTotal.notEquals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendasByValorTotalIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotal in DEFAULT_VALOR_TOTAL or UPDATED_VALOR_TOTAL
        defaultVendaShouldBeFound("valorTotal.in=" + DEFAULT_VALOR_TOTAL + "," + UPDATED_VALOR_TOTAL);

        // Get all the vendaList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultVendaShouldNotBeFound("valorTotal.in=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendasByValorTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotal is not null
        defaultVendaShouldBeFound("valorTotal.specified=true");

        // Get all the vendaList where valorTotal is null
        defaultVendaShouldNotBeFound("valorTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByValorTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotal is greater than or equal to DEFAULT_VALOR_TOTAL
        defaultVendaShouldBeFound("valorTotal.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaList where valorTotal is greater than or equal to UPDATED_VALOR_TOTAL
        defaultVendaShouldNotBeFound("valorTotal.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendasByValorTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotal is less than or equal to DEFAULT_VALOR_TOTAL
        defaultVendaShouldBeFound("valorTotal.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaList where valorTotal is less than or equal to SMALLER_VALOR_TOTAL
        defaultVendaShouldNotBeFound("valorTotal.lessThanOrEqual=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendasByValorTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotal is less than DEFAULT_VALOR_TOTAL
        defaultVendaShouldNotBeFound("valorTotal.lessThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaList where valorTotal is less than UPDATED_VALOR_TOTAL
        defaultVendaShouldBeFound("valorTotal.lessThan=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendasByValorTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotal is greater than DEFAULT_VALOR_TOTAL
        defaultVendaShouldNotBeFound("valorTotal.greaterThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaList where valorTotal is greater than SMALLER_VALOR_TOTAL
        defaultVendaShouldBeFound("valorTotal.greaterThan=" + SMALLER_VALOR_TOTAL);
    }


    @Test
    @Transactional
    public void getAllVendasByVendaConsumoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        VendaConsumo vendaConsumo = VendaConsumoResourceIT.createEntity(em);
        em.persist(vendaConsumo);
        em.flush();
        venda.addVendaConsumo(vendaConsumo);
        vendaRepository.saveAndFlush(venda);
        Long vendaConsumoId = vendaConsumo.getId();

        // Get all the vendaList where vendaConsumo equals to vendaConsumoId
        defaultVendaShouldBeFound("vendaConsumoId.equals=" + vendaConsumoId);

        // Get all the vendaList where vendaConsumo equals to vendaConsumoId + 1
        defaultVendaShouldNotBeFound("vendaConsumoId.equals=" + (vendaConsumoId + 1));
    }


    @Test
    @Transactional
    public void getAllVendasByAtendimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        Atendimento atendimento = AtendimentoResourceIT.createEntity(em);
        em.persist(atendimento);
        em.flush();
        venda.setAtendimento(atendimento);
        vendaRepository.saveAndFlush(venda);
        Long atendimentoId = atendimento.getId();

        // Get all the vendaList where atendimento equals to atendimentoId
        defaultVendaShouldBeFound("atendimentoId.equals=" + atendimentoId);

        // Get all the vendaList where atendimento equals to atendimentoId + 1
        defaultVendaShouldNotBeFound("atendimentoId.equals=" + (atendimentoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVendaShouldBeFound(String filter) throws Exception {
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venda.getId().intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].dataDaCompra").value(hasItem(sameInstant(DEFAULT_DATA_DA_COMPRA))))
            .andExpect(jsonPath("$.[*].dataDoPagamento").value(hasItem(sameInstant(DEFAULT_DATA_DO_PAGAMENTO))))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.intValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())));

        // Check, that the count call also returns 1
        restVendaMockMvc.perform(get("/api/vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVendaShouldNotBeFound(String filter) throws Exception {
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendaMockMvc.perform(get("/api/vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVenda() throws Exception {
        // Get the venda
        restVendaMockMvc.perform(get("/api/vendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Update the venda
        Venda updatedVenda = vendaRepository.findById(venda.getId()).get();
        // Disconnect from session so that the updates on updatedVenda are not directly saved in db
        em.detach(updatedVenda);
        updatedVenda
            .observacao(UPDATED_OBSERVACAO)
            .dataDaCompra(UPDATED_DATA_DA_COMPRA)
            .dataDoPagamento(UPDATED_DATA_DO_PAGAMENTO)
            .desconto(UPDATED_DESCONTO)
            .situacao(UPDATED_SITUACAO)
            .valorTotal(UPDATED_VALOR_TOTAL);
        VendaDTO vendaDTO = vendaMapper.toDto(updatedVenda);

        restVendaMockMvc.perform(put("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isOk());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testVenda.getDataDaCompra()).isEqualTo(UPDATED_DATA_DA_COMPRA);
        assertThat(testVenda.getDataDoPagamento()).isEqualTo(UPDATED_DATA_DO_PAGAMENTO);
        assertThat(testVenda.getDesconto()).isEqualTo(UPDATED_DESCONTO);
        assertThat(testVenda.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testVenda.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendaMockMvc.perform(put("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeDelete = vendaRepository.findAll().size();

        // Delete the venda
        restVendaMockMvc.perform(delete("/api/vendas/{id}", venda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
