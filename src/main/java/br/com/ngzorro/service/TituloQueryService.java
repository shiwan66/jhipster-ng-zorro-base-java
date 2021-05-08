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

import br.com.ngzorro.domain.Titulo;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.TituloRepository;
import br.com.ngzorro.service.dto.TituloCriteria;
import br.com.ngzorro.service.dto.TituloDTO;
import br.com.ngzorro.service.mapper.TituloMapper;

/**
 * Service for executing complex queries for {@link Titulo} entities in the database.
 * The main input is a {@link TituloCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TituloDTO} or a {@link Page} of {@link TituloDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TituloQueryService extends QueryService<Titulo> {

    private final Logger log = LoggerFactory.getLogger(TituloQueryService.class);

    private final TituloRepository tituloRepository;

    private final TituloMapper tituloMapper;

    public TituloQueryService(TituloRepository tituloRepository, TituloMapper tituloMapper) {
        this.tituloRepository = tituloRepository;
        this.tituloMapper = tituloMapper;
    }

    /**
     * Return a {@link List} of {@link TituloDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TituloDTO> findByCriteria(TituloCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Titulo> specification = createSpecification(criteria);
        return tituloMapper.toDto(tituloRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TituloDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TituloDTO> findByCriteria(TituloCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Titulo> specification = createSpecification(criteria);
        return tituloRepository.findAll(specification, page)
            .map(tituloMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TituloCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Titulo> specification = createSpecification(criteria);
        return tituloRepository.count(specification);
    }

    /**
     * Function to convert {@link TituloCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Titulo> createSpecification(TituloCriteria criteria) {
        Specification<Titulo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Titulo_.id));
            }
            if (criteria.getIsPago() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPago(), Titulo_.isPago));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), Titulo_.tipo));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Titulo_.valor));
            }
            if (criteria.getDataEmissao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataEmissao(), Titulo_.dataEmissao));
            }
            if (criteria.getDataPagamento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataPagamento(), Titulo_.dataPagamento));
            }
            if (criteria.getDataVencimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataVencimento(), Titulo_.dataVencimento));
            }
            if (criteria.getTutorId() != null) {
                specification = specification.and(buildSpecification(criteria.getTutorId(),
                    root -> root.join(Titulo_.tutor, JoinType.LEFT).get(Tutor_.id)));
            }
            if (criteria.getFornecedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getFornecedorId(),
                    root -> root.join(Titulo_.fornecedor, JoinType.LEFT).get(Fornecedor_.id)));
            }
        }
        return specification;
    }
}
