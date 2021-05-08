package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnimalCarrapaticida;
import br.com.ngzorro.repository.AnimalCarrapaticidaRepository;
import br.com.ngzorro.service.dto.AnimalCarrapaticidaDTO;
import br.com.ngzorro.service.mapper.AnimalCarrapaticidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalCarrapaticida}.
 */
@Service
@Transactional
public class AnimalCarrapaticidaService {

    private final Logger log = LoggerFactory.getLogger(AnimalCarrapaticidaService.class);

    private final AnimalCarrapaticidaRepository animalCarrapaticidaRepository;

    private final AnimalCarrapaticidaMapper animalCarrapaticidaMapper;

    public AnimalCarrapaticidaService(AnimalCarrapaticidaRepository animalCarrapaticidaRepository, AnimalCarrapaticidaMapper animalCarrapaticidaMapper) {
        this.animalCarrapaticidaRepository = animalCarrapaticidaRepository;
        this.animalCarrapaticidaMapper = animalCarrapaticidaMapper;
    }

    /**
     * Save a animalCarrapaticida.
     *
     * @param animalCarrapaticidaDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalCarrapaticidaDTO save(AnimalCarrapaticidaDTO animalCarrapaticidaDTO) {
        log.debug("Request to save AnimalCarrapaticida : {}", animalCarrapaticidaDTO);
        AnimalCarrapaticida animalCarrapaticida = animalCarrapaticidaMapper.toEntity(animalCarrapaticidaDTO);
        animalCarrapaticida = animalCarrapaticidaRepository.save(animalCarrapaticida);
        return animalCarrapaticidaMapper.toDto(animalCarrapaticida);
    }

    /**
     * Get all the animalCarrapaticidas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalCarrapaticidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalCarrapaticidas");
        return animalCarrapaticidaRepository.findAll(pageable)
            .map(animalCarrapaticidaMapper::toDto);
    }


    /**
     * Get one animalCarrapaticida by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalCarrapaticidaDTO> findOne(Long id) {
        log.debug("Request to get AnimalCarrapaticida : {}", id);
        return animalCarrapaticidaRepository.findById(id)
            .map(animalCarrapaticidaMapper::toDto);
    }

    /**
     * Delete the animalCarrapaticida by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalCarrapaticida : {}", id);
        animalCarrapaticidaRepository.deleteById(id);
    }
}
