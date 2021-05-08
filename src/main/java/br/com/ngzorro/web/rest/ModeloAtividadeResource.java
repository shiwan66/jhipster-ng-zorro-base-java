package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.ModeloAtividadeService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.ModeloAtividadeDTO;
import br.com.ngzorro.service.dto.ModeloAtividadeCriteria;
import br.com.ngzorro.service.ModeloAtividadeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.ngzorro.domain.ModeloAtividade}.
 */
@RestController
@RequestMapping("/api")
public class ModeloAtividadeResource {

    private final Logger log = LoggerFactory.getLogger(ModeloAtividadeResource.class);

    private static final String ENTITY_NAME = "modeloAtividade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModeloAtividadeService modeloAtividadeService;

    private final ModeloAtividadeQueryService modeloAtividadeQueryService;

    public ModeloAtividadeResource(ModeloAtividadeService modeloAtividadeService, ModeloAtividadeQueryService modeloAtividadeQueryService) {
        this.modeloAtividadeService = modeloAtividadeService;
        this.modeloAtividadeQueryService = modeloAtividadeQueryService;
    }

    /**
     * {@code POST  /modelo-atividades} : Create a new modeloAtividade.
     *
     * @param modeloAtividadeDTO the modeloAtividadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modeloAtividadeDTO, or with status {@code 400 (Bad Request)} if the modeloAtividade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modelo-atividades")
    public ResponseEntity<ModeloAtividadeDTO> createModeloAtividade(@RequestBody ModeloAtividadeDTO modeloAtividadeDTO) throws URISyntaxException {
        log.debug("REST request to save ModeloAtividade : {}", modeloAtividadeDTO);
        if (modeloAtividadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new modeloAtividade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModeloAtividadeDTO result = modeloAtividadeService.save(modeloAtividadeDTO);
        return ResponseEntity.created(new URI("/api/modelo-atividades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modelo-atividades} : Updates an existing modeloAtividade.
     *
     * @param modeloAtividadeDTO the modeloAtividadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modeloAtividadeDTO,
     * or with status {@code 400 (Bad Request)} if the modeloAtividadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modeloAtividadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modelo-atividades")
    public ResponseEntity<ModeloAtividadeDTO> updateModeloAtividade(@RequestBody ModeloAtividadeDTO modeloAtividadeDTO) throws URISyntaxException {
        log.debug("REST request to update ModeloAtividade : {}", modeloAtividadeDTO);
        if (modeloAtividadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModeloAtividadeDTO result = modeloAtividadeService.save(modeloAtividadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modeloAtividadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /modelo-atividades} : get all the modeloAtividades.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modeloAtividades in body.
     */
    @GetMapping("/modelo-atividades")
    public ResponseEntity<List<ModeloAtividadeDTO>> getAllModeloAtividades(ModeloAtividadeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ModeloAtividades by criteria: {}", criteria);
        Page<ModeloAtividadeDTO> page = modeloAtividadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /modelo-atividades/count} : count all the modeloAtividades.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/modelo-atividades/count")
    public ResponseEntity<Long> countModeloAtividades(ModeloAtividadeCriteria criteria) {
        log.debug("REST request to count ModeloAtividades by criteria: {}", criteria);
        return ResponseEntity.ok().body(modeloAtividadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /modelo-atividades/:id} : get the "id" modeloAtividade.
     *
     * @param id the id of the modeloAtividadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modeloAtividadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modelo-atividades/{id}")
    public ResponseEntity<ModeloAtividadeDTO> getModeloAtividade(@PathVariable Long id) {
        log.debug("REST request to get ModeloAtividade : {}", id);
        Optional<ModeloAtividadeDTO> modeloAtividadeDTO = modeloAtividadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modeloAtividadeDTO);
    }

    /**
     * {@code DELETE  /modelo-atividades/:id} : delete the "id" modeloAtividade.
     *
     * @param id the id of the modeloAtividadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modelo-atividades/{id}")
    public ResponseEntity<Void> deleteModeloAtividade(@PathVariable Long id) {
        log.debug("REST request to delete ModeloAtividade : {}", id);
        modeloAtividadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
