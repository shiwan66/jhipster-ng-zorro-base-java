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

import br.com.ngzorro.domain.Atendimento;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AtendimentoRepository;
import br.com.ngzorro.service.dto.AtendimentoCriteria;
import br.com.ngzorro.service.dto.AtendimentoDTO;
import br.com.ngzorro.service.mapper.AtendimentoMapper;

/**
 * Service for executing complex queries for {@link Atendimento} entities in the database.
 * The main input is a {@link AtendimentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AtendimentoDTO} or a {@link Page} of {@link AtendimentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AtendimentoQueryService extends QueryService<Atendimento> {

    private final Logger log = LoggerFactory.getLogger(AtendimentoQueryService.class);

    private final AtendimentoRepository atendimentoRepository;

    private final AtendimentoMapper atendimentoMapper;

    public AtendimentoQueryService(AtendimentoRepository atendimentoRepository, AtendimentoMapper atendimentoMapper) {
        this.atendimentoRepository = atendimentoRepository;
        this.atendimentoMapper = atendimentoMapper;
    }

    /**
     * Return a {@link List} of {@link AtendimentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AtendimentoDTO> findByCriteria(AtendimentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Atendimento> specification = createSpecification(criteria);
        return atendimentoMapper.toDto(atendimentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AtendimentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AtendimentoDTO> findByCriteria(AtendimentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Atendimento> specification = createSpecification(criteria);
        return atendimentoRepository.findAll(specification, page)
            .map(atendimentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AtendimentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Atendimento> specification = createSpecification(criteria);
        return atendimentoRepository.count(specification);
    }

    /**
     * Function to convert {@link AtendimentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Atendimento> createSpecification(AtendimentoCriteria criteria) {
        Specification<Atendimento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Atendimento_.id));
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), Atendimento_.situacao));
            }
            if (criteria.getDataDeChegada() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataDeChegada(), Atendimento_.dataDeChegada));
            }
            if (criteria.getDataDeSaida() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataDeSaida(), Atendimento_.dataDeSaida));
            }
            if (criteria.getObservacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacao(), Atendimento_.observacao));
            }
            if (criteria.getAtividadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getAtividadeId(),
                    root -> root.join(Atendimento_.atividades, JoinType.LEFT).get(Atividade_.id)));
            }
            if (criteria.getVendaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendaId(),
                    root -> root.join(Atendimento_.vendas, JoinType.LEFT).get(Venda_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(Atendimento_.anexos, JoinType.LEFT).get(AnexoAtendimento_.id)));
            }
            if (criteria.getAnimalId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalId(),
                    root -> root.join(Atendimento_.animal, JoinType.LEFT).get(Animal_.id)));
            }
        }
        return specification;
    }
}
