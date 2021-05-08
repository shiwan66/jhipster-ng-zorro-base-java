package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnimalVeterinario;
import br.com.ngzorro.repository.AnimalVeterinarioRepository;
import br.com.ngzorro.service.dto.AnimalVeterinarioDTO;
import br.com.ngzorro.service.mapper.AnimalVeterinarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalVeterinario}.
 */
@Service
@Transactional
public class AnimalVeterinarioService {

    private final Logger log = LoggerFactory.getLogger(AnimalVeterinarioService.class);

    private final AnimalVeterinarioRepository animalVeterinarioRepository;

    private final AnimalVeterinarioMapper animalVeterinarioMapper;

    public AnimalVeterinarioService(AnimalVeterinarioRepository animalVeterinarioRepository, AnimalVeterinarioMapper animalVeterinarioMapper) {
        this.animalVeterinarioRepository = animalVeterinarioRepository;
        this.animalVeterinarioMapper = animalVeterinarioMapper;
    }

    /**
     * Save a animalVeterinario.
     *
     * @param animalVeterinarioDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalVeterinarioDTO save(AnimalVeterinarioDTO animalVeterinarioDTO) {
        log.debug("Request to save AnimalVeterinario : {}", animalVeterinarioDTO);
        AnimalVeterinario animalVeterinario = animalVeterinarioMapper.toEntity(animalVeterinarioDTO);
        animalVeterinario = animalVeterinarioRepository.save(animalVeterinario);
        return animalVeterinarioMapper.toDto(animalVeterinario);
    }

    /**
     * Get all the animalVeterinarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalVeterinarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalVeterinarios");
        return animalVeterinarioRepository.findAll(pageable)
            .map(animalVeterinarioMapper::toDto);
    }


    /**
     * Get one animalVeterinario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalVeterinarioDTO> findOne(Long id) {
        log.debug("Request to get AnimalVeterinario : {}", id);
        return animalVeterinarioRepository.findById(id)
            .map(animalVeterinarioMapper::toDto);
    }

    /**
     * Delete the animalVeterinario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalVeterinario : {}", id);
        animalVeterinarioRepository.deleteById(id);
    }
}
