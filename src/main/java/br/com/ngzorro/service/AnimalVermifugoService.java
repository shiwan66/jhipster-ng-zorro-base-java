package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnimalVermifugo;
import br.com.ngzorro.repository.AnimalVermifugoRepository;
import br.com.ngzorro.service.dto.AnimalVermifugoDTO;
import br.com.ngzorro.service.mapper.AnimalVermifugoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalVermifugo}.
 */
@Service
@Transactional
public class AnimalVermifugoService {

    private final Logger log = LoggerFactory.getLogger(AnimalVermifugoService.class);

    private final AnimalVermifugoRepository animalVermifugoRepository;

    private final AnimalVermifugoMapper animalVermifugoMapper;

    public AnimalVermifugoService(AnimalVermifugoRepository animalVermifugoRepository, AnimalVermifugoMapper animalVermifugoMapper) {
        this.animalVermifugoRepository = animalVermifugoRepository;
        this.animalVermifugoMapper = animalVermifugoMapper;
    }

    /**
     * Save a animalVermifugo.
     *
     * @param animalVermifugoDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalVermifugoDTO save(AnimalVermifugoDTO animalVermifugoDTO) {
        log.debug("Request to save AnimalVermifugo : {}", animalVermifugoDTO);
        AnimalVermifugo animalVermifugo = animalVermifugoMapper.toEntity(animalVermifugoDTO);
        animalVermifugo = animalVermifugoRepository.save(animalVermifugo);
        return animalVermifugoMapper.toDto(animalVermifugo);
    }

    /**
     * Get all the animalVermifugos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalVermifugoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalVermifugos");
        return animalVermifugoRepository.findAll(pageable)
            .map(animalVermifugoMapper::toDto);
    }


    /**
     * Get one animalVermifugo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalVermifugoDTO> findOne(Long id) {
        log.debug("Request to get AnimalVermifugo : {}", id);
        return animalVermifugoRepository.findById(id)
            .map(animalVermifugoMapper::toDto);
    }

    /**
     * Delete the animalVermifugo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalVermifugo : {}", id);
        animalVermifugoRepository.deleteById(id);
    }
}
