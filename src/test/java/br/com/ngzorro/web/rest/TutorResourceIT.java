package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Tutor;
import br.com.ngzorro.domain.User;
import br.com.ngzorro.domain.Titulo;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.domain.Endereco;
import br.com.ngzorro.repository.TutorRepository;
import br.com.ngzorro.service.TutorService;
import br.com.ngzorro.service.dto.TutorDTO;
import br.com.ngzorro.service.mapper.TutorMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.TutorCriteria;
import br.com.ngzorro.service.TutorQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.ngzorro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.ngzorro.domain.enumeration.Sexo;
/**
 * Integration tests for the {@link TutorResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class TutorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_FOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMININO;

    private static final LocalDate DEFAULT_DATA_CADASTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CADASTRO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_CADASTRO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorQueryService tutorQueryService;

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

    private MockMvc restTutorMockMvc;

    private Tutor tutor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TutorResource tutorResource = new TutorResource(tutorService, tutorQueryService);
        this.restTutorMockMvc = MockMvcBuilders.standaloneSetup(tutorResource)
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
    public static Tutor createEntity(EntityManager em) {
        Tutor tutor = new Tutor()
            .nome(DEFAULT_NOME)
            .sobrenome(DEFAULT_SOBRENOME)
            .telefone1(DEFAULT_TELEFONE_1)
            .telefone2(DEFAULT_TELEFONE_2)
            .email(DEFAULT_EMAIL)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .fotoUrl(DEFAULT_FOTO_URL)
            .cpf(DEFAULT_CPF)
            .sexo(DEFAULT_SEXO)
            .dataCadastro(DEFAULT_DATA_CADASTRO);
        return tutor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tutor createUpdatedEntity(EntityManager em) {
        Tutor tutor = new Tutor()
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .telefone1(UPDATED_TELEFONE_1)
            .telefone2(UPDATED_TELEFONE_2)
            .email(UPDATED_EMAIL)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .fotoUrl(UPDATED_FOTO_URL)
            .cpf(UPDATED_CPF)
            .sexo(UPDATED_SEXO)
            .dataCadastro(UPDATED_DATA_CADASTRO);
        return tutor;
    }

    @BeforeEach
    public void initTest() {
        tutor = createEntity(em);
    }

    @Test
    @Transactional
    public void createTutor() throws Exception {
        int databaseSizeBeforeCreate = tutorRepository.findAll().size();

        // Create the Tutor
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);
        restTutorMockMvc.perform(post("/api/tutors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tutorDTO)))
            .andExpect(status().isCreated());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeCreate + 1);
        Tutor testTutor = tutorList.get(tutorList.size() - 1);
        assertThat(testTutor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTutor.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testTutor.getTelefone1()).isEqualTo(DEFAULT_TELEFONE_1);
        assertThat(testTutor.getTelefone2()).isEqualTo(DEFAULT_TELEFONE_2);
        assertThat(testTutor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTutor.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testTutor.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testTutor.getFotoUrl()).isEqualTo(DEFAULT_FOTO_URL);
        assertThat(testTutor.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testTutor.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testTutor.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void createTutorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tutorRepository.findAll().size();

        // Create the Tutor with an existing ID
        tutor.setId(1L);
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTutorMockMvc.perform(post("/api/tutors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tutorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = tutorRepository.findAll().size();
        // set the field null
        tutor.setEmail(null);

        // Create the Tutor, which fails.
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        restTutorMockMvc.perform(post("/api/tutors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tutorDTO)))
            .andExpect(status().isBadRequest());

        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTutors() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList
        restTutorMockMvc.perform(get("/api/tutors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tutor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].telefone1").value(hasItem(DEFAULT_TELEFONE_1)))
            .andExpect(jsonPath("$.[*].telefone2").value(hasItem(DEFAULT_TELEFONE_2)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].fotoUrl").value(hasItem(DEFAULT_FOTO_URL)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())));
    }
    
    @Test
    @Transactional
    public void getTutor() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get the tutor
        restTutorMockMvc.perform(get("/api/tutors/{id}", tutor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tutor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME))
            .andExpect(jsonPath("$.telefone1").value(DEFAULT_TELEFONE_1))
            .andExpect(jsonPath("$.telefone2").value(DEFAULT_TELEFONE_2))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.fotoUrl").value(DEFAULT_FOTO_URL))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()));
    }


    @Test
    @Transactional
    public void getTutorsByIdFiltering() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        Long id = tutor.getId();

        defaultTutorShouldBeFound("id.equals=" + id);
        defaultTutorShouldNotBeFound("id.notEquals=" + id);

        defaultTutorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTutorShouldNotBeFound("id.greaterThan=" + id);

        defaultTutorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTutorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTutorsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where nome equals to DEFAULT_NOME
        defaultTutorShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the tutorList where nome equals to UPDATED_NOME
        defaultTutorShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTutorsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where nome not equals to DEFAULT_NOME
        defaultTutorShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the tutorList where nome not equals to UPDATED_NOME
        defaultTutorShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTutorsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultTutorShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the tutorList where nome equals to UPDATED_NOME
        defaultTutorShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTutorsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where nome is not null
        defaultTutorShouldBeFound("nome.specified=true");

        // Get all the tutorList where nome is null
        defaultTutorShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllTutorsByNomeContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where nome contains DEFAULT_NOME
        defaultTutorShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the tutorList where nome contains UPDATED_NOME
        defaultTutorShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTutorsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where nome does not contain DEFAULT_NOME
        defaultTutorShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the tutorList where nome does not contain UPDATED_NOME
        defaultTutorShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllTutorsBySobrenomeIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sobrenome equals to DEFAULT_SOBRENOME
        defaultTutorShouldBeFound("sobrenome.equals=" + DEFAULT_SOBRENOME);

        // Get all the tutorList where sobrenome equals to UPDATED_SOBRENOME
        defaultTutorShouldNotBeFound("sobrenome.equals=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllTutorsBySobrenomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sobrenome not equals to DEFAULT_SOBRENOME
        defaultTutorShouldNotBeFound("sobrenome.notEquals=" + DEFAULT_SOBRENOME);

        // Get all the tutorList where sobrenome not equals to UPDATED_SOBRENOME
        defaultTutorShouldBeFound("sobrenome.notEquals=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllTutorsBySobrenomeIsInShouldWork() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sobrenome in DEFAULT_SOBRENOME or UPDATED_SOBRENOME
        defaultTutorShouldBeFound("sobrenome.in=" + DEFAULT_SOBRENOME + "," + UPDATED_SOBRENOME);

        // Get all the tutorList where sobrenome equals to UPDATED_SOBRENOME
        defaultTutorShouldNotBeFound("sobrenome.in=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllTutorsBySobrenomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sobrenome is not null
        defaultTutorShouldBeFound("sobrenome.specified=true");

        // Get all the tutorList where sobrenome is null
        defaultTutorShouldNotBeFound("sobrenome.specified=false");
    }
                @Test
    @Transactional
    public void getAllTutorsBySobrenomeContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sobrenome contains DEFAULT_SOBRENOME
        defaultTutorShouldBeFound("sobrenome.contains=" + DEFAULT_SOBRENOME);

        // Get all the tutorList where sobrenome contains UPDATED_SOBRENOME
        defaultTutorShouldNotBeFound("sobrenome.contains=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllTutorsBySobrenomeNotContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sobrenome does not contain DEFAULT_SOBRENOME
        defaultTutorShouldNotBeFound("sobrenome.doesNotContain=" + DEFAULT_SOBRENOME);

        // Get all the tutorList where sobrenome does not contain UPDATED_SOBRENOME
        defaultTutorShouldBeFound("sobrenome.doesNotContain=" + UPDATED_SOBRENOME);
    }


    @Test
    @Transactional
    public void getAllTutorsByTelefone1IsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone1 equals to DEFAULT_TELEFONE_1
        defaultTutorShouldBeFound("telefone1.equals=" + DEFAULT_TELEFONE_1);

        // Get all the tutorList where telefone1 equals to UPDATED_TELEFONE_1
        defaultTutorShouldNotBeFound("telefone1.equals=" + UPDATED_TELEFONE_1);
    }

    @Test
    @Transactional
    public void getAllTutorsByTelefone1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone1 not equals to DEFAULT_TELEFONE_1
        defaultTutorShouldNotBeFound("telefone1.notEquals=" + DEFAULT_TELEFONE_1);

        // Get all the tutorList where telefone1 not equals to UPDATED_TELEFONE_1
        defaultTutorShouldBeFound("telefone1.notEquals=" + UPDATED_TELEFONE_1);
    }

    @Test
    @Transactional
    public void getAllTutorsByTelefone1IsInShouldWork() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone1 in DEFAULT_TELEFONE_1 or UPDATED_TELEFONE_1
        defaultTutorShouldBeFound("telefone1.in=" + DEFAULT_TELEFONE_1 + "," + UPDATED_TELEFONE_1);

        // Get all the tutorList where telefone1 equals to UPDATED_TELEFONE_1
        defaultTutorShouldNotBeFound("telefone1.in=" + UPDATED_TELEFONE_1);
    }

    @Test
    @Transactional
    public void getAllTutorsByTelefone1IsNullOrNotNull() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone1 is not null
        defaultTutorShouldBeFound("telefone1.specified=true");

        // Get all the tutorList where telefone1 is null
        defaultTutorShouldNotBeFound("telefone1.specified=false");
    }
                @Test
    @Transactional
    public void getAllTutorsByTelefone1ContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone1 contains DEFAULT_TELEFONE_1
        defaultTutorShouldBeFound("telefone1.contains=" + DEFAULT_TELEFONE_1);

        // Get all the tutorList where telefone1 contains UPDATED_TELEFONE_1
        defaultTutorShouldNotBeFound("telefone1.contains=" + UPDATED_TELEFONE_1);
    }

    @Test
    @Transactional
    public void getAllTutorsByTelefone1NotContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone1 does not contain DEFAULT_TELEFONE_1
        defaultTutorShouldNotBeFound("telefone1.doesNotContain=" + DEFAULT_TELEFONE_1);

        // Get all the tutorList where telefone1 does not contain UPDATED_TELEFONE_1
        defaultTutorShouldBeFound("telefone1.doesNotContain=" + UPDATED_TELEFONE_1);
    }


    @Test
    @Transactional
    public void getAllTutorsByTelefone2IsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone2 equals to DEFAULT_TELEFONE_2
        defaultTutorShouldBeFound("telefone2.equals=" + DEFAULT_TELEFONE_2);

        // Get all the tutorList where telefone2 equals to UPDATED_TELEFONE_2
        defaultTutorShouldNotBeFound("telefone2.equals=" + UPDATED_TELEFONE_2);
    }

    @Test
    @Transactional
    public void getAllTutorsByTelefone2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone2 not equals to DEFAULT_TELEFONE_2
        defaultTutorShouldNotBeFound("telefone2.notEquals=" + DEFAULT_TELEFONE_2);

        // Get all the tutorList where telefone2 not equals to UPDATED_TELEFONE_2
        defaultTutorShouldBeFound("telefone2.notEquals=" + UPDATED_TELEFONE_2);
    }

    @Test
    @Transactional
    public void getAllTutorsByTelefone2IsInShouldWork() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone2 in DEFAULT_TELEFONE_2 or UPDATED_TELEFONE_2
        defaultTutorShouldBeFound("telefone2.in=" + DEFAULT_TELEFONE_2 + "," + UPDATED_TELEFONE_2);

        // Get all the tutorList where telefone2 equals to UPDATED_TELEFONE_2
        defaultTutorShouldNotBeFound("telefone2.in=" + UPDATED_TELEFONE_2);
    }

    @Test
    @Transactional
    public void getAllTutorsByTelefone2IsNullOrNotNull() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone2 is not null
        defaultTutorShouldBeFound("telefone2.specified=true");

        // Get all the tutorList where telefone2 is null
        defaultTutorShouldNotBeFound("telefone2.specified=false");
    }
                @Test
    @Transactional
    public void getAllTutorsByTelefone2ContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone2 contains DEFAULT_TELEFONE_2
        defaultTutorShouldBeFound("telefone2.contains=" + DEFAULT_TELEFONE_2);

        // Get all the tutorList where telefone2 contains UPDATED_TELEFONE_2
        defaultTutorShouldNotBeFound("telefone2.contains=" + UPDATED_TELEFONE_2);
    }

    @Test
    @Transactional
    public void getAllTutorsByTelefone2NotContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where telefone2 does not contain DEFAULT_TELEFONE_2
        defaultTutorShouldNotBeFound("telefone2.doesNotContain=" + DEFAULT_TELEFONE_2);

        // Get all the tutorList where telefone2 does not contain UPDATED_TELEFONE_2
        defaultTutorShouldBeFound("telefone2.doesNotContain=" + UPDATED_TELEFONE_2);
    }


    @Test
    @Transactional
    public void getAllTutorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where email equals to DEFAULT_EMAIL
        defaultTutorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the tutorList where email equals to UPDATED_EMAIL
        defaultTutorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTutorsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where email not equals to DEFAULT_EMAIL
        defaultTutorShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the tutorList where email not equals to UPDATED_EMAIL
        defaultTutorShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTutorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultTutorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the tutorList where email equals to UPDATED_EMAIL
        defaultTutorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTutorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where email is not null
        defaultTutorShouldBeFound("email.specified=true");

        // Get all the tutorList where email is null
        defaultTutorShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllTutorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where email contains DEFAULT_EMAIL
        defaultTutorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the tutorList where email contains UPDATED_EMAIL
        defaultTutorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTutorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where email does not contain DEFAULT_EMAIL
        defaultTutorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the tutorList where email does not contain UPDATED_EMAIL
        defaultTutorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllTutorsByFotoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where fotoUrl equals to DEFAULT_FOTO_URL
        defaultTutorShouldBeFound("fotoUrl.equals=" + DEFAULT_FOTO_URL);

        // Get all the tutorList where fotoUrl equals to UPDATED_FOTO_URL
        defaultTutorShouldNotBeFound("fotoUrl.equals=" + UPDATED_FOTO_URL);
    }

    @Test
    @Transactional
    public void getAllTutorsByFotoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where fotoUrl not equals to DEFAULT_FOTO_URL
        defaultTutorShouldNotBeFound("fotoUrl.notEquals=" + DEFAULT_FOTO_URL);

        // Get all the tutorList where fotoUrl not equals to UPDATED_FOTO_URL
        defaultTutorShouldBeFound("fotoUrl.notEquals=" + UPDATED_FOTO_URL);
    }

    @Test
    @Transactional
    public void getAllTutorsByFotoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where fotoUrl in DEFAULT_FOTO_URL or UPDATED_FOTO_URL
        defaultTutorShouldBeFound("fotoUrl.in=" + DEFAULT_FOTO_URL + "," + UPDATED_FOTO_URL);

        // Get all the tutorList where fotoUrl equals to UPDATED_FOTO_URL
        defaultTutorShouldNotBeFound("fotoUrl.in=" + UPDATED_FOTO_URL);
    }

    @Test
    @Transactional
    public void getAllTutorsByFotoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where fotoUrl is not null
        defaultTutorShouldBeFound("fotoUrl.specified=true");

        // Get all the tutorList where fotoUrl is null
        defaultTutorShouldNotBeFound("fotoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllTutorsByFotoUrlContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where fotoUrl contains DEFAULT_FOTO_URL
        defaultTutorShouldBeFound("fotoUrl.contains=" + DEFAULT_FOTO_URL);

        // Get all the tutorList where fotoUrl contains UPDATED_FOTO_URL
        defaultTutorShouldNotBeFound("fotoUrl.contains=" + UPDATED_FOTO_URL);
    }

    @Test
    @Transactional
    public void getAllTutorsByFotoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where fotoUrl does not contain DEFAULT_FOTO_URL
        defaultTutorShouldNotBeFound("fotoUrl.doesNotContain=" + DEFAULT_FOTO_URL);

        // Get all the tutorList where fotoUrl does not contain UPDATED_FOTO_URL
        defaultTutorShouldBeFound("fotoUrl.doesNotContain=" + UPDATED_FOTO_URL);
    }


    @Test
    @Transactional
    public void getAllTutorsByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where cpf equals to DEFAULT_CPF
        defaultTutorShouldBeFound("cpf.equals=" + DEFAULT_CPF);

        // Get all the tutorList where cpf equals to UPDATED_CPF
        defaultTutorShouldNotBeFound("cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllTutorsByCpfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where cpf not equals to DEFAULT_CPF
        defaultTutorShouldNotBeFound("cpf.notEquals=" + DEFAULT_CPF);

        // Get all the tutorList where cpf not equals to UPDATED_CPF
        defaultTutorShouldBeFound("cpf.notEquals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllTutorsByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where cpf in DEFAULT_CPF or UPDATED_CPF
        defaultTutorShouldBeFound("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF);

        // Get all the tutorList where cpf equals to UPDATED_CPF
        defaultTutorShouldNotBeFound("cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllTutorsByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where cpf is not null
        defaultTutorShouldBeFound("cpf.specified=true");

        // Get all the tutorList where cpf is null
        defaultTutorShouldNotBeFound("cpf.specified=false");
    }
                @Test
    @Transactional
    public void getAllTutorsByCpfContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where cpf contains DEFAULT_CPF
        defaultTutorShouldBeFound("cpf.contains=" + DEFAULT_CPF);

        // Get all the tutorList where cpf contains UPDATED_CPF
        defaultTutorShouldNotBeFound("cpf.contains=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllTutorsByCpfNotContainsSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where cpf does not contain DEFAULT_CPF
        defaultTutorShouldNotBeFound("cpf.doesNotContain=" + DEFAULT_CPF);

        // Get all the tutorList where cpf does not contain UPDATED_CPF
        defaultTutorShouldBeFound("cpf.doesNotContain=" + UPDATED_CPF);
    }


    @Test
    @Transactional
    public void getAllTutorsBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sexo equals to DEFAULT_SEXO
        defaultTutorShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the tutorList where sexo equals to UPDATED_SEXO
        defaultTutorShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllTutorsBySexoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sexo not equals to DEFAULT_SEXO
        defaultTutorShouldNotBeFound("sexo.notEquals=" + DEFAULT_SEXO);

        // Get all the tutorList where sexo not equals to UPDATED_SEXO
        defaultTutorShouldBeFound("sexo.notEquals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllTutorsBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultTutorShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the tutorList where sexo equals to UPDATED_SEXO
        defaultTutorShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllTutorsBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where sexo is not null
        defaultTutorShouldBeFound("sexo.specified=true");

        // Get all the tutorList where sexo is null
        defaultTutorShouldNotBeFound("sexo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTutorsByDataCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where dataCadastro equals to DEFAULT_DATA_CADASTRO
        defaultTutorShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

        // Get all the tutorList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultTutorShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllTutorsByDataCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where dataCadastro not equals to DEFAULT_DATA_CADASTRO
        defaultTutorShouldNotBeFound("dataCadastro.notEquals=" + DEFAULT_DATA_CADASTRO);

        // Get all the tutorList where dataCadastro not equals to UPDATED_DATA_CADASTRO
        defaultTutorShouldBeFound("dataCadastro.notEquals=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllTutorsByDataCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
        defaultTutorShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

        // Get all the tutorList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultTutorShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllTutorsByDataCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where dataCadastro is not null
        defaultTutorShouldBeFound("dataCadastro.specified=true");

        // Get all the tutorList where dataCadastro is null
        defaultTutorShouldNotBeFound("dataCadastro.specified=false");
    }

    @Test
    @Transactional
    public void getAllTutorsByDataCadastroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where dataCadastro is greater than or equal to DEFAULT_DATA_CADASTRO
        defaultTutorShouldBeFound("dataCadastro.greaterThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the tutorList where dataCadastro is greater than or equal to UPDATED_DATA_CADASTRO
        defaultTutorShouldNotBeFound("dataCadastro.greaterThanOrEqual=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllTutorsByDataCadastroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where dataCadastro is less than or equal to DEFAULT_DATA_CADASTRO
        defaultTutorShouldBeFound("dataCadastro.lessThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the tutorList where dataCadastro is less than or equal to SMALLER_DATA_CADASTRO
        defaultTutorShouldNotBeFound("dataCadastro.lessThanOrEqual=" + SMALLER_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllTutorsByDataCadastroIsLessThanSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where dataCadastro is less than DEFAULT_DATA_CADASTRO
        defaultTutorShouldNotBeFound("dataCadastro.lessThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the tutorList where dataCadastro is less than UPDATED_DATA_CADASTRO
        defaultTutorShouldBeFound("dataCadastro.lessThan=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllTutorsByDataCadastroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList where dataCadastro is greater than DEFAULT_DATA_CADASTRO
        defaultTutorShouldNotBeFound("dataCadastro.greaterThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the tutorList where dataCadastro is greater than SMALLER_DATA_CADASTRO
        defaultTutorShouldBeFound("dataCadastro.greaterThan=" + SMALLER_DATA_CADASTRO);
    }


    @Test
    @Transactional
    public void getAllTutorsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        tutor.setUser(user);
        tutorRepository.saveAndFlush(tutor);
        Long userId = user.getId();

        // Get all the tutorList where user equals to userId
        defaultTutorShouldBeFound("userId.equals=" + userId);

        // Get all the tutorList where user equals to userId + 1
        defaultTutorShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllTutorsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);
        Titulo titulo = TituloResourceIT.createEntity(em);
        em.persist(titulo);
        em.flush();
        tutor.addTitulo(titulo);
        tutorRepository.saveAndFlush(tutor);
        Long tituloId = titulo.getId();

        // Get all the tutorList where titulo equals to tituloId
        defaultTutorShouldBeFound("tituloId.equals=" + tituloId);

        // Get all the tutorList where titulo equals to tituloId + 1
        defaultTutorShouldNotBeFound("tituloId.equals=" + (tituloId + 1));
    }


    @Test
    @Transactional
    public void getAllTutorsByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);
        Animal animal = AnimalResourceIT.createEntity(em);
        em.persist(animal);
        em.flush();
        tutor.addAnimal(animal);
        tutorRepository.saveAndFlush(tutor);
        Long animalId = animal.getId();

        // Get all the tutorList where animal equals to animalId
        defaultTutorShouldBeFound("animalId.equals=" + animalId);

        // Get all the tutorList where animal equals to animalId + 1
        defaultTutorShouldNotBeFound("animalId.equals=" + (animalId + 1));
    }


    @Test
    @Transactional
    public void getAllTutorsByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);
        Endereco endereco = EnderecoResourceIT.createEntity(em);
        em.persist(endereco);
        em.flush();
        tutor.setEndereco(endereco);
        tutorRepository.saveAndFlush(tutor);
        Long enderecoId = endereco.getId();

        // Get all the tutorList where endereco equals to enderecoId
        defaultTutorShouldBeFound("enderecoId.equals=" + enderecoId);

        // Get all the tutorList where endereco equals to enderecoId + 1
        defaultTutorShouldNotBeFound("enderecoId.equals=" + (enderecoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTutorShouldBeFound(String filter) throws Exception {
        restTutorMockMvc.perform(get("/api/tutors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tutor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].telefone1").value(hasItem(DEFAULT_TELEFONE_1)))
            .andExpect(jsonPath("$.[*].telefone2").value(hasItem(DEFAULT_TELEFONE_2)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].fotoUrl").value(hasItem(DEFAULT_FOTO_URL)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())));

        // Check, that the count call also returns 1
        restTutorMockMvc.perform(get("/api/tutors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTutorShouldNotBeFound(String filter) throws Exception {
        restTutorMockMvc.perform(get("/api/tutors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTutorMockMvc.perform(get("/api/tutors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTutor() throws Exception {
        // Get the tutor
        restTutorMockMvc.perform(get("/api/tutors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTutor() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();

        // Update the tutor
        Tutor updatedTutor = tutorRepository.findById(tutor.getId()).get();
        // Disconnect from session so that the updates on updatedTutor are not directly saved in db
        em.detach(updatedTutor);
        updatedTutor
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .telefone1(UPDATED_TELEFONE_1)
            .telefone2(UPDATED_TELEFONE_2)
            .email(UPDATED_EMAIL)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .fotoUrl(UPDATED_FOTO_URL)
            .cpf(UPDATED_CPF)
            .sexo(UPDATED_SEXO)
            .dataCadastro(UPDATED_DATA_CADASTRO);
        TutorDTO tutorDTO = tutorMapper.toDto(updatedTutor);

        restTutorMockMvc.perform(put("/api/tutors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tutorDTO)))
            .andExpect(status().isOk());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
        Tutor testTutor = tutorList.get(tutorList.size() - 1);
        assertThat(testTutor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTutor.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testTutor.getTelefone1()).isEqualTo(UPDATED_TELEFONE_1);
        assertThat(testTutor.getTelefone2()).isEqualTo(UPDATED_TELEFONE_2);
        assertThat(testTutor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTutor.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testTutor.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testTutor.getFotoUrl()).isEqualTo(UPDATED_FOTO_URL);
        assertThat(testTutor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testTutor.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testTutor.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void updateNonExistingTutor() throws Exception {
        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();

        // Create the Tutor
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTutorMockMvc.perform(put("/api/tutors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tutorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTutor() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        int databaseSizeBeforeDelete = tutorRepository.findAll().size();

        // Delete the tutor
        restTutorMockMvc.perform(delete("/api/tutors/{id}", tutor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
