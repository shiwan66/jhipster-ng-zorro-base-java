package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnimalObservacaoService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnimalObservacaoDTO;
import br.com.ngzorro.service.dto.AnimalObservacaoCriteria;
import br.com.ngzorro.service.AnimalObservacaoQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.AnimalObservacao}.
 */
@RestController
@RequestMapping("/api")
public class AnimalObservacaoResource {

    private final Logger log = LoggerFactory.getLogger(AnimalObservacaoResource.class);

    private static final String ENTITY_NAME = "animalObservacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalObservacaoService animalObservacaoService;

    private final AnimalObservacaoQueryService animalObservacaoQueryService;

    public AnimalObservacaoResource(AnimalObservacaoService animalObservacaoService, AnimalObservacaoQueryService animalObservacaoQueryService) {
        this.animalObservacaoService = animalObservacaoService;
        this.animalObservacaoQueryService = animalObservacaoQueryService;
    }

    /**
     * {@code POST  /animal-observacaos} : Create a new animalObservacao.
     *
     * @param animalObservacaoDTO the animalObservacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalObservacaoDTO, or with status {@code 400 (Bad Request)} if the animalObservacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-observacaos")
    public ResponseEntity<AnimalObservacaoDTO> createAnimalObservacao(@RequestBody AnimalObservacaoDTO animalObservacaoDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalObservacao : {}", animalObservacaoDTO);
        if (animalObservacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalObservacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalObservacaoDTO result = animalObservacaoService.save(animalObservacaoDTO);
        return ResponseEntity.created(new URI("/api/animal-observacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-observacaos} : Updates an existing animalObservacao.
     *
     * @param animalObservacaoDTO the animalObservacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalObservacaoDTO,
     * or with status {@code 400 (Bad Request)} if the animalObservacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalObservacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-observacaos")
    public ResponseEntity<AnimalObservacaoDTO> updateAnimalObservacao(@RequestBody AnimalObservacaoDTO animalObservacaoDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalObservacao : {}", animalObservacaoDTO);
        if (animalObservacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalObservacaoDTO result = animalObservacaoService.save(animalObservacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalObservacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-observacaos} : get all the animalObservacaos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalObservacaos in body.
     */
    @GetMapping("/animal-observacaos")
    public ResponseEntity<List<AnimalObservacaoDTO>> getAllAnimalObservacaos(AnimalObservacaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnimalObservacaos by criteria: {}", criteria);
        Page<AnimalObservacaoDTO> page = animalObservacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /animal-observacaos/count} : count all the animalObservacaos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/animal-observacaos/count")
    public ResponseEntity<Long> countAnimalObservacaos(AnimalObservacaoCriteria criteria) {
        log.debug("REST request to count AnimalObservacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(animalObservacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /animal-observacaos/:id} : get the "id" animalObservacao.
     *
     * @param id the id of the animalObservacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalObservacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-observacaos/{id}")
    public ResponseEntity<AnimalObservacaoDTO> getAnimalObservacao(@PathVariable Long id) {
        log.debug("REST request to get AnimalObservacao : {}", id);
        Optional<AnimalObservacaoDTO> animalObservacaoDTO = animalObservacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalObservacaoDTO);
    }

    /**
     * {@code DELETE  /animal-observacaos/:id} : delete the "id" animalObservacao.
     *
     * @param id the id of the animalObservacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-observacaos/{id}")
    public ResponseEntity<Void> deleteAnimalObservacao(@PathVariable Long id) {
        log.debug("REST request to delete AnimalObservacao : {}", id);
        animalObservacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
