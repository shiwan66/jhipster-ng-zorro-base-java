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

import br.com.ngzorro.domain.AnimalVeterinario;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalVeterinarioRepository;
import br.com.ngzorro.service.dto.AnimalVeterinarioCriteria;
import br.com.ngzorro.service.dto.AnimalVeterinarioDTO;
import br.com.ngzorro.service.mapper.AnimalVeterinarioMapper;

/**
 * Service for executing complex queries for {@link AnimalVeterinario} entities in the database.
 * The main input is a {@link AnimalVeterinarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalVeterinarioDTO} or a {@link Page} of {@link AnimalVeterinarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalVeterinarioQueryService extends QueryService<AnimalVeterinario> {

    private final Logger log = LoggerFactory.getLogger(AnimalVeterinarioQueryService.class);

    private final AnimalVeterinarioRepository animalVeterinarioRepository;

    private final AnimalVeterinarioMapper animalVeterinarioMapper;

    public AnimalVeterinarioQueryService(AnimalVeterinarioRepository animalVeterinarioRepository, AnimalVeterinarioMapper animalVeterinarioMapper) {
        this.animalVeterinarioRepository = animalVeterinarioRepository;
        this.animalVeterinarioMapper = animalVeterinarioMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalVeterinarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalVeterinarioDTO> findByCriteria(AnimalVeterinarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnimalVeterinario> specification = createSpecification(criteria);
        return animalVeterinarioMapper.toDto(animalVeterinarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalVeterinarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalVeterinarioDTO> findByCriteria(AnimalVeterinarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnimalVeterinario> specification = createSpecification(criteria);
        return animalVeterinarioRepository.findAll(specification, page)
            .map(animalVeterinarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalVeterinarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnimalVeterinario> specification = createSpecification(criteria);
        return animalVeterinarioRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalVeterinarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnimalVeterinario> createSpecification(AnimalVeterinarioCriteria criteria) {
        Specification<AnimalVeterinario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnimalVeterinario_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AnimalVeterinario_.nome));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), AnimalVeterinario_.telefone));
            }
            if (criteria.getClinica() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClinica(), AnimalVeterinario_.clinica));
            }
        }
        return specification;
    }
}
