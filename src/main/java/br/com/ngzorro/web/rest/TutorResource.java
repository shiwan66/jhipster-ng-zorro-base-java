package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.TutorService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.TutorDTO;
import br.com.ngzorro.service.dto.TutorCriteria;
import br.com.ngzorro.service.TutorQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.ngzorro.domain.Tutor}.
 */
@RestController
@RequestMapping("/api")
public class TutorResource {

    private final Logger log = LoggerFactory.getLogger(TutorResource.class);

    private static final String ENTITY_NAME = "tutor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TutorService tutorService;

    private final TutorQueryService tutorQueryService;

    public TutorResource(TutorService tutorService, TutorQueryService tutorQueryService) {
        this.tutorService = tutorService;
        this.tutorQueryService = tutorQueryService;
    }

    /**
     * {@code POST  /tutors} : Create a new tutor.
     *
     * @param tutorDTO the tutorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tutorDTO, or with status {@code 400 (Bad Request)} if the tutor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tutors")
    public ResponseEntity<TutorDTO> createTutor(@Valid @RequestBody TutorDTO tutorDTO) throws URISyntaxException {
        log.debug("REST request to save Tutor : {}", tutorDTO);
        if (tutorDTO.getId() != null) {
            throw new BadRequestAlertException("A new tutor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TutorDTO result = tutorService.save(tutorDTO);
        return ResponseEntity.created(new URI("/api/tutors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tutors} : Updates an existing tutor.
     *
     * @param tutorDTO the tutorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorDTO,
     * or with status {@code 400 (Bad Request)} if the tutorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tutorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tutors")
    public ResponseEntity<TutorDTO> updateTutor(@Valid @RequestBody TutorDTO tutorDTO) throws URISyntaxException {
        log.debug("REST request to update Tutor : {}", tutorDTO);
        if (tutorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TutorDTO result = tutorService.save(tutorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tutors} : get all the tutors.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tutors in body.
     */
    @GetMapping("/tutors")
    public ResponseEntity<List<TutorDTO>> getAllTutors(TutorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tutors by criteria: {}", criteria);
        Page<TutorDTO> page = tutorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tutors/count} : count all the tutors.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tutors/count")
    public ResponseEntity<Long> countTutors(TutorCriteria criteria) {
        log.debug("REST request to count Tutors by criteria: {}", criteria);
        return ResponseEntity.ok().body(tutorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tutors/:id} : get the "id" tutor.
     *
     * @param id the id of the tutorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tutorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tutors/{id}")
    public ResponseEntity<TutorDTO> getTutor(@PathVariable Long id) {
        log.debug("REST request to get Tutor : {}", id);
        Optional<TutorDTO> tutorDTO = tutorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tutorDTO);
    }

    /**
     * {@code DELETE  /tutors/:id} : delete the "id" tutor.
     *
     * @param id the id of the tutorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tutors/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
        log.debug("REST request to delete Tutor : {}", id);
        tutorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
