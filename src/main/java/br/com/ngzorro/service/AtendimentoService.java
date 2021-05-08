package br.com.ngzorro.service;

import br.com.ngzorro.domain.Atendimento;
import br.com.ngzorro.repository.AtendimentoRepository;
import br.com.ngzorro.service.dto.AtendimentoDTO;
import br.com.ngzorro.service.mapper.AtendimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Atendimento}.
 */
@Service
@Transactional
public class AtendimentoService {

    private final Logger log = LoggerFactory.getLogger(AtendimentoService.class);

    private final AtendimentoRepository atendimentoRepository;

    private final AtendimentoMapper atendimentoMapper;

    public AtendimentoService(AtendimentoRepository atendimentoRepository, AtendimentoMapper atendimentoMapper) {
        this.atendimentoRepository = atendimentoRepository;
        this.atendimentoMapper = atendimentoMapper;
    }

    /**
     * Save a atendimento.
     *
     * @param atendimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public AtendimentoDTO save(AtendimentoDTO atendimentoDTO) {
        log.debug("Request to save Atendimento : {}", atendimentoDTO);
        Atendimento atendimento = atendimentoMapper.toEntity(atendimentoDTO);
        atendimento = atendimentoRepository.save(atendimento);
        return atendimentoMapper.toDto(atendimento);
    }

    /**
     * Get all the atendimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AtendimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Atendimentos");
        return atendimentoRepository.findAll(pageable)
            .map(atendimentoMapper::toDto);
    }


    /**
     * Get one atendimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtendimentoDTO> findOne(Long id) {
        log.debug("Request to get Atendimento : {}", id);
        return atendimentoRepository.findById(id)
            .map(atendimentoMapper::toDto);
    }

    /**
     * Delete the atendimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Atendimento : {}", id);
        atendimentoRepository.deleteById(id);
    }
}
