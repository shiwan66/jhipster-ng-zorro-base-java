package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnimalVacina;
import br.com.ngzorro.repository.AnimalVacinaRepository;
import br.com.ngzorro.service.dto.AnimalVacinaDTO;
import br.com.ngzorro.service.mapper.AnimalVacinaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalVacina}.
 */
@Service
@Transactional
public class AnimalVacinaService {

    private final Logger log = LoggerFactory.getLogger(AnimalVacinaService.class);

    private final AnimalVacinaRepository animalVacinaRepository;

    private final AnimalVacinaMapper animalVacinaMapper;

    public AnimalVacinaService(AnimalVacinaRepository animalVacinaRepository, AnimalVacinaMapper animalVacinaMapper) {
        this.animalVacinaRepository = animalVacinaRepository;
        this.animalVacinaMapper = animalVacinaMapper;
    }

    /**
     * Save a animalVacina.
     *
     * @param animalVacinaDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalVacinaDTO save(AnimalVacinaDTO animalVacinaDTO) {
        log.debug("Request to save AnimalVacina : {}", animalVacinaDTO);
        AnimalVacina animalVacina = animalVacinaMapper.toEntity(animalVacinaDTO);
        animalVacina = animalVacinaRepository.save(animalVacina);
        return animalVacinaMapper.toDto(animalVacina);
    }

    /**
     * Get all the animalVacinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalVacinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalVacinas");
        return animalVacinaRepository.findAll(pageable)
            .map(animalVacinaMapper::toDto);
    }


    /**
     * Get one animalVacina by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalVacinaDTO> findOne(Long id) {
        log.debug("Request to get AnimalVacina : {}", id);
        return animalVacinaRepository.findById(id)
            .map(animalVacinaMapper::toDto);
    }

    /**
     * Delete the animalVacina by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalVacina : {}", id);
        animalVacinaRepository.deleteById(id);
    }
}
