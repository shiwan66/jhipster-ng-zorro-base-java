package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnimalAlteracaoService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnimalAlteracaoDTO;
import br.com.ngzorro.service.dto.AnimalAlteracaoCriteria;
import br.com.ngzorro.service.AnimalAlteracaoQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.AnimalAlteracao}.
 */
@RestController
@RequestMapping("/api")
public class AnimalAlteracaoResource {

    private final Logger log = LoggerFactory.getLogger(AnimalAlteracaoResource.class);

    private static final String ENTITY_NAME = "animalAlteracao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalAlteracaoService animalAlteracaoService;

    private final AnimalAlteracaoQueryService animalAlteracaoQueryService;

    public AnimalAlteracaoResource(AnimalAlteracaoService animalAlteracaoService, AnimalAlteracaoQueryService animalAlteracaoQueryService) {
        this.animalAlteracaoService = animalAlteracaoService;
        this.animalAlteracaoQueryService = animalAlteracaoQueryService;
    }

    /**
     * {@code POST  /animal-alteracaos} : Create a new animalAlteracao.
     *
     * @param animalAlteracaoDTO the animalAlteracaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalAlteracaoDTO, or with status {@code 400 (Bad Request)} if the animalAlteracao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-alteracaos")
    public ResponseEntity<AnimalAlteracaoDTO> createAnimalAlteracao(@RequestBody AnimalAlteracaoDTO animalAlteracaoDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalAlteracao : {}", animalAlteracaoDTO);
        if (animalAlteracaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalAlteracao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalAlteracaoDTO result = animalAlteracaoService.save(animalAlteracaoDTO);
        return ResponseEntity.created(new URI("/api/animal-alteracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-alteracaos} : Updates an existing animalAlteracao.
     *
     * @param animalAlteracaoDTO the animalAlteracaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalAlteracaoDTO,
     * or with status {@code 400 (Bad Request)} if the animalAlteracaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalAlteracaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-alteracaos")
    public ResponseEntity<AnimalAlteracaoDTO> updateAnimalAlteracao(@RequestBody AnimalAlteracaoDTO animalAlteracaoDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalAlteracao : {}", animalAlteracaoDTO);
        if (animalAlteracaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalAlteracaoDTO result = animalAlteracaoService.save(animalAlteracaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalAlteracaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-alteracaos} : get all the animalAlteracaos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalAlteracaos in body.
     */
    @GetMapping("/animal-alteracaos")
    public ResponseEntity<List<AnimalAlteracaoDTO>> getAllAnimalAlteracaos(AnimalAlteracaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnimalAlteracaos by criteria: {}", criteria);
        Page<AnimalAlteracaoDTO> page = animalAlteracaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /animal-alteracaos/count} : count all the animalAlteracaos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/animal-alteracaos/count")
    public ResponseEntity<Long> countAnimalAlteracaos(AnimalAlteracaoCriteria criteria) {
        log.debug("REST request to count AnimalAlteracaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(animalAlteracaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /animal-alteracaos/:id} : get the "id" animalAlteracao.
     *
     * @param id the id of the animalAlteracaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalAlteracaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-alteracaos/{id}")
    public ResponseEntity<AnimalAlteracaoDTO> getAnimalAlteracao(@PathVariable Long id) {
        log.debug("REST request to get AnimalAlteracao : {}", id);
        Optional<AnimalAlteracaoDTO> animalAlteracaoDTO = animalAlteracaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalAlteracaoDTO);
    }

    /**
     * {@code DELETE  /animal-alteracaos/:id} : delete the "id" animalAlteracao.
     *
     * @param id the id of the animalAlteracaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-alteracaos/{id}")
    public ResponseEntity<Void> deleteAnimalAlteracao(@PathVariable Long id) {
        log.debug("REST request to delete AnimalAlteracao : {}", id);
        animalAlteracaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
