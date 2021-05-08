package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AtividadeService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AtividadeDTO;
import br.com.ngzorro.service.dto.AtividadeCriteria;
import br.com.ngzorro.service.AtividadeQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.Atividade}.
 */
@RestController
@RequestMapping("/api")
public class AtividadeResource {

    private final Logger log = LoggerFactory.getLogger(AtividadeResource.class);

    private static final String ENTITY_NAME = "atividade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtividadeService atividadeService;

    private final AtividadeQueryService atividadeQueryService;

    public AtividadeResource(AtividadeService atividadeService, AtividadeQueryService atividadeQueryService) {
        this.atividadeService = atividadeService;
        this.atividadeQueryService = atividadeQueryService;
    }

    /**
     * {@code POST  /atividades} : Create a new atividade.
     *
     * @param atividadeDTO the atividadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atividadeDTO, or with status {@code 400 (Bad Request)} if the atividade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/atividades")
    public ResponseEntity<AtividadeDTO> createAtividade(@RequestBody AtividadeDTO atividadeDTO) throws URISyntaxException {
        log.debug("REST request to save Atividade : {}", atividadeDTO);
        if (atividadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new atividade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AtividadeDTO result = atividadeService.save(atividadeDTO);
        return ResponseEntity.created(new URI("/api/atividades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /atividades} : Updates an existing atividade.
     *
     * @param atividadeDTO the atividadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atividadeDTO,
     * or with status {@code 400 (Bad Request)} if the atividadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atividadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/atividades")
    public ResponseEntity<AtividadeDTO> updateAtividade(@RequestBody AtividadeDTO atividadeDTO) throws URISyntaxException {
        log.debug("REST request to update Atividade : {}", atividadeDTO);
        if (atividadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AtividadeDTO result = atividadeService.save(atividadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atividadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /atividades} : get all the atividades.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atividades in body.
     */
    @GetMapping("/atividades")
    public ResponseEntity<List<AtividadeDTO>> getAllAtividades(AtividadeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Atividades by criteria: {}", criteria);
        Page<AtividadeDTO> page = atividadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /atividades/count} : count all the atividades.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/atividades/count")
    public ResponseEntity<Long> countAtividades(AtividadeCriteria criteria) {
        log.debug("REST request to count Atividades by criteria: {}", criteria);
        return ResponseEntity.ok().body(atividadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /atividades/:id} : get the "id" atividade.
     *
     * @param id the id of the atividadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atividadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/atividades/{id}")
    public ResponseEntity<AtividadeDTO> getAtividade(@PathVariable Long id) {
        log.debug("REST request to get Atividade : {}", id);
        Optional<AtividadeDTO> atividadeDTO = atividadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atividadeDTO);
    }

    /**
     * {@code DELETE  /atividades/:id} : delete the "id" atividade.
     *
     * @param id the id of the atividadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/atividades/{id}")
    public ResponseEntity<Void> deleteAtividade(@PathVariable Long id) {
        log.debug("REST request to delete Atividade : {}", id);
        atividadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
