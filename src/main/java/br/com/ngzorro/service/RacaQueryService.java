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

import br.com.ngzorro.domain.Raca;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.RacaRepository;
import br.com.ngzorro.service.dto.RacaCriteria;
import br.com.ngzorro.service.dto.RacaDTO;
import br.com.ngzorro.service.mapper.RacaMapper;

/**
 * Service for executing complex queries for {@link Raca} entities in the database.
 * The main input is a {@link RacaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RacaDTO} or a {@link Page} of {@link RacaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RacaQueryService extends QueryService<Raca> {

    private final Logger log = LoggerFactory.getLogger(RacaQueryService.class);

    private final RacaRepository racaRepository;

    private final RacaMapper racaMapper;

    public RacaQueryService(RacaRepository racaRepository, RacaMapper racaMapper) {
        this.racaRepository = racaRepository;
        this.racaMapper = racaMapper;
    }

    /**
     * Return a {@link List} of {@link RacaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RacaDTO> findByCriteria(RacaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Raca> specification = createSpecification(criteria);
        return racaMapper.toDto(racaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RacaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RacaDTO> findByCriteria(RacaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Raca> specification = createSpecification(criteria);
        return racaRepository.findAll(specification, page)
            .map(racaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RacaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Raca> specification = createSpecification(criteria);
        return racaRepository.count(specification);
    }

    /**
     * Function to convert {@link RacaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Raca> createSpecification(RacaCriteria criteria) {
        Specification<Raca> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Raca_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Raca_.nome));
            }
        }
        return specification;
    }
}
