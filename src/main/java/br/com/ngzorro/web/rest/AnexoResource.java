package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnexoService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnexoDTO;
import br.com.ngzorro.service.dto.AnexoCriteria;
import br.com.ngzorro.service.AnexoQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.Anexo}.
 */
@RestController
@RequestMapping("/api")
public class AnexoResource {

    private final Logger log = LoggerFactory.getLogger(AnexoResource.class);

    private static final String ENTITY_NAME = "anexo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoService anexoService;

    private final AnexoQueryService anexoQueryService;

    public AnexoResource(AnexoService anexoService, AnexoQueryService anexoQueryService) {
        this.anexoService = anexoService;
        this.anexoQueryService = anexoQueryService;
    }

    /**
     * {@code POST  /anexos} : Create a new anexo.
     *
     * @param anexoDTO the anexoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoDTO, or with status {@code 400 (Bad Request)} if the anexo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anexos")
    public ResponseEntity<AnexoDTO> createAnexo(@RequestBody AnexoDTO anexoDTO) throws URISyntaxException {
        log.debug("REST request to save Anexo : {}", anexoDTO);
        if (anexoDTO.getId() != null) {
            throw new BadRequestAlertException("A new anexo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnexoDTO result = anexoService.save(anexoDTO);
        return ResponseEntity.created(new URI("/api/anexos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anexos} : Updates an existing anexo.
     *
     * @param anexoDTO the anexoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoDTO,
     * or with status {@code 400 (Bad Request)} if the anexoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anexos")
    public ResponseEntity<AnexoDTO> updateAnexo(@RequestBody AnexoDTO anexoDTO) throws URISyntaxException {
        log.debug("REST request to update Anexo : {}", anexoDTO);
        if (anexoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnexoDTO result = anexoService.save(anexoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /anexos} : get all the anexos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexos in body.
     */
    @GetMapping("/anexos")
    public ResponseEntity<List<AnexoDTO>> getAllAnexos(AnexoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Anexos by criteria: {}", criteria);
        Page<AnexoDTO> page = anexoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /anexos/count} : count all the anexos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/anexos/count")
    public ResponseEntity<Long> countAnexos(AnexoCriteria criteria) {
        log.debug("REST request to count Anexos by criteria: {}", criteria);
        return ResponseEntity.ok().body(anexoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /anexos/:id} : get the "id" anexo.
     *
     * @param id the id of the anexoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anexos/{id}")
    public ResponseEntity<AnexoDTO> getAnexo(@PathVariable Long id) {
        log.debug("REST request to get Anexo : {}", id);
        Optional<AnexoDTO> anexoDTO = anexoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoDTO);
    }

    /**
     * {@code DELETE  /anexos/:id} : delete the "id" anexo.
     *
     * @param id the id of the anexoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anexos/{id}")
    public ResponseEntity<Void> deleteAnexo(@PathVariable Long id) {
        log.debug("REST request to delete Anexo : {}", id);
        anexoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
