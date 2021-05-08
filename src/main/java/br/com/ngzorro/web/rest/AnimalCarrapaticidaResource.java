package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnimalCarrapaticidaService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnimalCarrapaticidaDTO;
import br.com.ngzorro.service.dto.AnimalCarrapaticidaCriteria;
import br.com.ngzorro.service.AnimalCarrapaticidaQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.AnimalCarrapaticida}.
 */
@RestController
@RequestMapping("/api")
public class AnimalCarrapaticidaResource {

    private final Logger log = LoggerFactory.getLogger(AnimalCarrapaticidaResource.class);

    private static final String ENTITY_NAME = "animalCarrapaticida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalCarrapaticidaService animalCarrapaticidaService;

    private final AnimalCarrapaticidaQueryService animalCarrapaticidaQueryService;

    public AnimalCarrapaticidaResource(AnimalCarrapaticidaService animalCarrapaticidaService, AnimalCarrapaticidaQueryService animalCarrapaticidaQueryService) {
        this.animalCarrapaticidaService = animalCarrapaticidaService;
        this.animalCarrapaticidaQueryService = animalCarrapaticidaQueryService;
    }

    /**
     * {@code POST  /animal-carrapaticidas} : Create a new animalCarrapaticida.
     *
     * @param animalCarrapaticidaDTO the animalCarrapaticidaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalCarrapaticidaDTO, or with status {@code 400 (Bad Request)} if the animalCarrapaticida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-carrapaticidas")
    public ResponseEntity<AnimalCarrapaticidaDTO> createAnimalCarrapaticida(@RequestBody AnimalCarrapaticidaDTO animalCarrapaticidaDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalCarrapaticida : {}", animalCarrapaticidaDTO);
        if (animalCarrapaticidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalCarrapaticida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalCarrapaticidaDTO result = animalCarrapaticidaService.save(animalCarrapaticidaDTO);
        return ResponseEntity.created(new URI("/api/animal-carrapaticidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-carrapaticidas} : Updates an existing animalCarrapaticida.
     *
     * @param animalCarrapaticidaDTO the animalCarrapaticidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalCarrapaticidaDTO,
     * or with status {@code 400 (Bad Request)} if the animalCarrapaticidaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalCarrapaticidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-carrapaticidas")
    public ResponseEntity<AnimalCarrapaticidaDTO> updateAnimalCarrapaticida(@RequestBody AnimalCarrapaticidaDTO animalCarrapaticidaDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalCarrapaticida : {}", animalCarrapaticidaDTO);
        if (animalCarrapaticidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalCarrapaticidaDTO result = animalCarrapaticidaService.save(animalCarrapaticidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalCarrapaticidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-carrapaticidas} : get all the animalCarrapaticidas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalCarrapaticidas in body.
     */
    @GetMapping("/animal-carrapaticidas")
    public ResponseEntity<List<AnimalCarrapaticidaDTO>> getAllAnimalCarrapaticidas(AnimalCarrapaticidaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnimalCarrapaticidas by criteria: {}", criteria);
        Page<AnimalCarrapaticidaDTO> page = animalCarrapaticidaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /animal-carrapaticidas/count} : count all the animalCarrapaticidas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/animal-carrapaticidas/count")
    public ResponseEntity<Long> countAnimalCarrapaticidas(AnimalCarrapaticidaCriteria criteria) {
        log.debug("REST request to count AnimalCarrapaticidas by criteria: {}", criteria);
        return ResponseEntity.ok().body(animalCarrapaticidaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /animal-carrapaticidas/:id} : get the "id" animalCarrapaticida.
     *
     * @param id the id of the animalCarrapaticidaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalCarrapaticidaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-carrapaticidas/{id}")
    public ResponseEntity<AnimalCarrapaticidaDTO> getAnimalCarrapaticida(@PathVariable Long id) {
        log.debug("REST request to get AnimalCarrapaticida : {}", id);
        Optional<AnimalCarrapaticidaDTO> animalCarrapaticidaDTO = animalCarrapaticidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalCarrapaticidaDTO);
    }

    /**
     * {@code DELETE  /animal-carrapaticidas/:id} : delete the "id" animalCarrapaticida.
     *
     * @param id the id of the animalCarrapaticidaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-carrapaticidas/{id}")
    public ResponseEntity<Void> deleteAnimalCarrapaticida(@PathVariable Long id) {
        log.debug("REST request to delete AnimalCarrapaticida : {}", id);
        animalCarrapaticidaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
