package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnimalCio;
import br.com.ngzorro.repository.AnimalCioRepository;
import br.com.ngzorro.service.dto.AnimalCioDTO;
import br.com.ngzorro.service.mapper.AnimalCioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalCio}.
 */
@Service
@Transactional
public class AnimalCioService {

    private final Logger log = LoggerFactory.getLogger(AnimalCioService.class);

    private final AnimalCioRepository animalCioRepository;

    private final AnimalCioMapper animalCioMapper;

    public AnimalCioService(AnimalCioRepository animalCioRepository, AnimalCioMapper animalCioMapper) {
        this.animalCioRepository = animalCioRepository;
        this.animalCioMapper = animalCioMapper;
    }

    /**
     * Save a animalCio.
     *
     * @param animalCioDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalCioDTO save(AnimalCioDTO animalCioDTO) {
        log.debug("Request to save AnimalCio : {}", animalCioDTO);
        AnimalCio animalCio = animalCioMapper.toEntity(animalCioDTO);
        animalCio = animalCioRepository.save(animalCio);
        return animalCioMapper.toDto(animalCio);
    }

    /**
     * Get all the animalCios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalCioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalCios");
        return animalCioRepository.findAll(pageable)
            .map(animalCioMapper::toDto);
    }


    /**
     * Get one animalCio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalCioDTO> findOne(Long id) {
        log.debug("Request to get AnimalCio : {}", id);
        return animalCioRepository.findById(id)
            .map(animalCioMapper::toDto);
    }

    /**
     * Delete the animalCio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalCio : {}", id);
        animalCioRepository.deleteById(id);
    }
}
