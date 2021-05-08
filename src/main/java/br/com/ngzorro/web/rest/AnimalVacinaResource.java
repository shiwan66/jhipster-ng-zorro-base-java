package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnimalVacinaService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnimalVacinaDTO;
import br.com.ngzorro.service.dto.AnimalVacinaCriteria;
import br.com.ngzorro.service.AnimalVacinaQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.AnimalVacina}.
 */
@RestController
@RequestMapping("/api")
public class AnimalVacinaResource {

    private final Logger log = LoggerFactory.getLogger(AnimalVacinaResource.class);

    private static final String ENTITY_NAME = "animalVacina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalVacinaService animalVacinaService;

    private final AnimalVacinaQueryService animalVacinaQueryService;

    public AnimalVacinaResource(AnimalVacinaService animalVacinaService, AnimalVacinaQueryService animalVacinaQueryService) {
        this.animalVacinaService = animalVacinaService;
        this.animalVacinaQueryService = animalVacinaQueryService;
    }

    /**
     * {@code POST  /animal-vacinas} : Create a new animalVacina.
     *
     * @param animalVacinaDTO the animalVacinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalVacinaDTO, or with status {@code 400 (Bad Request)} if the animalVacina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-vacinas")
    public ResponseEntity<AnimalVacinaDTO> createAnimalVacina(@RequestBody AnimalVacinaDTO animalVacinaDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalVacina : {}", animalVacinaDTO);
        if (animalVacinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalVacina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalVacinaDTO result = animalVacinaService.save(animalVacinaDTO);
        return ResponseEntity.created(new URI("/api/animal-vacinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-vacinas} : Updates an existing animalVacina.
     *
     * @param animalVacinaDTO the animalVacinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalVacinaDTO,
     * or with status {@code 400 (Bad Request)} if the animalVacinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalVacinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-vacinas")
    public ResponseEntity<AnimalVacinaDTO> updateAnimalVacina(@RequestBody AnimalVacinaDTO animalVacinaDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalVacina : {}", animalVacinaDTO);
        if (animalVacinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalVacinaDTO result = animalVacinaService.save(animalVacinaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalVacinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-vacinas} : get all the animalVacinas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalVacinas in body.
     */
    @GetMapping("/animal-vacinas")
    public ResponseEntity<List<AnimalVacinaDTO>> getAllAnimalVacinas(AnimalVacinaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnimalVacinas by criteria: {}", criteria);
        Page<AnimalVacinaDTO> page = animalVacinaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /animal-vacinas/count} : count all the animalVacinas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/animal-vacinas/count")
    public ResponseEntity<Long> countAnimalVacinas(AnimalVacinaCriteria criteria) {
        log.debug("REST request to count AnimalVacinas by criteria: {}", criteria);
        return ResponseEntity.ok().body(animalVacinaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /animal-vacinas/:id} : get the "id" animalVacina.
     *
     * @param id the id of the animalVacinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalVacinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-vacinas/{id}")
    public ResponseEntity<AnimalVacinaDTO> getAnimalVacina(@PathVariable Long id) {
        log.debug("REST request to get AnimalVacina : {}", id);
        Optional<AnimalVacinaDTO> animalVacinaDTO = animalVacinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalVacinaDTO);
    }

    /**
     * {@code DELETE  /animal-vacinas/:id} : delete the "id" animalVacina.
     *
     * @param id the id of the animalVacinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-vacinas/{id}")
    public ResponseEntity<Void> deleteAnimalVacina(@PathVariable Long id) {
        log.debug("REST request to delete AnimalVacina : {}", id);
        animalVacinaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
