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

import br.com.ngzorro.domain.AnimalAlteracao;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalAlteracaoRepository;
import br.com.ngzorro.service.dto.AnimalAlteracaoCriteria;
import br.com.ngzorro.service.dto.AnimalAlteracaoDTO;
import br.com.ngzorro.service.mapper.AnimalAlteracaoMapper;

/**
 * Service for executing complex queries for {@link AnimalAlteracao} entities in the database.
 * The main input is a {@link AnimalAlteracaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalAlteracaoDTO} or a {@link Page} of {@link AnimalAlteracaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalAlteracaoQueryService extends QueryService<AnimalAlteracao> {

    private final Logger log = LoggerFactory.getLogger(AnimalAlteracaoQueryService.class);

    private final AnimalAlteracaoRepository animalAlteracaoRepository;

    private final AnimalAlteracaoMapper animalAlteracaoMapper;

    public AnimalAlteracaoQueryService(AnimalAlteracaoRepository animalAlteracaoRepository, AnimalAlteracaoMapper animalAlteracaoMapper) {
        this.animalAlteracaoRepository = animalAlteracaoRepository;
        this.animalAlteracaoMapper = animalAlteracaoMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalAlteracaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalAlteracaoDTO> findByCriteria(AnimalAlteracaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnimalAlteracao> specification = createSpecification(criteria);
        return animalAlteracaoMapper.toDto(animalAlteracaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalAlteracaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalAlteracaoDTO> findByCriteria(AnimalAlteracaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnimalAlteracao> specification = createSpecification(criteria);
        return animalAlteracaoRepository.findAll(specification, page)
            .map(animalAlteracaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalAlteracaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnimalAlteracao> specification = createSpecification(criteria);
        return animalAlteracaoRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalAlteracaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnimalAlteracao> createSpecification(AnimalAlteracaoCriteria criteria) {
        Specification<AnimalAlteracao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnimalAlteracao_.id));
            }
            if (criteria.getDataAlteracao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAlteracao(), AnimalAlteracao_.dataAlteracao));
            }
            if (criteria.getAnimalTipoDeAlteracaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalTipoDeAlteracaoId(),
                    root -> root.join(AnimalAlteracao_.animalTipoDeAlteracao, JoinType.LEFT).get(AnimalTipoDeAlteracao_.id)));
            }
            if (criteria.getAnimalId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalId(),
                    root -> root.join(AnimalAlteracao_.animal, JoinType.LEFT).get(Animal_.id)));
            }
        }
        return specification;
    }
}
