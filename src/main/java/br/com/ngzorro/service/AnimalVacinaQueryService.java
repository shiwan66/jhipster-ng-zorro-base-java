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

import br.com.ngzorro.domain.AnimalVacina;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalVacinaRepository;
import br.com.ngzorro.service.dto.AnimalVacinaCriteria;
import br.com.ngzorro.service.dto.AnimalVacinaDTO;
import br.com.ngzorro.service.mapper.AnimalVacinaMapper;

/**
 * Service for executing complex queries for {@link AnimalVacina} entities in the database.
 * The main input is a {@link AnimalVacinaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalVacinaDTO} or a {@link Page} of {@link AnimalVacinaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalVacinaQueryService extends QueryService<AnimalVacina> {

    private final Logger log = LoggerFactory.getLogger(AnimalVacinaQueryService.class);

    private final AnimalVacinaRepository animalVacinaRepository;

    private final AnimalVacinaMapper animalVacinaMapper;

    public AnimalVacinaQueryService(AnimalVacinaRepository animalVacinaRepository, AnimalVacinaMapper animalVacinaMapper) {
        this.animalVacinaRepository = animalVacinaRepository;
        this.animalVacinaMapper = animalVacinaMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalVacinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalVacinaDTO> findByCriteria(AnimalVacinaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnimalVacina> specification = createSpecification(criteria);
        return animalVacinaMapper.toDto(animalVacinaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalVacinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalVacinaDTO> findByCriteria(AnimalVacinaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnimalVacina> specification = createSpecification(criteria);
        return animalVacinaRepository.findAll(specification, page)
            .map(animalVacinaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalVacinaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnimalVacina> specification = createSpecification(criteria);
        return animalVacinaRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalVacinaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnimalVacina> createSpecification(AnimalVacinaCriteria criteria) {
        Specification<AnimalVacina> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnimalVacina_.id));
            }
            if (criteria.getDataDaAplicacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataDaAplicacao(), AnimalVacina_.dataDaAplicacao));
            }
            if (criteria.getAnimalTipoDeVacinaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalTipoDeVacinaId(),
                    root -> root.join(AnimalVacina_.animalTipoDeVacina, JoinType.LEFT).get(AnimalTipoDeVacina_.id)));
            }
            if (criteria.getAnimalId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalId(),
                    root -> root.join(AnimalVacina_.animal, JoinType.LEFT).get(Animal_.id)));
            }
        }
        return specification;
    }
}
