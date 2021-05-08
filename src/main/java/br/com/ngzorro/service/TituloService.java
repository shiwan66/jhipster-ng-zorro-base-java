package br.com.ngzorro.service;

import br.com.ngzorro.domain.Titulo;
import br.com.ngzorro.repository.TituloRepository;
import br.com.ngzorro.service.dto.TituloDTO;
import br.com.ngzorro.service.mapper.TituloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Titulo}.
 */
@Service
@Transactional
public class TituloService {

    private final Logger log = LoggerFactory.getLogger(TituloService.class);

    private final TituloRepository tituloRepository;

    private final TituloMapper tituloMapper;

    public TituloService(TituloRepository tituloRepository, TituloMapper tituloMapper) {
        this.tituloRepository = tituloRepository;
        this.tituloMapper = tituloMapper;
    }

    /**
     * Save a titulo.
     *
     * @param tituloDTO the entity to save.
     * @return the persisted entity.
     */
    public TituloDTO save(TituloDTO tituloDTO) {
        log.debug("Request to save Titulo : {}", tituloDTO);
        Titulo titulo = tituloMapper.toEntity(tituloDTO);
        titulo = tituloRepository.save(titulo);
        return tituloMapper.toDto(titulo);
    }

    /**
     * Get all the titulos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TituloDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Titulos");
        return tituloRepository.findAll(pageable)
            .map(tituloMapper::toDto);
    }


    /**
     * Get one titulo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TituloDTO> findOne(Long id) {
        log.debug("Request to get Titulo : {}", id);
        return tituloRepository.findById(id)
            .map(tituloMapper::toDto);
    }

    /**
     * Delete the titulo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Titulo : {}", id);
        tituloRepository.deleteById(id);
    }
}
