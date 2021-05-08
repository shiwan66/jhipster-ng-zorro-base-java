package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.AnimalVeterinario;
import br.com.ngzorro.repository.AnimalVeterinarioRepository;
import br.com.ngzorro.service.AnimalVeterinarioService;
import br.com.ngzorro.service.dto.AnimalVeterinarioDTO;
import br.com.ngzorro.service.mapper.AnimalVeterinarioMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.AnimalVeterinarioCriteria;
import br.com.ngzorro.service.AnimalVeterinarioQueryService;

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
 * Integration tests for the {@link AnimalVeterinarioResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class AnimalVeterinarioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_CLINICA = "AAAAAAAAAA";
    private static final String UPDATED_CLINICA = "BBBBBBBBBB";

    @Autowired
    private AnimalVeterinarioRepository animalVeterinarioRepository;

    @Autowired
    private AnimalVeterinarioMapper animalVeterinarioMapper;

    @Autowired
    private AnimalVeterinarioService animalVeterinarioService;

    @Autowired
    private AnimalVeterinarioQueryService animalVeterinarioQueryService;

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

    private MockMvc restAnimalVeterinarioMockMvc;

    private AnimalVeterinario animalVeterinario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalVeterinarioResource animalVeterinarioResource = new AnimalVeterinarioResource(animalVeterinarioService, animalVeterinarioQueryService);
        this.restAnimalVeterinarioMockMvc = MockMvcBuilders.standaloneSetup(animalVeterinarioResource)
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
    public static AnimalVeterinario createEntity(EntityManager em) {
        AnimalVeterinario animalVeterinario = new AnimalVeterinario()
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE)
            .clinica(DEFAULT_CLINICA);
        return animalVeterinario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalVeterinario createUpdatedEntity(EntityManager em) {
        AnimalVeterinario animalVeterinario = new AnimalVeterinario()
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .clinica(UPDATED_CLINICA);
        return animalVeterinario;
    }

    @BeforeEach
    public void initTest() {
        animalVeterinario = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalVeterinario() throws Exception {
        int databaseSizeBeforeCreate = animalVeterinarioRepository.findAll().size();

        // Create the AnimalVeterinario
        AnimalVeterinarioDTO animalVeterinarioDTO = animalVeterinarioMapper.toDto(animalVeterinario);
        restAnimalVeterinarioMockMvc.perform(post("/api/animal-veterinarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVeterinarioDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalVeterinario in the database
        List<AnimalVeterinario> animalVeterinarioList = animalVeterinarioRepository.findAll();
        assertThat(animalVeterinarioList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalVeterinario testAnimalVeterinario = animalVeterinarioList.get(animalVeterinarioList.size() - 1);
        assertThat(testAnimalVeterinario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAnimalVeterinario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testAnimalVeterinario.getClinica()).isEqualTo(DEFAULT_CLINICA);
    }

    @Test
    @Transactional
    public void createAnimalVeterinarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalVeterinarioRepository.findAll().size();

        // Create the AnimalVeterinario with an existing ID
        animalVeterinario.setId(1L);
        AnimalVeterinarioDTO animalVeterinarioDTO = animalVeterinarioMapper.toDto(animalVeterinario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalVeterinarioMockMvc.perform(post("/api/animal-veterinarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVeterinarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalVeterinario in the database
        List<AnimalVeterinario> animalVeterinarioList = animalVeterinarioRepository.findAll();
        assertThat(animalVeterinarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalVeterinarios() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList
        restAnimalVeterinarioMockMvc.perform(get("/api/animal-veterinarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalVeterinario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].clinica").value(hasItem(DEFAULT_CLINICA)));
    }
    
    @Test
    @Transactional
    public void getAnimalVeterinario() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get the animalVeterinario
        restAnimalVeterinarioMockMvc.perform(get("/api/animal-veterinarios/{id}", animalVeterinario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalVeterinario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.clinica").value(DEFAULT_CLINICA));
    }


    @Test
    @Transactional
    public void getAnimalVeterinariosByIdFiltering() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        Long id = animalVeterinario.getId();

        defaultAnimalVeterinarioShouldBeFound("id.equals=" + id);
        defaultAnimalVeterinarioShouldNotBeFound("id.notEquals=" + id);

        defaultAnimalVeterinarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnimalVeterinarioShouldNotBeFound("id.greaterThan=" + id);

        defaultAnimalVeterinarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnimalVeterinarioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnimalVeterinariosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where nome equals to DEFAULT_NOME
        defaultAnimalVeterinarioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the animalVeterinarioList where nome equals to UPDATED_NOME
        defaultAnimalVeterinarioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where nome not equals to DEFAULT_NOME
        defaultAnimalVeterinarioShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the animalVeterinarioList where nome not equals to UPDATED_NOME
        defaultAnimalVeterinarioShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAnimalVeterinarioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the animalVeterinarioList where nome equals to UPDATED_NOME
        defaultAnimalVeterinarioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where nome is not null
        defaultAnimalVeterinarioShouldBeFound("nome.specified=true");

        // Get all the animalVeterinarioList where nome is null
        defaultAnimalVeterinarioShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalVeterinariosByNomeContainsSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where nome contains DEFAULT_NOME
        defaultAnimalVeterinarioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the animalVeterinarioList where nome contains UPDATED_NOME
        defaultAnimalVeterinarioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where nome does not contain DEFAULT_NOME
        defaultAnimalVeterinarioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the animalVeterinarioList where nome does not contain UPDATED_NOME
        defaultAnimalVeterinarioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAnimalVeterinariosByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where telefone equals to DEFAULT_TELEFONE
        defaultAnimalVeterinarioShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the animalVeterinarioList where telefone equals to UPDATED_TELEFONE
        defaultAnimalVeterinarioShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where telefone not equals to DEFAULT_TELEFONE
        defaultAnimalVeterinarioShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the animalVeterinarioList where telefone not equals to UPDATED_TELEFONE
        defaultAnimalVeterinarioShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultAnimalVeterinarioShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the animalVeterinarioList where telefone equals to UPDATED_TELEFONE
        defaultAnimalVeterinarioShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where telefone is not null
        defaultAnimalVeterinarioShouldBeFound("telefone.specified=true");

        // Get all the animalVeterinarioList where telefone is null
        defaultAnimalVeterinarioShouldNotBeFound("telefone.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalVeterinariosByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where telefone contains DEFAULT_TELEFONE
        defaultAnimalVeterinarioShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the animalVeterinarioList where telefone contains UPDATED_TELEFONE
        defaultAnimalVeterinarioShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where telefone does not contain DEFAULT_TELEFONE
        defaultAnimalVeterinarioShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the animalVeterinarioList where telefone does not contain UPDATED_TELEFONE
        defaultAnimalVeterinarioShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }


    @Test
    @Transactional
    public void getAllAnimalVeterinariosByClinicaIsEqualToSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where clinica equals to DEFAULT_CLINICA
        defaultAnimalVeterinarioShouldBeFound("clinica.equals=" + DEFAULT_CLINICA);

        // Get all the animalVeterinarioList where clinica equals to UPDATED_CLINICA
        defaultAnimalVeterinarioShouldNotBeFound("clinica.equals=" + UPDATED_CLINICA);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByClinicaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where clinica not equals to DEFAULT_CLINICA
        defaultAnimalVeterinarioShouldNotBeFound("clinica.notEquals=" + DEFAULT_CLINICA);

        // Get all the animalVeterinarioList where clinica not equals to UPDATED_CLINICA
        defaultAnimalVeterinarioShouldBeFound("clinica.notEquals=" + UPDATED_CLINICA);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByClinicaIsInShouldWork() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where clinica in DEFAULT_CLINICA or UPDATED_CLINICA
        defaultAnimalVeterinarioShouldBeFound("clinica.in=" + DEFAULT_CLINICA + "," + UPDATED_CLINICA);

        // Get all the animalVeterinarioList where clinica equals to UPDATED_CLINICA
        defaultAnimalVeterinarioShouldNotBeFound("clinica.in=" + UPDATED_CLINICA);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByClinicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where clinica is not null
        defaultAnimalVeterinarioShouldBeFound("clinica.specified=true");

        // Get all the animalVeterinarioList where clinica is null
        defaultAnimalVeterinarioShouldNotBeFound("clinica.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnimalVeterinariosByClinicaContainsSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where clinica contains DEFAULT_CLINICA
        defaultAnimalVeterinarioShouldBeFound("clinica.contains=" + DEFAULT_CLINICA);

        // Get all the animalVeterinarioList where clinica contains UPDATED_CLINICA
        defaultAnimalVeterinarioShouldNotBeFound("clinica.contains=" + UPDATED_CLINICA);
    }

    @Test
    @Transactional
    public void getAllAnimalVeterinariosByClinicaNotContainsSomething() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        // Get all the animalVeterinarioList where clinica does not contain DEFAULT_CLINICA
        defaultAnimalVeterinarioShouldNotBeFound("clinica.doesNotContain=" + DEFAULT_CLINICA);

        // Get all the animalVeterinarioList where clinica does not contain UPDATED_CLINICA
        defaultAnimalVeterinarioShouldBeFound("clinica.doesNotContain=" + UPDATED_CLINICA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnimalVeterinarioShouldBeFound(String filter) throws Exception {
        restAnimalVeterinarioMockMvc.perform(get("/api/animal-veterinarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalVeterinario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].clinica").value(hasItem(DEFAULT_CLINICA)));

        // Check, that the count call also returns 1
        restAnimalVeterinarioMockMvc.perform(get("/api/animal-veterinarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnimalVeterinarioShouldNotBeFound(String filter) throws Exception {
        restAnimalVeterinarioMockMvc.perform(get("/api/animal-veterinarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnimalVeterinarioMockMvc.perform(get("/api/animal-veterinarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnimalVeterinario() throws Exception {
        // Get the animalVeterinario
        restAnimalVeterinarioMockMvc.perform(get("/api/animal-veterinarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalVeterinario() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        int databaseSizeBeforeUpdate = animalVeterinarioRepository.findAll().size();

        // Update the animalVeterinario
        AnimalVeterinario updatedAnimalVeterinario = animalVeterinarioRepository.findById(animalVeterinario.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalVeterinario are not directly saved in db
        em.detach(updatedAnimalVeterinario);
        updatedAnimalVeterinario
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .clinica(UPDATED_CLINICA);
        AnimalVeterinarioDTO animalVeterinarioDTO = animalVeterinarioMapper.toDto(updatedAnimalVeterinario);

        restAnimalVeterinarioMockMvc.perform(put("/api/animal-veterinarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVeterinarioDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalVeterinario in the database
        List<AnimalVeterinario> animalVeterinarioList = animalVeterinarioRepository.findAll();
        assertThat(animalVeterinarioList).hasSize(databaseSizeBeforeUpdate);
        AnimalVeterinario testAnimalVeterinario = animalVeterinarioList.get(animalVeterinarioList.size() - 1);
        assertThat(testAnimalVeterinario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAnimalVeterinario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testAnimalVeterinario.getClinica()).isEqualTo(UPDATED_CLINICA);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalVeterinario() throws Exception {
        int databaseSizeBeforeUpdate = animalVeterinarioRepository.findAll().size();

        // Create the AnimalVeterinario
        AnimalVeterinarioDTO animalVeterinarioDTO = animalVeterinarioMapper.toDto(animalVeterinario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalVeterinarioMockMvc.perform(put("/api/animal-veterinarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalVeterinarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalVeterinario in the database
        List<AnimalVeterinario> animalVeterinarioList = animalVeterinarioRepository.findAll();
        assertThat(animalVeterinarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalVeterinario() throws Exception {
        // Initialize the database
        animalVeterinarioRepository.saveAndFlush(animalVeterinario);

        int databaseSizeBeforeDelete = animalVeterinarioRepository.findAll().size();

        // Delete the animalVeterinario
        restAnimalVeterinarioMockMvc.perform(delete("/api/animal-veterinarios/{id}", animalVeterinario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalVeterinario> animalVeterinarioList = animalVeterinarioRepository.findAll();
        assertThat(animalVeterinarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
