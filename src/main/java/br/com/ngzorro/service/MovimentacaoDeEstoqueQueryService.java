package br.com.ngzorro.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.com.ngzorro.domain.MovimentacaoDeEstoque;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.MovimentacaoDeEstoqueRepository;
import br.com.ngzorro.service.dto.MovimentacaoDeEstoqueCriteria;
import br.com.ngzorro.service.dto.MovimentacaoDeEstoqueDTO;
import br.com.ngzorro.service.mapper.MovimentacaoDeEstoqueMapper;

/**
 * Service for executing complex queries for {@link MovimentacaoDeEstoque} entities in the database.
 * The main input is a {@link MovimentacaoDeEstoqueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MovimentacaoDeEstoqueDTO} or a {@link Page} of {@link MovimentacaoDeEstoqueDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MovimentacaoDeEstoqueQueryService extends QueryService<MovimentacaoDeEstoque> {

    private final Logger log = LoggerFactory.getLogger(MovimentacaoDeEstoqueQueryService.class);

    private final MovimentacaoDeEstoqueRepository movimentacaoDeEstoqueRepository;

    private final MovimentacaoDeEstoqueMapper movimentacaoDeEstoqueMapper;

    public MovimentacaoDeEstoqueQueryService(MovimentacaoDeEstoqueRepository movimentacaoDeEstoqueRepository, MovimentacaoDeEstoqueMapper movimentacaoDeEstoqueMapper) {
        this.movimentacaoDeEstoqueRepository = movimentacaoDeEstoqueRepository;
        this.movimentacaoDeEstoqueMapper = movimentacaoDeEstoqueMapper;
    }

    /**
     * Return a {@link List} of {@link MovimentacaoDeEstoqueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MovimentacaoDeEstoqueDTO> findByCriteria(MovimentacaoDeEstoqueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MovimentacaoDeEstoque> specification = createSpecification(criteria);
        return movimentacaoDeEstoqueMapper.toDto(movimentacaoDeEstoqueRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MovimentacaoDeEstoqueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MovimentacaoDeEstoqueDTO> findByCriteria(MovimentacaoDeEstoqueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MovimentacaoDeEstoque> specification = createSpecification(criteria);
        return movimentacaoDeEstoqueRepository.findAll(specification, page)
            .map(movimentacaoDeEstoqueMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MovimentacaoDeEstoqueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MovimentacaoDeEstoque> specification = createSpecification(criteria);
        return movimentacaoDeEstoqueRepository.count(specification);
    }

    /**
     * Function to convert {@link MovimentacaoDeEstoqueCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MovimentacaoDeEstoque> createSpecification(MovimentacaoDeEstoqueCriteria criteria) {
        Specification<MovimentacaoDeEstoque> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MovimentacaoDeEstoque_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), MovimentacaoDeEstoque_.tipo));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), MovimentacaoDeEstoque_.data));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), MovimentacaoDeEstoque_.quantidade));
            }
            if (criteria.getConsumoId() != null) {
                specification = specification.and(buildSpecification(criteria.getConsumoId(),
                    root -> root.join(MovimentacaoDeEstoque_.consumo, JoinType.LEFT).get(Consumo_.id)));
            }
        }
        return specification;
    }
}
