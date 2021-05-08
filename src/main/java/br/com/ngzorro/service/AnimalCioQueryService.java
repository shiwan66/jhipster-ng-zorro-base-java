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

import br.com.ngzorro.domain.AnimalCio;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalCioRepository;
import br.com.ngzorro.service.dto.AnimalCioCriteria;
import br.com.ngzorro.service.dto.AnimalCioDTO;
import br.com.ngzorro.service.mapper.AnimalCioMapper;

/**
 * Service for executing complex queries for {@link AnimalCio} entities in the database.
 * The main input is a {@link AnimalCioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalCioDTO} or a {@link Page} of {@link AnimalCioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalCioQueryService extends QueryService<AnimalCio> {

    private final Logger log = LoggerFactory.getLogger(AnimalCioQueryService.class);

    private final AnimalCioRepository animalCioRepository;

    private final AnimalCioMapper animalCioMapper;

    public AnimalCioQueryService(AnimalCioRepository animalCioRepository, AnimalCioMapper animalCioMapper) {
        this.animalCioRepository = animalCioRepository;
        this.animalCioMapper = animalCioMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalCioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalCioDTO> findByCriteria(AnimalCioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnimalCio> specification = createSpecification(criteria);
        return animalCioMapper.toDto(animalCioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalCioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalCioDTO> findByCriteria(AnimalCioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnimalCio> specification = createSpecification(criteria);
        return animalCioRepository.findAll(specification, page)
            .map(animalCioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalCioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnimalCio> specification = createSpecification(criteria);
        return animalCioRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalCioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnimalCio> createSpecification(AnimalCioCriteria criteria) {
        Specification<AnimalCio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnimalCio_.id));
            }
            if (criteria.getDataDoCio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataDoCio(), AnimalCio_.dataDoCio));
            }
            if (criteria.getAnimalId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalId(),
                    root -> root.join(AnimalCio_.animal, JoinType.LEFT).get(Animal_.id)));
            }
        }
        return specification;
    }
}
