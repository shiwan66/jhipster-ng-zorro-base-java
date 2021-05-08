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

import br.com.ngzorro.domain.Consumo;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.ConsumoRepository;
import br.com.ngzorro.service.dto.ConsumoCriteria;
import br.com.ngzorro.service.dto.ConsumoDTO;
import br.com.ngzorro.service.mapper.ConsumoMapper;

/**
 * Service for executing complex queries for {@link Consumo} entities in the database.
 * The main input is a {@link ConsumoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConsumoDTO} or a {@link Page} of {@link ConsumoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConsumoQueryService extends QueryService<Consumo> {

    private final Logger log = LoggerFactory.getLogger(ConsumoQueryService.class);

    private final ConsumoRepository consumoRepository;

    private final ConsumoMapper consumoMapper;

    public ConsumoQueryService(ConsumoRepository consumoRepository, ConsumoMapper consumoMapper) {
        this.consumoRepository = consumoRepository;
        this.consumoMapper = consumoMapper;
    }

    /**
     * Return a {@link List} of {@link ConsumoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConsumoDTO> findByCriteria(ConsumoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Consumo> specification = createSpecification(criteria);
        return consumoMapper.toDto(consumoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConsumoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsumoDTO> findByCriteria(ConsumoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Consumo> specification = createSpecification(criteria);
        return consumoRepository.findAll(specification, page)
            .map(consumoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConsumoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Consumo> specification = createSpecification(criteria);
        return consumoRepository.count(specification);
    }

    /**
     * Function to convert {@link ConsumoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Consumo> createSpecification(ConsumoCriteria criteria) {
        Specification<Consumo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Consumo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Consumo_.nome));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), Consumo_.tipo));
            }
            if (criteria.getEstoque() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstoque(), Consumo_.estoque));
            }
            if (criteria.getValorUnitario() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorUnitario(), Consumo_.valorUnitario));
            }
            if (criteria.getVendaConsumoId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendaConsumoId(),
                    root -> root.join(Consumo_.vendaConsumos, JoinType.LEFT).get(VendaConsumo_.id)));
            }
            if (criteria.getMovimentacaoDeEstoqueId() != null) {
                specification = specification.and(buildSpecification(criteria.getMovimentacaoDeEstoqueId(),
                    root -> root.join(Consumo_.movimentacaoDeEstoques, JoinType.LEFT).get(MovimentacaoDeEstoque_.id)));
            }
        }
        return specification;
    }
}
