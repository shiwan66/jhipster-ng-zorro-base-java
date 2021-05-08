package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnimalTipoDeVacina;
import br.com.ngzorro.repository.AnimalTipoDeVacinaRepository;
import br.com.ngzorro.service.dto.AnimalTipoDeVacinaDTO;
import br.com.ngzorro.service.mapper.AnimalTipoDeVacinaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalTipoDeVacina}.
 */
@Service
@Transactional
public class AnimalTipoDeVacinaService {

    private final Logger log = LoggerFactory.getLogger(AnimalTipoDeVacinaService.class);

    private final AnimalTipoDeVacinaRepository animalTipoDeVacinaRepository;

    private final AnimalTipoDeVacinaMapper animalTipoDeVacinaMapper;

    public AnimalTipoDeVacinaService(AnimalTipoDeVacinaRepository animalTipoDeVacinaRepository, AnimalTipoDeVacinaMapper animalTipoDeVacinaMapper) {
        this.animalTipoDeVacinaRepository = animalTipoDeVacinaRepository;
        this.animalTipoDeVacinaMapper = animalTipoDeVacinaMapper;
    }

    /**
     * Save a animalTipoDeVacina.
     *
     * @param animalTipoDeVacinaDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalTipoDeVacinaDTO save(AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO) {
        log.debug("Request to save AnimalTipoDeVacina : {}", animalTipoDeVacinaDTO);
        AnimalTipoDeVacina animalTipoDeVacina = animalTipoDeVacinaMapper.toEntity(animalTipoDeVacinaDTO);
        animalTipoDeVacina = animalTipoDeVacinaRepository.save(animalTipoDeVacina);
        return animalTipoDeVacinaMapper.toDto(animalTipoDeVacina);
    }

    /**
     * Get all the animalTipoDeVacinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalTipoDeVacinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalTipoDeVacinas");
        return animalTipoDeVacinaRepository.findAll(pageable)
            .map(animalTipoDeVacinaMapper::toDto);
    }


    /**
     * Get one animalTipoDeVacina by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalTipoDeVacinaDTO> findOne(Long id) {
        log.debug("Request to get AnimalTipoDeVacina : {}", id);
        return animalTipoDeVacinaRepository.findById(id)
            .map(animalTipoDeVacinaMapper::toDto);
    }

    /**
     * Delete the animalTipoDeVacina by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalTipoDeVacina : {}", id);
        animalTipoDeVacinaRepository.deleteById(id);
    }
}
