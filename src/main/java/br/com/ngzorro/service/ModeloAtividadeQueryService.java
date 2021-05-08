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

import br.com.ngzorro.domain.ModeloAtividade;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.ModeloAtividadeRepository;
import br.com.ngzorro.service.dto.ModeloAtividadeCriteria;
import br.com.ngzorro.service.dto.ModeloAtividadeDTO;
import br.com.ngzorro.service.mapper.ModeloAtividadeMapper;

/**
 * Service for executing complex queries for {@link ModeloAtividade} entities in the database.
 * The main input is a {@link ModeloAtividadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ModeloAtividadeDTO} or a {@link Page} of {@link ModeloAtividadeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ModeloAtividadeQueryService extends QueryService<ModeloAtividade> {

    private final Logger log = LoggerFactory.getLogger(ModeloAtividadeQueryService.class);

    private final ModeloAtividadeRepository modeloAtividadeRepository;

    private final ModeloAtividadeMapper modeloAtividadeMapper;

    public ModeloAtividadeQueryService(ModeloAtividadeRepository modeloAtividadeRepository, ModeloAtividadeMapper modeloAtividadeMapper) {
        this.modeloAtividadeRepository = modeloAtividadeRepository;
        this.modeloAtividadeMapper = modeloAtividadeMapper;
    }

    /**
     * Return a {@link List} of {@link ModeloAtividadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ModeloAtividadeDTO> findByCriteria(ModeloAtividadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ModeloAtividade> specification = createSpecification(criteria);
        return modeloAtividadeMapper.toDto(modeloAtividadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ModeloAtividadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ModeloAtividadeDTO> findByCriteria(ModeloAtividadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ModeloAtividade> specification = createSpecification(criteria);
        return modeloAtividadeRepository.findAll(specification, page)
            .map(modeloAtividadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ModeloAtividadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ModeloAtividade> specification = createSpecification(criteria);
        return modeloAtividadeRepository.count(specification);
    }

    /**
     * Function to convert {@link ModeloAtividadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ModeloAtividade> createSpecification(ModeloAtividadeCriteria criteria) {
        Specification<ModeloAtividade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ModeloAtividade_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), ModeloAtividade_.descricao));
            }
            if (criteria.getAtividadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getAtividadeId(),
                    root -> root.join(ModeloAtividade_.atividades, JoinType.LEFT).get(Atividade_.id)));
            }
        }
        return specification;
    }
}
