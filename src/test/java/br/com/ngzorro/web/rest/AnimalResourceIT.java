package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.domain.Atendimento;
import br.com.ngzorro.domain.AnimalVacina;
import br.com.ngzorro.domain.AnimalAlteracao;
import br.com.ngzorro.domain.AnimalVermifugo;
import br.com.ngzorro.domain.AnimalCarrapaticida;
import br.com.ngzorro.domain.AnimalObservacao;
import br.com.ngzorro.domain.Anexo;
import br.com.ngzorro.domain.AnimalCio;
import br.com.ngzorro.domain.Endereco;
import br.com.ngzorro.domain.AnimalVeterinario;
import br.com.ngzorro.domain.Raca;
import br.com.ngzorro.domain.Tutor;
import br.com.ngzorro.repository.AnimalRepository;
import br.com.ngzorro.service.AnimalService;
import br.com.ngzorro.service.dto.AnimalDTO;
import br.com.ngzorro.service.mapper.AnimalMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalCriteria;
import br.com.ngzorro.service.AnimalQueryService;

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

import br.com.ngzorro.domain.enumeration.AnimalSexo;
/**
 * Integration tests for the {@link AnimalResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalResourceIT {

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_FOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final AnimalSexo DEFAULT_SEXO = AnimalSexo.MACHO;
    private static final AnimalSexo UPDATED_SEXO = AnimalSexo.FEMEA;

    private static final String DEFAULT_PELAGEM = "AAAAAAAAAA";
    private static final String UPDATED_PELAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERAMENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EM_ATENDIMENTO = false;
    private static final Boolean UPDATED_EM_ATENDIMENTO = true;

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_NASCIMENTO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalMapper animalMapper;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AnimalQueryService animalQueryService;

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

    private MockMvc restAnimalMockMvc;

    private Animal animal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalResource animalResource = new AnimalResource(animalService, animalQueryService);
        this.restAnimalMockMvc = MockMvcBuilders.standaloneSetup(animalResource)
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
    public static Animal createEntity(EntityManager em) {
        Animal animal = new Animal()
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .fotoUrl(DEFAULT_FOTO_URL)
            .nome(DEFAULT_NOME)
            .sexo(DEFAULT_SEXO)
            .pelagem(DEFAULT_PELAGEM)
            .temperamento(DEFAULT_TEMPERAMENTO)
            .emAtendimento(DEFAULT_EM_ATENDIMENTO)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO);
        return animal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Animal createUpdatedEntity(EntityManager em) {
        Animal animal = new Animal()
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .fotoUrl(UPDATED_FOTO_URL)
            .nome(UPDATED_NOME)
            .sexo(UPDATED_SEXO)
            .pelagem(UPDATED_PELAGEM)
            .temperamento(UPDATED_TEMPERAMENTO)
            .emAtendimento(UPDATED_EM_ATENDIMENTO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO);
        return animal;
    }

    @BeforeEach
    public void initTest() {
        animal = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimal() throws Exception {
        int databaseSizeBeforeCreate = animalRepository.findAll().size();

        // Create the Animal
        AnimalDTO animalDTO = animalMapper.toDto(animal);
        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalDTO)))
            .andExpect(status().isCreated());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeCreate + 1);
        Animal testAnimal = animalList.get(animalList.size() - 1);
        assertThat(testAnimal.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testAnimal.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testAnimal.getFotoUrl()).isEqualTo(DEFAULT_FOTO_URL);
        assertThat(testAnimal.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAnimal.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testAnimal.getPelagem()).isEqualTo(DEFAULT_PELAGEM);
        assertThat(testAnimal.getTemperamento()).isEqualTo(DEFAULT_TEMPERAMENTO);
        assertThat(testAnimal.isEmAtendimento()).isEqualTo(DEFAULT_EM_ATENDIMENTO);
        assertThat(testAnimal.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void createAnimalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalRepository.findAll().size();

        // Create the Animal with an existing ID
        animal.setId(1L);
        AnimalDTO animalDTO = animalMapper.toDto(animal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimals() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList
        restAnimalMockMvc.perform(get("/api/animals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animal.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].fotoUrl").value(hasItem(DEFAULT_FOTO_URL)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].pelagem").value(hasItem(DEFAULT_PELAGEM)))
            .andExpect(jsonPath("$.[*].temperamento").value(hasItem(DEFAULT_TEMPERAMENTO)))
            .andExpect(jsonPath("$.[*].emAtendimento").value(hasItem(DEFAULT_EM_ATENDIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", animal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animal.getId().intValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.fotoUrl").value(DEFAULT_FOTO_URL))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.pelagem").value(DEFAULT_PELAGEM))
            .andExpect(jsonPath("$.temperamento").value(DEFAULT_TEMPERAMENTO))
            .andExpect(jsonPath("$.emAtendimento").value(DEFAULT_EM_ATENDIMENTO.booleanValue()))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()));
    }


    @Test
    @Transactional
    public void getAnimalsByIdFiltering() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        Long id = animal.getId();

        defaultAnimalShouldBeFound("id.equals=" + id);
        defaultAnimalShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalsByFotoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where fotoUrl equals to DEFAULT_FOTO_URL
        defaultAnimalShouldBeFound("fotoUrl.equals=" + DEFAULT_FOTO_URL);

        // Get all the animalList where fotoUrl equals to UPDATED_FOTO_URL
        defaultAnimalShouldNotBeFound("fotoUrl.equals=" + UPDATED_FOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAnimalsByFotoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where fotoUrl not equals to DEFAULT_FOTO_URL
        defaultAnimalShouldNotBeFound("fotoUrl.notEquals=" + DEFAULT_FOTO_URL);

        // Get all the animalList where fotoUrl not equals to UPDATED_FOTO_URL
        defaultAnimalShouldBeFound("fotoUrl.notEquals=" + UPDATED_FOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAnimalsByFotoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where fotoUrl in DEFAULT_FOTO_URL or UPDATED_FOTO_URL
        defaultAnimalShouldBeFound("fotoUrl.in=" + DEFAULT_FOTO_URL + "," + UPDATED_FOTO_URL);

        // Get all the animalList where fotoUrl equals to UPDATED_FOTO_URL
        defaultAnimalShouldNotBeFound("fotoUrl.in=" + UPDATED_FOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAnimalsByFotoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where fotoUrl is not null
        defaultAnimalShouldBeFound("fotoUrl.specified=true");

        // Get all the animalList where fotoUrl is null
        defaultAnimalShouldNotBeFound("fotoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalsByFotoUrlContainsSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where fotoUrl contains DEFAULT_FOTO_URL
        defaultAnimalShouldBeFound("fotoUrl.contains=" + DEFAULT_FOTO_URL);

        // Get all the animalList where fotoUrl contains UPDATED_FOTO_URL
        defaultAnimalShouldNotBeFound("fotoUrl.contains=" + UPDATED_FOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAnimalsByFotoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where fotoUrl does not contain DEFAULT_FOTO_URL
        defaultAnimalShouldNotBeFound("fotoUrl.doesNotContain=" + DEFAULT_FOTO_URL);

        // Get all the animalList where fotoUrl does not contain UPDATED_FOTO_URL
        defaultAnimalShouldBeFound("fotoUrl.doesNotContain=" + UPDATED_FOTO_URL);
    }


    @Test
    @Transactional
    public void getAllAnimalsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where nome equals to DEFAULT_NOME
        defaultAnimalShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the animalList where nome equals to UPDATED_NOME
        defaultAnimalShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where nome not equals to DEFAULT_NOME
        defaultAnimalShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the animalList where nome not equals to UPDATED_NOME
        defaultAnimalShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAnimalShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the animalList where nome equals to UPDATED_NOME
        defaultAnimalShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where nome is not null
        defaultAnimalShouldBeFound("nome.specified=true");

        // Get all the animalList where nome is null
        defaultAnimalShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalsByNomeContainsSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where nome contains DEFAULT_NOME
        defaultAnimalShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the animalList where nome contains UPDATED_NOME
        defaultAnimalShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where nome does not contain DEFAULT_NOME
        defaultAnimalShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the animalList where nome does not contain UPDATED_NOME
        defaultAnimalShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAnimalsBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where sexo equals to DEFAULT_SEXO
        defaultAnimalShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the animalList where sexo equals to UPDATED_SEXO
        defaultAnimalShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllAnimalsBySexoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where sexo not equals to DEFAULT_SEXO
        defaultAnimalShouldNotBeFound("sexo.notEquals=" + DEFAULT_SEXO);

        // Get all the animalList where sexo not equals to UPDATED_SEXO
        defaultAnimalShouldBeFound("sexo.notEquals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllAnimalsBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultAnimalShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the animalList where sexo equals to UPDATED_SEXO
        defaultAnimalShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllAnimalsBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where sexo is not null
        defaultAnimalShouldBeFound("sexo.specified=true");

        // Get all the animalList where sexo is null
        defaultAnimalShouldNotBeFound("sexo.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnimalsByPelagemIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where pelagem equals to DEFAULT_PELAGEM
        defaultAnimalShouldBeFound("pelagem.equals=" + DEFAULT_PELAGEM);

        // Get all the animalList where pelagem equals to UPDATED_PELAGEM
        defaultAnimalShouldNotBeFound("pelagem.equals=" + UPDATED_PELAGEM);
    }

    @Test
    @Transactional
    public void getAllAnimalsByPelagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where pelagem not equals to DEFAULT_PELAGEM
        defaultAnimalShouldNotBeFound("pelagem.notEquals=" + DEFAULT_PELAGEM);

        // Get all the animalList where pelagem not equals to UPDATED_PELAGEM
        defaultAnimalShouldBeFound("pelagem.notEquals=" + UPDATED_PELAGEM);
    }

    @Test
    @Transactional
    public void getAllAnimalsByPelagemIsInShouldWork() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where pelagem in DEFAULT_PELAGEM or UPDATED_PELAGEM
        defaultAnimalShouldBeFound("pelagem.in=" + DEFAULT_PELAGEM + "," + UPDATED_PELAGEM);

        // Get all the animalList where pelagem equals to UPDATED_PELAGEM
        defaultAnimalShouldNotBeFound("pelagem.in=" + UPDATED_PELAGEM);
    }

    @Test
    @Transactional
    public void getAllAnimalsByPelagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where pelagem is not null
        defaultAnimalShouldBeFound("pelagem.specified=true");

        // Get all the animalList where pelagem is null
        defaultAnimalShouldNotBeFound("pelagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalsByPelagemContainsSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where pelagem contains DEFAULT_PELAGEM
        defaultAnimalShouldBeFound("pelagem.contains=" + DEFAULT_PELAGEM);

        // Get all the animalList where pelagem contains UPDATED_PELAGEM
        defaultAnimalShouldNotBeFound("pelagem.contains=" + UPDATED_PELAGEM);
    }

    @Test
    @Transactional
    public void getAllAnimalsByPelagemNotContainsSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where pelagem does not contain DEFAULT_PELAGEM
        defaultAnimalShouldNotBeFound("pelagem.doesNotContain=" + DEFAULT_PELAGEM);

        // Get all the animalList where pelagem does not contain UPDATED_PELAGEM
        defaultAnimalShouldBeFound("pelagem.doesNotContain=" + UPDATED_PELAGEM);
    }


    @Test
    @Transactional
    public void getAllAnimalsByTemperamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where temperamento equals to DEFAULT_TEMPERAMENTO
        defaultAnimalShouldBeFound("temperamento.equals=" + DEFAULT_TEMPERAMENTO);

        // Get all the animalList where temperamento equals to UPDATED_TEMPERAMENTO
        defaultAnimalShouldNotBeFound("temperamento.equals=" + UPDATED_TEMPERAMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByTemperamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where temperamento not equals to DEFAULT_TEMPERAMENTO
        defaultAnimalShouldNotBeFound("temperamento.notEquals=" + DEFAULT_TEMPERAMENTO);

        // Get all the animalList where temperamento not equals to UPDATED_TEMPERAMENTO
        defaultAnimalShouldBeFound("temperamento.notEquals=" + UPDATED_TEMPERAMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByTemperamentoIsInShouldWork() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where temperamento in DEFAULT_TEMPERAMENTO or UPDATED_TEMPERAMENTO
        defaultAnimalShouldBeFound("temperamento.in=" + DEFAULT_TEMPERAMENTO + "," + UPDATED_TEMPERAMENTO);

        // Get all the animalList where temperamento equals to UPDATED_TEMPERAMENTO
        defaultAnimalShouldNotBeFound("temperamento.in=" + UPDATED_TEMPERAMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByTemperamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where temperamento is not null
        defaultAnimalShouldBeFound("temperamento.specified=true");

        // Get all the animalList where temperamento is null
        defaultAnimalShouldNotBeFound("temperamento.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalsByTemperamentoContainsSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where temperamento contains DEFAULT_TEMPERAMENTO
        defaultAnimalShouldBeFound("temperamento.contains=" + DEFAULT_TEMPERAMENTO);

        // Get all the animalList where temperamento contains UPDATED_TEMPERAMENTO
        defaultAnimalShouldNotBeFound("temperamento.contains=" + UPDATED_TEMPERAMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByTemperamentoNotContainsSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where temperamento does not contain DEFAULT_TEMPERAMENTO
        defaultAnimalShouldNotBeFound("temperamento.doesNotContain=" + DEFAULT_TEMPERAMENTO);

        // Get all the animalList where temperamento does not contain UPDATED_TEMPERAMENTO
        defaultAnimalShouldBeFound("temperamento.doesNotContain=" + UPDATED_TEMPERAMENTO);
    }


    @Test
    @Transactional
    public void getAllAnimalsByEmAtendimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where emAtendimento equals to DEFAULT_EM_ATENDIMENTO
        defaultAnimalShouldBeFound("emAtendimento.equals=" + DEFAULT_EM_ATENDIMENTO);

        // Get all the animalList where emAtendimento equals to UPDATED_EM_ATENDIMENTO
        defaultAnimalShouldNotBeFound("emAtendimento.equals=" + UPDATED_EM_ATENDIMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByEmAtendimentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where emAtendimento not equals to DEFAULT_EM_ATENDIMENTO
        defaultAnimalShouldNotBeFound("emAtendimento.notEquals=" + DEFAULT_EM_ATENDIMENTO);

        // Get all the animalList where emAtendimento not equals to UPDATED_EM_ATENDIMENTO
        defaultAnimalShouldBeFound("emAtendimento.notEquals=" + UPDATED_EM_ATENDIMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByEmAtendimentoIsInShouldWork() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where emAtendimento in DEFAULT_EM_ATENDIMENTO or UPDATED_EM_ATENDIMENTO
        defaultAnimalShouldBeFound("emAtendimento.in=" + DEFAULT_EM_ATENDIMENTO + "," + UPDATED_EM_ATENDIMENTO);

        // Get all the animalList where emAtendimento equals to UPDATED_EM_ATENDIMENTO
        defaultAnimalShouldNotBeFound("emAtendimento.in=" + UPDATED_EM_ATENDIMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByEmAtendimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where emAtendimento is not null
        defaultAnimalShouldBeFound("emAtendimento.specified=true");

        // Get all the animalList where emAtendimento is null
        defaultAnimalShouldNotBeFound("emAtendimento.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnimalsByDataNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where dataNascimento equals to DEFAULT_DATA_NASCIMENTO
        defaultAnimalShouldBeFound("dataNascimento.equals=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the animalList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultAnimalShouldNotBeFound("dataNascimento.equals=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByDataNascimentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where dataNascimento not equals to DEFAULT_DATA_NASCIMENTO
        defaultAnimalShouldNotBeFound("dataNascimento.notEquals=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the animalList where dataNascimento not equals to UPDATED_DATA_NASCIMENTO
        defaultAnimalShouldBeFound("dataNascimento.notEquals=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByDataNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where dataNascimento in DEFAULT_DATA_NASCIMENTO or UPDATED_DATA_NASCIMENTO
        defaultAnimalShouldBeFound("dataNascimento.in=" + DEFAULT_DATA_NASCIMENTO + "," + UPDATED_DATA_NASCIMENTO);

        // Get all the animalList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultAnimalShouldNotBeFound("dataNascimento.in=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByDataNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where dataNascimento is not null
        defaultAnimalShouldBeFound("dataNascimento.specified=true");

        // Get all the animalList where dataNascimento is null
        defaultAnimalShouldNotBeFound("dataNascimento.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnimalsByDataNascimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where dataNascimento is greater than or equal to DEFAULT_DATA_NASCIMENTO
        defaultAnimalShouldBeFound("dataNascimento.greaterThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the animalList where dataNascimento is greater than or equal to UPDATED_DATA_NASCIMENTO
        defaultAnimalShouldNotBeFound("dataNascimento.greaterThanOrEqual=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByDataNascimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where dataNascimento is less than or equal to DEFAULT_DATA_NASCIMENTO
        defaultAnimalShouldBeFound("dataNascimento.lessThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the animalList where dataNascimento is less than or equal to SMALLER_DATA_NASCIMENTO
        defaultAnimalShouldNotBeFound("dataNascimento.lessThanOrEqual=" + SMALLER_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByDataNascimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where dataNascimento is less than DEFAULT_DATA_NASCIMENTO
        defaultAnimalShouldNotBeFound("dataNascimento.lessThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the animalList where dataNascimento is less than UPDATED_DATA_NASCIMENTO
        defaultAnimalShouldBeFound("dataNascimento.lessThan=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllAnimalsByDataNascimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList where dataNascimento is greater than DEFAULT_DATA_NASCIMENTO
        defaultAnimalShouldNotBeFound("dataNascimento.greaterThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the animalList where dataNascimento is greater than SMALLER_DATA_NASCIMENTO
        defaultAnimalShouldBeFound("dataNascimento.greaterThan=" + SMALLER_DATA_NASCIMENTO);
    }


    @Test
    @Transactional
    public void getAllAnimalsByAtendimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        Atendimento atendimento = AtendimentoResourceIT.createEntity(em);
        em.persist(atendimento);
        em.flush();
        animal.addAtendimento(atendimento);
        animalRepository.saveAndFlush(animal);
        Long atendimentoId = atendimento.getId();

        // Get all the animalList where atendimento equals to atendimentoId
        defaultAnimalShouldBeFound("atendimentoId.equals=" + atendimentoId);

        // Get all the animalList where atendimento equals to atendimentoId + 1
        defaultAnimalShouldNotBeFound("atendimentoId.equals=" + (atendimentoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByTipoVacinaIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        AnimalVacina tipoVacina = AnimalVacinaResourceIT.createEntity(em);
        em.persist(tipoVacina);
        em.flush();
        animal.addTipoVacina(tipoVacina);
        animalRepository.saveAndFlush(animal);
        Long tipoVacinaId = tipoVacina.getId();

        // Get all the animalList where tipoVacina equals to tipoVacinaId
        defaultAnimalShouldBeFound("tipoVacinaId.equals=" + tipoVacinaId);

        // Get all the animalList where tipoVacina equals to tipoVacinaId + 1
        defaultAnimalShouldNotBeFound("tipoVacinaId.equals=" + (tipoVacinaId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByAnimalAlteracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        AnimalAlteracao animalAlteracao = AnimalAlteracaoResourceIT.createEntity(em);
        em.persist(animalAlteracao);
        em.flush();
        animal.addAnimalAlteracao(animalAlteracao);
        animalRepository.saveAndFlush(animal);
        Long animalAlteracaoId = animalAlteracao.getId();

        // Get all the animalList where animalAlteracao equals to animalAlteracaoId
        defaultAnimalShouldBeFound("animalAlteracaoId.equals=" + animalAlteracaoId);

        // Get all the animalList where animalAlteracao equals to animalAlteracaoId + 1
        defaultAnimalShouldNotBeFound("animalAlteracaoId.equals=" + (animalAlteracaoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByAnimalVermifugoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        AnimalVermifugo animalVermifugo = AnimalVermifugoResourceIT.createEntity(em);
        em.persist(animalVermifugo);
        em.flush();
        animal.addAnimalVermifugo(animalVermifugo);
        animalRepository.saveAndFlush(animal);
        Long animalVermifugoId = animalVermifugo.getId();

        // Get all the animalList where animalVermifugo equals to animalVermifugoId
        defaultAnimalShouldBeFound("animalVermifugoId.equals=" + animalVermifugoId);

        // Get all the animalList where animalVermifugo equals to animalVermifugoId + 1
        defaultAnimalShouldNotBeFound("animalVermifugoId.equals=" + (animalVermifugoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByAnimalCarrapaticidaIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        AnimalCarrapaticida animalCarrapaticida = AnimalCarrapaticidaResourceIT.createEntity(em);
        em.persist(animalCarrapaticida);
        em.flush();
        animal.addAnimalCarrapaticida(animalCarrapaticida);
        animalRepository.saveAndFlush(animal);
        Long animalCarrapaticidaId = animalCarrapaticida.getId();

        // Get all the animalList where animalCarrapaticida equals to animalCarrapaticidaId
        defaultAnimalShouldBeFound("animalCarrapaticidaId.equals=" + animalCarrapaticidaId);

        // Get all the animalList where animalCarrapaticida equals to animalCarrapaticidaId + 1
        defaultAnimalShouldNotBeFound("animalCarrapaticidaId.equals=" + (animalCarrapaticidaId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        AnimalObservacao observacao = AnimalObservacaoResourceIT.createEntity(em);
        em.persist(observacao);
        em.flush();
        animal.addObservacao(observacao);
        animalRepository.saveAndFlush(animal);
        Long observacaoId = observacao.getId();

        // Get all the animalList where observacao equals to observacaoId
        defaultAnimalShouldBeFound("observacaoId.equals=" + observacaoId);

        // Get all the animalList where observacao equals to observacaoId + 1
        defaultAnimalShouldNotBeFound("observacaoId.equals=" + (observacaoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        animal.addAnexo(anexo);
        animalRepository.saveAndFlush(animal);
        Long anexoId = anexo.getId();

        // Get all the animalList where anexo equals to anexoId
        defaultAnimalShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the animalList where anexo equals to anexoId + 1
        defaultAnimalShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByAnimalCioIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        AnimalCio animalCio = AnimalCioResourceIT.createEntity(em);
        em.persist(animalCio);
        em.flush();
        animal.addAnimalCio(animalCio);
        animalRepository.saveAndFlush(animal);
        Long animalCioId = animalCio.getId();

        // Get all the animalList where animalCio equals to animalCioId
        defaultAnimalShouldBeFound("animalCioId.equals=" + animalCioId);

        // Get all the animalList where animalCio equals to animalCioId + 1
        defaultAnimalShouldNotBeFound("animalCioId.equals=" + (animalCioId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        Endereco endereco = EnderecoResourceIT.createEntity(em);
        em.persist(endereco);
        em.flush();
        animal.setEndereco(endereco);
        animalRepository.saveAndFlush(animal);
        Long enderecoId = endereco.getId();

        // Get all the animalList where endereco equals to enderecoId
        defaultAnimalShouldBeFound("enderecoId.equals=" + enderecoId);

        // Get all the animalList where endereco equals to enderecoId + 1
        defaultAnimalShouldNotBeFound("enderecoId.equals=" + (enderecoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByVeterinarioIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        AnimalVeterinario veterinario = AnimalVeterinarioResourceIT.createEntity(em);
        em.persist(veterinario);
        em.flush();
        animal.setVeterinario(veterinario);
        animalRepository.saveAndFlush(animal);
        Long veterinarioId = veterinario.getId();

        // Get all the animalList where veterinario equals to veterinarioId
        defaultAnimalShouldBeFound("veterinarioId.equals=" + veterinarioId);

        // Get all the animalList where veterinario equals to veterinarioId + 1
        defaultAnimalShouldNotBeFound("veterinarioId.equals=" + (veterinarioId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByRacaIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        Raca raca = RacaResourceIT.createEntity(em);
        em.persist(raca);
        em.flush();
        animal.setRaca(raca);
        animalRepository.saveAndFlush(animal);
        Long racaId = raca.getId();

        // Get all the animalList where raca equals to racaId
        defaultAnimalShouldBeFound("racaId.equals=" + racaId);

        // Get all the animalList where raca equals to racaId + 1
        defaultAnimalShouldNotBeFound("racaId.equals=" + (racaId + 1));
    }


    @Test
    @Transactional
    public void getAllAnimalsByTutorIsEqualToSomething() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);
        Tutor tutor = TutorResourceIT.createEntity(em);
        em.persist(tutor);
        em.flush();
        animal.setTutor(tutor);
        animalRepository.saveAndFlush(animal);
        Long tutorId = tutor.getId();

        // Get all the animalList where tutor equals to tutorId
        defaultAnimalShouldBeFound("tutorId.equals=" + tutorId);

        // Get all the animalList where tutor equals to tutorId + 1
        defaultAnimalShouldNotBeFound("tutorId.equals=" + (tutorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalShouldBeFound(String filter) throws Exception {
        restAnimalMockMvc.perform(get("/api/animals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animal.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].fotoUrl").value(hasItem(DEFAULT_FOTO_URL)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].pelagem").value(hasItem(DEFAULT_PELAGEM)))
            .andExpect(jsonPath("$.[*].temperamento").value(hasItem(DEFAULT_TEMPERAMENTO)))
            .andExpect(jsonPath("$.[*].emAtendimento").value(hasItem(DEFAULT_EM_ATENDIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())));

        // Check, that the count call also returns 1
        restAnimalMockMvc.perform(get("/api/animals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalShouldNotBeFound(String filter) throws Exception {
        restAnimalMockMvc.perform(get("/api/animals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalMockMvc.perform(get("/api/animals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimal() throws Exception {
        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        int databaseSizeBeforeUpdate = animalRepository.findAll().size();

        // Update the animal
        Animal updatedAnimal = animalRepository.findById(animal.getId()).get();
        // Disconnect from session so that the updates on updatedAnimal are not directly saved in db
        em.detach(updatedAnimal);
        updatedAnimal
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .fotoUrl(UPDATED_FOTO_URL)
            .nome(UPDATED_NOME)
            .sexo(UPDATED_SEXO)
            .pelagem(UPDATED_PELAGEM)
            .temperamento(UPDATED_TEMPERAMENTO)
            .emAtendimento(UPDATED_EM_ATENDIMENTO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO);
        AnimalDTO animalDTO = animalMapper.toDto(updatedAnimal);

        restAnimalMockMvc.perform(put("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalDTO)))
            .andExpect(status().isOk());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
        Animal testAnimal = animalList.get(animalList.size() - 1);
        assertThat(testAnimal.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testAnimal.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testAnimal.getFotoUrl()).isEqualTo(UPDATED_FOTO_URL);
        assertThat(testAnimal.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAnimal.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testAnimal.getPelagem()).isEqualTo(UPDATED_PELAGEM);
        assertThat(testAnimal.getTemperamento()).isEqualTo(UPDATED_TEMPERAMENTO);
        assertThat(testAnimal.isEmAtendimento()).isEqualTo(UPDATED_EM_ATENDIMENTO);
        assertThat(testAnimal.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimal() throws Exception {
        int databaseSizeBeforeUpdate = animalRepository.findAll().size();

        // Create the Animal
        AnimalDTO animalDTO = animalMapper.toDto(animal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalMockMvc.perform(put("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        int databaseSizeBeforeDelete = animalRepository.findAll().size();

        // Delete the animal
        restAnimalMockMvc.perform(delete("/api/animals/{id}", animal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
