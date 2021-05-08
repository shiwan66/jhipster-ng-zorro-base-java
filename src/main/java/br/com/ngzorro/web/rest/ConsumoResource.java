package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.ConsumoService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.ConsumoDTO;
import br.com.ngzorro.service.dto.ConsumoCriteria;
import br.com.ngzorro.service.ConsumoQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.Consumo}.
 */
@RestController
@RequestMapping("/api")
public class ConsumoResource {

    private final Logger log = LoggerFactory.getLogger(ConsumoResource.class);

    private static final String ENTITY_NAME = "consumo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumoService consumoService;

    private final ConsumoQueryService consumoQueryService;

    public ConsumoResource(ConsumoService consumoService, ConsumoQueryService consumoQueryService) {
        this.consumoService = consumoService;
        this.consumoQueryService = consumoQueryService;
    }

    /**
     * {@code POST  /consumos} : Create a new consumo.
     *
     * @param consumoDTO the consumoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumoDTO, or with status {@code 400 (Bad Request)} if the consumo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumos")
    public ResponseEntity<ConsumoDTO> createConsumo(@RequestBody ConsumoDTO consumoDTO) throws URISyntaxException {
        log.debug("REST request to save Consumo : {}", consumoDTO);
        if (consumoDTO.getId() != null) {
            throw new BadRequestAlertException("A new consumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsumoDTO result = consumoService.save(consumoDTO);
        return ResponseEntity.created(new URI("/api/consumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consumos} : Updates an existing consumo.
     *
     * @param consumoDTO the consumoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumoDTO,
     * or with status {@code 400 (Bad Request)} if the consumoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumos")
    public ResponseEntity<ConsumoDTO> updateConsumo(@RequestBody ConsumoDTO consumoDTO) throws URISyntaxException {
        log.debug("REST request to update Consumo : {}", consumoDTO);
        if (consumoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsumoDTO result = consumoService.save(consumoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consumoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consumos} : get all the consumos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumos in body.
     */
    @GetMapping("/consumos")
    public ResponseEntity<List<ConsumoDTO>> getAllConsumos(ConsumoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Consumos by criteria: {}", criteria);
        Page<ConsumoDTO> page = consumoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /consumos/count} : count all the consumos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/consumos/count")
    public ResponseEntity<Long> countConsumos(ConsumoCriteria criteria) {
        log.debug("REST request to count Consumos by criteria: {}", criteria);
        return ResponseEntity.ok().body(consumoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /consumos/:id} : get the "id" consumo.
     *
     * @param id the id of the consumoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumos/{id}")
    public ResponseEntity<ConsumoDTO> getConsumo(@PathVariable Long id) {
        log.debug("REST request to get Consumo : {}", id);
        Optional<ConsumoDTO> consumoDTO = consumoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumoDTO);
    }

    /**
     * {@code DELETE  /consumos/:id} : delete the "id" consumo.
     *
     * @param id the id of the consumoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumos/{id}")
    public ResponseEntity<Void> deleteConsumo(@PathVariable Long id) {
        log.debug("REST request to delete Consumo : {}", id);
        consumoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
