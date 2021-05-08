package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.AnimalVeterinarioService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.AnimalVeterinarioDTO;
import br.com.ngzorro.service.dto.AnimalVeterinarioCriteria;
import br.com.ngzorro.service.AnimalVeterinarioQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.AnimalVeterinario}.
 */
@RestController
@RequestMapping("/api")
public class AnimalVeterinarioResource {

    private final Logger log = LoggerFactory.getLogger(AnimalVeterinarioResource.class);

    private static final String ENTITY_NAME = "animalVeterinario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalVeterinarioService animalVeterinarioService;

    private final AnimalVeterinarioQueryService animalVeterinarioQueryService;

    public AnimalVeterinarioResource(AnimalVeterinarioService animalVeterinarioService, AnimalVeterinarioQueryService animalVeterinarioQueryService) {
        this.animalVeterinarioService = animalVeterinarioService;
        this.animalVeterinarioQueryService = animalVeterinarioQueryService;
    }

    /**
     * {@code POST  /animal-veterinarios} : Create a new animalVeterinario.
     *
     * @param animalVeterinarioDTO the animalVeterinarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalVeterinarioDTO, or with status {@code 400 (Bad Request)} if the animalVeterinario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-veterinarios")
    public ResponseEntity<AnimalVeterinarioDTO> createAnimalVeterinario(@RequestBody AnimalVeterinarioDTO animalVeterinarioDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalVeterinario : {}", animalVeterinarioDTO);
        if (animalVeterinarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalVeterinario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalVeterinarioDTO result = animalVeterinarioService.save(animalVeterinarioDTO);
        return ResponseEntity.created(new URI("/api/animal-veterinarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-veterinarios} : Updates an existing animalVeterinario.
     *
     * @param animalVeterinarioDTO the animalVeterinarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalVeterinarioDTO,
     * or with status {@code 400 (Bad Request)} if the animalVeterinarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalVeterinarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-veterinarios")
    public ResponseEntity<AnimalVeterinarioDTO> updateAnimalVeterinario(@RequestBody AnimalVeterinarioDTO animalVeterinarioDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalVeterinario : {}", animalVeterinarioDTO);
        if (animalVeterinarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalVeterinarioDTO result = animalVeterinarioService.save(animalVeterinarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalVeterinarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-veterinarios} : get all the animalVeterinarios.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalVeterinarios in body.
     */
    @GetMapping("/animal-veterinarios")
    public ResponseEntity<List<AnimalVeterinarioDTO>> getAllAnimalVeterinarios(AnimalVeterinarioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnimalVeterinarios by criteria: {}", criteria);
        Page<AnimalVeterinarioDTO> page = animalVeterinarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /animal-veterinarios/count} : count all the animalVeterinarios.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/animal-veterinarios/count")
    public ResponseEntity<Long> countAnimalVeterinarios(AnimalVeterinarioCriteria criteria) {
        log.debug("REST request to count AnimalVeterinarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(animalVeterinarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /animal-veterinarios/:id} : get the "id" animalVeterinario.
     *
     * @param id the id of the animalVeterinarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalVeterinarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-veterinarios/{id}")
    public ResponseEntity<AnimalVeterinarioDTO> getAnimalVeterinario(@PathVariable Long id) {
        log.debug("REST request to get AnimalVeterinario : {}", id);
        Optional<AnimalVeterinarioDTO> animalVeterinarioDTO = animalVeterinarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalVeterinarioDTO);
    }

    /**
     * {@code DELETE  /animal-veterinarios/:id} : delete the "id" animalVeterinario.
     *
     * @param id the id of the animalVeterinarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-veterinarios/{id}")
    public ResponseEntity<Void> deleteAnimalVeterinario(@PathVariable Long id) {
        log.debug("REST request to delete AnimalVeterinario : {}", id);
        animalVeterinarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
