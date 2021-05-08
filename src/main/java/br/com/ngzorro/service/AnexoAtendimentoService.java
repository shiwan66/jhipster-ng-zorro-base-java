package br.com.ngzorro.service;

import br.com.ngzorro.domain.AnexoAtendimento;
import br.com.ngzorro.repository.AnexoAtendimentoRepository;
import br.com.ngzorro.service.dto.AnexoAtendimentoDTO;
import br.com.ngzorro.service.mapper.AnexoAtendimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnexoAtendimento}.
 */
@Service
@Transactional
public class AnexoAtendimentoService {

    private final Logger log = LoggerFactory.getLogger(AnexoAtendimentoService.class);

    private final AnexoAtendimentoRepository anexoAtendimentoRepository;

    private final AnexoAtendimentoMapper anexoAtendimentoMapper;

    public AnexoAtendimentoService(AnexoAtendimentoRepository anexoAtendimentoRepository, AnexoAtendimentoMapper anexoAtendimentoMapper) {
        this.anexoAtendimentoRepository = anexoAtendimentoRepository;
        this.anexoAtendimentoMapper = anexoAtendimentoMapper;
    }

    /**
     * Save a anexoAtendimento.
     *
     * @param anexoAtendimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public AnexoAtendimentoDTO save(AnexoAtendimentoDTO anexoAtendimentoDTO) {
        log.debug("Request to save AnexoAtendimento : {}", anexoAtendimentoDTO);
        AnexoAtendimento anexoAtendimento = anexoAtendimentoMapper.toEntity(anexoAtendimentoDTO);
        anexoAtendimento = anexoAtendimentoRepository.save(anexoAtendimento);
        return anexoAtendimentoMapper.toDto(anexoAtendimento);
    }

    /**
     * Get all the anexoAtendimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnexoAtendimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnexoAtendimentos");
        return anexoAtendimentoRepository.findAll(pageable)
            .map(anexoAtendimentoMapper::toDto);
    }


    /**
     * Get one anexoAtendimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoAtendimentoDTO> findOne(Long id) {
        log.debug("Request to get AnexoAtendimento : {}", id);
        return anexoAtendimentoRepository.findById(id)
            .map(anexoAtendimentoMapper::toDto);
    }

    /**
     * Delete the anexoAtendimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoAtendimento : {}", id);
        anexoAtendimentoRepository.deleteById(id);
    }
}
