package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnimalAlteracao;
import br.com.ngzorro.repository.AnimalAlteracaoRepository;
import br.com.ngzorro.service.dto.AnimalAlteracaoDTO;
import br.com.ngzorro.service.mapper.AnimalAlteracaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalAlteracao}.
 */
@Service
@Transactional
public class AnimalAlteracaoService {

    private final Logger log = LoggerFactory.getLogger(AnimalAlteracaoService.class);

    private final AnimalAlteracaoRepository animalAlteracaoRepository;

    private final AnimalAlteracaoMapper animalAlteracaoMapper;

    public AnimalAlteracaoService(AnimalAlteracaoRepository animalAlteracaoRepository, AnimalAlteracaoMapper animalAlteracaoMapper) {
        this.animalAlteracaoRepository = animalAlteracaoRepository;
        this.animalAlteracaoMapper = animalAlteracaoMapper;
    }

    /**
     * Save a animalAlteracao.
     *
     * @param animalAlteracaoDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalAlteracaoDTO save(AnimalAlteracaoDTO animalAlteracaoDTO) {
        log.debug("Request to save AnimalAlteracao : {}", animalAlteracaoDTO);
        AnimalAlteracao animalAlteracao = animalAlteracaoMapper.toEntity(animalAlteracaoDTO);
        animalAlteracao = animalAlteracaoRepository.save(animalAlteracao);
        return animalAlteracaoMapper.toDto(animalAlteracao);
    }

    /**
     * Get all the animalAlteracaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalAlteracaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalAlteracaos");
        return animalAlteracaoRepository.findAll(pageable)
            .map(animalAlteracaoMapper::toDto);
    }


    /**
     * Get one animalAlteracao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalAlteracaoDTO> findOne(Long id) {
        log.debug("Request to get AnimalAlteracao : {}", id);
        return animalAlteracaoRepository.findById(id)
            .map(animalAlteracaoMapper::toDto);
    }

    /**
     * Delete the animalAlteracao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalAlteracao : {}", id);
        animalAlteracaoRepository.deleteById(id);
    }
}
