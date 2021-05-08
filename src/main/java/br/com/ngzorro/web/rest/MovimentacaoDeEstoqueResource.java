package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.MovimentacaoDeEstoqueService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.MovimentacaoDeEstoqueDTO;
import br.com.ngzorro.service.dto.MovimentacaoDeEstoqueCriteria;
import br.com.ngzorro.service.MovimentacaoDeEstoqueQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.MovimentacaoDeEstoque}.
 */
@RestController
@RequestMapping("/api")
public class MovimentacaoDeEstoqueResource {

    private final Logger log = LoggerFactory.getLogger(MovimentacaoDeEstoqueResource.class);

    private static final String ENTITY_NAME = "movimentacaoDeEstoque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovimentacaoDeEstoqueService movimentacaoDeEstoqueService;

    private final MovimentacaoDeEstoqueQueryService movimentacaoDeEstoqueQueryService;

    public MovimentacaoDeEstoqueResource(MovimentacaoDeEstoqueService movimentacaoDeEstoqueService, MovimentacaoDeEstoqueQueryService movimentacaoDeEstoqueQueryService) {
        this.movimentacaoDeEstoqueService = movimentacaoDeEstoqueService;
        this.movimentacaoDeEstoqueQueryService = movimentacaoDeEstoqueQueryService;
    }

    /**
     * {@code POST  /movimentacao-de-estoques} : Create a new movimentacaoDeEstoque.
     *
     * @param movimentacaoDeEstoqueDTO the movimentacaoDeEstoqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movimentacaoDeEstoqueDTO, or with status {@code 400 (Bad Request)} if the movimentacaoDeEstoque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movimentacao-de-estoques")
    public ResponseEntity<MovimentacaoDeEstoqueDTO> createMovimentacaoDeEstoque(@RequestBody MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO) throws URISyntaxException {
        log.debug("REST request to save MovimentacaoDeEstoque : {}", movimentacaoDeEstoqueDTO);
        if (movimentacaoDeEstoqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new movimentacaoDeEstoque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovimentacaoDeEstoqueDTO result = movimentacaoDeEstoqueService.save(movimentacaoDeEstoqueDTO);
        return ResponseEntity.created(new URI("/api/movimentacao-de-estoques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movimentacao-de-estoques} : Updates an existing movimentacaoDeEstoque.
     *
     * @param movimentacaoDeEstoqueDTO the movimentacaoDeEstoqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movimentacaoDeEstoqueDTO,
     * or with status {@code 400 (Bad Request)} if the movimentacaoDeEstoqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movimentacaoDeEstoqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movimentacao-de-estoques")
    public ResponseEntity<MovimentacaoDeEstoqueDTO> updateMovimentacaoDeEstoque(@RequestBody MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO) throws URISyntaxException {
        log.debug("REST request to update MovimentacaoDeEstoque : {}", movimentacaoDeEstoqueDTO);
        if (movimentacaoDeEstoqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovimentacaoDeEstoqueDTO result = movimentacaoDeEstoqueService.save(movimentacaoDeEstoqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movimentacaoDeEstoqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movimentacao-de-estoques} : get all the movimentacaoDeEstoques.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movimentacaoDeEstoques in body.
     */
    @GetMapping("/movimentacao-de-estoques")
    public ResponseEntity<List<MovimentacaoDeEstoqueDTO>> getAllMovimentacaoDeEstoques(MovimentacaoDeEstoqueCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MovimentacaoDeEstoques by criteria: {}", criteria);
        Page<MovimentacaoDeEstoqueDTO> page = movimentacaoDeEstoqueQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /movimentacao-de-estoques/count} : count all the movimentacaoDeEstoques.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/movimentacao-de-estoques/count")
    public ResponseEntity<Long> countMovimentacaoDeEstoques(MovimentacaoDeEstoqueCriteria criteria) {
        log.debug("REST request to count MovimentacaoDeEstoques by criteria: {}", criteria);
        return ResponseEntity.ok().body(movimentacaoDeEstoqueQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /movimentacao-de-estoques/:id} : get the "id" movimentacaoDeEstoque.
     *
     * @param id the id of the movimentacaoDeEstoqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movimentacaoDeEstoqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movimentacao-de-estoques/{id}")
    public ResponseEntity<MovimentacaoDeEstoqueDTO> getMovimentacaoDeEstoque(@PathVariable Long id) {
        log.debug("REST request to get MovimentacaoDeEstoque : {}", id);
        Optional<MovimentacaoDeEstoqueDTO> movimentacaoDeEstoqueDTO = movimentacaoDeEstoqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movimentacaoDeEstoqueDTO);
    }

    /**
     * {@code DELETE  /movimentacao-de-estoques/:id} : delete the "id" movimentacaoDeEstoque.
     *
     * @param id the id of the movimentacaoDeEstoqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movimentacao-de-estoques/{id}")
    public ResponseEntity<Void> deleteMovimentacaoDeEstoque(@PathVariable Long id) {
        log.debug("REST request to delete MovimentacaoDeEstoque : {}", id);
        movimentacaoDeEstoqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
