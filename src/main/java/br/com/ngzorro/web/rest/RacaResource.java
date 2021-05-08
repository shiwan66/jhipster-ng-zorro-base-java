package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.RacaService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.RacaDTO;
import br.com.ngzorro.service.dto.RacaCriteria;
import br.com.ngzorro.service.RacaQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.Raca}.
 */
@RestController
@RequestMapping("/api")
public class RacaResource {

    private final Logger log = LoggerFactory.getLogger(RacaResource.class);

    private static final String ENTITY_NAME = "raca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RacaService racaService;

    private final RacaQueryService racaQueryService;

    public RacaResource(RacaService racaService, RacaQueryService racaQueryService) {
        this.racaService = racaService;
        this.racaQueryService = racaQueryService;
    }

    /**
     * {@code POST  /racas} : Create a new raca.
     *
     * @param racaDTO the racaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new racaDTO, or with status {@code 400 (Bad Request)} if the raca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/racas")
    public ResponseEntity<RacaDTO> createRaca(@RequestBody RacaDTO racaDTO) throws URISyntaxException {
        log.debug("REST request to save Raca : {}", racaDTO);
        if (racaDTO.getId() != null) {
            throw new BadRequestAlertException("A new raca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RacaDTO result = racaService.save(racaDTO);
        return ResponseEntity.created(new URI("/api/racas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /racas} : Updates an existing raca.
     *
     * @param racaDTO the racaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated racaDTO,
     * or with status {@code 400 (Bad Request)} if the racaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the racaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/racas")
    public ResponseEntity<RacaDTO> updateRaca(@RequestBody RacaDTO racaDTO) throws URISyntaxException {
        log.debug("REST request to update Raca : {}", racaDTO);
        if (racaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RacaDTO result = racaService.save(racaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, racaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /racas} : get all the racas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of racas in body.
     */
    @GetMapping("/racas")
    public ResponseEntity<List<RacaDTO>> getAllRacas(RacaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Racas by criteria: {}", criteria);
        Page<RacaDTO> page = racaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /racas/count} : count all the racas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/racas/count")
    public ResponseEntity<Long> countRacas(RacaCriteria criteria) {
        log.debug("REST request to count Racas by criteria: {}", criteria);
        return ResponseEntity.ok().body(racaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /racas/:id} : get the "id" raca.
     *
     * @param id the id of the racaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the racaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/racas/{id}")
    public ResponseEntity<RacaDTO> getRaca(@PathVariable Long id) {
        log.debug("REST request to get Raca : {}", id);
        Optional<RacaDTO> racaDTO = racaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(racaDTO);
    }

    /**
     * {@code DELETE  /racas/:id} : delete the "id" raca.
     *
     * @param id the id of the racaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/racas/{id}")
    public ResponseEntity<Void> deleteRaca(@PathVariable Long id) {
        log.debug("REST request to delete Raca : {}", id);
        racaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
