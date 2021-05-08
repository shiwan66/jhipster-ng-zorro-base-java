package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Fornecedor;
import br.com.ngzorro.domain.Titulo;
import br.com.ngzorro.domain.Endereco;
import br.com.ngzorro.repository.FornecedorRepository;
import br.com.ngzorro.service.FornecedorService;
import br.com.ngzorro.service.dto.FornecedorDTO;
import br.com.ngzorro.service.mapper.FornecedorMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.FornecedorCriteria;
import br.com.ngzorro.service.FornecedorQueryService;

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
import java.util.List;

import static br.com.ngzorro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FornecedorResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class FornecedorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PONTO_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_PONTO_REFERENCIA = "BBBBBBBBBB";

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private FornecedorQueryService fornecedorQueryService;

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

    private MockMvc restFornecedorMockMvc;

    private Fornecedor fornecedor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FornecedorResource fornecedorResource = new FornecedorResource(fornecedorService, fornecedorQueryService);
        this.restFornecedorMockMvc = MockMvcBuilders.standaloneSetup(fornecedorResource)
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
    public static Fornecedor createEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE)
            .email(DEFAULT_EMAIL)
            .pontoReferencia(DEFAULT_PONTO_REFERENCIA);
        return fornecedor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createUpdatedEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .pontoReferencia(UPDATED_PONTO_REFERENCIA);
        return fornecedor;
    }

    @BeforeEach
    public void initTest() {
        fornecedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFornecedor() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFornecedor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testFornecedor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFornecedor.getPontoReferencia()).isEqualTo(DEFAULT_PONTO_REFERENCIA);
    }

    @Test
    @Transactional
    public void createFornecedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor with an existing ID
        fornecedor.setId(1L);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFornecedors() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].pontoReferencia").value(hasItem(DEFAULT_PONTO_REFERENCIA)));
    }
    
    @Test
    @Transactional
    public void getFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.pontoReferencia").value(DEFAULT_PONTO_REFERENCIA));
    }


    @Test
    @Transactional
    public void getFornecedorsByIdFiltering() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        Long id = fornecedor.getId();

        defaultFornecedorShouldBeFound("id.equals=" + id);
        defaultFornecedorShouldNotBeFound("id.notEquals=" + id);

        defaultFornecedorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFornecedorShouldNotBeFound("id.greaterThan=" + id);

        defaultFornecedorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFornecedorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFornecedorsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome equals to DEFAULT_NOME
        defaultFornecedorShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the fornecedorList where nome equals to UPDATED_NOME
        defaultFornecedorShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome not equals to DEFAULT_NOME
        defaultFornecedorShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the fornecedorList where nome not equals to UPDATED_NOME
        defaultFornecedorShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultFornecedorShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the fornecedorList where nome equals to UPDATED_NOME
        defaultFornecedorShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome is not null
        defaultFornecedorShouldBeFound("nome.specified=true");

        // Get all the fornecedorList where nome is null
        defaultFornecedorShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllFornecedorsByNomeContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome contains DEFAULT_NOME
        defaultFornecedorShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the fornecedorList where nome contains UPDATED_NOME
        defaultFornecedorShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome does not contain DEFAULT_NOME
        defaultFornecedorShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the fornecedorList where nome does not contain UPDATED_NOME
        defaultFornecedorShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllFornecedorsByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone equals to DEFAULT_TELEFONE
        defaultFornecedorShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the fornecedorList where telefone equals to UPDATED_TELEFONE
        defaultFornecedorShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone not equals to DEFAULT_TELEFONE
        defaultFornecedorShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the fornecedorList where telefone not equals to UPDATED_TELEFONE
        defaultFornecedorShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultFornecedorShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the fornecedorList where telefone equals to UPDATED_TELEFONE
        defaultFornecedorShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone is not null
        defaultFornecedorShouldBeFound("telefone.specified=true");

        // Get all the fornecedorList where telefone is null
        defaultFornecedorShouldNotBeFound("telefone.specified=false");
    }
                @Test
    @Transactional
    public void getAllFornecedorsByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone contains DEFAULT_TELEFONE
        defaultFornecedorShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the fornecedorList where telefone contains UPDATED_TELEFONE
        defaultFornecedorShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone does not contain DEFAULT_TELEFONE
        defaultFornecedorShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the fornecedorList where telefone does not contain UPDATED_TELEFONE
        defaultFornecedorShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }


    @Test
    @Transactional
    public void getAllFornecedorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where email equals to DEFAULT_EMAIL
        defaultFornecedorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the fornecedorList where email equals to UPDATED_EMAIL
        defaultFornecedorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where email not equals to DEFAULT_EMAIL
        defaultFornecedorShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the fornecedorList where email not equals to UPDATED_EMAIL
        defaultFornecedorShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultFornecedorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the fornecedorList where email equals to UPDATED_EMAIL
        defaultFornecedorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where email is not null
        defaultFornecedorShouldBeFound("email.specified=true");

        // Get all the fornecedorList where email is null
        defaultFornecedorShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllFornecedorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where email contains DEFAULT_EMAIL
        defaultFornecedorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the fornecedorList where email contains UPDATED_EMAIL
        defaultFornecedorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where email does not contain DEFAULT_EMAIL
        defaultFornecedorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the fornecedorList where email does not contain UPDATED_EMAIL
        defaultFornecedorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllFornecedorsByPontoReferenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pontoReferencia equals to DEFAULT_PONTO_REFERENCIA
        defaultFornecedorShouldBeFound("pontoReferencia.equals=" + DEFAULT_PONTO_REFERENCIA);

        // Get all the fornecedorList where pontoReferencia equals to UPDATED_PONTO_REFERENCIA
        defaultFornecedorShouldNotBeFound("pontoReferencia.equals=" + UPDATED_PONTO_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByPontoReferenciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pontoReferencia not equals to DEFAULT_PONTO_REFERENCIA
        defaultFornecedorShouldNotBeFound("pontoReferencia.notEquals=" + DEFAULT_PONTO_REFERENCIA);

        // Get all the fornecedorList where pontoReferencia not equals to UPDATED_PONTO_REFERENCIA
        defaultFornecedorShouldBeFound("pontoReferencia.notEquals=" + UPDATED_PONTO_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByPontoReferenciaIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pontoReferencia in DEFAULT_PONTO_REFERENCIA or UPDATED_PONTO_REFERENCIA
        defaultFornecedorShouldBeFound("pontoReferencia.in=" + DEFAULT_PONTO_REFERENCIA + "," + UPDATED_PONTO_REFERENCIA);

        // Get all the fornecedorList where pontoReferencia equals to UPDATED_PONTO_REFERENCIA
        defaultFornecedorShouldNotBeFound("pontoReferencia.in=" + UPDATED_PONTO_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByPontoReferenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pontoReferencia is not null
        defaultFornecedorShouldBeFound("pontoReferencia.specified=true");

        // Get all the fornecedorList where pontoReferencia is null
        defaultFornecedorShouldNotBeFound("pontoReferencia.specified=false");
    }
                @Test
    @Transactional
    public void getAllFornecedorsByPontoReferenciaContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pontoReferencia contains DEFAULT_PONTO_REFERENCIA
        defaultFornecedorShouldBeFound("pontoReferencia.contains=" + DEFAULT_PONTO_REFERENCIA);

        // Get all the fornecedorList where pontoReferencia contains UPDATED_PONTO_REFERENCIA
        defaultFornecedorShouldNotBeFound("pontoReferencia.contains=" + UPDATED_PONTO_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByPontoReferenciaNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pontoReferencia does not contain DEFAULT_PONTO_REFERENCIA
        defaultFornecedorShouldNotBeFound("pontoReferencia.doesNotContain=" + DEFAULT_PONTO_REFERENCIA);

        // Get all the fornecedorList where pontoReferencia does not contain UPDATED_PONTO_REFERENCIA
        defaultFornecedorShouldBeFound("pontoReferencia.doesNotContain=" + UPDATED_PONTO_REFERENCIA);
    }


    @Test
    @Transactional
    public void getAllFornecedorsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);
        Titulo titulo = TituloResourceIT.createEntity(em);
        em.persist(titulo);
        em.flush();
        fornecedor.addTitulo(titulo);
        fornecedorRepository.saveAndFlush(fornecedor);
        Long tituloId = titulo.getId();

        // Get all the fornecedorList where titulo equals to tituloId
        defaultFornecedorShouldBeFound("tituloId.equals=" + tituloId);

        // Get all the fornecedorList where titulo equals to tituloId + 1
        defaultFornecedorShouldNotBeFound("tituloId.equals=" + (tituloId + 1));
    }


    @Test
    @Transactional
    public void getAllFornecedorsByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);
        Endereco endereco = EnderecoResourceIT.createEntity(em);
        em.persist(endereco);
        em.flush();
        fornecedor.setEndereco(endereco);
        fornecedorRepository.saveAndFlush(fornecedor);
        Long enderecoId = endereco.getId();

        // Get all the fornecedorList where endereco equals to enderecoId
        defaultFornecedorShouldBeFound("enderecoId.equals=" + enderecoId);

        // Get all the fornecedorList where endereco equals to enderecoId + 1
        defaultFornecedorShouldNotBeFound("enderecoId.equals=" + (enderecoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFornecedorShouldBeFound(String filter) throws Exception {
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].pontoReferencia").value(hasItem(DEFAULT_PONTO_REFERENCIA)));

        // Check, that the count call also returns 1
        restFornecedorMockMvc.perform(get("/api/fornecedors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFornecedorShouldNotBeFound(String filter) throws Exception {
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFornecedorMockMvc.perform(get("/api/fornecedors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFornecedor() throws Exception {
        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor
        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).get();
        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
        em.detach(updatedFornecedor);
        updatedFornecedor
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .pontoReferencia(UPDATED_PONTO_REFERENCIA);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(updatedFornecedor);

        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFornecedor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testFornecedor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFornecedor.getPontoReferencia()).isEqualTo(UPDATED_PONTO_REFERENCIA);
    }

    @Test
    @Transactional
    public void updateNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeDelete = fornecedorRepository.findAll().size();

        // Delete the fornecedor
        restFornecedorMockMvc.perform(delete("/api/fornecedors/{id}", fornecedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
