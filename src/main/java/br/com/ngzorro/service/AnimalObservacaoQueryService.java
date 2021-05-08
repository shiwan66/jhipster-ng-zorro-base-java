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

import br.com.ngzorro.domain.AnimalObservacao;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalObservacaoRepository;
import br.com.ngzorro.service.dto.AnimalObservacaoCriteria;
import br.com.ngzorro.service.dto.AnimalObservacaoDTO;
import br.com.ngzorro.service.mapper.AnimalObservacaoMapper;

/**
 * Service for executing complex queries for {@link AnimalObservacao} entities in the database.
 * The main input is a {@link AnimalObservacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalObservacaoDTO} or a {@link Page} of {@link AnimalObservacaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalObservacaoQueryService extends QueryService<AnimalObservacao> {

    private final Logger log = LoggerFactory.getLogger(AnimalObservacaoQueryService.class);

    private final AnimalObservacaoRepository animalObservacaoRepository;

    private final AnimalObservacaoMapper animalObservacaoMapper;

    public AnimalObservacaoQueryService(AnimalObservacaoRepository animalObservacaoRepository, AnimalObservacaoMapper animalObservacaoMapper) {
        this.animalObservacaoRepository = animalObservacaoRepository;
        this.animalObservacaoMapper = animalObservacaoMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalObservacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalObservacaoDTO> findByCriteria(AnimalObservacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnimalObservacao> specification = createSpecification(criteria);
        return animalObservacaoMapper.toDto(animalObservacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalObservacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalObservacaoDTO> findByCriteria(AnimalObservacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnimalObservacao> specification = createSpecification(criteria);
        return animalObservacaoRepository.findAll(specification, page)
            .map(animalObservacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalObservacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnimalObservacao> specification = createSpecification(criteria);
        return animalObservacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalObservacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnimalObservacao> createSpecification(AnimalObservacaoCriteria criteria) {
        Specification<AnimalObservacao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnimalObservacao_.id));
            }
            if (criteria.getDataAlteracao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAlteracao(), AnimalObservacao_.dataAlteracao));
            }
            if (criteria.getAnimalId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalId(),
                    root -> root.join(AnimalObservacao_.animal, JoinType.LEFT).get(Animal_.id)));
            }
        }
        return specification;
    }
}
