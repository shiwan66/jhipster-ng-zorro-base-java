package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Anexo;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.repository.AnexoRepository;
import br.com.ngzorro.service.AnexoService;
import br.com.ngzorro.service.dto.AnexoDTO;
import br.com.ngzorro.service.mapper.AnexoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnexoCriteria;
import br.com.ngzorro.service.AnexoQueryService;

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
 * Integration tests for the {@link AnexoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnexoResourceIT {

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
    private AnexoRepository anexoRepository;

    @Autowired
    private AnexoMapper anexoMapper;

    @Autowired
    private AnexoService anexoService;

    @Autowired
    private AnexoQueryService anexoQueryService;

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

    private MockMvc restAnexoMockMvc;

    private Anexo anexo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnexoResource anexoResource = new AnexoResource(anexoService, anexoQueryService);
        this.restAnexoMockMvc = MockMvcBuilders.standaloneSetup(anexoResource)
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
    public static Anexo createEntity(EntityManager em) {
        Anexo anexo = new Anexo()
            .anexo(DEFAULT_ANEXO)
            .anexoContentType(DEFAULT_ANEXO_CONTENT_TYPE)
            .descricao(DEFAULT_DESCRICAO)
            .data(DEFAULT_DATA)
            .url(DEFAULT_URL)
            .urlThumbnail(DEFAULT_URL_THUMBNAIL);
        return anexo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anexo createUpdatedEntity(EntityManager em) {
        Anexo anexo = new Anexo()
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .url(UPDATED_URL)
            .urlThumbnail(UPDATED_URL_THUMBNAIL);
        return anexo;
    }

    @BeforeEach
    public void initTest() {
        anexo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnexo() throws Exception {
        int databaseSizeBeforeCreate = anexoRepository.findAll().size();

        // Create the Anexo
        AnexoDTO anexoDTO = anexoMapper.toDto(anexo);
        restAnexoMockMvc.perform(post("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoDTO)))
            .andExpect(status().isCreated());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeCreate + 1);
        Anexo testAnexo = anexoList.get(anexoList.size() - 1);
        assertThat(testAnexo.getAnexo()).isEqualTo(DEFAULT_ANEXO);
        assertThat(testAnexo.getAnexoContentType()).isEqualTo(DEFAULT_ANEXO_CONTENT_TYPE);
        assertThat(testAnexo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAnexo.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAnexo.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testAnexo.getUrlThumbnail()).isEqualTo(DEFAULT_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void createAnexoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anexoRepository.findAll().size();

        // Create the Anexo with an existing ID
        anexo.setId(1L);
        AnexoDTO anexoDTO = anexoMapper.toDto(anexo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoMockMvc.perform(post("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnexos() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList
        restAnexoMockMvc.perform(get("/api/anexos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexo.getId().intValue())))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].urlThumbnail").value(hasItem(DEFAULT_URL_THUMBNAIL)));
    }
    
    @Test
    @Transactional
    public void getAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get the anexo
        restAnexoMockMvc.perform(get("/api/anexos/{id}", anexo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anexo.getId().intValue()))
            .andExpect(jsonPath("$.anexoContentType").value(DEFAULT_ANEXO_CONTENT_TYPE))
            .andExpect(jsonPath("$.anexo").value(Base64Utils.encodeToString(DEFAULT_ANEXO)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.urlThumbnail").value(DEFAULT_URL_THUMBNAIL));
    }


    @Test
    @Transactional
    public void getAnexosByIdFiltering() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        Long id = anexo.getId();

        defaultAnexoShouldBeFound("id.equals=" + id);
        defaultAnexoShouldNotBeFound("id.notEquals=" + id);

        defaultAnexoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnexoShouldNotBeFound("id.greaterThan=" + id);

        defaultAnexoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnexoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnexosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where descricao equals to DEFAULT_DESCRICAO
        defaultAnexoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the anexoList where descricao equals to UPDATED_DESCRICAO
        defaultAnexoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnexosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where descricao not equals to DEFAULT_DESCRICAO
        defaultAnexoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the anexoList where descricao not equals to UPDATED_DESCRICAO
        defaultAnexoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnexosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAnexoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the anexoList where descricao equals to UPDATED_DESCRICAO
        defaultAnexoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnexosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where descricao is not null
        defaultAnexoShouldBeFound("descricao.specified=true");

        // Get all the anexoList where descricao is null
        defaultAnexoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnexosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where descricao contains DEFAULT_DESCRICAO
        defaultAnexoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the anexoList where descricao contains UPDATED_DESCRICAO
        defaultAnexoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAnexosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where descricao does not contain DEFAULT_DESCRICAO
        defaultAnexoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the anexoList where descricao does not contain UPDATED_DESCRICAO
        defaultAnexoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAnexosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where data equals to DEFAULT_DATA
        defaultAnexoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the anexoList where data equals to UPDATED_DATA
        defaultAnexoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexosByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where data not equals to DEFAULT_DATA
        defaultAnexoShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the anexoList where data not equals to UPDATED_DATA
        defaultAnexoShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultAnexoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the anexoList where data equals to UPDATED_DATA
        defaultAnexoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where data is not null
        defaultAnexoShouldBeFound("data.specified=true");

        // Get all the anexoList where data is null
        defaultAnexoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnexosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where data is greater than or equal to DEFAULT_DATA
        defaultAnexoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the anexoList where data is greater than or equal to UPDATED_DATA
        defaultAnexoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where data is less than or equal to DEFAULT_DATA
        defaultAnexoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the anexoList where data is less than or equal to SMALLER_DATA
        defaultAnexoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where data is less than DEFAULT_DATA
        defaultAnexoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the anexoList where data is less than UPDATED_DATA
        defaultAnexoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllAnexosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where data is greater than DEFAULT_DATA
        defaultAnexoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the anexoList where data is greater than SMALLER_DATA
        defaultAnexoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllAnexosByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where url equals to DEFAULT_URL
        defaultAnexoShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the anexoList where url equals to UPDATED_URL
        defaultAnexoShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllAnexosByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where url not equals to DEFAULT_URL
        defaultAnexoShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the anexoList where url not equals to UPDATED_URL
        defaultAnexoShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllAnexosByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where url in DEFAULT_URL or UPDATED_URL
        defaultAnexoShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the anexoList where url equals to UPDATED_URL
        defaultAnexoShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllAnexosByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where url is not null
        defaultAnexoShouldBeFound("url.specified=true");

        // Get all the anexoList where url is null
        defaultAnexoShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnexosByUrlContainsSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where url contains DEFAULT_URL
        defaultAnexoShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the anexoList where url contains UPDATED_URL
        defaultAnexoShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllAnexosByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where url does not contain DEFAULT_URL
        defaultAnexoShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the anexoList where url does not contain UPDATED_URL
        defaultAnexoShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }


    @Test
    @Transactional
    public void getAllAnexosByUrlThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where urlThumbnail equals to DEFAULT_URL_THUMBNAIL
        defaultAnexoShouldBeFound("urlThumbnail.equals=" + DEFAULT_URL_THUMBNAIL);

        // Get all the anexoList where urlThumbnail equals to UPDATED_URL_THUMBNAIL
        defaultAnexoShouldNotBeFound("urlThumbnail.equals=" + UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllAnexosByUrlThumbnailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where urlThumbnail not equals to DEFAULT_URL_THUMBNAIL
        defaultAnexoShouldNotBeFound("urlThumbnail.notEquals=" + DEFAULT_URL_THUMBNAIL);

        // Get all the anexoList where urlThumbnail not equals to UPDATED_URL_THUMBNAIL
        defaultAnexoShouldBeFound("urlThumbnail.notEquals=" + UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllAnexosByUrlThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where urlThumbnail in DEFAULT_URL_THUMBNAIL or UPDATED_URL_THUMBNAIL
        defaultAnexoShouldBeFound("urlThumbnail.in=" + DEFAULT_URL_THUMBNAIL + "," + UPDATED_URL_THUMBNAIL);

        // Get all the anexoList where urlThumbnail equals to UPDATED_URL_THUMBNAIL
        defaultAnexoShouldNotBeFound("urlThumbnail.in=" + UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllAnexosByUrlThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where urlThumbnail is not null
        defaultAnexoShouldBeFound("urlThumbnail.specified=true");

        // Get all the anexoList where urlThumbnail is null
        defaultAnexoShouldNotBeFound("urlThumbnail.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnexosByUrlThumbnailContainsSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where urlThumbnail contains DEFAULT_URL_THUMBNAIL
        defaultAnexoShouldBeFound("urlThumbnail.contains=" + DEFAULT_URL_THUMBNAIL);

        // Get all the anexoList where urlThumbnail contains UPDATED_URL_THUMBNAIL
        defaultAnexoShouldNotBeFound("urlThumbnail.contains=" + UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllAnexosByUrlThumbnailNotContainsSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where urlThumbnail does not contain DEFAULT_URL_THUMBNAIL
        defaultAnexoShouldNotBeFound("urlThumbnail.doesNotContain=" + DEFAULT_URL_THUMBNAIL);

        // Get all the anexoList where urlThumbnail does not contain UPDATED_URL_THUMBNAIL
        defaultAnexoShouldBeFound("urlThumbnail.doesNotContain=" + UPDATED_URL_THUMBNAIL);
    }


    @Test
    @Transactional
    public void getAllAnexosByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        Animal animal = AnimalResourceIT.createEntity(em);
        em.persist(animal);
        em.flush();
        anexo.setAnimal(animal);
        anexoRepository.saveAndFlush(anexo);
        Long animalId = animal.getId();

        // Get all the anexoList where animal equals to animalId
        defaultAnexoShouldBeFound("animalId.equals=" + animalId);

        // Get all the anexoList where animal equals to animalId + 1
        defaultAnexoShouldNotBeFound("animalId.equals=" + (animalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnexoShouldBeFound(String filter) throws Exception {
        restAnexoMockMvc.perform(get("/api/anexos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexo.getId().intValue())))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].urlThumbnail").value(hasItem(DEFAULT_URL_THUMBNAIL)));

        // Check, that the count call also returns 1
        restAnexoMockMvc.perform(get("/api/anexos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnexoShouldNotBeFound(String filter) throws Exception {
        restAnexoMockMvc.perform(get("/api/anexos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnexoMockMvc.perform(get("/api/anexos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnexo() throws Exception {
        // Get the anexo
        restAnexoMockMvc.perform(get("/api/anexos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        int databaseSizeBeforeUpdate = anexoRepository.findAll().size();

        // Update the anexo
        Anexo updatedAnexo = anexoRepository.findById(anexo.getId()).get();
        // Disconnect from session so that the updates on updatedAnexo are not directly saved in db
        em.detach(updatedAnexo);
        updatedAnexo
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .url(UPDATED_URL)
            .urlThumbnail(UPDATED_URL_THUMBNAIL);
        AnexoDTO anexoDTO = anexoMapper.toDto(updatedAnexo);

        restAnexoMockMvc.perform(put("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoDTO)))
            .andExpect(status().isOk());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeUpdate);
        Anexo testAnexo = anexoList.get(anexoList.size() - 1);
        assertThat(testAnexo.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testAnexo.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
        assertThat(testAnexo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAnexo.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAnexo.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAnexo.getUrlThumbnail()).isEqualTo(UPDATED_URL_THUMBNAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingAnexo() throws Exception {
        int databaseSizeBeforeUpdate = anexoRepository.findAll().size();

        // Create the Anexo
        AnexoDTO anexoDTO = anexoMapper.toDto(anexo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoMockMvc.perform(put("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        int databaseSizeBeforeDelete = anexoRepository.findAll().size();

        // Delete the anexo
        restAnexoMockMvc.perform(delete("/api/anexos/{id}", anexo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
