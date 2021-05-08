package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnimalObservacao;
import br.com.ngzorro.repository.AnimalObservacaoRepository;
import br.com.ngzorro.service.dto.AnimalObservacaoDTO;
import br.com.ngzorro.service.mapper.AnimalObservacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalObservacao}.
 */
@Service
@Transactional
public class AnimalObservacaoService {

    private final Logger log = LoggerFactory.getLogger(AnimalObservacaoService.class);

    private final AnimalObservacaoRepository animalObservacaoRepository;

    private final AnimalObservacaoMapper animalObservacaoMapper;

    public AnimalObservacaoService(AnimalObservacaoRepository animalObservacaoRepository, AnimalObservacaoMapper animalObservacaoMapper) {
        this.animalObservacaoRepository = animalObservacaoRepository;
        this.animalObservacaoMapper = animalObservacaoMapper;
    }

    /**
     * Save a animalObservacao.
     *
     * @param animalObservacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalObservacaoDTO save(AnimalObservacaoDTO animalObservacaoDTO) {
        log.debug("Request to save AnimalObservacao : {}", animalObservacaoDTO);
        AnimalObservacao animalObservacao = animalObservacaoMapper.toEntity(animalObservacaoDTO);
        animalObservacao = animalObservacaoRepository.save(animalObservacao);
        return animalObservacaoMapper.toDto(animalObservacao);
    }

    /**
     * Get all the animalObservacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalObservacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalObservacaos");
        return animalObservacaoRepository.findAll(pageable)
            .map(animalObservacaoMapper::toDto);
    }


    /**
     * Get one animalObservacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalObservacaoDTO> findOne(Long id) {
        log.debug("Request to get AnimalObservacao : {}", id);
        return animalObservacaoRepository.findById(id)
            .map(animalObservacaoMapper::toDto);
    }

    /**
     * Delete the animalObservacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalObservacao : {}", id);
        animalObservacaoRepository.deleteById(id);
    }
}
