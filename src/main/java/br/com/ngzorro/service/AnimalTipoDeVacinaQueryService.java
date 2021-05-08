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

import br.com.ngzorro.domain.AnimalTipoDeVacina;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalTipoDeVacinaRepository;
import br.com.ngzorro.service.dto.AnimalTipoDeVacinaCriteria;
import br.com.ngzorro.service.dto.AnimalTipoDeVacinaDTO;
import br.com.ngzorro.service.mapper.AnimalTipoDeVacinaMapper;

/**
 * Service for executing complex queries for {@link AnimalTipoDeVacina} entities in the database.
 * The main input is a {@link AnimalTipoDeVacinaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalTipoDeVacinaDTO} or a {@link Page} of {@link AnimalTipoDeVacinaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalTipoDeVacinaQueryService extends QueryService<AnimalTipoDeVacina> {

    private final Logger log = LoggerFactory.getLogger(AnimalTipoDeVacinaQueryService.class);

    private final AnimalTipoDeVacinaRepository animalTipoDeVacinaRepository;

    private final AnimalTipoDeVacinaMapper animalTipoDeVacinaMapper;

    public AnimalTipoDeVacinaQueryService(AnimalTipoDeVacinaRepository animalTipoDeVacinaRepository, AnimalTipoDeVacinaMapper animalTipoDeVacinaMapper) {
        this.animalTipoDeVacinaRepository = animalTipoDeVacinaRepository;
        this.animalTipoDeVacinaMapper = animalTipoDeVacinaMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalTipoDeVacinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalTipoDeVacinaDTO> findByCriteria(AnimalTipoDeVacinaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnimalTipoDeVacina> specification = createSpecification(criteria);
        return animalTipoDeVacinaMapper.toDto(animalTipoDeVacinaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalTipoDeVacinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalTipoDeVacinaDTO> findByCriteria(AnimalTipoDeVacinaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnimalTipoDeVacina> specification = createSpecification(criteria);
        return animalTipoDeVacinaRepository.findAll(specification, page)
            .map(animalTipoDeVacinaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalTipoDeVacinaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnimalTipoDeVacina> specification = createSpecification(criteria);
        return animalTipoDeVacinaRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalTipoDeVacinaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnimalTipoDeVacina> createSpecification(AnimalTipoDeVacinaCriteria criteria) {
        Specification<AnimalTipoDeVacina> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnimalTipoDeVacina_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AnimalTipoDeVacina_.descricao));
            }
        }
        return specification;
    }
}
