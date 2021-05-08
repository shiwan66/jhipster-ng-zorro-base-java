package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnimalCioService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnimalCioDTO;
import br.com.ngzorro.service.dto.AnimalCioCriteria;
import br.com.ngzorro.service.AnimalCioQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.AnimalCio}.
 */
@RestController
@RequestMapping("/api")
public class AnimalCioResource {

    private final Logger log = LoggerFactory.getLogger(AnimalCioResource.class);

    private static final String ENTITY_NAME = "animalCio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalCioService animalCioService;

    private final AnimalCioQueryService animalCioQueryService;

    public AnimalCioResource(AnimalCioService animalCioService, AnimalCioQueryService animalCioQueryService) {
        this.animalCioService = animalCioService;
        this.animalCioQueryService = animalCioQueryService;
    }

    /**
     * {@code POST  /animal-cios} : Create a new animalCio.
     *
     * @param animalCioDTO the animalCioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalCioDTO, or with status {@code 400 (Bad Request)} if the animalCio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-cios")
    public ResponseEntity<AnimalCioDTO> createAnimalCio(@RequestBody AnimalCioDTO animalCioDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalCio : {}", animalCioDTO);
        if (animalCioDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalCio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalCioDTO result = animalCioService.save(animalCioDTO);
        return ResponseEntity.created(new URI("/api/animal-cios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-cios} : Updates an existing animalCio.
     *
     * @param animalCioDTO the animalCioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalCioDTO,
     * or with status {@code 400 (Bad Request)} if the animalCioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalCioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-cios")
    public ResponseEntity<AnimalCioDTO> updateAnimalCio(@RequestBody AnimalCioDTO animalCioDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalCio : {}", animalCioDTO);
        if (animalCioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalCioDTO result = animalCioService.save(animalCioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalCioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-cios} : get all the animalCios.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalCios in body.
     */
    @GetMapping("/animal-cios")
    public ResponseEntity<List<AnimalCioDTO>> getAllAnimalCios(AnimalCioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnimalCios by criteria: {}", criteria);
        Page<AnimalCioDTO> page = animalCioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /animal-cios/count} : count all the animalCios.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/animal-cios/count")
    public ResponseEntity<Long> countAnimalCios(AnimalCioCriteria criteria) {
        log.debug("REST request to count AnimalCios by criteria: {}", criteria);
        return ResponseEntity.ok().body(animalCioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /animal-cios/:id} : get the "id" animalCio.
     *
     * @param id the id of the animalCioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalCioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-cios/{id}")
    public ResponseEntity<AnimalCioDTO> getAnimalCio(@PathVariable Long id) {
        log.debug("REST request to get AnimalCio : {}", id);
        Optional<AnimalCioDTO> animalCioDTO = animalCioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalCioDTO);
    }

    /**
     * {@code DELETE  /animal-cios/:id} : delete the "id" animalCio.
     *
     * @param id the id of the animalCioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-cios/{id}")
    public ResponseEntity<Void> deleteAnimalCio(@PathVariable Long id) {
        log.debug("REST request to delete AnimalCio : {}", id);
        animalCioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
