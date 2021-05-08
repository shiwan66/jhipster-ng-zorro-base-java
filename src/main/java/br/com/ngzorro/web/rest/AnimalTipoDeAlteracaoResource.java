package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnimalTipoDeAlteracaoService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnimalTipoDeAlteracaoDTO;
import br.com.ngzorro.service.dto.AnimalTipoDeAlteracaoCriteria;
import br.com.ngzorro.service.AnimalTipoDeAlteracaoQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.AnimalTipoDeAlteracao}.
 */
@RestController
@RequestMapping("/api")
public class AnimalTipoDeAlteracaoResource {

    private final Logger log = LoggerFactory.getLogger(AnimalTipoDeAlteracaoResource.class);

    private static final String ENTITY_NAME = "animalTipoDeAlteracao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalTipoDeAlteracaoService animalTipoDeAlteracaoService;

    private final AnimalTipoDeAlteracaoQueryService animalTipoDeAlteracaoQueryService;

    public AnimalTipoDeAlteracaoResource(AnimalTipoDeAlteracaoService animalTipoDeAlteracaoService, AnimalTipoDeAlteracaoQueryService animalTipoDeAlteracaoQueryService) {
        this.animalTipoDeAlteracaoService = animalTipoDeAlteracaoService;
        this.animalTipoDeAlteracaoQueryService = animalTipoDeAlteracaoQueryService;
    }

    /**
     * {@code POST  /animal-tipo-de-alteracaos} : Create a new animalTipoDeAlteracao.
     *
     * @param animalTipoDeAlteracaoDTO the animalTipoDeAlteracaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalTipoDeAlteracaoDTO, or with status {@code 400 (Bad Request)} if the animalTipoDeAlteracao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-tipo-de-alteracaos")
    public ResponseEntity<AnimalTipoDeAlteracaoDTO> createAnimalTipoDeAlteracao(@RequestBody AnimalTipoDeAlteracaoDTO animalTipoDeAlteracaoDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalTipoDeAlteracao : {}", animalTipoDeAlteracaoDTO);
        if (animalTipoDeAlteracaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalTipoDeAlteracao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalTipoDeAlteracaoDTO result = animalTipoDeAlteracaoService.save(animalTipoDeAlteracaoDTO);
        return ResponseEntity.created(new URI("/api/animal-tipo-de-alteracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-tipo-de-alteracaos} : Updates an existing animalTipoDeAlteracao.
     *
     * @param animalTipoDeAlteracaoDTO the animalTipoDeAlteracaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalTipoDeAlteracaoDTO,
     * or with status {@code 400 (Bad Request)} if the animalTipoDeAlteracaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalTipoDeAlteracaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-tipo-de-alteracaos")
    public ResponseEntity<AnimalTipoDeAlteracaoDTO> updateAnimalTipoDeAlteracao(@RequestBody AnimalTipoDeAlteracaoDTO animalTipoDeAlteracaoDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalTipoDeAlteracao : {}", animalTipoDeAlteracaoDTO);
        if (animalTipoDeAlteracaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalTipoDeAlteracaoDTO result = animalTipoDeAlteracaoService.save(animalTipoDeAlteracaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalTipoDeAlteracaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-tipo-de-alteracaos} : get all the animalTipoDeAlteracaos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalTipoDeAlteracaos in body.
     */
    @GetMapping("/animal-tipo-de-alteracaos")
    public ResponseEntity<List<AnimalTipoDeAlteracaoDTO>> getAllAnimalTipoDeAlteracaos(AnimalTipoDeAlteracaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnimalTipoDeAlteracaos by criteria: {}", criteria);
        Page<AnimalTipoDeAlteracaoDTO> page = animalTipoDeAlteracaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /animal-tipo-de-alteracaos/count} : count all the animalTipoDeAlteracaos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/animal-tipo-de-alteracaos/count")
    public ResponseEntity<Long> countAnimalTipoDeAlteracaos(AnimalTipoDeAlteracaoCriteria criteria) {
        log.debug("REST request to count AnimalTipoDeAlteracaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(animalTipoDeAlteracaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /animal-tipo-de-alteracaos/:id} : get the "id" animalTipoDeAlteracao.
     *
     * @param id the id of the animalTipoDeAlteracaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalTipoDeAlteracaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-tipo-de-alteracaos/{id}")
    public ResponseEntity<AnimalTipoDeAlteracaoDTO> getAnimalTipoDeAlteracao(@PathVariable Long id) {
        log.debug("REST request to get AnimalTipoDeAlteracao : {}", id);
        Optional<AnimalTipoDeAlteracaoDTO> animalTipoDeAlteracaoDTO = animalTipoDeAlteracaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalTipoDeAlteracaoDTO);
    }

    /**
     * {@code DELETE  /animal-tipo-de-alteracaos/:id} : delete the "id" animalTipoDeAlteracao.
     *
     * @param id the id of the animalTipoDeAlteracaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-tipo-de-alteracaos/{id}")
    public ResponseEntity<Void> deleteAnimalTipoDeAlteracao(@PathVariable Long id) {
        log.debug("REST request to delete AnimalTipoDeAlteracao : {}", id);
        animalTipoDeAlteracaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
