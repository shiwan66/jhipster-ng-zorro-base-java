package br.com.ngzorro.service;

import br.com.ngzorro.domain.ModeloAtividade;
import br.com.ngzorro.repository.ModeloAtividadeRepository;
import br.com.ngzorro.service.dto.ModeloAtividadeDTO;
import br.com.ngzorro.service.mapper.ModeloAtividadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ModeloAtividade}.
 */
@Service
@Transactional
public class ModeloAtividadeService {

    private final Logger log = LoggerFactory.getLogger(ModeloAtividadeService.class);

    private final ModeloAtividadeRepository modeloAtividadeRepository;

    private final ModeloAtividadeMapper modeloAtividadeMapper;

    public ModeloAtividadeService(ModeloAtividadeRepository modeloAtividadeRepository, ModeloAtividadeMapper modeloAtividadeMapper) {
        this.modeloAtividadeRepository = modeloAtividadeRepository;
        this.modeloAtividadeMapper = modeloAtividadeMapper;
    }

    /**
     * Save a modeloAtividade.
     *
     * @param modeloAtividadeDTO the entity to save.
     * @return the persisted entity.
     */
    public ModeloAtividadeDTO save(ModeloAtividadeDTO modeloAtividadeDTO) {
        log.debug("Request to save ModeloAtividade : {}", modeloAtividadeDTO);
        ModeloAtividade modeloAtividade = modeloAtividadeMapper.toEntity(modeloAtividadeDTO);
        modeloAtividade = modeloAtividadeRepository.save(modeloAtividade);
        return modeloAtividadeMapper.toDto(modeloAtividade);
    }

    /**
     * Get all the modeloAtividades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ModeloAtividadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModeloAtividades");
        return modeloAtividadeRepository.findAll(pageable)
            .map(modeloAtividadeMapper::toDto);
    }

    /**
     * Get all the modeloAtividades with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ModeloAtividadeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return modeloAtividadeRepository.findAllWithEagerRelationships(pageable).map(modeloAtividadeMapper::toDto);
    }
    

    /**
     * Get one modeloAtividade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModeloAtividadeDTO> findOne(Long id) {
        log.debug("Request to get ModeloAtividade : {}", id);
        return modeloAtividadeRepository.findOneWithEagerRelationships(id)
            .map(modeloAtividadeMapper::toDto);
    }

    /**
     * Delete the modeloAtividade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ModeloAtividade : {}", id);
        modeloAtividadeRepository.deleteById(id);
    }
}
