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

import br.com.ngzorro.domain.AnimalVermifugo;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalVermifugoRepository;
import br.com.ngzorro.service.dto.AnimalVermifugoCriteria;
import br.com.ngzorro.service.dto.AnimalVermifugoDTO;
import br.com.ngzorro.service.mapper.AnimalVermifugoMapper;

/**
 * Service for executing complex queries for {@link AnimalVermifugo} entities in the database.
 * The main input is a {@link AnimalVermifugoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalVermifugoDTO} or a {@link Page} of {@link AnimalVermifugoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalVermifugoQueryService extends QueryService<AnimalVermifugo> {

    private final Logger log = LoggerFactory.getLogger(AnimalVermifugoQueryService.class);

    private final AnimalVermifugoRepository animalVermifugoRepository;

    private final AnimalVermifugoMapper animalVermifugoMapper;

    public AnimalVermifugoQueryService(AnimalVermifugoRepository animalVermifugoRepository, AnimalVermifugoMapper animalVermifugoMapper) {
        this.animalVermifugoRepository = animalVermifugoRepository;
        this.animalVermifugoMapper = animalVermifugoMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalVermifugoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalVermifugoDTO> findByCriteria(AnimalVermifugoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnimalVermifugo> specification = createSpecification(criteria);
        return animalVermifugoMapper.toDto(animalVermifugoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalVermifugoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalVermifugoDTO> findByCriteria(AnimalVermifugoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnimalVermifugo> specification = createSpecification(criteria);
        return animalVermifugoRepository.findAll(specification, page)
            .map(animalVermifugoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalVermifugoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnimalVermifugo> specification = createSpecification(criteria);
        return animalVermifugoRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalVermifugoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnimalVermifugo> createSpecification(AnimalVermifugoCriteria criteria) {
        Specification<AnimalVermifugo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnimalVermifugo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AnimalVermifugo_.nome));
            }
            if (criteria.getDataDaAplicacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataDaAplicacao(), AnimalVermifugo_.dataDaAplicacao));
            }
            if (criteria.getAnimalId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalId(),
                    root -> root.join(AnimalVermifugo_.animal, JoinType.LEFT).get(Animal_.id)));
            }
        }
        return specification;
    }
}
