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

import br.com.ngzorro.domain.AnimalCarrapaticida;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalCarrapaticidaRepository;
import br.com.ngzorro.service.dto.AnimalCarrapaticidaCriteria;
import br.com.ngzorro.service.dto.AnimalCarrapaticidaDTO;
import br.com.ngzorro.service.mapper.AnimalCarrapaticidaMapper;

/**
 * Service for executing complex queries for {@link AnimalCarrapaticida} entities in the database.
 * The main input is a {@link AnimalCarrapaticidaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalCarrapaticidaDTO} or a {@link Page} of {@link AnimalCarrapaticidaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalCarrapaticidaQueryService extends QueryService<AnimalCarrapaticida> {

    private final Logger log = LoggerFactory.getLogger(AnimalCarrapaticidaQueryService.class);

    private final AnimalCarrapaticidaRepository animalCarrapaticidaRepository;

    private final AnimalCarrapaticidaMapper animalCarrapaticidaMapper;

    public AnimalCarrapaticidaQueryService(AnimalCarrapaticidaRepository animalCarrapaticidaRepository, AnimalCarrapaticidaMapper animalCarrapaticidaMapper) {
        this.animalCarrapaticidaRepository = animalCarrapaticidaRepository;
        this.animalCarrapaticidaMapper = animalCarrapaticidaMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalCarrapaticidaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalCarrapaticidaDTO> findByCriteria(AnimalCarrapaticidaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnimalCarrapaticida> specification = createSpecification(criteria);
        return animalCarrapaticidaMapper.toDto(animalCarrapaticidaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalCarrapaticidaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalCarrapaticidaDTO> findByCriteria(AnimalCarrapaticidaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnimalCarrapaticida> specification = createSpecification(criteria);
        return animalCarrapaticidaRepository.findAll(specification, page)
            .map(animalCarrapaticidaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalCarrapaticidaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnimalCarrapaticida> specification = createSpecification(criteria);
        return animalCarrapaticidaRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalCarrapaticidaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnimalCarrapaticida> createSpecification(AnimalCarrapaticidaCriteria criteria) {
        Specification<AnimalCarrapaticida> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnimalCarrapaticida_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AnimalCarrapaticida_.nome));
            }
            if (criteria.getDataAplicacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAplicacao(), AnimalCarrapaticida_.dataAplicacao));
            }
            if (criteria.getAnimalId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalId(),
                    root -> root.join(AnimalCarrapaticida_.animal, JoinType.LEFT).get(Animal_.id)));
            }
        }
        return specification;
    }
}
