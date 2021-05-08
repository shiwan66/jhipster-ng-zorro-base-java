package br.com.ngzorro.web.rest;

import br.com.ngzorro.service.EnderecoService;
import br.com.ngzorro.web.rest.errors.BadRequestAlertException;
import br.com.ngzorro.service.dto.EnderecoDTO;
import br.com.ngzorro.service.dto.EnderecoCriteria;
import br.com.ngzorro.service.EnderecoQueryService;

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
 * REST controller for managing {@link br.com.ngzorro.domain.Endereco}.
 */
@RestController
@RequestMapping("/api")
public class EnderecoResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoResource.class);

    private static final String ENTITY_NAME = "endereco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnderecoService enderecoService;

    private final EnderecoQueryService enderecoQueryService;

    public EnderecoResource(EnderecoService enderecoService, EnderecoQueryService enderecoQueryService) {
        this.enderecoService = enderecoService;
        this.enderecoQueryService = enderecoQueryService;
    }

    /**
     * {@code POST  /enderecos} : Create a new endereco.
     *
     * @param enderecoDTO the enderecoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enderecoDTO, or with status {@code 400 (Bad Request)} if the endereco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoDTO> createEndereco(@RequestBody EnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("REST request to save Endereco : {}", enderecoDTO);
        if (enderecoDTO.getId() != null) {
            throw new BadRequestAlertException("A new endereco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnderecoDTO result = enderecoService.save(enderecoDTO);
        return ResponseEntity.created(new URI("/api/enderecos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enderecos} : Updates an existing endereco.
     *
     * @param enderecoDTO the enderecoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoDTO,
     * or with status {@code 400 (Bad Request)} if the enderecoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enderecoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enderecos")
    public ResponseEntity<EnderecoDTO> updateEndereco(@RequestBody EnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("REST request to update Endereco : {}", enderecoDTO);
        if (enderecoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnderecoDTO result = enderecoService.save(enderecoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enderecos} : get all the enderecos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enderecos in body.
     */
    @GetMapping("/enderecos")
    public ResponseEntity<List<EnderecoDTO>> getAllEnderecos(EnderecoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Enderecos by criteria: {}", criteria);
        Page<EnderecoDTO> page = enderecoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /enderecos/count} : count all the enderecos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/enderecos/count")
    public ResponseEntity<Long> countEnderecos(EnderecoCriteria criteria) {
        log.debug("REST request to count Enderecos by criteria: {}", criteria);
        return ResponseEntity.ok().body(enderecoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enderecos/:id} : get the "id" endereco.
     *
     * @param id the id of the enderecoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enderecoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enderecos/{id}")
    public ResponseEntity<EnderecoDTO> getEndereco(@PathVariable Long id) {
        log.debug("REST request to get Endereco : {}", id);
        Optional<EnderecoDTO> enderecoDTO = enderecoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enderecoDTO);
    }

    /**
     * {@code DELETE  /enderecos/:id} : delete the "id" endereco.
     *
     * @param id the id of the enderecoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enderecos/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        log.debug("REST request to delete Endereco : {}", id);
        enderecoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
