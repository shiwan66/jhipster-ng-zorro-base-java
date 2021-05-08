package br.com.ngzorro.service;

import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.repository.AnimalRepository;
import br.com.ngzorro.service.dto.AnimalDTO;
import br.com.ngzorro.service.mapper.AnimalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Animal}.
 */
@Service
@Transactional
public class AnimalService {

    private final Logger log = LoggerFactory.getLogger(AnimalService.class);

    private final AnimalRepository animalRepository;

    private final AnimalMapper animalMapper;

    public AnimalService(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    /**
     * Save a animal.
     *
     * @param animalDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalDTO save(AnimalDTO animalDTO) {
        log.debug("Request to save Animal : {}", animalDTO);
        Animal animal = animalMapper.toEntity(animalDTO);
        animal = animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }

    /**
     * Get all the animals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Animals");
        return animalRepository.findAll(pageable)
            .map(animalMapper::toDto);
    }


    /**
     * Get one animal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalDTO> findOne(Long id) {
        log.debug("Request to get Animal : {}", id);
        return animalRepository.findById(id)
            .map(animalMapper::toDto);
    }

    /**
     * Delete the animal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Animal : {}", id);
        animalRepository.deleteById(id);
    }
}
