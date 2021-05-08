package br.com.ngzorro.web.rest;

import br.com.ngzorro.NgzorroApp;
import br.com.ngzorro.domain.Categoria;
import br.com.ngzorro.domain.Produto;
import br.com.ngzorro.repository.CategoriaRepository;
import br.com.ngzorro.service.CategoriaService;
import br.com.ngzorro.service.dto.CategoriaDTO;
import br.com.ngzorro.service.mapper.CategoriaMapper;
import br.com.ngzorro.web.rest.errors.ExceptionTranslator;
import br.com.ngzorro.service.dto.CategoriaCriteria;
import br.com.ngzorro.service.CategoriaQueryService;

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
 * Integration tests for the {@link CategoriaResource} REST controller.
 */
@SpringBootTest(classes = NgzorroApp.class)
public class CategoriaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaQueryService categoriaQueryService;

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

    private MockMvc restCategoriaMockMvc;

    private Categoria categoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaResource categoriaResource = new CategoriaResource(categoriaService, categoriaQueryService);
        this.restCategoriaMockMvc = MockMvcBuilders.standaloneSetup(categoriaResource)
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
    public static Categoria createEntity(EntityManager em) {
        Categoria categoria = new Categoria()
            .nome(DEFAULT_NOME);
        return categoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categoria createUpdatedEntity(EntityManager em) {
        Categoria categoria = new Categoria()
            .nome(UPDATED_NOME);
        return categoria;
    }

    @BeforeEach
    public void initTest() {
        categoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoria() throws Exception {
        int databaseSizeBeforeCreate = categoriaRepository.findAll().size();

        // Create the Categoria
        CategoriaDTO categoriaDTO = categoriaMapper.toDto(categoria);
        restCategoriaMockMvc.perform(post("/api/categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDTO)))
            .andExpect(status().isCreated());

        // Validate the Categoria in the database
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Categoria testCategoria = categoriaList.get(categoriaList.size() - 1);
        assertThat(testCategoria.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createCategoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaRepository.findAll().size();

        // Create the Categoria with an existing ID
        categoria.setId(1L);
        CategoriaDTO categoriaDTO = categoriaMapper.toDto(categoria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaMockMvc.perform(post("/api/categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categoria in the database
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCategorias() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categoriaList
        restCategoriaMockMvc.perform(get("/api/categorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getCategoria() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get the categoria
        restCategoriaMockMvc.perform(get("/api/categorias/{id}", categoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoria.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }


    @Test
    @Transactional
    public void getCategoriasByIdFiltering() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        Long id = categoria.getId();

        defaultCategoriaShouldBeFound("id.equals=" + id);
        defaultCategoriaShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoriasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categoriaList where nome equals to DEFAULT_NOME
        defaultCategoriaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the categoriaList where nome equals to UPDATED_NOME
        defaultCategoriaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllCategoriasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categoriaList where nome not equals to DEFAULT_NOME
        defaultCategoriaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the categoriaList where nome not equals to UPDATED_NOME
        defaultCategoriaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllCategoriasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categoriaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultCategoriaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the categoriaList where nome equals to UPDATED_NOME
        defaultCategoriaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllCategoriasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categoriaList where nome is not null
        defaultCategoriaShouldBeFound("nome.specified=true");

        // Get all the categoriaList where nome is null
        defaultCategoriaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriasByNomeContainsSomething() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categoriaList where nome contains DEFAULT_NOME
        defaultCategoriaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the categoriaList where nome contains UPDATED_NOME
        defaultCategoriaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllCategoriasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categoriaList where nome does not contain DEFAULT_NOME
        defaultCategoriaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the categoriaList where nome does not contain UPDATED_NOME
        defaultCategoriaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllCategoriasByProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);
        Produto produto = ProdutoResourceIT.createEntity(em);
        em.persist(produto);
        em.flush();
        categoria.addProduto(produto);
        categoriaRepository.saveAndFlush(categoria);
        Long produtoId = produto.getId();

        // Get all the categoriaList where produto equals to produtoId
        defaultCategoriaShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the categoriaList where produto equals to produtoId + 1
        defaultCategoriaShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaShouldBeFound(String filter) throws Exception {
        restCategoriaMockMvc.perform(get("/api/categorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restCategoriaMockMvc.perform(get("/api/categorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaShouldNotBeFound(String filter) throws Exception {
        restCategoriaMockMvc.perform(get("/api/categorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaMockMvc.perform(get("/api/categorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategoria() throws Exception {
        // Get the categoria
        restCategoriaMockMvc.perform(get("/api/categorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoria() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        int databaseSizeBeforeUpdate = categoriaRepository.findAll().size();

        // Update the categoria
        Categoria updatedCategoria = categoriaRepository.findById(categoria.getId()).get();
        // Disconnect from session so that the updates on updatedCategoria are not directly saved in db
        em.detach(updatedCategoria);
        updatedCategoria
            .nome(UPDATED_NOME);
        CategoriaDTO categoriaDTO = categoriaMapper.toDto(updatedCategoria);

        restCategoriaMockMvc.perform(put("/api/categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDTO)))
            .andExpect(status().isOk());

        // Validate the Categoria in the database
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeUpdate);
        Categoria testCategoria = categoriaList.get(categoriaList.size() - 1);
        assertThat(testCategoria.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoria() throws Exception {
        int databaseSizeBeforeUpdate = categoriaRepository.findAll().size();

        // Create the Categoria
        CategoriaDTO categoriaDTO = categoriaMapper.toDto(categoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaMockMvc.perform(put("/api/categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categoria in the database
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoria() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        int databaseSizeBeforeDelete = categoriaRepository.findAll().size();

        // Delete the categoria
        restCategoriaMockMvc.perform(delete("/api/categorias/{id}", categoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
