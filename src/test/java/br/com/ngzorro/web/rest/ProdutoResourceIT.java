package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Produto;
import br.com.ngzorro.domain.Categoria;
import br.com.ngzorro.repository.ProdutoRepository;
import br.com.ngzorro.service.ProdutoService;
import br.com.ngzorro.service.dto.ProdutoDTO;
import br.com.ngzorro.service.mapper.ProdutoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.ProdutoCriteria;
import br.com.ngzorro.service.ProdutoQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static br.com.ngzorro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProdutoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class ProdutoResourceIT {

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECO = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRECO = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA = LocalDate.ofEpochDay(-1L);

    private static final Instant DEFAULT_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoRepository produtoRepositoryMock;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Mock
    private ProdutoService produtoServiceMock;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoQueryService produtoQueryService;

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

    private MockMvc restProdutoMockMvc;

    private Produto produto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdutoResource produtoResource = new ProdutoResource(produtoService, produtoQueryService);
        this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
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
    public static Produto createEntity(EntityManager em) {
        Produto produto = new Produto()
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .preco(DEFAULT_PRECO)
            .data(DEFAULT_DATA)
            .hora(DEFAULT_HORA);
        return produto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produto createUpdatedEntity(EntityManager em) {
        Produto produto = new Produto()
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO)
            .data(UPDATED_DATA)
            .hora(UPDATED_HORA);
        return produto;
    }

    @BeforeEach
    public void initTest() {
        produto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testProduto.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualTo(DEFAULT_PRECO);
        assertThat(testProduto.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testProduto.getHora()).isEqualTo(DEFAULT_HORA);
    }

    @Test
    @Transactional
    public void createProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto with an existing ID
        produto.setId(1L);
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNome(null);

        // Create the Produto, which fails.
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(DEFAULT_HORA.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProdutosWithEagerRelationshipsIsEnabled() throws Exception {
        ProdutoResource produtoResource = new ProdutoResource(produtoServiceMock, produtoQueryService);
        when(produtoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProdutoMockMvc.perform(get("/api/produtos?eagerload=true"))
        .andExpect(status().isOk());

        verify(produtoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProdutosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProdutoResource produtoResource = new ProdutoResource(produtoServiceMock, produtoQueryService);
            when(produtoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProdutoMockMvc.perform(get("/api/produtos?eagerload=true"))
        .andExpect(status().isOk());

            verify(produtoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.hora").value(DEFAULT_HORA.toString()));
    }


    @Test
    @Transactional
    public void getProdutosByIdFiltering() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        Long id = produto.getId();

        defaultProdutoShouldBeFound("id.equals=" + id);
        defaultProdutoShouldNotBeFound("id.notEquals=" + id);

        defaultProdutoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProdutoShouldNotBeFound("id.greaterThan=" + id);

        defaultProdutoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProdutoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProdutosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome equals to DEFAULT_NOME
        defaultProdutoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the produtoList where nome equals to UPDATED_NOME
        defaultProdutoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome not equals to DEFAULT_NOME
        defaultProdutoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the produtoList where nome not equals to UPDATED_NOME
        defaultProdutoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultProdutoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the produtoList where nome equals to UPDATED_NOME
        defaultProdutoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome is not null
        defaultProdutoShouldBeFound("nome.specified=true");

        // Get all the produtoList where nome is null
        defaultProdutoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllProdutosByNomeContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome contains DEFAULT_NOME
        defaultProdutoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the produtoList where nome contains UPDATED_NOME
        defaultProdutoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome does not contain DEFAULT_NOME
        defaultProdutoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the produtoList where nome does not contain UPDATED_NOME
        defaultProdutoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllProdutosByPrecoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco equals to DEFAULT_PRECO
        defaultProdutoShouldBeFound("preco.equals=" + DEFAULT_PRECO);

        // Get all the produtoList where preco equals to UPDATED_PRECO
        defaultProdutoShouldNotBeFound("preco.equals=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrecoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco not equals to DEFAULT_PRECO
        defaultProdutoShouldNotBeFound("preco.notEquals=" + DEFAULT_PRECO);

        // Get all the produtoList where preco not equals to UPDATED_PRECO
        defaultProdutoShouldBeFound("preco.notEquals=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrecoIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco in DEFAULT_PRECO or UPDATED_PRECO
        defaultProdutoShouldBeFound("preco.in=" + DEFAULT_PRECO + "," + UPDATED_PRECO);

        // Get all the produtoList where preco equals to UPDATED_PRECO
        defaultProdutoShouldNotBeFound("preco.in=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrecoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is not null
        defaultProdutoShouldBeFound("preco.specified=true");

        // Get all the produtoList where preco is null
        defaultProdutoShouldNotBeFound("preco.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutosByPrecoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is greater than or equal to DEFAULT_PRECO
        defaultProdutoShouldBeFound("preco.greaterThanOrEqual=" + DEFAULT_PRECO);

        // Get all the produtoList where preco is greater than or equal to UPDATED_PRECO
        defaultProdutoShouldNotBeFound("preco.greaterThanOrEqual=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrecoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is less than or equal to DEFAULT_PRECO
        defaultProdutoShouldBeFound("preco.lessThanOrEqual=" + DEFAULT_PRECO);

        // Get all the produtoList where preco is less than or equal to SMALLER_PRECO
        defaultProdutoShouldNotBeFound("preco.lessThanOrEqual=" + SMALLER_PRECO);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrecoIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is less than DEFAULT_PRECO
        defaultProdutoShouldNotBeFound("preco.lessThan=" + DEFAULT_PRECO);

        // Get all the produtoList where preco is less than UPDATED_PRECO
        defaultProdutoShouldBeFound("preco.lessThan=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrecoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where preco is greater than DEFAULT_PRECO
        defaultProdutoShouldNotBeFound("preco.greaterThan=" + DEFAULT_PRECO);

        // Get all the produtoList where preco is greater than SMALLER_PRECO
        defaultProdutoShouldBeFound("preco.greaterThan=" + SMALLER_PRECO);
    }


    @Test
    @Transactional
    public void getAllProdutosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where data equals to DEFAULT_DATA
        defaultProdutoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the produtoList where data equals to UPDATED_DATA
        defaultProdutoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllProdutosByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where data not equals to DEFAULT_DATA
        defaultProdutoShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the produtoList where data not equals to UPDATED_DATA
        defaultProdutoShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllProdutosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultProdutoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the produtoList where data equals to UPDATED_DATA
        defaultProdutoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllProdutosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where data is not null
        defaultProdutoShouldBeFound("data.specified=true");

        // Get all the produtoList where data is null
        defaultProdutoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where data is greater than or equal to DEFAULT_DATA
        defaultProdutoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the produtoList where data is greater than or equal to UPDATED_DATA
        defaultProdutoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllProdutosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where data is less than or equal to DEFAULT_DATA
        defaultProdutoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the produtoList where data is less than or equal to SMALLER_DATA
        defaultProdutoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllProdutosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where data is less than DEFAULT_DATA
        defaultProdutoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the produtoList where data is less than UPDATED_DATA
        defaultProdutoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllProdutosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where data is greater than DEFAULT_DATA
        defaultProdutoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the produtoList where data is greater than SMALLER_DATA
        defaultProdutoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllProdutosByHoraIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where hora equals to DEFAULT_HORA
        defaultProdutoShouldBeFound("hora.equals=" + DEFAULT_HORA);

        // Get all the produtoList where hora equals to UPDATED_HORA
        defaultProdutoShouldNotBeFound("hora.equals=" + UPDATED_HORA);
    }

    @Test
    @Transactional
    public void getAllProdutosByHoraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where hora not equals to DEFAULT_HORA
        defaultProdutoShouldNotBeFound("hora.notEquals=" + DEFAULT_HORA);

        // Get all the produtoList where hora not equals to UPDATED_HORA
        defaultProdutoShouldBeFound("hora.notEquals=" + UPDATED_HORA);
    }

    @Test
    @Transactional
    public void getAllProdutosByHoraIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where hora in DEFAULT_HORA or UPDATED_HORA
        defaultProdutoShouldBeFound("hora.in=" + DEFAULT_HORA + "," + UPDATED_HORA);

        // Get all the produtoList where hora equals to UPDATED_HORA
        defaultProdutoShouldNotBeFound("hora.in=" + UPDATED_HORA);
    }

    @Test
    @Transactional
    public void getAllProdutosByHoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where hora is not null
        defaultProdutoShouldBeFound("hora.specified=true");

        // Get all the produtoList where hora is null
        defaultProdutoShouldNotBeFound("hora.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutosByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        Categoria categoria = CategoriaResourceIT.createEntity(em);
        em.persist(categoria);
        em.flush();
        produto.addCategoria(categoria);
        produtoRepository.saveAndFlush(produto);
        Long categoriaId = categoria.getId();

        // Get all the produtoList where categoria equals to categoriaId
        defaultProdutoShouldBeFound("categoriaId.equals=" + categoriaId);

        // Get all the produtoList where categoria equals to categoriaId + 1
        defaultProdutoShouldNotBeFound("categoriaId.equals=" + (categoriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProdutoShouldBeFound(String filter) throws Exception {
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(DEFAULT_HORA.toString())));

        // Check, that the count call also returns 1
        restProdutoMockMvc.perform(get("/api/produtos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProdutoShouldNotBeFound(String filter) throws Exception {
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProdutoMockMvc.perform(get("/api/produtos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = produtoRepository.findById(produto.getId()).get();
        // Disconnect from session so that the updates on updatedProduto are not directly saved in db
        em.detach(updatedProduto);
        updatedProduto
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO)
            .data(UPDATED_DATA)
            .hora(UPDATED_HORA);
        ProdutoDTO produtoDTO = produtoMapper.toDto(updatedProduto);

        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testProduto.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualTo(UPDATED_PRECO);
        assertThat(testProduto.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testProduto.getHora()).isEqualTo(UPDATED_HORA);
    }

    @Test
    @Transactional
    public void updateNonExistingProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Create the Produto
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Delete the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
