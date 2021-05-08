package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnimalTipoDeAlteracao;
import br.com.ngzorro.repository.AnimalTipoDeAlteracaoRepository;
import br.com.ngzorro.service.dto.AnimalTipoDeAlteracaoDTO;
import br.com.ngzorro.service.mapper.AnimalTipoDeAlteracaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalTipoDeAlteracao}.
 */
@Service
@Transactional
public class AnimalTipoDeAlteracaoService {

    private final Logger log = LoggerFactory.getLogger(AnimalTipoDeAlteracaoService.class);

    private final AnimalTipoDeAlteracaoRepository animalTipoDeAlteracaoRepository;

    private final AnimalTipoDeAlteracaoMapper animalTipoDeAlteracaoMapper;

    public AnimalTipoDeAlteracaoService(AnimalTipoDeAlteracaoRepository animalTipoDeAlteracaoRepository, AnimalTipoDeAlteracaoMapper animalTipoDeAlteracaoMapper) {
        this.animalTipoDeAlteracaoRepository = animalTipoDeAlteracaoRepository;
        this.animalTipoDeAlteracaoMapper = animalTipoDeAlteracaoMapper;
    }

    /**
     * Save a animalTipoDeAlteracao.
     *
     * @param animalTipoDeAlteracaoDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalTipoDeAlteracaoDTO save(AnimalTipoDeAlteracaoDTO animalTipoDeAlteracaoDTO) {
        log.debug("Request to save AnimalTipoDeAlteracao : {}", animalTipoDeAlteracaoDTO);
        AnimalTipoDeAlteracao animalTipoDeAlteracao = animalTipoDeAlteracaoMapper.toEntity(animalTipoDeAlteracaoDTO);
        animalTipoDeAlteracao = animalTipoDeAlteracaoRepository.save(animalTipoDeAlteracao);
        return animalTipoDeAlteracaoMapper.toDto(animalTipoDeAlteracao);
    }

    /**
     * Get all the animalTipoDeAlteracaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalTipoDeAlteracaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalTipoDeAlteracaos");
        return animalTipoDeAlteracaoRepository.findAll(pageable)
            .map(animalTipoDeAlteracaoMapper::toDto);
    }


    /**
     * Get one animalTipoDeAlteracao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalTipoDeAlteracaoDTO> findOne(Long id) {
        log.debug("Request to get AnimalTipoDeAlteracao : {}", id);
        return animalTipoDeAlteracaoRepository.findById(id)
            .map(animalTipoDeAlteracaoMapper::toDto);
    }

    /**
     * Delete the animalTipoDeAlteracao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalTipoDeAlteracao : {}", id);
        animalTipoDeAlteracaoRepository.deleteById(id);
    }
}
