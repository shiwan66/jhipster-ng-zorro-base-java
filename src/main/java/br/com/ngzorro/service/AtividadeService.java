package br.com.ngzorro.service;

import br.com.ngzorro.domain.Atividade;
import br.com.ngzorro.repository.AtividadeRepository;
import br.com.ngzorro.service.dto.AtividadeDTO;
import br.com.ngzorro.service.mapper.AtividadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Atividade}.
 */
@Service
@Transactional
public class AtividadeService {

    private final Logger log = LoggerFactory.getLogger(AtividadeService.class);

    private final AtividadeRepository atividadeRepository;

    private final AtividadeMapper atividadeMapper;

    public AtividadeService(AtividadeRepository atividadeRepository, AtividadeMapper atividadeMapper) {
        this.atividadeRepository = atividadeRepository;
        this.atividadeMapper = atividadeMapper;
    }

    /**
     * Save a atividade.
     *
     * @param atividadeDTO the entity to save.
     * @return the persisted entity.
     */
    public AtividadeDTO save(AtividadeDTO atividadeDTO) {
        log.debug("Request to save Atividade : {}", atividadeDTO);
        Atividade atividade = atividadeMapper.toEntity(atividadeDTO);
        atividade = atividadeRepository.save(atividade);
        return atividadeMapper.toDto(atividade);
    }

    /**
     * Get all the atividades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AtividadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Atividades");
        return atividadeRepository.findAll(pageable)
            .map(atividadeMapper::toDto);
    }


    /**
     * Get one atividade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtividadeDTO> findOne(Long id) {
        log.debug("Request to get Atividade : {}", id);
        return atividadeRepository.findById(id)
            .map(atividadeMapper::toDto);
    }

    /**
     * Delete the atividade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Atividade : {}", id);
        atividadeRepository.deleteById(id);
    }
}
