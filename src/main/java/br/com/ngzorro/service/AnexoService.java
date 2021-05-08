package br.com.ngzorro.service;

import br.com.ngzorro.domain.Anexo;
import br.com.ngzorro.repository.AnexoRepository;
import br.com.ngzorro.service.dto.AnexoDTO;
import br.com.ngzorro.service.mapper.AnexoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Anexo}.
 */
@Service
@Transactional
public class AnexoService {

    private final Logger log = LoggerFactory.getLogger(AnexoService.class);

    private final AnexoRepository anexoRepository;

    private final AnexoMapper anexoMapper;

    public AnexoService(AnexoRepository anexoRepository, AnexoMapper anexoMapper) {
        this.anexoRepository = anexoRepository;
        this.anexoMapper = anexoMapper;
    }

    /**
     * Save a anexo.
     *
     * @param anexoDTO the entity to save.
     * @return the persisted entity.
     */
    public AnexoDTO save(AnexoDTO anexoDTO) {
        log.debug("Request to save Anexo : {}", anexoDTO);
        Anexo anexo = anexoMapper.toEntity(anexoDTO);
        anexo = anexoRepository.save(anexo);
        return anexoMapper.toDto(anexo);
    }

    /**
     * Get all the anexos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnexoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Anexos");
        return anexoRepository.findAll(pageable)
            .map(anexoMapper::toDto);
    }


    /**
     * Get one anexo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoDTO> findOne(Long id) {
        log.debug("Request to get Anexo : {}", id);
        return anexoRepository.findById(id)
            .map(anexoMapper::toDto);
    }

    /**
     * Delete the anexo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Anexo : {}", id);
        anexoRepository.deleteById(id);
    }
}
