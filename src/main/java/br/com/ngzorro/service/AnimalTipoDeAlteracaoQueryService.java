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

import br.com.ngzorro.domain.AnimalTipoDeAlteracao;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalTipoDeAlteracaoRepository;
import br.com.ngzorro.service.dto.AnimalTipoDeAlteracaoCriteria;
import br.com.ngzorro.service.dto.AnimalTipoDeAlteracaoDTO;
import br.com.ngzorro.service.mapper.AnimalTipoDeAlteracaoMapper;

/**
 * Service for executing complex queries for {@link AnimalTipoDeAlteracao} entities in the database.
 * The main input is a {@link AnimalTipoDeAlteracaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalTipoDeAlteracaoDTO} or a {@link Page} of {@link AnimalTipoDeAlteracaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalTipoDeAlteracaoQueryService extends QueryService<AnimalTipoDeAlteracao> {

    private final Logger log = LoggerFactory.getLogger(AnimalTipoDeAlteracaoQueryService.class);

    private final AnimalTipoDeAlteracaoRepository animalTipoDeAlteracaoRepository;

    private final AnimalTipoDeAlteracaoMapper animalTipoDeAlteracaoMapper;

    public AnimalTipoDeAlteracaoQueryService(AnimalTipoDeAlteracaoRepository animalTipoDeAlteracaoRepository, AnimalTipoDeAlteracaoMapper animalTipoDeAlteracaoMapper) {
        this.animalTipoDeAlteracaoRepository = animalTipoDeAlteracaoRepository;
        this.animalTipoDeAlteracaoMapper = animalTipoDeAlteracaoMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalTipoDeAlteracaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalTipoDeAlteracaoDTO> findByCriteria(AnimalTipoDeAlteracaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnimalTipoDeAlteracao> specification = createSpecification(criteria);
        return animalTipoDeAlteracaoMapper.toDto(animalTipoDeAlteracaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalTipoDeAlteracaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalTipoDeAlteracaoDTO> findByCriteria(AnimalTipoDeAlteracaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnimalTipoDeAlteracao> specification = createSpecification(criteria);
        return animalTipoDeAlteracaoRepository.findAll(specification, page)
            .map(animalTipoDeAlteracaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalTipoDeAlteracaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnimalTipoDeAlteracao> specification = createSpecification(criteria);
        return animalTipoDeAlteracaoRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalTipoDeAlteracaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnimalTipoDeAlteracao> createSpecification(AnimalTipoDeAlteracaoCriteria criteria) {
        Specification<AnimalTipoDeAlteracao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnimalTipoDeAlteracao_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AnimalTipoDeAlteracao_.descricao));
            }
        }
        return specification;
    }
}
