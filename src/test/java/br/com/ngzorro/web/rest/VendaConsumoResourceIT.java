package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.VendaConsumo;
import br.com.ngzorro.domain.Venda;
import br.com.ngzorro.domain.Consumo;
import br.com.ngzorro.repository.VendaConsumoRepository;
import br.com.ngzorro.service.VendaConsumoService;
import br.com.ngzorro.service.dto.VendaConsumoDTO;
import br.com.ngzorro.service.mapper.VendaConsumoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.VendaConsumoCriteria;
import br.com.ngzorro.service.VendaConsumoQueryService;

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
import java.math.BigDecimal;
import java.util.List;

import static br.com.ngzorro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VendaConsumoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class VendaConsumoResourceIT {

    private static final BigDecimal DEFAULT_QUANTIDADE = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTIDADE = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTIDADE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_UNITARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_UNITARIO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_UNITARIO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL = new BigDecimal(1 - 1);

    @Autowired
    private VendaConsumoRepository vendaConsumoRepository;

    @Autowired
    private VendaConsumoMapper vendaConsumoMapper;

    @Autowired
    private VendaConsumoService vendaConsumoService;

    @Autowired
    private VendaConsumoQueryService vendaConsumoQueryService;

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

    private MockMvc restVendaConsumoMockMvc;

    private VendaConsumo vendaConsumo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendaConsumoResource vendaConsumoResource = new VendaConsumoResource(vendaConsumoService, vendaConsumoQueryService);
        this.restVendaConsumoMockMvc = MockMvcBuilders.standaloneSetup(vendaConsumoResource)
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
    public static VendaConsumo createEntity(EntityManager em) {
        VendaConsumo vendaConsumo = new VendaConsumo()
            .quantidade(DEFAULT_QUANTIDADE)
            .valorUnitario(DEFAULT_VALOR_UNITARIO)
            .valorTotal(DEFAULT_VALOR_TOTAL);
        return vendaConsumo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VendaConsumo createUpdatedEntity(EntityManager em) {
        VendaConsumo vendaConsumo = new VendaConsumo()
            .quantidade(UPDATED_QUANTIDADE)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .valorTotal(UPDATED_VALOR_TOTAL);
        return vendaConsumo;
    }

    @BeforeEach
    public void initTest() {
        vendaConsumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendaConsumo() throws Exception {
        int databaseSizeBeforeCreate = vendaConsumoRepository.findAll().size();

        // Create the VendaConsumo
        VendaConsumoDTO vendaConsumoDTO = vendaConsumoMapper.toDto(vendaConsumo);
        restVendaConsumoMockMvc.perform(post("/api/venda-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaConsumoDTO)))
            .andExpect(status().isCreated());

        // Validate the VendaConsumo in the database
        List<VendaConsumo> vendaConsumoList = vendaConsumoRepository.findAll();
        assertThat(vendaConsumoList).hasSize(databaseSizeBeforeCreate + 1);
        VendaConsumo testVendaConsumo = vendaConsumoList.get(vendaConsumoList.size() - 1);
        assertThat(testVendaConsumo.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testVendaConsumo.getValorUnitario()).isEqualTo(DEFAULT_VALOR_UNITARIO);
        assertThat(testVendaConsumo.getValorTotal()).isEqualTo(DEFAULT_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void createVendaConsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendaConsumoRepository.findAll().size();

        // Create the VendaConsumo with an existing ID
        vendaConsumo.setId(1L);
        VendaConsumoDTO vendaConsumoDTO = vendaConsumoMapper.toDto(vendaConsumo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendaConsumoMockMvc.perform(post("/api/venda-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaConsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VendaConsumo in the database
        List<VendaConsumo> vendaConsumoList = vendaConsumoRepository.findAll();
        assertThat(vendaConsumoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVendaConsumos() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList
        restVendaConsumoMockMvc.perform(get("/api/venda-consumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendaConsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.intValue())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getVendaConsumo() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get the vendaConsumo
        restVendaConsumoMockMvc.perform(get("/api/venda-consumos/{id}", vendaConsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vendaConsumo.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.intValue()))
            .andExpect(jsonPath("$.valorUnitario").value(DEFAULT_VALOR_UNITARIO.intValue()))
            .andExpect(jsonPath("$.valorTotal").value(DEFAULT_VALOR_TOTAL.intValue()));
    }


    @Test
    @Transactional
    public void getVendaConsumosByIdFiltering() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        Long id = vendaConsumo.getId();

        defaultVendaConsumoShouldBeFound("id.equals=" + id);
        defaultVendaConsumoShouldNotBeFound("id.notEquals=" + id);

        defaultVendaConsumoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVendaConsumoShouldNotBeFound("id.greaterThan=" + id);

        defaultVendaConsumoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVendaConsumoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVendaConsumosByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where quantidade equals to DEFAULT_QUANTIDADE
        defaultVendaConsumoShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the vendaConsumoList where quantidade equals to UPDATED_QUANTIDADE
        defaultVendaConsumoShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultVendaConsumoShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the vendaConsumoList where quantidade not equals to UPDATED_QUANTIDADE
        defaultVendaConsumoShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultVendaConsumoShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the vendaConsumoList where quantidade equals to UPDATED_QUANTIDADE
        defaultVendaConsumoShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where quantidade is not null
        defaultVendaConsumoShouldBeFound("quantidade.specified=true");

        // Get all the vendaConsumoList where quantidade is null
        defaultVendaConsumoShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultVendaConsumoShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the vendaConsumoList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultVendaConsumoShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultVendaConsumoShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the vendaConsumoList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultVendaConsumoShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where quantidade is less than DEFAULT_QUANTIDADE
        defaultVendaConsumoShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the vendaConsumoList where quantidade is less than UPDATED_QUANTIDADE
        defaultVendaConsumoShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultVendaConsumoShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the vendaConsumoList where quantidade is greater than SMALLER_QUANTIDADE
        defaultVendaConsumoShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllVendaConsumosByValorUnitarioIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorUnitario equals to DEFAULT_VALOR_UNITARIO
        defaultVendaConsumoShouldBeFound("valorUnitario.equals=" + DEFAULT_VALOR_UNITARIO);

        // Get all the vendaConsumoList where valorUnitario equals to UPDATED_VALOR_UNITARIO
        defaultVendaConsumoShouldNotBeFound("valorUnitario.equals=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorUnitarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorUnitario not equals to DEFAULT_VALOR_UNITARIO
        defaultVendaConsumoShouldNotBeFound("valorUnitario.notEquals=" + DEFAULT_VALOR_UNITARIO);

        // Get all the vendaConsumoList where valorUnitario not equals to UPDATED_VALOR_UNITARIO
        defaultVendaConsumoShouldBeFound("valorUnitario.notEquals=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorUnitarioIsInShouldWork() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorUnitario in DEFAULT_VALOR_UNITARIO or UPDATED_VALOR_UNITARIO
        defaultVendaConsumoShouldBeFound("valorUnitario.in=" + DEFAULT_VALOR_UNITARIO + "," + UPDATED_VALOR_UNITARIO);

        // Get all the vendaConsumoList where valorUnitario equals to UPDATED_VALOR_UNITARIO
        defaultVendaConsumoShouldNotBeFound("valorUnitario.in=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorUnitarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorUnitario is not null
        defaultVendaConsumoShouldBeFound("valorUnitario.specified=true");

        // Get all the vendaConsumoList where valorUnitario is null
        defaultVendaConsumoShouldNotBeFound("valorUnitario.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorUnitarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorUnitario is greater than or equal to DEFAULT_VALOR_UNITARIO
        defaultVendaConsumoShouldBeFound("valorUnitario.greaterThanOrEqual=" + DEFAULT_VALOR_UNITARIO);

        // Get all the vendaConsumoList where valorUnitario is greater than or equal to UPDATED_VALOR_UNITARIO
        defaultVendaConsumoShouldNotBeFound("valorUnitario.greaterThanOrEqual=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorUnitarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorUnitario is less than or equal to DEFAULT_VALOR_UNITARIO
        defaultVendaConsumoShouldBeFound("valorUnitario.lessThanOrEqual=" + DEFAULT_VALOR_UNITARIO);

        // Get all the vendaConsumoList where valorUnitario is less than or equal to SMALLER_VALOR_UNITARIO
        defaultVendaConsumoShouldNotBeFound("valorUnitario.lessThanOrEqual=" + SMALLER_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorUnitarioIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorUnitario is less than DEFAULT_VALOR_UNITARIO
        defaultVendaConsumoShouldNotBeFound("valorUnitario.lessThan=" + DEFAULT_VALOR_UNITARIO);

        // Get all the vendaConsumoList where valorUnitario is less than UPDATED_VALOR_UNITARIO
        defaultVendaConsumoShouldBeFound("valorUnitario.lessThan=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorUnitarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorUnitario is greater than DEFAULT_VALOR_UNITARIO
        defaultVendaConsumoShouldNotBeFound("valorUnitario.greaterThan=" + DEFAULT_VALOR_UNITARIO);

        // Get all the vendaConsumoList where valorUnitario is greater than SMALLER_VALOR_UNITARIO
        defaultVendaConsumoShouldBeFound("valorUnitario.greaterThan=" + SMALLER_VALOR_UNITARIO);
    }


    @Test
    @Transactional
    public void getAllVendaConsumosByValorTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorTotal equals to DEFAULT_VALOR_TOTAL
        defaultVendaConsumoShouldBeFound("valorTotal.equals=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaConsumoList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultVendaConsumoShouldNotBeFound("valorTotal.equals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorTotal not equals to DEFAULT_VALOR_TOTAL
        defaultVendaConsumoShouldNotBeFound("valorTotal.notEquals=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaConsumoList where valorTotal not equals to UPDATED_VALOR_TOTAL
        defaultVendaConsumoShouldBeFound("valorTotal.notEquals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorTotalIsInShouldWork() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorTotal in DEFAULT_VALOR_TOTAL or UPDATED_VALOR_TOTAL
        defaultVendaConsumoShouldBeFound("valorTotal.in=" + DEFAULT_VALOR_TOTAL + "," + UPDATED_VALOR_TOTAL);

        // Get all the vendaConsumoList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultVendaConsumoShouldNotBeFound("valorTotal.in=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorTotal is not null
        defaultVendaConsumoShouldBeFound("valorTotal.specified=true");

        // Get all the vendaConsumoList where valorTotal is null
        defaultVendaConsumoShouldNotBeFound("valorTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorTotal is greater than or equal to DEFAULT_VALOR_TOTAL
        defaultVendaConsumoShouldBeFound("valorTotal.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaConsumoList where valorTotal is greater than or equal to UPDATED_VALOR_TOTAL
        defaultVendaConsumoShouldNotBeFound("valorTotal.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorTotal is less than or equal to DEFAULT_VALOR_TOTAL
        defaultVendaConsumoShouldBeFound("valorTotal.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaConsumoList where valorTotal is less than or equal to SMALLER_VALOR_TOTAL
        defaultVendaConsumoShouldNotBeFound("valorTotal.lessThanOrEqual=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorTotal is less than DEFAULT_VALOR_TOTAL
        defaultVendaConsumoShouldNotBeFound("valorTotal.lessThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaConsumoList where valorTotal is less than UPDATED_VALOR_TOTAL
        defaultVendaConsumoShouldBeFound("valorTotal.lessThan=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void getAllVendaConsumosByValorTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        // Get all the vendaConsumoList where valorTotal is greater than DEFAULT_VALOR_TOTAL
        defaultVendaConsumoShouldNotBeFound("valorTotal.greaterThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the vendaConsumoList where valorTotal is greater than SMALLER_VALOR_TOTAL
        defaultVendaConsumoShouldBeFound("valorTotal.greaterThan=" + SMALLER_VALOR_TOTAL);
    }


    @Test
    @Transactional
    public void getAllVendaConsumosByVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);
        Venda venda = VendaResourceIT.createEntity(em);
        em.persist(venda);
        em.flush();
        vendaConsumo.setVenda(venda);
        vendaConsumoRepository.saveAndFlush(vendaConsumo);
        Long vendaId = venda.getId();

        // Get all the vendaConsumoList where venda equals to vendaId
        defaultVendaConsumoShouldBeFound("vendaId.equals=" + vendaId);

        // Get all the vendaConsumoList where venda equals to vendaId + 1
        defaultVendaConsumoShouldNotBeFound("vendaId.equals=" + (vendaId + 1));
    }


    @Test
    @Transactional
    public void getAllVendaConsumosByConsumoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);
        Consumo consumo = ConsumoResourceIT.createEntity(em);
        em.persist(consumo);
        em.flush();
        vendaConsumo.setConsumo(consumo);
        vendaConsumoRepository.saveAndFlush(vendaConsumo);
        Long consumoId = consumo.getId();

        // Get all the vendaConsumoList where consumo equals to consumoId
        defaultVendaConsumoShouldBeFound("consumoId.equals=" + consumoId);

        // Get all the vendaConsumoList where consumo equals to consumoId + 1
        defaultVendaConsumoShouldNotBeFound("consumoId.equals=" + (consumoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVendaConsumoShouldBeFound(String filter) throws Exception {
        restVendaConsumoMockMvc.perform(get("/api/venda-consumos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendaConsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.intValue())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())));

        // Check, that the count call also returns 1
        restVendaConsumoMockMvc.perform(get("/api/venda-consumos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVendaConsumoShouldNotBeFound(String filter) throws Exception {
        restVendaConsumoMockMvc.perform(get("/api/venda-consumos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendaConsumoMockMvc.perform(get("/api/venda-consumos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVendaConsumo() throws Exception {
        // Get the vendaConsumo
        restVendaConsumoMockMvc.perform(get("/api/venda-consumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendaConsumo() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        int databaseSizeBeforeUpdate = vendaConsumoRepository.findAll().size();

        // Update the vendaConsumo
        VendaConsumo updatedVendaConsumo = vendaConsumoRepository.findById(vendaConsumo.getId()).get();
        // Disconnect from session so that the updates on updatedVendaConsumo are not directly saved in db
        em.detach(updatedVendaConsumo);
        updatedVendaConsumo
            .quantidade(UPDATED_QUANTIDADE)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .valorTotal(UPDATED_VALOR_TOTAL);
        VendaConsumoDTO vendaConsumoDTO = vendaConsumoMapper.toDto(updatedVendaConsumo);

        restVendaConsumoMockMvc.perform(put("/api/venda-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaConsumoDTO)))
            .andExpect(status().isOk());

        // Validate the VendaConsumo in the database
        List<VendaConsumo> vendaConsumoList = vendaConsumoRepository.findAll();
        assertThat(vendaConsumoList).hasSize(databaseSizeBeforeUpdate);
        VendaConsumo testVendaConsumo = vendaConsumoList.get(vendaConsumoList.size() - 1);
        assertThat(testVendaConsumo.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testVendaConsumo.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testVendaConsumo.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingVendaConsumo() throws Exception {
        int databaseSizeBeforeUpdate = vendaConsumoRepository.findAll().size();

        // Create the VendaConsumo
        VendaConsumoDTO vendaConsumoDTO = vendaConsumoMapper.toDto(vendaConsumo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendaConsumoMockMvc.perform(put("/api/venda-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaConsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VendaConsumo in the database
        List<VendaConsumo> vendaConsumoList = vendaConsumoRepository.findAll();
        assertThat(vendaConsumoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVendaConsumo() throws Exception {
        // Initialize the database
        vendaConsumoRepository.saveAndFlush(vendaConsumo);

        int databaseSizeBeforeDelete = vendaConsumoRepository.findAll().size();

        // Delete the vendaConsumo
        restVendaConsumoMockMvc.perform(delete("/api/venda-consumos/{id}", vendaConsumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VendaConsumo> vendaConsumoList = vendaConsumoRepository.findAll();
        assertThat(vendaConsumoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
