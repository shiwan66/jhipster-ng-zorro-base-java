package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.TituloService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.TituloDTO;
import br.com.ngzorro.service.dto.TituloCriteria;
import br.com.ngzorro.service.TituloQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.Titulo}.
 */
@RestController
@RequestMapping("/api")
public class TituloResource {

    private final Logger log = LoggerFactory.getLogger(TituloResource.class);

    private static final String ENTITY_NAME = "titulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TituloService tituloService;

    private final TituloQueryService tituloQueryService;

    public TituloResource(TituloService tituloService, TituloQueryService tituloQueryService) {
        this.tituloService = tituloService;
        this.tituloQueryService = tituloQueryService;
    }

    /**
     * {@code POST  /titulos} : Create a new titulo.
     *
     * @param tituloDTO the tituloDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tituloDTO, or with status {@code 400 (Bad Request)} if the titulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/titulos")
    public ResponseEntity<TituloDTO> createTitulo(@RequestBody TituloDTO tituloDTO) throws URISyntaxException {
        log.debug("REST request to save Titulo : {}", tituloDTO);
        if (tituloDTO.getId() != null) {
            throw new BadRequestAlertException("A new titulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TituloDTO result = tituloService.save(tituloDTO);
        return ResponseEntity.created(new URI("/api/titulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /titulos} : Updates an existing titulo.
     *
     * @param tituloDTO the tituloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tituloDTO,
     * or with status {@code 400 (Bad Request)} if the tituloDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tituloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/titulos")
    public ResponseEntity<TituloDTO> updateTitulo(@RequestBody TituloDTO tituloDTO) throws URISyntaxException {
        log.debug("REST request to update Titulo : {}", tituloDTO);
        if (tituloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TituloDTO result = tituloService.save(tituloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tituloDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /titulos} : get all the titulos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of titulos in body.
     */
    @GetMapping("/titulos")
    public ResponseEntity<List<TituloDTO>> getAllTitulos(TituloCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Titulos by criteria: {}", criteria);
        Page<TituloDTO> page = tituloQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /titulos/count} : count all the titulos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/titulos/count")
    public ResponseEntity<Long> countTitulos(TituloCriteria criteria) {
        log.debug("REST request to count Titulos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tituloQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /titulos/:id} : get the "id" titulo.
     *
     * @param id the id of the tituloDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tituloDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/titulos/{id}")
    public ResponseEntity<TituloDTO> getTitulo(@PathVariable Long id) {
        log.debug("REST request to get Titulo : {}", id);
        Optional<TituloDTO> tituloDTO = tituloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tituloDTO);
    }

    /**
     * {@code DELETE  /titulos/:id} : delete the "id" titulo.
     *
     * @param id the id of the tituloDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/titulos/{id}")
    public ResponseEntity<Void> deleteTitulo(@PathVariable Long id) {
        log.debug("REST request to delete Titulo : {}", id);
        tituloService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
