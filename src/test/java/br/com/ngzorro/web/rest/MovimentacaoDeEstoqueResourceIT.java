package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.MovimentacaoDeEstoque;
import br.com.ngzorro.domain.Consumo;
import br.com.ngzorro.repository.MovimentacaoDeEstoqueRepository;
import br.com.ngzorro.service.MovimentacaoDeEstoqueService;
import br.com.ngzorro.service.dto.MovimentacaoDeEstoqueDTO;
import br.com.ngzorro.service.mapper.MovimentacaoDeEstoqueMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.MovimentacaoDeEstoqueCriteria;
import br.com.ngzorro.service.MovimentacaoDeEstoqueQueryService;

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

import br.com.ngzorro.domain.enumeration.TipoMovimentacaoDeEstoque;
/**
 * Integration tests for the {@link MovimentacaoDeEstoqueResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class MovimentacaoDeEstoqueResourceIT {

    private static final TipoMovimentacaoDeEstoque DEFAULT_TIPO = TipoMovimentacaoDeEstoque.ENTRADA;
    private static final TipoMovimentacaoDeEstoque UPDATED_TIPO = TipoMovimentacaoDeEstoque.SAIDA;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Double DEFAULT_QUANTIDADE = 1D;
    private static final Double UPDATED_QUANTIDADE = 2D;
    private static final Double SMALLER_QUANTIDADE = 1D - 1D;

    @Autowired
    private MovimentacaoDeEstoqueRepository movimentacaoDeEstoqueRepository;

    @Autowired
    private MovimentacaoDeEstoqueMapper movimentacaoDeEstoqueMapper;

    @Autowired
    private MovimentacaoDeEstoqueService movimentacaoDeEstoqueService;

    @Autowired
    private MovimentacaoDeEstoqueQueryService movimentacaoDeEstoqueQueryService;

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

    private MockMvc restMovimentacaoDeEstoqueMockMvc;

    private MovimentacaoDeEstoque movimentacaoDeEstoque;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovimentacaoDeEstoqueResource movimentacaoDeEstoqueResource = new MovimentacaoDeEstoqueResource(movimentacaoDeEstoqueService, movimentacaoDeEstoqueQueryService);
        this.restMovimentacaoDeEstoqueMockMvc = MockMvcBuilders.standaloneSetup(movimentacaoDeEstoqueResource)
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
    public static MovimentacaoDeEstoque createEntity(EntityManager em) {
        MovimentacaoDeEstoque movimentacaoDeEstoque = new MovimentacaoDeEstoque()
            .tipo(DEFAULT_TIPO)
            .descricao(DEFAULT_DESCRICAO)
            .data(DEFAULT_DATA)
            .quantidade(DEFAULT_QUANTIDADE);
        return movimentacaoDeEstoque;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovimentacaoDeEstoque createUpdatedEntity(EntityManager em) {
        MovimentacaoDeEstoque movimentacaoDeEstoque = new MovimentacaoDeEstoque()
            .tipo(UPDATED_TIPO)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .quantidade(UPDATED_QUANTIDADE);
        return movimentacaoDeEstoque;
    }

    @BeforeEach
    public void initTest() {
        movimentacaoDeEstoque = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovimentacaoDeEstoque() throws Exception {
        int databaseSizeBeforeCreate = movimentacaoDeEstoqueRepository.findAll().size();

        // Create the MovimentacaoDeEstoque
        MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO = movimentacaoDeEstoqueMapper.toDto(movimentacaoDeEstoque);
        restMovimentacaoDeEstoqueMockMvc.perform(post("/api/movimentacao-de-estoques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimentacaoDeEstoqueDTO)))
            .andExpect(status().isCreated());

        // Validate the MovimentacaoDeEstoque in the database
        List<MovimentacaoDeEstoque> movimentacaoDeEstoqueList = movimentacaoDeEstoqueRepository.findAll();
        assertThat(movimentacaoDeEstoqueList).hasSize(databaseSizeBeforeCreate + 1);
        MovimentacaoDeEstoque testMovimentacaoDeEstoque = movimentacaoDeEstoqueList.get(movimentacaoDeEstoqueList.size() - 1);
        assertThat(testMovimentacaoDeEstoque.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testMovimentacaoDeEstoque.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMovimentacaoDeEstoque.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testMovimentacaoDeEstoque.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
    }

    @Test
    @Transactional
    public void createMovimentacaoDeEstoqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movimentacaoDeEstoqueRepository.findAll().size();

        // Create the MovimentacaoDeEstoque with an existing ID
        movimentacaoDeEstoque.setId(1L);
        MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO = movimentacaoDeEstoqueMapper.toDto(movimentacaoDeEstoque);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovimentacaoDeEstoqueMockMvc.perform(post("/api/movimentacao-de-estoques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimentacaoDeEstoqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovimentacaoDeEstoque in the database
        List<MovimentacaoDeEstoque> movimentacaoDeEstoqueList = movimentacaoDeEstoqueRepository.findAll();
        assertThat(movimentacaoDeEstoqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoques() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList
        restMovimentacaoDeEstoqueMockMvc.perform(get("/api/movimentacao-de-estoques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimentacaoDeEstoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMovimentacaoDeEstoque() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get the movimentacaoDeEstoque
        restMovimentacaoDeEstoqueMockMvc.perform(get("/api/movimentacao-de-estoques/{id}", movimentacaoDeEstoque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movimentacaoDeEstoque.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.doubleValue()));
    }


    @Test
    @Transactional
    public void getMovimentacaoDeEstoquesByIdFiltering() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        Long id = movimentacaoDeEstoque.getId();

        defaultMovimentacaoDeEstoqueShouldBeFound("id.equals=" + id);
        defaultMovimentacaoDeEstoqueShouldNotBeFound("id.notEquals=" + id);

        defaultMovimentacaoDeEstoqueShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMovimentacaoDeEstoqueShouldNotBeFound("id.greaterThan=" + id);

        defaultMovimentacaoDeEstoqueShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMovimentacaoDeEstoqueShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where tipo equals to DEFAULT_TIPO
        defaultMovimentacaoDeEstoqueShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the movimentacaoDeEstoqueList where tipo equals to UPDATED_TIPO
        defaultMovimentacaoDeEstoqueShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where tipo not equals to DEFAULT_TIPO
        defaultMovimentacaoDeEstoqueShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the movimentacaoDeEstoqueList where tipo not equals to UPDATED_TIPO
        defaultMovimentacaoDeEstoqueShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultMovimentacaoDeEstoqueShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the movimentacaoDeEstoqueList where tipo equals to UPDATED_TIPO
        defaultMovimentacaoDeEstoqueShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where tipo is not null
        defaultMovimentacaoDeEstoqueShouldBeFound("tipo.specified=true");

        // Get all the movimentacaoDeEstoqueList where tipo is null
        defaultMovimentacaoDeEstoqueShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where data equals to DEFAULT_DATA
        defaultMovimentacaoDeEstoqueShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the movimentacaoDeEstoqueList where data equals to UPDATED_DATA
        defaultMovimentacaoDeEstoqueShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where data not equals to DEFAULT_DATA
        defaultMovimentacaoDeEstoqueShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the movimentacaoDeEstoqueList where data not equals to UPDATED_DATA
        defaultMovimentacaoDeEstoqueShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByDataIsInShouldWork() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where data in DEFAULT_DATA or UPDATED_DATA
        defaultMovimentacaoDeEstoqueShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the movimentacaoDeEstoqueList where data equals to UPDATED_DATA
        defaultMovimentacaoDeEstoqueShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where data is not null
        defaultMovimentacaoDeEstoqueShouldBeFound("data.specified=true");

        // Get all the movimentacaoDeEstoqueList where data is null
        defaultMovimentacaoDeEstoqueShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where data is greater than or equal to DEFAULT_DATA
        defaultMovimentacaoDeEstoqueShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the movimentacaoDeEstoqueList where data is greater than or equal to UPDATED_DATA
        defaultMovimentacaoDeEstoqueShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where data is less than or equal to DEFAULT_DATA
        defaultMovimentacaoDeEstoqueShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the movimentacaoDeEstoqueList where data is less than or equal to SMALLER_DATA
        defaultMovimentacaoDeEstoqueShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where data is less than DEFAULT_DATA
        defaultMovimentacaoDeEstoqueShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the movimentacaoDeEstoqueList where data is less than UPDATED_DATA
        defaultMovimentacaoDeEstoqueShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where data is greater than DEFAULT_DATA
        defaultMovimentacaoDeEstoqueShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the movimentacaoDeEstoqueList where data is greater than SMALLER_DATA
        defaultMovimentacaoDeEstoqueShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where quantidade equals to DEFAULT_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the movimentacaoDeEstoqueList where quantidade equals to UPDATED_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the movimentacaoDeEstoqueList where quantidade not equals to UPDATED_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the movimentacaoDeEstoqueList where quantidade equals to UPDATED_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where quantidade is not null
        defaultMovimentacaoDeEstoqueShouldBeFound("quantidade.specified=true");

        // Get all the movimentacaoDeEstoqueList where quantidade is null
        defaultMovimentacaoDeEstoqueShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the movimentacaoDeEstoqueList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the movimentacaoDeEstoqueList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where quantidade is less than DEFAULT_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the movimentacaoDeEstoqueList where quantidade is less than UPDATED_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        // Get all the movimentacaoDeEstoqueList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the movimentacaoDeEstoqueList where quantidade is greater than SMALLER_QUANTIDADE
        defaultMovimentacaoDeEstoqueShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllMovimentacaoDeEstoquesByConsumoIsEqualToSomething() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);
        Consumo consumo = ConsumoResourceIT.createEntity(em);
        em.persist(consumo);
        em.flush();
        movimentacaoDeEstoque.setConsumo(consumo);
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);
        Long consumoId = consumo.getId();

        // Get all the movimentacaoDeEstoqueList where consumo equals to consumoId
        defaultMovimentacaoDeEstoqueShouldBeFound("consumoId.equals=" + consumoId);

        // Get all the movimentacaoDeEstoqueList where consumo equals to consumoId + 1
        defaultMovimentacaoDeEstoqueShouldNotBeFound("consumoId.equals=" + (consumoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMovimentacaoDeEstoqueShouldBeFound(String filter) throws Exception {
        restMovimentacaoDeEstoqueMockMvc.perform(get("/api/movimentacao-de-estoques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimentacaoDeEstoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())));

        // Check, that the count call also returns 1
        restMovimentacaoDeEstoqueMockMvc.perform(get("/api/movimentacao-de-estoques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMovimentacaoDeEstoqueShouldNotBeFound(String filter) throws Exception {
        restMovimentacaoDeEstoqueMockMvc.perform(get("/api/movimentacao-de-estoques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMovimentacaoDeEstoqueMockMvc.perform(get("/api/movimentacao-de-estoques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMovimentacaoDeEstoque() throws Exception {
        // Get the movimentacaoDeEstoque
        restMovimentacaoDeEstoqueMockMvc.perform(get("/api/movimentacao-de-estoques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovimentacaoDeEstoque() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        int databaseSizeBeforeUpdate = movimentacaoDeEstoqueRepository.findAll().size();

        // Update the movimentacaoDeEstoque
        MovimentacaoDeEstoque updatedMovimentacaoDeEstoque = movimentacaoDeEstoqueRepository.findById(movimentacaoDeEstoque.getId()).get();
        // Disconnect from session so that the updates on updatedMovimentacaoDeEstoque are not directly saved in db
        em.detach(updatedMovimentacaoDeEstoque);
        updatedMovimentacaoDeEstoque
            .tipo(UPDATED_TIPO)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .quantidade(UPDATED_QUANTIDADE);
        MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO = movimentacaoDeEstoqueMapper.toDto(updatedMovimentacaoDeEstoque);

        restMovimentacaoDeEstoqueMockMvc.perform(put("/api/movimentacao-de-estoques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimentacaoDeEstoqueDTO)))
            .andExpect(status().isOk());

        // Validate the MovimentacaoDeEstoque in the database
        List<MovimentacaoDeEstoque> movimentacaoDeEstoqueList = movimentacaoDeEstoqueRepository.findAll();
        assertThat(movimentacaoDeEstoqueList).hasSize(databaseSizeBeforeUpdate);
        MovimentacaoDeEstoque testMovimentacaoDeEstoque = movimentacaoDeEstoqueList.get(movimentacaoDeEstoqueList.size() - 1);
        assertThat(testMovimentacaoDeEstoque.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testMovimentacaoDeEstoque.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMovimentacaoDeEstoque.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMovimentacaoDeEstoque.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovimentacaoDeEstoque() throws Exception {
        int databaseSizeBeforeUpdate = movimentacaoDeEstoqueRepository.findAll().size();

        // Create the MovimentacaoDeEstoque
        MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO = movimentacaoDeEstoqueMapper.toDto(movimentacaoDeEstoque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovimentacaoDeEstoqueMockMvc.perform(put("/api/movimentacao-de-estoques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimentacaoDeEstoqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovimentacaoDeEstoque in the database
        List<MovimentacaoDeEstoque> movimentacaoDeEstoqueList = movimentacaoDeEstoqueRepository.findAll();
        assertThat(movimentacaoDeEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovimentacaoDeEstoque() throws Exception {
        // Initialize the database
        movimentacaoDeEstoqueRepository.saveAndFlush(movimentacaoDeEstoque);

        int databaseSizeBeforeDelete = movimentacaoDeEstoqueRepository.findAll().size();

        // Delete the movimentacaoDeEstoque
        restMovimentacaoDeEstoqueMockMvc.perform(delete("/api/movimentacao-de-estoques/{id}", movimentacaoDeEstoque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MovimentacaoDeEstoque> movimentacaoDeEstoqueList = movimentacaoDeEstoqueRepository.findAll();
        assertThat(movimentacaoDeEstoqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
