package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AtendimentoService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AtendimentoDTO;
import br.com.ngzorro.service.dto.AtendimentoCriteria;
import br.com.ngzorro.service.AtendimentoQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.Atendimento}.
 */
@RestController
@RequestMapping("/api")
public class AtendimentoResource {

    private final Logger log = LoggerFactory.getLogger(AtendimentoResource.class);

    private static final String ENTITY_NAME = "atendimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtendimentoService atendimentoService;

    private final AtendimentoQueryService atendimentoQueryService;

    public AtendimentoResource(AtendimentoService atendimentoService, AtendimentoQueryService atendimentoQueryService) {
        this.atendimentoService = atendimentoService;
        this.atendimentoQueryService = atendimentoQueryService;
    }

    /**
     * {@code POST  /atendimentos} : Create a new atendimento.
     *
     * @param atendimentoDTO the atendimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atendimentoDTO, or with status {@code 400 (Bad Request)} if the atendimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/atendimentos")
    public ResponseEntity<AtendimentoDTO> createAtendimento(@RequestBody AtendimentoDTO atendimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Atendimento : {}", atendimentoDTO);
        if (atendimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new atendimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AtendimentoDTO result = atendimentoService.save(atendimentoDTO);
        return ResponseEntity.created(new URI("/api/atendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /atendimentos} : Updates an existing atendimento.
     *
     * @param atendimentoDTO the atendimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atendimentoDTO,
     * or with status {@code 400 (Bad Request)} if the atendimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atendimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/atendimentos")
    public ResponseEntity<AtendimentoDTO> updateAtendimento(@RequestBody AtendimentoDTO atendimentoDTO) throws URISyntaxException {
        log.debug("REST request to update Atendimento : {}", atendimentoDTO);
        if (atendimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AtendimentoDTO result = atendimentoService.save(atendimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atendimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /atendimentos} : get all the atendimentos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atendimentos in body.
     */
    @GetMapping("/atendimentos")
    public ResponseEntity<List<AtendimentoDTO>> getAllAtendimentos(AtendimentoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Atendimentos by criteria: {}", criteria);
        Page<AtendimentoDTO> page = atendimentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /atendimentos/count} : count all the atendimentos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/atendimentos/count")
    public ResponseEntity<Long> countAtendimentos(AtendimentoCriteria criteria) {
        log.debug("REST request to count Atendimentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(atendimentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /atendimentos/:id} : get the "id" atendimento.
     *
     * @param id the id of the atendimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atendimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/atendimentos/{id}")
    public ResponseEntity<AtendimentoDTO> getAtendimento(@PathVariable Long id) {
        log.debug("REST request to get Atendimento : {}", id);
        Optional<AtendimentoDTO> atendimentoDTO = atendimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atendimentoDTO);
    }

    /**
     * {@code DELETE  /atendimentos/:id} : delete the "id" atendimento.
     *
     * @param id the id of the atendimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/atendimentos/{id}")
    public ResponseEntity<Void> deleteAtendimento(@PathVariable Long id) {
        log.debug("REST request to delete Atendimento : {}", id);
        atendimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
