package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Endereco;
import br.com.ngzorro.repository.EnderecoRepository;
import br.com.ngzorro.service.EnderecoService;
import br.com.ngzorro.service.dto.EnderecoDTO;
import br.com.ngzorro.service.mapper.EnderecoMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.EnderecoCriteria;
import br.com.ngzorro.service.EnderecoQueryService;

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
 * Integration tests for the {@link EnderecoResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class EnderecoResourceIT {

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AAAAAAAAAA";
    private static final String UPDATED_UF = "BBBBBBBBBB";

    private static final String DEFAULT_IBGE = "AAAAAAAAAA";
    private static final String UPDATED_IBGE = "BBBBBBBBBB";

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoQueryService enderecoQueryService;

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

    private MockMvc restEnderecoMockMvc;

    private Endereco endereco;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnderecoResource enderecoResource = new EnderecoResource(enderecoService, enderecoQueryService);
        this.restEnderecoMockMvc = MockMvcBuilders.standaloneSetup(enderecoResource)
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
    public static Endereco createEntity(EntityManager em) {
        Endereco endereco = new Endereco()
            .cep(DEFAULT_CEP)
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .bairro(DEFAULT_BAIRRO)
            .localidade(DEFAULT_LOCALIDADE)
            .uf(DEFAULT_UF)
            .ibge(DEFAULT_IBGE);
        return endereco;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Endereco createUpdatedEntity(EntityManager em) {
        Endereco endereco = new Endereco()
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .localidade(UPDATED_LOCALIDADE)
            .uf(UPDATED_UF)
            .ibge(UPDATED_IBGE);
        return endereco;
    }

    @BeforeEach
    public void initTest() {
        endereco = createEntity(em);
    }

    @Test
    @Transactional
    public void createEndereco() throws Exception {
        int databaseSizeBeforeCreate = enderecoRepository.findAll().size();

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);
        restEnderecoMockMvc.perform(post("/api/enderecos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isCreated());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeCreate + 1);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testEndereco.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testEndereco.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEndereco.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testEndereco.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEndereco.getLocalidade()).isEqualTo(DEFAULT_LOCALIDADE);
        assertThat(testEndereco.getUf()).isEqualTo(DEFAULT_UF);
        assertThat(testEndereco.getIbge()).isEqualTo(DEFAULT_IBGE);
    }

    @Test
    @Transactional
    public void createEnderecoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enderecoRepository.findAll().size();

        // Create the Endereco with an existing ID
        endereco.setId(1L);
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoMockMvc.perform(post("/api/enderecos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEnderecos() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList
        restEnderecoMockMvc.perform(get("/api/enderecos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endereco.getId().intValue())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].localidade").value(hasItem(DEFAULT_LOCALIDADE)))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF)))
            .andExpect(jsonPath("$.[*].ibge").value(hasItem(DEFAULT_IBGE)));
    }
    
    @Test
    @Transactional
    public void getEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get the endereco
        restEnderecoMockMvc.perform(get("/api/enderecos/{id}", endereco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(endereco.getId().intValue()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.localidade").value(DEFAULT_LOCALIDADE))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF))
            .andExpect(jsonPath("$.ibge").value(DEFAULT_IBGE));
    }


    @Test
    @Transactional
    public void getEnderecosByIdFiltering() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        Long id = endereco.getId();

        defaultEnderecoShouldBeFound("id.equals=" + id);
        defaultEnderecoShouldNotBeFound("id.notEquals=" + id);

        defaultEnderecoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnderecoShouldNotBeFound("id.greaterThan=" + id);

        defaultEnderecoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnderecoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnderecosByCepIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep equals to DEFAULT_CEP
        defaultEnderecoShouldBeFound("cep.equals=" + DEFAULT_CEP);

        // Get all the enderecoList where cep equals to UPDATED_CEP
        defaultEnderecoShouldNotBeFound("cep.equals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllEnderecosByCepIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep not equals to DEFAULT_CEP
        defaultEnderecoShouldNotBeFound("cep.notEquals=" + DEFAULT_CEP);

        // Get all the enderecoList where cep not equals to UPDATED_CEP
        defaultEnderecoShouldBeFound("cep.notEquals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllEnderecosByCepIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep in DEFAULT_CEP or UPDATED_CEP
        defaultEnderecoShouldBeFound("cep.in=" + DEFAULT_CEP + "," + UPDATED_CEP);

        // Get all the enderecoList where cep equals to UPDATED_CEP
        defaultEnderecoShouldNotBeFound("cep.in=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllEnderecosByCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep is not null
        defaultEnderecoShouldBeFound("cep.specified=true");

        // Get all the enderecoList where cep is null
        defaultEnderecoShouldNotBeFound("cep.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnderecosByCepContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep contains DEFAULT_CEP
        defaultEnderecoShouldBeFound("cep.contains=" + DEFAULT_CEP);

        // Get all the enderecoList where cep contains UPDATED_CEP
        defaultEnderecoShouldNotBeFound("cep.contains=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllEnderecosByCepNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep does not contain DEFAULT_CEP
        defaultEnderecoShouldNotBeFound("cep.doesNotContain=" + DEFAULT_CEP);

        // Get all the enderecoList where cep does not contain UPDATED_CEP
        defaultEnderecoShouldBeFound("cep.doesNotContain=" + UPDATED_CEP);
    }


    @Test
    @Transactional
    public void getAllEnderecosByLogradouroIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where logradouro equals to DEFAULT_LOGRADOURO
        defaultEnderecoShouldBeFound("logradouro.equals=" + DEFAULT_LOGRADOURO);

        // Get all the enderecoList where logradouro equals to UPDATED_LOGRADOURO
        defaultEnderecoShouldNotBeFound("logradouro.equals=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLogradouroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where logradouro not equals to DEFAULT_LOGRADOURO
        defaultEnderecoShouldNotBeFound("logradouro.notEquals=" + DEFAULT_LOGRADOURO);

        // Get all the enderecoList where logradouro not equals to UPDATED_LOGRADOURO
        defaultEnderecoShouldBeFound("logradouro.notEquals=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLogradouroIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where logradouro in DEFAULT_LOGRADOURO or UPDATED_LOGRADOURO
        defaultEnderecoShouldBeFound("logradouro.in=" + DEFAULT_LOGRADOURO + "," + UPDATED_LOGRADOURO);

        // Get all the enderecoList where logradouro equals to UPDATED_LOGRADOURO
        defaultEnderecoShouldNotBeFound("logradouro.in=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLogradouroIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where logradouro is not null
        defaultEnderecoShouldBeFound("logradouro.specified=true");

        // Get all the enderecoList where logradouro is null
        defaultEnderecoShouldNotBeFound("logradouro.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnderecosByLogradouroContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where logradouro contains DEFAULT_LOGRADOURO
        defaultEnderecoShouldBeFound("logradouro.contains=" + DEFAULT_LOGRADOURO);

        // Get all the enderecoList where logradouro contains UPDATED_LOGRADOURO
        defaultEnderecoShouldNotBeFound("logradouro.contains=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLogradouroNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where logradouro does not contain DEFAULT_LOGRADOURO
        defaultEnderecoShouldNotBeFound("logradouro.doesNotContain=" + DEFAULT_LOGRADOURO);

        // Get all the enderecoList where logradouro does not contain UPDATED_LOGRADOURO
        defaultEnderecoShouldBeFound("logradouro.doesNotContain=" + UPDATED_LOGRADOURO);
    }


    @Test
    @Transactional
    public void getAllEnderecosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where numero equals to DEFAULT_NUMERO
        defaultEnderecoShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the enderecoList where numero equals to UPDATED_NUMERO
        defaultEnderecoShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where numero not equals to DEFAULT_NUMERO
        defaultEnderecoShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the enderecoList where numero not equals to UPDATED_NUMERO
        defaultEnderecoShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultEnderecoShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the enderecoList where numero equals to UPDATED_NUMERO
        defaultEnderecoShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where numero is not null
        defaultEnderecoShouldBeFound("numero.specified=true");

        // Get all the enderecoList where numero is null
        defaultEnderecoShouldNotBeFound("numero.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnderecosByNumeroContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where numero contains DEFAULT_NUMERO
        defaultEnderecoShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the enderecoList where numero contains UPDATED_NUMERO
        defaultEnderecoShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where numero does not contain DEFAULT_NUMERO
        defaultEnderecoShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the enderecoList where numero does not contain UPDATED_NUMERO
        defaultEnderecoShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllEnderecosByComplementoIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where complemento equals to DEFAULT_COMPLEMENTO
        defaultEnderecoShouldBeFound("complemento.equals=" + DEFAULT_COMPLEMENTO);

        // Get all the enderecoList where complemento equals to UPDATED_COMPLEMENTO
        defaultEnderecoShouldNotBeFound("complemento.equals=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByComplementoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where complemento not equals to DEFAULT_COMPLEMENTO
        defaultEnderecoShouldNotBeFound("complemento.notEquals=" + DEFAULT_COMPLEMENTO);

        // Get all the enderecoList where complemento not equals to UPDATED_COMPLEMENTO
        defaultEnderecoShouldBeFound("complemento.notEquals=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByComplementoIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where complemento in DEFAULT_COMPLEMENTO or UPDATED_COMPLEMENTO
        defaultEnderecoShouldBeFound("complemento.in=" + DEFAULT_COMPLEMENTO + "," + UPDATED_COMPLEMENTO);

        // Get all the enderecoList where complemento equals to UPDATED_COMPLEMENTO
        defaultEnderecoShouldNotBeFound("complemento.in=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByComplementoIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where complemento is not null
        defaultEnderecoShouldBeFound("complemento.specified=true");

        // Get all the enderecoList where complemento is null
        defaultEnderecoShouldNotBeFound("complemento.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnderecosByComplementoContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where complemento contains DEFAULT_COMPLEMENTO
        defaultEnderecoShouldBeFound("complemento.contains=" + DEFAULT_COMPLEMENTO);

        // Get all the enderecoList where complemento contains UPDATED_COMPLEMENTO
        defaultEnderecoShouldNotBeFound("complemento.contains=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByComplementoNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where complemento does not contain DEFAULT_COMPLEMENTO
        defaultEnderecoShouldNotBeFound("complemento.doesNotContain=" + DEFAULT_COMPLEMENTO);

        // Get all the enderecoList where complemento does not contain UPDATED_COMPLEMENTO
        defaultEnderecoShouldBeFound("complemento.doesNotContain=" + UPDATED_COMPLEMENTO);
    }


    @Test
    @Transactional
    public void getAllEnderecosByBairroIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where bairro equals to DEFAULT_BAIRRO
        defaultEnderecoShouldBeFound("bairro.equals=" + DEFAULT_BAIRRO);

        // Get all the enderecoList where bairro equals to UPDATED_BAIRRO
        defaultEnderecoShouldNotBeFound("bairro.equals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByBairroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where bairro not equals to DEFAULT_BAIRRO
        defaultEnderecoShouldNotBeFound("bairro.notEquals=" + DEFAULT_BAIRRO);

        // Get all the enderecoList where bairro not equals to UPDATED_BAIRRO
        defaultEnderecoShouldBeFound("bairro.notEquals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByBairroIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where bairro in DEFAULT_BAIRRO or UPDATED_BAIRRO
        defaultEnderecoShouldBeFound("bairro.in=" + DEFAULT_BAIRRO + "," + UPDATED_BAIRRO);

        // Get all the enderecoList where bairro equals to UPDATED_BAIRRO
        defaultEnderecoShouldNotBeFound("bairro.in=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByBairroIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where bairro is not null
        defaultEnderecoShouldBeFound("bairro.specified=true");

        // Get all the enderecoList where bairro is null
        defaultEnderecoShouldNotBeFound("bairro.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnderecosByBairroContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where bairro contains DEFAULT_BAIRRO
        defaultEnderecoShouldBeFound("bairro.contains=" + DEFAULT_BAIRRO);

        // Get all the enderecoList where bairro contains UPDATED_BAIRRO
        defaultEnderecoShouldNotBeFound("bairro.contains=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByBairroNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where bairro does not contain DEFAULT_BAIRRO
        defaultEnderecoShouldNotBeFound("bairro.doesNotContain=" + DEFAULT_BAIRRO);

        // Get all the enderecoList where bairro does not contain UPDATED_BAIRRO
        defaultEnderecoShouldBeFound("bairro.doesNotContain=" + UPDATED_BAIRRO);
    }


    @Test
    @Transactional
    public void getAllEnderecosByLocalidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where localidade equals to DEFAULT_LOCALIDADE
        defaultEnderecoShouldBeFound("localidade.equals=" + DEFAULT_LOCALIDADE);

        // Get all the enderecoList where localidade equals to UPDATED_LOCALIDADE
        defaultEnderecoShouldNotBeFound("localidade.equals=" + UPDATED_LOCALIDADE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLocalidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where localidade not equals to DEFAULT_LOCALIDADE
        defaultEnderecoShouldNotBeFound("localidade.notEquals=" + DEFAULT_LOCALIDADE);

        // Get all the enderecoList where localidade not equals to UPDATED_LOCALIDADE
        defaultEnderecoShouldBeFound("localidade.notEquals=" + UPDATED_LOCALIDADE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLocalidadeIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where localidade in DEFAULT_LOCALIDADE or UPDATED_LOCALIDADE
        defaultEnderecoShouldBeFound("localidade.in=" + DEFAULT_LOCALIDADE + "," + UPDATED_LOCALIDADE);

        // Get all the enderecoList where localidade equals to UPDATED_LOCALIDADE
        defaultEnderecoShouldNotBeFound("localidade.in=" + UPDATED_LOCALIDADE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLocalidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where localidade is not null
        defaultEnderecoShouldBeFound("localidade.specified=true");

        // Get all the enderecoList where localidade is null
        defaultEnderecoShouldNotBeFound("localidade.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnderecosByLocalidadeContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where localidade contains DEFAULT_LOCALIDADE
        defaultEnderecoShouldBeFound("localidade.contains=" + DEFAULT_LOCALIDADE);

        // Get all the enderecoList where localidade contains UPDATED_LOCALIDADE
        defaultEnderecoShouldNotBeFound("localidade.contains=" + UPDATED_LOCALIDADE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLocalidadeNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where localidade does not contain DEFAULT_LOCALIDADE
        defaultEnderecoShouldNotBeFound("localidade.doesNotContain=" + DEFAULT_LOCALIDADE);

        // Get all the enderecoList where localidade does not contain UPDATED_LOCALIDADE
        defaultEnderecoShouldBeFound("localidade.doesNotContain=" + UPDATED_LOCALIDADE);
    }


    @Test
    @Transactional
    public void getAllEnderecosByUfIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where uf equals to DEFAULT_UF
        defaultEnderecoShouldBeFound("uf.equals=" + DEFAULT_UF);

        // Get all the enderecoList where uf equals to UPDATED_UF
        defaultEnderecoShouldNotBeFound("uf.equals=" + UPDATED_UF);
    }

    @Test
    @Transactional
    public void getAllEnderecosByUfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where uf not equals to DEFAULT_UF
        defaultEnderecoShouldNotBeFound("uf.notEquals=" + DEFAULT_UF);

        // Get all the enderecoList where uf not equals to UPDATED_UF
        defaultEnderecoShouldBeFound("uf.notEquals=" + UPDATED_UF);
    }

    @Test
    @Transactional
    public void getAllEnderecosByUfIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where uf in DEFAULT_UF or UPDATED_UF
        defaultEnderecoShouldBeFound("uf.in=" + DEFAULT_UF + "," + UPDATED_UF);

        // Get all the enderecoList where uf equals to UPDATED_UF
        defaultEnderecoShouldNotBeFound("uf.in=" + UPDATED_UF);
    }

    @Test
    @Transactional
    public void getAllEnderecosByUfIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where uf is not null
        defaultEnderecoShouldBeFound("uf.specified=true");

        // Get all the enderecoList where uf is null
        defaultEnderecoShouldNotBeFound("uf.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnderecosByUfContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where uf contains DEFAULT_UF
        defaultEnderecoShouldBeFound("uf.contains=" + DEFAULT_UF);

        // Get all the enderecoList where uf contains UPDATED_UF
        defaultEnderecoShouldNotBeFound("uf.contains=" + UPDATED_UF);
    }

    @Test
    @Transactional
    public void getAllEnderecosByUfNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where uf does not contain DEFAULT_UF
        defaultEnderecoShouldNotBeFound("uf.doesNotContain=" + DEFAULT_UF);

        // Get all the enderecoList where uf does not contain UPDATED_UF
        defaultEnderecoShouldBeFound("uf.doesNotContain=" + UPDATED_UF);
    }


    @Test
    @Transactional
    public void getAllEnderecosByIbgeIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where ibge equals to DEFAULT_IBGE
        defaultEnderecoShouldBeFound("ibge.equals=" + DEFAULT_IBGE);

        // Get all the enderecoList where ibge equals to UPDATED_IBGE
        defaultEnderecoShouldNotBeFound("ibge.equals=" + UPDATED_IBGE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByIbgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where ibge not equals to DEFAULT_IBGE
        defaultEnderecoShouldNotBeFound("ibge.notEquals=" + DEFAULT_IBGE);

        // Get all the enderecoList where ibge not equals to UPDATED_IBGE
        defaultEnderecoShouldBeFound("ibge.notEquals=" + UPDATED_IBGE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByIbgeIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where ibge in DEFAULT_IBGE or UPDATED_IBGE
        defaultEnderecoShouldBeFound("ibge.in=" + DEFAULT_IBGE + "," + UPDATED_IBGE);

        // Get all the enderecoList where ibge equals to UPDATED_IBGE
        defaultEnderecoShouldNotBeFound("ibge.in=" + UPDATED_IBGE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByIbgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where ibge is not null
        defaultEnderecoShouldBeFound("ibge.specified=true");

        // Get all the enderecoList where ibge is null
        defaultEnderecoShouldNotBeFound("ibge.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnderecosByIbgeContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where ibge contains DEFAULT_IBGE
        defaultEnderecoShouldBeFound("ibge.contains=" + DEFAULT_IBGE);

        // Get all the enderecoList where ibge contains UPDATED_IBGE
        defaultEnderecoShouldNotBeFound("ibge.contains=" + UPDATED_IBGE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByIbgeNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where ibge does not contain DEFAULT_IBGE
        defaultEnderecoShouldNotBeFound("ibge.doesNotContain=" + DEFAULT_IBGE);

        // Get all the enderecoList where ibge does not contain UPDATED_IBGE
        defaultEnderecoShouldBeFound("ibge.doesNotContain=" + UPDATED_IBGE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnderecoShouldBeFound(String filter) throws Exception {
        restEnderecoMockMvc.perform(get("/api/enderecos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endereco.getId().intValue())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].localidade").value(hasItem(DEFAULT_LOCALIDADE)))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF)))
            .andExpect(jsonPath("$.[*].ibge").value(hasItem(DEFAULT_IBGE)));

        // Check, that the count call also returns 1
        restEnderecoMockMvc.perform(get("/api/enderecos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnderecoShouldNotBeFound(String filter) throws Exception {
        restEnderecoMockMvc.perform(get("/api/enderecos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnderecoMockMvc.perform(get("/api/enderecos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEndereco() throws Exception {
        // Get the endereco
        restEnderecoMockMvc.perform(get("/api/enderecos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();

        // Update the endereco
        Endereco updatedEndereco = enderecoRepository.findById(endereco.getId()).get();
        // Disconnect from session so that the updates on updatedEndereco are not directly saved in db
        em.detach(updatedEndereco);
        updatedEndereco
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .localidade(UPDATED_LOCALIDADE)
            .uf(UPDATED_UF)
            .ibge(UPDATED_IBGE);
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(updatedEndereco);

        restEnderecoMockMvc.perform(put("/api/enderecos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isOk());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testEndereco.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEndereco.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEndereco.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEndereco.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEndereco.getLocalidade()).isEqualTo(UPDATED_LOCALIDADE);
        assertThat(testEndereco.getUf()).isEqualTo(UPDATED_UF);
        assertThat(testEndereco.getIbge()).isEqualTo(UPDATED_IBGE);
    }

    @Test
    @Transactional
    public void updateNonExistingEndereco() throws Exception {
        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoMockMvc.perform(put("/api/enderecos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        int databaseSizeBeforeDelete = enderecoRepository.findAll().size();

        // Delete the endereco
        restEnderecoMockMvc.perform(delete("/api/enderecos/{id}", endereco.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
