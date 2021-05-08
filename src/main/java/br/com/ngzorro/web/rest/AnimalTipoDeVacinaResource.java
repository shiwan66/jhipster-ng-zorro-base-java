package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnimalTipoDeVacinaService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnimalTipoDeVacinaDTO;
import br.com.ngzorro.service.dto.AnimalTipoDeVacinaCriteria;
import br.com.ngzorro.service.AnimalTipoDeVacinaQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.AnimalTipoDeVacina}.
 */
@RestController
@RequestMapping("/api")
public class AnimalTipoDeVacinaResource {

    private final Logger log = LoggerFactory.getLogger(AnimalTipoDeVacinaResource.class);

    private static final String ENTITY_NAME = "animalTipoDeVacina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalTipoDeVacinaService animalTipoDeVacinaService;

    private final AnimalTipoDeVacinaQueryService animalTipoDeVacinaQueryService;

    public AnimalTipoDeVacinaResource(AnimalTipoDeVacinaService animalTipoDeVacinaService, AnimalTipoDeVacinaQueryService animalTipoDeVacinaQueryService) {
        this.animalTipoDeVacinaService = animalTipoDeVacinaService;
        this.animalTipoDeVacinaQueryService = animalTipoDeVacinaQueryService;
    }

    /**
     * {@code POST  /animal-tipo-de-vacinas} : Create a new animalTipoDeVacina.
     *
     * @param animalTipoDeVacinaDTO the animalTipoDeVacinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalTipoDeVacinaDTO, or with status {@code 400 (Bad Request)} if the animalTipoDeVacina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-tipo-de-vacinas")
    public ResponseEntity<AnimalTipoDeVacinaDTO> createAnimalTipoDeVacina(@RequestBody AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalTipoDeVacina : {}", animalTipoDeVacinaDTO);
        if (animalTipoDeVacinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalTipoDeVacina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalTipoDeVacinaDTO result = animalTipoDeVacinaService.save(animalTipoDeVacinaDTO);
        return ResponseEntity.created(new URI("/api/animal-tipo-de-vacinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-tipo-de-vacinas} : Updates an existing animalTipoDeVacina.
     *
     * @param animalTipoDeVacinaDTO the animalTipoDeVacinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalTipoDeVacinaDTO,
     * or with status {@code 400 (Bad Request)} if the animalTipoDeVacinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalTipoDeVacinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-tipo-de-vacinas")
    public ResponseEntity<AnimalTipoDeVacinaDTO> updateAnimalTipoDeVacina(@RequestBody AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalTipoDeVacina : {}", animalTipoDeVacinaDTO);
        if (animalTipoDeVacinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalTipoDeVacinaDTO result = animalTipoDeVacinaService.save(animalTipoDeVacinaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalTipoDeVacinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-tipo-de-vacinas} : get all the animalTipoDeVacinas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalTipoDeVacinas in body.
     */
    @GetMapping("/animal-tipo-de-vacinas")
    public ResponseEntity<List<AnimalTipoDeVacinaDTO>> getAllAnimalTipoDeVacinas(AnimalTipoDeVacinaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnimalTipoDeVacinas by criteria: {}", criteria);
        Page<AnimalTipoDeVacinaDTO> page = animalTipoDeVacinaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /animal-tipo-de-vacinas/count} : count all the animalTipoDeVacinas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/animal-tipo-de-vacinas/count")
    public ResponseEntity<Long> countAnimalTipoDeVacinas(AnimalTipoDeVacinaCriteria criteria) {
        log.debug("REST request to count AnimalTipoDeVacinas by criteria: {}", criteria);
        return ResponseEntity.ok().body(animalTipoDeVacinaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /animal-tipo-de-vacinas/:id} : get the "id" animalTipoDeVacina.
     *
     * @param id the id of the animalTipoDeVacinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalTipoDeVacinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-tipo-de-vacinas/{id}")
    public ResponseEntity<AnimalTipoDeVacinaDTO> getAnimalTipoDeVacina(@PathVariable Long id) {
        log.debug("REST request to get AnimalTipoDeVacina : {}", id);
        Optional<AnimalTipoDeVacinaDTO> animalTipoDeVacinaDTO = animalTipoDeVacinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalTipoDeVacinaDTO);
    }

    /**
     * {@code DELETE  /animal-tipo-de-vacinas/:id} : delete the "id" animalTipoDeVacina.
     *
     * @param id the id of the animalTipoDeVacinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-tipo-de-vacinas/{id}")
    public ResponseEntity<Void> deleteAnimalTipoDeVacina(@PathVariable Long id) {
        log.debug("REST request to delete AnimalTipoDeVacina : {}", id);
        animalTipoDeVacinaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
