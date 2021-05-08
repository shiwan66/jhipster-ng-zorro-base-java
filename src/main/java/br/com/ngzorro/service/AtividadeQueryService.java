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

import br.com.ngzorro.domain.Atividade;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AtividadeRepository;
import br.com.ngzorro.service.dto.AtividadeCriteria;
import br.com.ngzorro.service.dto.AtividadeDTO;
import br.com.ngzorro.service.mapper.AtividadeMapper;

/**
 * Service for executing complex queries for {@link Atividade} entities in the database.
 * The main input is a {@link AtividadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AtividadeDTO} or a {@link Page} of {@link AtividadeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AtividadeQueryService extends QueryService<Atividade> {

    private final Logger log = LoggerFactory.getLogger(AtividadeQueryService.class);

    private final AtividadeRepository atividadeRepository;

    private final AtividadeMapper atividadeMapper;

    public AtividadeQueryService(AtividadeRepository atividadeRepository, AtividadeMapper atividadeMapper) {
        this.atividadeRepository = atividadeRepository;
        this.atividadeMapper = atividadeMapper;
    }

    /**
     * Return a {@link List} of {@link AtividadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AtividadeDTO> findByCriteria(AtividadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Atividade> specification = createSpecification(criteria);
        return atividadeMapper.toDto(atividadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AtividadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AtividadeDTO> findByCriteria(AtividadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Atividade> specification = createSpecification(criteria);
        return atividadeRepository.findAll(specification, page)
            .map(atividadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AtividadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Atividade> specification = createSpecification(criteria);
        return atividadeRepository.count(specification);
    }

    /**
     * Function to convert {@link AtividadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Atividade> createSpecification(AtividadeCriteria criteria) {
        Specification<Atividade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Atividade_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Atividade_.titulo));
            }
            if (criteria.getInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInicio(), Atividade_.inicio));
            }
            if (criteria.getTermino() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTermino(), Atividade_.termino));
            }
            if (criteria.getObservacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacao(), Atividade_.observacao));
            }
            if (criteria.getRealizado() != null) {
                specification = specification.and(buildSpecification(criteria.getRealizado(), Atividade_.realizado));
            }
            if (criteria.getAtendimentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAtendimentoId(),
                    root -> root.join(Atividade_.atendimento, JoinType.LEFT).get(Atendimento_.id)));
            }
            if (criteria.getModeloAtividadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getModeloAtividadeId(),
                    root -> root.join(Atividade_.modeloAtividades, JoinType.LEFT).get(ModeloAtividade_.id)));
            }
        }
        return specification;
    }
}
