package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Consumo;
import br.com.ngzorro.domain.VendaConsumo;
import br.com.ngzorro.domain.MovimentacaoDeEstoque;
import br.com.ngzorro.repository.ConsumoRepository;
import br.com.ngzorro.service.ConsumoService;
import br.com.ngzorro.service.dto.ConsumoDTO;
import br.com.ngzorro.service.mapper.ConsumoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.ConsumoCriteria;
import br.com.ngzorro.service.ConsumoQueryService;

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

import br.com.ngzorro.domain.enumeration.TipoConsumo;
/**
 * Integration tests for the {@link ConsumoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class ConsumoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final TipoConsumo DEFAULT_TIPO = TipoConsumo.PRODUTO;
    private static final TipoConsumo UPDATED_TIPO = TipoConsumo.SERVICO;

    private static final BigDecimal DEFAULT_ESTOQUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ESTOQUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_ESTOQUE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_UNITARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_UNITARIO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_UNITARIO = new BigDecimal(1 - 1);

    @Autowired
    private ConsumoRepository consumoRepository;

    @Autowired
    private ConsumoMapper consumoMapper;

    @Autowired
    private ConsumoService consumoService;

    @Autowired
    private ConsumoQueryService consumoQueryService;

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

    private MockMvc restConsumoMockMvc;

    private Consumo consumo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsumoResource consumoResource = new ConsumoResource(consumoService, consumoQueryService);
        this.restConsumoMockMvc = MockMvcBuilders.standaloneSetup(consumoResource)
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
    public static Consumo createEntity(EntityManager em) {
        Consumo consumo = new Consumo()
            .nome(DEFAULT_NOME)
            .tipo(DEFAULT_TIPO)
            .estoque(DEFAULT_ESTOQUE)
            .valorUnitario(DEFAULT_VALOR_UNITARIO);
        return consumo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consumo createUpdatedEntity(EntityManager em) {
        Consumo consumo = new Consumo()
            .nome(UPDATED_NOME)
            .tipo(UPDATED_TIPO)
            .estoque(UPDATED_ESTOQUE)
            .valorUnitario(UPDATED_VALOR_UNITARIO);
        return consumo;
    }

    @BeforeEach
    public void initTest() {
        consumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsumo() throws Exception {
        int databaseSizeBeforeCreate = consumoRepository.findAll().size();

        // Create the Consumo
        ConsumoDTO consumoDTO = consumoMapper.toDto(consumo);
        restConsumoMockMvc.perform(post("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumoDTO)))
            .andExpect(status().isCreated());

        // Validate the Consumo in the database
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeCreate + 1);
        Consumo testConsumo = consumoList.get(consumoList.size() - 1);
        assertThat(testConsumo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConsumo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testConsumo.getEstoque()).isEqualTo(DEFAULT_ESTOQUE);
        assertThat(testConsumo.getValorUnitario()).isEqualTo(DEFAULT_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void createConsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consumoRepository.findAll().size();

        // Create the Consumo with an existing ID
        consumo.setId(1L);
        ConsumoDTO consumoDTO = consumoMapper.toDto(consumo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumoMockMvc.perform(post("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consumo in the database
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConsumos() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList
        restConsumoMockMvc.perform(get("/api/consumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].estoque").value(hasItem(DEFAULT_ESTOQUE.intValue())))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.intValue())));
    }
    
    @Test
    @Transactional
    public void getConsumo() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get the consumo
        restConsumoMockMvc.perform(get("/api/consumos/{id}", consumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consumo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.estoque").value(DEFAULT_ESTOQUE.intValue()))
            .andExpect(jsonPath("$.valorUnitario").value(DEFAULT_VALOR_UNITARIO.intValue()));
    }


    @Test
    @Transactional
    public void getConsumosByIdFiltering() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        Long id = consumo.getId();

        defaultConsumoShouldBeFound("id.equals=" + id);
        defaultConsumoShouldNotBeFound("id.notEquals=" + id);

        defaultConsumoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConsumoShouldNotBeFound("id.greaterThan=" + id);

        defaultConsumoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConsumoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConsumosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where nome equals to DEFAULT_NOME
        defaultConsumoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the consumoList where nome equals to UPDATED_NOME
        defaultConsumoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsumosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where nome not equals to DEFAULT_NOME
        defaultConsumoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the consumoList where nome not equals to UPDATED_NOME
        defaultConsumoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsumosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultConsumoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the consumoList where nome equals to UPDATED_NOME
        defaultConsumoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsumosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where nome is not null
        defaultConsumoShouldBeFound("nome.specified=true");

        // Get all the consumoList where nome is null
        defaultConsumoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsumosByNomeContainsSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where nome contains DEFAULT_NOME
        defaultConsumoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the consumoList where nome contains UPDATED_NOME
        defaultConsumoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsumosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where nome does not contain DEFAULT_NOME
        defaultConsumoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the consumoList where nome does not contain UPDATED_NOME
        defaultConsumoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllConsumosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where tipo equals to DEFAULT_TIPO
        defaultConsumoShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the consumoList where tipo equals to UPDATED_TIPO
        defaultConsumoShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllConsumosByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where tipo not equals to DEFAULT_TIPO
        defaultConsumoShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the consumoList where tipo not equals to UPDATED_TIPO
        defaultConsumoShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllConsumosByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultConsumoShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the consumoList where tipo equals to UPDATED_TIPO
        defaultConsumoShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllConsumosByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where tipo is not null
        defaultConsumoShouldBeFound("tipo.specified=true");

        // Get all the consumoList where tipo is null
        defaultConsumoShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsumosByEstoqueIsEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where estoque equals to DEFAULT_ESTOQUE
        defaultConsumoShouldBeFound("estoque.equals=" + DEFAULT_ESTOQUE);

        // Get all the consumoList where estoque equals to UPDATED_ESTOQUE
        defaultConsumoShouldNotBeFound("estoque.equals=" + UPDATED_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllConsumosByEstoqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where estoque not equals to DEFAULT_ESTOQUE
        defaultConsumoShouldNotBeFound("estoque.notEquals=" + DEFAULT_ESTOQUE);

        // Get all the consumoList where estoque not equals to UPDATED_ESTOQUE
        defaultConsumoShouldBeFound("estoque.notEquals=" + UPDATED_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllConsumosByEstoqueIsInShouldWork() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where estoque in DEFAULT_ESTOQUE or UPDATED_ESTOQUE
        defaultConsumoShouldBeFound("estoque.in=" + DEFAULT_ESTOQUE + "," + UPDATED_ESTOQUE);

        // Get all the consumoList where estoque equals to UPDATED_ESTOQUE
        defaultConsumoShouldNotBeFound("estoque.in=" + UPDATED_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllConsumosByEstoqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where estoque is not null
        defaultConsumoShouldBeFound("estoque.specified=true");

        // Get all the consumoList where estoque is null
        defaultConsumoShouldNotBeFound("estoque.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsumosByEstoqueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where estoque is greater than or equal to DEFAULT_ESTOQUE
        defaultConsumoShouldBeFound("estoque.greaterThanOrEqual=" + DEFAULT_ESTOQUE);

        // Get all the consumoList where estoque is greater than or equal to UPDATED_ESTOQUE
        defaultConsumoShouldNotBeFound("estoque.greaterThanOrEqual=" + UPDATED_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllConsumosByEstoqueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where estoque is less than or equal to DEFAULT_ESTOQUE
        defaultConsumoShouldBeFound("estoque.lessThanOrEqual=" + DEFAULT_ESTOQUE);

        // Get all the consumoList where estoque is less than or equal to SMALLER_ESTOQUE
        defaultConsumoShouldNotBeFound("estoque.lessThanOrEqual=" + SMALLER_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllConsumosByEstoqueIsLessThanSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where estoque is less than DEFAULT_ESTOQUE
        defaultConsumoShouldNotBeFound("estoque.lessThan=" + DEFAULT_ESTOQUE);

        // Get all the consumoList where estoque is less than UPDATED_ESTOQUE
        defaultConsumoShouldBeFound("estoque.lessThan=" + UPDATED_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllConsumosByEstoqueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where estoque is greater than DEFAULT_ESTOQUE
        defaultConsumoShouldNotBeFound("estoque.greaterThan=" + DEFAULT_ESTOQUE);

        // Get all the consumoList where estoque is greater than SMALLER_ESTOQUE
        defaultConsumoShouldBeFound("estoque.greaterThan=" + SMALLER_ESTOQUE);
    }


    @Test
    @Transactional
    public void getAllConsumosByValorUnitarioIsEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where valorUnitario equals to DEFAULT_VALOR_UNITARIO
        defaultConsumoShouldBeFound("valorUnitario.equals=" + DEFAULT_VALOR_UNITARIO);

        // Get all the consumoList where valorUnitario equals to UPDATED_VALOR_UNITARIO
        defaultConsumoShouldNotBeFound("valorUnitario.equals=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllConsumosByValorUnitarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where valorUnitario not equals to DEFAULT_VALOR_UNITARIO
        defaultConsumoShouldNotBeFound("valorUnitario.notEquals=" + DEFAULT_VALOR_UNITARIO);

        // Get all the consumoList where valorUnitario not equals to UPDATED_VALOR_UNITARIO
        defaultConsumoShouldBeFound("valorUnitario.notEquals=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllConsumosByValorUnitarioIsInShouldWork() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where valorUnitario in DEFAULT_VALOR_UNITARIO or UPDATED_VALOR_UNITARIO
        defaultConsumoShouldBeFound("valorUnitario.in=" + DEFAULT_VALOR_UNITARIO + "," + UPDATED_VALOR_UNITARIO);

        // Get all the consumoList where valorUnitario equals to UPDATED_VALOR_UNITARIO
        defaultConsumoShouldNotBeFound("valorUnitario.in=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllConsumosByValorUnitarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where valorUnitario is not null
        defaultConsumoShouldBeFound("valorUnitario.specified=true");

        // Get all the consumoList where valorUnitario is null
        defaultConsumoShouldNotBeFound("valorUnitario.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsumosByValorUnitarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where valorUnitario is greater than or equal to DEFAULT_VALOR_UNITARIO
        defaultConsumoShouldBeFound("valorUnitario.greaterThanOrEqual=" + DEFAULT_VALOR_UNITARIO);

        // Get all the consumoList where valorUnitario is greater than or equal to UPDATED_VALOR_UNITARIO
        defaultConsumoShouldNotBeFound("valorUnitario.greaterThanOrEqual=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllConsumosByValorUnitarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where valorUnitario is less than or equal to DEFAULT_VALOR_UNITARIO
        defaultConsumoShouldBeFound("valorUnitario.lessThanOrEqual=" + DEFAULT_VALOR_UNITARIO);

        // Get all the consumoList where valorUnitario is less than or equal to SMALLER_VALOR_UNITARIO
        defaultConsumoShouldNotBeFound("valorUnitario.lessThanOrEqual=" + SMALLER_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllConsumosByValorUnitarioIsLessThanSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where valorUnitario is less than DEFAULT_VALOR_UNITARIO
        defaultConsumoShouldNotBeFound("valorUnitario.lessThan=" + DEFAULT_VALOR_UNITARIO);

        // Get all the consumoList where valorUnitario is less than UPDATED_VALOR_UNITARIO
        defaultConsumoShouldBeFound("valorUnitario.lessThan=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllConsumosByValorUnitarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList where valorUnitario is greater than DEFAULT_VALOR_UNITARIO
        defaultConsumoShouldNotBeFound("valorUnitario.greaterThan=" + DEFAULT_VALOR_UNITARIO);

        // Get all the consumoList where valorUnitario is greater than SMALLER_VALOR_UNITARIO
        defaultConsumoShouldBeFound("valorUnitario.greaterThan=" + SMALLER_VALOR_UNITARIO);
    }


    @Test
    @Transactional
    public void getAllConsumosByVendaConsumoIsEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);
        VendaConsumo vendaConsumo = VendaConsumoResourceIT.createEntity(em);
        em.persist(vendaConsumo);
        em.flush();
        consumo.addVendaConsumo(vendaConsumo);
        consumoRepository.saveAndFlush(consumo);
        Long vendaConsumoId = vendaConsumo.getId();

        // Get all the consumoList where vendaConsumo equals to vendaConsumoId
        defaultConsumoShouldBeFound("vendaConsumoId.equals=" + vendaConsumoId);

        // Get all the consumoList where vendaConsumo equals to vendaConsumoId + 1
        defaultConsumoShouldNotBeFound("vendaConsumoId.equals=" + (vendaConsumoId + 1));
    }


    @Test
    @Transactional
    public void getAllConsumosByMovimentacaoDeEstoqueIsEqualToSomething() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);
        MovimentacaoDeEstoque movimentacaoDeEstoque = MovimentacaoDeEstoqueResourceIT.createEntity(em);
        em.persist(movimentacaoDeEstoque);
        em.flush();
        consumo.addMovimentacaoDeEstoque(movimentacaoDeEstoque);
        consumoRepository.saveAndFlush(consumo);
        Long movimentacaoDeEstoqueId = movimentacaoDeEstoque.getId();

        // Get all the consumoList where movimentacaoDeEstoque equals to movimentacaoDeEstoqueId
        defaultConsumoShouldBeFound("movimentacaoDeEstoqueId.equals=" + movimentacaoDeEstoqueId);

        // Get all the consumoList where movimentacaoDeEstoque equals to movimentacaoDeEstoqueId + 1
        defaultConsumoShouldNotBeFound("movimentacaoDeEstoqueId.equals=" + (movimentacaoDeEstoqueId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConsumoShouldBeFound(String filter) throws Exception {
        restConsumoMockMvc.perform(get("/api/consumos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].estoque").value(hasItem(DEFAULT_ESTOQUE.intValue())))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.intValue())));

        // Check, that the count call also returns 1
        restConsumoMockMvc.perform(get("/api/consumos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConsumoShouldNotBeFound(String filter) throws Exception {
        restConsumoMockMvc.perform(get("/api/consumos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConsumoMockMvc.perform(get("/api/consumos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingConsumo() throws Exception {
        // Get the consumo
        restConsumoMockMvc.perform(get("/api/consumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsumo() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        int databaseSizeBeforeUpdate = consumoRepository.findAll().size();

        // Update the consumo
        Consumo updatedConsumo = consumoRepository.findById(consumo.getId()).get();
        // Disconnect from session so that the updates on updatedConsumo are not directly saved in db
        em.detach(updatedConsumo);
        updatedConsumo
            .nome(UPDATED_NOME)
            .tipo(UPDATED_TIPO)
            .estoque(UPDATED_ESTOQUE)
            .valorUnitario(UPDATED_VALOR_UNITARIO);
        ConsumoDTO consumoDTO = consumoMapper.toDto(updatedConsumo);

        restConsumoMockMvc.perform(put("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumoDTO)))
            .andExpect(status().isOk());

        // Validate the Consumo in the database
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeUpdate);
        Consumo testConsumo = consumoList.get(consumoList.size() - 1);
        assertThat(testConsumo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConsumo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testConsumo.getEstoque()).isEqualTo(UPDATED_ESTOQUE);
        assertThat(testConsumo.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingConsumo() throws Exception {
        int databaseSizeBeforeUpdate = consumoRepository.findAll().size();

        // Create the Consumo
        ConsumoDTO consumoDTO = consumoMapper.toDto(consumo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumoMockMvc.perform(put("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consumo in the database
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsumo() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        int databaseSizeBeforeDelete = consumoRepository.findAll().size();

        // Delete the consumo
        restConsumoMockMvc.perform(delete("/api/consumos/{id}", consumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
