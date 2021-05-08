package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnimalVermifugoService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnimalVermifugoDTO;
import br.com.ngzorro.service.dto.AnimalVermifugoCriteria;
import br.com.ngzorro.service.AnimalVermifugoQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.AnimalVermifugo}.
 */
@RestController
@RequestMapping("/api")
public class AnimalVermifugoResource {

    private final Logger log = LoggerFactory.getLogger(AnimalVermifugoResource.class);

    private static final String ENTITY_NAME = "animalVermifugo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalVermifugoService animalVermifugoService;

    private final AnimalVermifugoQueryService animalVermifugoQueryService;

    public AnimalVermifugoResource(AnimalVermifugoService animalVermifugoService, AnimalVermifugoQueryService animalVermifugoQueryService) {
        this.animalVermifugoService = animalVermifugoService;
        this.animalVermifugoQueryService = animalVermifugoQueryService;
    }

    /**
     * {@code POST  /animal-vermifugos} : Create a new animalVermifugo.
     *
     * @param animalVermifugoDTO the animalVermifugoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalVermifugoDTO, or with status {@code 400 (Bad Request)} if the animalVermifugo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-vermifugos")
    public ResponseEntity<AnimalVermifugoDTO> createAnimalVermifugo(@RequestBody AnimalVermifugoDTO animalVermifugoDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalVermifugo : {}", animalVermifugoDTO);
        if (animalVermifugoDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalVermifugo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalVermifugoDTO result = animalVermifugoService.save(animalVermifugoDTO);
        return ResponseEntity.created(new URI("/api/animal-vermifugos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-vermifugos} : Updates an existing animalVermifugo.
     *
     * @param animalVermifugoDTO the animalVermifugoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalVermifugoDTO,
     * or with status {@code 400 (Bad Request)} if the animalVermifugoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalVermifugoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-vermifugos")
    public ResponseEntity<AnimalVermifugoDTO> updateAnimalVermifugo(@RequestBody AnimalVermifugoDTO animalVermifugoDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalVermifugo : {}", animalVermifugoDTO);
        if (animalVermifugoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalVermifugoDTO result = animalVermifugoService.save(animalVermifugoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalVermifugoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-vermifugos} : get all the animalVermifugos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalVermifugos in body.
     */
    @GetMapping("/animal-vermifugos")
    public ResponseEntity<List<AnimalVermifugoDTO>> getAllAnimalVermifugos(AnimalVermifugoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnimalVermifugos by criteria: {}", criteria);
        Page<AnimalVermifugoDTO> page = animalVermifugoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /animal-vermifugos/count} : count all the animalVermifugos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/animal-vermifugos/count")
    public ResponseEntity<Long> countAnimalVermifugos(AnimalVermifugoCriteria criteria) {
        log.debug("REST request to count AnimalVermifugos by criteria: {}", criteria);
        return ResponseEntity.ok().body(animalVermifugoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /animal-vermifugos/:id} : get the "id" animalVermifugo.
     *
     * @param id the id of the animalVermifugoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalVermifugoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-vermifugos/{id}")
    public ResponseEntity<AnimalVermifugoDTO> getAnimalVermifugo(@PathVariable Long id) {
        log.debug("REST request to get AnimalVermifugo : {}", id);
        Optional<AnimalVermifugoDTO> animalVermifugoDTO = animalVermifugoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalVermifugoDTO);
    }

    /**
     * {@code DELETE  /animal-vermifugos/:id} : delete the "id" animalVermifugo.
     *
     * @param id the id of the animalVermifugoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-vermifugos/{id}")
    public ResponseEntity<Void> deleteAnimalVermifugo(@PathVariable Long id) {
        log.debug("REST request to delete AnimalVermifugo : {}", id);
        animalVermifugoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
