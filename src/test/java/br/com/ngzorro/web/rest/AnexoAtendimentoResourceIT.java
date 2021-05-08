package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnexoAtendimento;
import br.com.ngzorro.domain.Atendimento;
import br.com.ngzorro.repository.AnexoAtendimentoRepository;
import br.com.ngzorro.service.AnexoAtendimentoService;
import br.com.ngzorro.service.dto.AnexoAtendimentoDTO;
import br.com.ngzorro.service.mapper.AnexoAtendimentoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnexoAtendimentoCriteria;
import br.com.ngzorro.service.AnexoAtendimentoQueryService;

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

/**
 * Integration tests for the {@link AnexoAtendimentoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnexoAtendimentoResourceIT {

    private static final byte[] DEFAULT_ANEXO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANEXO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ANEXO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANEXO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_URL_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_URL_THUMBNAIL = "BBBBBBBBBB";

    @Autowired
    private AnexoAtendimentoRepository anexoAtendimentoRepository;

    @Autowired
    private AnexoAtendimentoMapper anexoAtendimentoMapper;

    @Autowired
    private AnexoAtendimentoService anexoAtendimentoService;

    @Autowired
    private AnexoAtendimentoQueryService anexoAtendimentoQueryService;

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

    private MockMvc restAnexoAtendimentoMockMvc;

    private AnexoAtendimento anexoAtendimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnexoAtendimentoResource anexoAtendimentoResource = new AnexoAtendimentoResource(anexoAtendimentoService, anexoAtendimentoQueryService);
        this.restAnexoAtendimentoMockMvc = MockMvcBuilders.standaloneSetup(anexoAtendimentoResource)
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
    public static AnexoAtendimento createEntity(EntityManager em) {
        AnexoAtendimento anexoAtendimento = new AnexoAtendimento()
            .anexo(DEFAULT_ANEXO)
            .anexoContentType(DEFAULT_ANEXO_CONTENT_TYPE)
            .descricao(DEFAULT_DESCRICAO)
            .data(DEFAULT_DATA)
            .url(DEFAULT_URL)
            .urlThumbnail(DEFAULT_URL_THUMBNAIL);
        return anexoAtendimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoAtendimento createUpdatedEntity(EntityManager em) {
        AnexoAtendimento anexoAtendimento = new AnexoAtendimento()
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .url(UPDATED_URL)
            .urlThumbnail(UPDATED_URL_THUMBNAIL);
        return anexoAtendimento;
    }

    @BeforeEach
    public void initTest() {
        anexoAtendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnexoAtendimento() throws Exception {
        int databaseSizeBeforeCreate = anexoAtendimentoRepository.findAll().size();

        // Create the AnexoAtendimento
        AnexoAtendimentoDTO anexoAtendimentoDTO = anexoAtendimentoMapper.toDto(anexoAtendimento);
        restAnexoAtendimentoMockMvc.perform(post("/api/anexo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoAtendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the AnexoAtendimento in the database
        List<AnexoAtendimento> anexoAtendimentoList = anexoAtendimentoRepository.findAll();
        assertThat(anexoAtendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        AnexoAtendimento testAnexoAtendimento = anexoAtendimentoList.get(anexoAtendimentoList.size() - 1);
        assertThat(testAnexoAtendimento.getAnexo()).isEqualTo(DEFAULT_ANEXO);
        assertThat(testAnexoAtendimento.getAnexoContentType()).isEqualTo(DEFAULT_ANEXO_CONTENT_TYPE);
        assertThat(testAnexoAtendimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAnexoAtendimento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAnexoAtendimento.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testAnexoAtendimento.getUrlThumbnail()).isEqualTo(DEFAULT_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void createAnexoAtendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anexoAtendimentoRepository.findAll().size();

        // Create the AnexoAtendimento with an existing ID
        anexoAtendimento.setId(1L);
        AnexoAtendimentoDTO anexoAtendimentoDTO = anexoAtendimentoMapper.toDto(anexoAtendimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoAtendimentoMockMvc.perform(post("/api/anexo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnexoAtendimento in the database
        List<AnexoAtendimento> anexoAtendimentoList = anexoAtendimentoRepository.findAll();
        assertThat(anexoAtendimentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnexoAtendimentos() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList
        restAnexoAtendimentoMockMvc.perform(get("/api/anexo-atendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].urlThumbnail").value(hasItem(DEFAULT_URL_THUMBNAIL)));
    }
    
    @Test
    @Transactional
    public void getAnexoAtendimento() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get the anexoAtendimento
        restAnexoAtendimentoMockMvc.perform(get("/api/anexo-atendimentos/{id}", anexoAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anexoAtendimento.getId().intValue()))
            .andExpect(jsonPath("$.anexoContentType").value(DEFAULT_ANEXO_CONTENT_TYPE))
            .andExpect(jsonPath("$.anexo").value(Base64Utils.encodeToString(DEFAULT_ANEXO)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.urlThumbnail").value(DEFAULT_URL_THUMBNAIL));
    }


    @Test
    @Transactional
    public void getAnexoAtendimentosByIdFiltering() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        Long id = anexoAtendimento.getId();

        defaultAnexoAtendimentoShouldBeFound("id.equals=" + id);
        defaultAnexoAtendimentoShouldNotBeFound("id.notEquals=" + id);

        defaultAnexoAtendimentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnexoAtendimentoShouldNotBeFound("id.greaterThan=" + id);

        defaultAnexoAtendimentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnexoAtendimentoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where descricao equals to DEFAULT_DESCRICAO
        defaultAnexoAtendimentoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the anexoAtendimentoList where descricao equals to UPDATED_DESCRICAO
        defaultAnexoAtendimentoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where descricao not equals to DEFAULT_DESCRICAO
        defaultAnexoAtendimentoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the anexoAtendimentoList where descricao not equals to UPDATED_DESCRICAO
        defaultAnexoAtendimentoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAnexoAtendimentoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the anexoAtendimentoList where descricao equals to UPDATED_DESCRICAO
        defaultAnexoAtendimentoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where descricao is not null
        defaultAnexoAtendimentoShouldBeFound("descricao.specified=true");

        // Get all the anexoAtendimentoList where descricao is null
        defaultAnexoAtendimentoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnexoAtendimentosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where descricao contains DEFAULT_DESCRICAO
        defaultAnexoAtendimentoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the anexoAtendimentoList where descricao contains UPDATED_DESCRICAO
        defaultAnexoAtendimentoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where descricao does not contain DEFAULT_DESCRICAO
        defaultAnexoAtendimentoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the anexoAtendimentoList where descricao does not contain UPDATED_DESCRICAO
        defaultAnexoAtendimentoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where data equals to DEFAULT_DATA
        defaultAnexoAtendimentoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the anexoAtendimentoList where data equals to UPDATED_DATA
        defaultAnexoAtendimentoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where data not equals to DEFAULT_DATA
        defaultAnexoAtendimentoShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the anexoAtendimentoList where data not equals to UPDATED_DATA
        defaultAnexoAtendimentoShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultAnexoAtendimentoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the anexoAtendimentoList where data equals to UPDATED_DATA
        defaultAnexoAtendimentoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where data is not null
        defaultAnexoAtendimentoShouldBeFound("data.specified=true");

        // Get all the anexoAtendimentoList where data is null
        defaultAnexoAtendimentoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where data is greater than or equal to DEFAULT_DATA
        defaultAnexoAtendimentoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the anexoAtendimentoList where data is greater than or equal to UPDATED_DATA
        defaultAnexoAtendimentoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where data is less than or equal to DEFAULT_DATA
        defaultAnexoAtendimentoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the anexoAtendimentoList where data is less than or equal to SMALLER_DATA
        defaultAnexoAtendimentoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where data is less than DEFAULT_DATA
        defaultAnexoAtendimentoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the anexoAtendimentoList where data is less than UPDATED_DATA
        defaultAnexoAtendimentoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where data is greater than DEFAULT_DATA
        defaultAnexoAtendimentoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the anexoAtendimentoList where data is greater than SMALLER_DATA
        defaultAnexoAtendimentoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where url equals to DEFAULT_URL
        defaultAnexoAtendimentoShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the anexoAtendimentoList where url equals to UPDATED_URL
        defaultAnexoAtendimentoShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where url not equals to DEFAULT_URL
        defaultAnexoAtendimentoShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the anexoAtendimentoList where url not equals to UPDATED_URL
        defaultAnexoAtendimentoShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where url in DEFAULT_URL or UPDATED_URL
        defaultAnexoAtendimentoShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the anexoAtendimentoList where url equals to UPDATED_URL
        defaultAnexoAtendimentoShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where url is not null
        defaultAnexoAtendimentoShouldBeFound("url.specified=true");

        // Get all the anexoAtendimentoList where url is null
        defaultAnexoAtendimentoShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlContainsSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where url contains DEFAULT_URL
        defaultAnexoAtendimentoShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the anexoAtendimentoList where url contains UPDATED_URL
        defaultAnexoAtendimentoShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where url does not contain DEFAULT_URL
        defaultAnexoAtendimentoShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the anexoAtendimentoList where url does not contain UPDATED_URL
        defaultAnexoAtendimentoShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }


    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where urlThumbnail equals to DEFAULT_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldBeFound("urlThumbnail.equals=" + DEFAULT_URL_THUMBNAIL);

        // Get all the anexoAtendimentoList where urlThumbnail equals to UPDATED_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldNotBeFound("urlThumbnail.equals=" + UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlThumbnailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where urlThumbnail not equals to DEFAULT_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldNotBeFound("urlThumbnail.notEquals=" + DEFAULT_URL_THUMBNAIL);

        // Get all the anexoAtendimentoList where urlThumbnail not equals to UPDATED_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldBeFound("urlThumbnail.notEquals=" + UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where urlThumbnail in DEFAULT_URL_THUMBNAIL or UPDATED_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldBeFound("urlThumbnail.in=" + DEFAULT_URL_THUMBNAIL + "," + UPDATED_URL_THUMBNAIL);

        // Get all the anexoAtendimentoList where urlThumbnail equals to UPDATED_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldNotBeFound("urlThumbnail.in=" + UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where urlThumbnail is not null
        defaultAnexoAtendimentoShouldBeFound("urlThumbnail.specified=true");

        // Get all the anexoAtendimentoList where urlThumbnail is null
        defaultAnexoAtendimentoShouldNotBeFound("urlThumbnail.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlThumbnailContainsSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where urlThumbnail contains DEFAULT_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldBeFound("urlThumbnail.contains=" + DEFAULT_URL_THUMBNAIL);

        // Get all the anexoAtendimentoList where urlThumbnail contains UPDATED_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldNotBeFound("urlThumbnail.contains=" + UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllAnexoAtendimentosByUrlThumbnailNotContainsSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        // Get all the anexoAtendimentoList where urlThumbnail does not contain DEFAULT_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldNotBeFound("urlThumbnail.doesNotContain=" + DEFAULT_URL_THUMBNAIL);

        // Get all the anexoAtendimentoList where urlThumbnail does not contain UPDATED_URL_THUMBNAIL
        defaultAnexoAtendimentoShouldBeFound("urlThumbnail.doesNotContain=" + UPDATED_URL_THUMBNAIL);
    }


    @Test
    @Transactional
    public void getAllAnexoAtendimentosByAtendimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);
        Atendimento atendimento = AtendimentoResourceIT.createEntity(em);
        em.persist(atendimento);
        em.flush();
        anexoAtendimento.setAtendimento(atendimento);
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);
        Long atendimentoId = atendimento.getId();

        // Get all the anexoAtendimentoList where atendimento equals to atendimentoId
        defaultAnexoAtendimentoShouldBeFound("atendimentoId.equals=" + atendimentoId);

        // Get all the anexoAtendimentoList where atendimento equals to atendimentoId + 1
        defaultAnexoAtendimentoShouldNotBeFound("atendimentoId.equals=" + (atendimentoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnexoAtendimentoShouldBeFound(String filter) throws Exception {
        restAnexoAtendimentoMockMvc.perform(get("/api/anexo-atendimentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].urlThumbnail").value(hasItem(DEFAULT_URL_THUMBNAIL)));

        // Check, that the count call also returns 1
        restAnexoAtendimentoMockMvc.perform(get("/api/anexo-atendimentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnexoAtendimentoShouldNotBeFound(String filter) throws Exception {
        restAnexoAtendimentoMockMvc.perform(get("/api/anexo-atendimentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnexoAtendimentoMockMvc.perform(get("/api/anexo-atendimentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnexoAtendimento() throws Exception {
        // Get the anexoAtendimento
        restAnexoAtendimentoMockMvc.perform(get("/api/anexo-atendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnexoAtendimento() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        int databaseSizeBeforeUpdate = anexoAtendimentoRepository.findAll().size();

        // Update the anexoAtendimento
        AnexoAtendimento updatedAnexoAtendimento = anexoAtendimentoRepository.findById(anexoAtendimento.getId()).get();
        // Disconnect from session so that the updates on updatedAnexoAtendimento are not directly saved in db
        em.detach(updatedAnexoAtendimento);
        updatedAnexoAtendimento
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .url(UPDATED_URL)
            .urlThumbnail(UPDATED_URL_THUMBNAIL);
        AnexoAtendimentoDTO anexoAtendimentoDTO = anexoAtendimentoMapper.toDto(updatedAnexoAtendimento);

        restAnexoAtendimentoMockMvc.perform(put("/api/anexo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoAtendimentoDTO)))
            .andExpect(status().isOk());

        // Validate the AnexoAtendimento in the database
        List<AnexoAtendimento> anexoAtendimentoList = anexoAtendimentoRepository.findAll();
        assertThat(anexoAtendimentoList).hasSize(databaseSizeBeforeUpdate);
        AnexoAtendimento testAnexoAtendimento = anexoAtendimentoList.get(anexoAtendimentoList.size() - 1);
        assertThat(testAnexoAtendimento.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testAnexoAtendimento.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
        assertThat(testAnexoAtendimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAnexoAtendimento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAnexoAtendimento.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAnexoAtendimento.getUrlThumbnail()).isEqualTo(UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingAnexoAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = anexoAtendimentoRepository.findAll().size();

        // Create the AnexoAtendimento
        AnexoAtendimentoDTO anexoAtendimentoDTO = anexoAtendimentoMapper.toDto(anexoAtendimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoAtendimentoMockMvc.perform(put("/api/anexo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnexoAtendimento in the database
        List<AnexoAtendimento> anexoAtendimentoList = anexoAtendimentoRepository.findAll();
        assertThat(anexoAtendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnexoAtendimento() throws Exception {
        // Initialize the database
        anexoAtendimentoRepository.saveAndFlush(anexoAtendimento);

        int databaseSizeBeforeDelete = anexoAtendimentoRepository.findAll().size();

        // Delete the anexoAtendimento
        restAnexoAtendimentoMockMvc.perform(delete("/api/anexo-atendimentos/{id}", anexoAtendimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnexoAtendimento> anexoAtendimentoList = anexoAtendimentoRepository.findAll();
        assertThat(anexoAtendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
