package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.VendaConsumoService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.VendaConsumoDTO;
import br.com.ngzorro.service.dto.VendaConsumoCriteria;
import br.com.ngzorro.service.VendaConsumoQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.VendaConsumo}.
 */
@RestController
@RequestMapping("/api")
public class VendaConsumoResource {

    private final Logger log = LoggerFactory.getLogger(VendaConsumoResource.class);

    private static final String ENTITY_NAME = "vendaConsumo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendaConsumoService vendaConsumoService;

    private final VendaConsumoQueryService vendaConsumoQueryService;

    public VendaConsumoResource(VendaConsumoService vendaConsumoService, VendaConsumoQueryService vendaConsumoQueryService) {
        this.vendaConsumoService = vendaConsumoService;
        this.vendaConsumoQueryService = vendaConsumoQueryService;
    }

    /**
     * {@code POST  /venda-consumos} : Create a new vendaConsumo.
     *
     * @param vendaConsumoDTO the vendaConsumoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vendaConsumoDTO, or with status {@code 400 (Bad Request)} if the vendaConsumo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/venda-consumos")
    public ResponseEntity<VendaConsumoDTO> createVendaConsumo(@RequestBody VendaConsumoDTO vendaConsumoDTO) throws URISyntaxException {
        log.debug("REST request to save VendaConsumo : {}", vendaConsumoDTO);
        if (vendaConsumoDTO.getId() != null) {
            throw new BadRequestAlertException("A new vendaConsumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendaConsumoDTO result = vendaConsumoService.save(vendaConsumoDTO);
        return ResponseEntity.created(new URI("/api/venda-consumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /venda-consumos} : Updates an existing vendaConsumo.
     *
     * @param vendaConsumoDTO the vendaConsumoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendaConsumoDTO,
     * or with status {@code 400 (Bad Request)} if the vendaConsumoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vendaConsumoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/venda-consumos")
    public ResponseEntity<VendaConsumoDTO> updateVendaConsumo(@RequestBody VendaConsumoDTO vendaConsumoDTO) throws URISyntaxException {
        log.debug("REST request to update VendaConsumo : {}", vendaConsumoDTO);
        if (vendaConsumoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VendaConsumoDTO result = vendaConsumoService.save(vendaConsumoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendaConsumoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /venda-consumos} : get all the vendaConsumos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendaConsumos in body.
     */
    @GetMapping("/venda-consumos")
    public ResponseEntity<List<VendaConsumoDTO>> getAllVendaConsumos(VendaConsumoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VendaConsumos by criteria: {}", criteria);
        Page<VendaConsumoDTO> page = vendaConsumoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /venda-consumos/count} : count all the vendaConsumos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/venda-consumos/count")
    public ResponseEntity<Long> countVendaConsumos(VendaConsumoCriteria criteria) {
        log.debug("REST request to count VendaConsumos by criteria: {}", criteria);
        return ResponseEntity.ok().body(vendaConsumoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /venda-consumos/:id} : get the "id" vendaConsumo.
     *
     * @param id the id of the vendaConsumoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vendaConsumoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/venda-consumos/{id}")
    public ResponseEntity<VendaConsumoDTO> getVendaConsumo(@PathVariable Long id) {
        log.debug("REST request to get VendaConsumo : {}", id);
        Optional<VendaConsumoDTO> vendaConsumoDTO = vendaConsumoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendaConsumoDTO);
    }

    /**
     * {@code DELETE  /venda-consumos/:id} : delete the "id" vendaConsumo.
     *
     * @param id the id of the vendaConsumoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/venda-consumos/{id}")
    public ResponseEntity<Void> deleteVendaConsumo(@PathVariable Long id) {
        log.debug("REST request to delete VendaConsumo : {}", id);
        vendaConsumoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
