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

import br.com.ngzorro.domain.AnexoAtendimento;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnexoAtendimentoRepository;
import br.com.ngzorro.service.dto.AnexoAtendimentoCriteria;
import br.com.ngzorro.service.dto.AnexoAtendimentoDTO;
import br.com.ngzorro.service.mapper.AnexoAtendimentoMapper;

/**
 * Service for executing complex queries for {@link AnexoAtendimento} entities in the database.
 * The main input is a {@link AnexoAtendimentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnexoAtendimentoDTO} or a {@link Page} of {@link AnexoAtendimentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnexoAtendimentoQueryService extends QueryService<AnexoAtendimento> {

    private final Logger log = LoggerFactory.getLogger(AnexoAtendimentoQueryService.class);

    private final AnexoAtendimentoRepository anexoAtendimentoRepository;

    private final AnexoAtendimentoMapper anexoAtendimentoMapper;

    public AnexoAtendimentoQueryService(AnexoAtendimentoRepository anexoAtendimentoRepository, AnexoAtendimentoMapper anexoAtendimentoMapper) {
        this.anexoAtendimentoRepository = anexoAtendimentoRepository;
        this.anexoAtendimentoMapper = anexoAtendimentoMapper;
    }

    /**
     * Return a {@link List} of {@link AnexoAtendimentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoAtendimentoDTO> findByCriteria(AnexoAtendimentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnexoAtendimento> specification = createSpecification(criteria);
        return anexoAtendimentoMapper.toDto(anexoAtendimentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnexoAtendimentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnexoAtendimentoDTO> findByCriteria(AnexoAtendimentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnexoAtendimento> specification = createSpecification(criteria);
        return anexoAtendimentoRepository.findAll(specification, page)
            .map(anexoAtendimentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnexoAtendimentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnexoAtendimento> specification = createSpecification(criteria);
        return anexoAtendimentoRepository.count(specification);
    }

    /**
     * Function to convert {@link AnexoAtendimentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnexoAtendimento> createSpecification(AnexoAtendimentoCriteria criteria) {
        Specification<AnexoAtendimento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnexoAtendimento_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AnexoAtendimento_.descricao));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), AnexoAtendimento_.data));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), AnexoAtendimento_.url));
            }
            if (criteria.getUrlThumbnail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlThumbnail(), AnexoAtendimento_.urlThumbnail));
            }
            if (criteria.getAtendimentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAtendimentoId(),
                    root -> root.join(AnexoAtendimento_.atendimento, JoinType.LEFT).get(Atendimento_.id)));
            }
        }
        return specification;
    }
}
