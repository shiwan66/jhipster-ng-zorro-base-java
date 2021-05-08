package br.com.ngzorro.service;

import br.com.ngzorro.domain.MovimentacaoDeEstoque;
import br.com.ngzorro.repository.MovimentacaoDeEstoqueRepository;
import br.com.ngzorro.service.dto.MovimentacaoDeEstoqueDTO;
import br.com.ngzorro.service.mapper.MovimentacaoDeEstoqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MovimentacaoDeEstoque}.
 */
@Service
@Transactional
public class MovimentacaoDeEstoqueService {

    private final Logger log = LoggerFactory.getLogger(MovimentacaoDeEstoqueService.class);

    private final MovimentacaoDeEstoqueRepository movimentacaoDeEstoqueRepository;

    private final MovimentacaoDeEstoqueMapper movimentacaoDeEstoqueMapper;

    public MovimentacaoDeEstoqueService(MovimentacaoDeEstoqueRepository movimentacaoDeEstoqueRepository, MovimentacaoDeEstoqueMapper movimentacaoDeEstoqueMapper) {
        this.movimentacaoDeEstoqueRepository = movimentacaoDeEstoqueRepository;
        this.movimentacaoDeEstoqueMapper = movimentacaoDeEstoqueMapper;
    }

    /**
     * Save a movimentacaoDeEstoque.
     *
     * @param movimentacaoDeEstoqueDTO the entity to save.
     * @return the persisted entity.
     */
    public MovimentacaoDeEstoqueDTO save(MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO) {
        log.debug("Request to save MovimentacaoDeEstoque : {}", movimentacaoDeEstoqueDTO);
        MovimentacaoDeEstoque movimentacaoDeEstoque = movimentacaoDeEstoqueMapper.toEntity(movimentacaoDeEstoqueDTO);
        movimentacaoDeEstoque = movimentacaoDeEstoqueRepository.save(movimentacaoDeEstoque);
        return movimentacaoDeEstoqueMapper.toDto(movimentacaoDeEstoque);
    }

    /**
     * Get all the movimentacaoDeEstoques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MovimentacaoDeEstoqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovimentacaoDeEstoques");
        return movimentacaoDeEstoqueRepository.findAll(pageable)
            .map(movimentacaoDeEstoqueMapper::toDto);
    }


    /**
     * Get one movimentacaoDeEstoque by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovimentacaoDeEstoqueDTO> findOne(Long id) {
        log.debug("Request to get MovimentacaoDeEstoque : {}", id);
        return movimentacaoDeEstoqueRepository.findById(id)
            .map(movimentacaoDeEstoqueMapper::toDto);
    }

    /**
     * Delete the movimentacaoDeEstoque by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovimentacaoDeEstoque : {}", id);
        movimentacaoDeEstoqueRepository.deleteById(id);
    }
}
