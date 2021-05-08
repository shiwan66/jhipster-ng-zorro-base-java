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

import br.com.ngzorro.domain.VendaConsumo;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.VendaConsumoRepository;
import br.com.ngzorro.service.dto.VendaConsumoCriteria;
import br.com.ngzorro.service.dto.VendaConsumoDTO;
import br.com.ngzorro.service.mapper.VendaConsumoMapper;

/**
 * Service for executing complex queries for {@link VendaConsumo} entities in the database.
 * The main input is a {@link VendaConsumoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VendaConsumoDTO} or a {@link Page} of {@link VendaConsumoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendaConsumoQueryService extends QueryService<VendaConsumo> {

    private final Logger log = LoggerFactory.getLogger(VendaConsumoQueryService.class);

    private final VendaConsumoRepository vendaConsumoRepository;

    private final VendaConsumoMapper vendaConsumoMapper;

    public VendaConsumoQueryService(VendaConsumoRepository vendaConsumoRepository, VendaConsumoMapper vendaConsumoMapper) {
        this.vendaConsumoRepository = vendaConsumoRepository;
        this.vendaConsumoMapper = vendaConsumoMapper;
    }

    /**
     * Return a {@link List} of {@link VendaConsumoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VendaConsumoDTO> findByCriteria(VendaConsumoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VendaConsumo> specification = createSpecification(criteria);
        return vendaConsumoMapper.toDto(vendaConsumoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VendaConsumoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VendaConsumoDTO> findByCriteria(VendaConsumoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VendaConsumo> specification = createSpecification(criteria);
        return vendaConsumoRepository.findAll(specification, page)
            .map(vendaConsumoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VendaConsumoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VendaConsumo> specification = createSpecification(criteria);
        return vendaConsumoRepository.count(specification);
    }

    /**
     * Function to convert {@link VendaConsumoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VendaConsumo> createSpecification(VendaConsumoCriteria criteria) {
        Specification<VendaConsumo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VendaConsumo_.id));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), VendaConsumo_.quantidade));
            }
            if (criteria.getValorUnitario() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorUnitario(), VendaConsumo_.valorUnitario));
            }
            if (criteria.getValorTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotal(), VendaConsumo_.valorTotal));
            }
            if (criteria.getVendaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendaId(),
                    root -> root.join(VendaConsumo_.venda, JoinType.LEFT).get(Venda_.id)));
            }
            if (criteria.getConsumoId() != null) {
                specification = specification.and(buildSpecification(criteria.getConsumoId(),
                    root -> root.join(VendaConsumo_.consumo, JoinType.LEFT).get(Consumo_.id)));
            }
        }
        return specification;
    }
}
