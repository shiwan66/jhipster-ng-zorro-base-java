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

import br.com.ngzorro.domain.Fornecedor;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.FornecedorRepository;
import br.com.ngzorro.service.dto.FornecedorCriteria;
import br.com.ngzorro.service.dto.FornecedorDTO;
import br.com.ngzorro.service.mapper.FornecedorMapper;

/**
 * Service for executing complex queries for {@link Fornecedor} entities in the database.
 * The main input is a {@link FornecedorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FornecedorDTO} or a {@link Page} of {@link FornecedorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FornecedorQueryService extends QueryService<Fornecedor> {

    private final Logger log = LoggerFactory.getLogger(FornecedorQueryService.class);

    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    public FornecedorQueryService(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    /**
     * Return a {@link List} of {@link FornecedorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FornecedorDTO> findByCriteria(FornecedorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fornecedor> specification = createSpecification(criteria);
        return fornecedorMapper.toDto(fornecedorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FornecedorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> findByCriteria(FornecedorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fornecedor> specification = createSpecification(criteria);
        return fornecedorRepository.findAll(specification, page)
            .map(fornecedorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FornecedorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fornecedor> specification = createSpecification(criteria);
        return fornecedorRepository.count(specification);
    }

    /**
     * Function to convert {@link FornecedorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fornecedor> createSpecification(FornecedorCriteria criteria) {
        Specification<Fornecedor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Fornecedor_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Fornecedor_.nome));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Fornecedor_.telefone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Fornecedor_.email));
            }
            if (criteria.getPontoReferencia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPontoReferencia(), Fornecedor_.pontoReferencia));
            }
            if (criteria.getTituloId() != null) {
                specification = specification.and(buildSpecification(criteria.getTituloId(),
                    root -> root.join(Fornecedor_.titulos, JoinType.LEFT).get(Titulo_.id)));
            }
            if (criteria.getEnderecoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnderecoId(),
                    root -> root.join(Fornecedor_.endereco, JoinType.LEFT).get(Endereco_.id)));
            }
        }
        return specification;
    }
}
