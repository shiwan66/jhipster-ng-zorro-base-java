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

import br.com.ngzorro.domain.Produto;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.ProdutoRepository;
import br.com.ngzorro.service.dto.ProdutoCriteria;
import br.com.ngzorro.service.dto.ProdutoDTO;
import br.com.ngzorro.service.mapper.ProdutoMapper;

/**
 * Service for executing complex queries for {@link Produto} entities in the database.
 * The main input is a {@link ProdutoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProdutoDTO} or a {@link Page} of {@link ProdutoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProdutoQueryService extends QueryService<Produto> {

    private final Logger log = LoggerFactory.getLogger(ProdutoQueryService.class);

    private final ProdutoRepository produtoRepository;

    private final ProdutoMapper produtoMapper;

    public ProdutoQueryService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    /**
     * Return a {@link List} of {@link ProdutoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProdutoDTO> findByCriteria(ProdutoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Produto> specification = createSpecification(criteria);
        return produtoMapper.toDto(produtoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProdutoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findByCriteria(ProdutoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Produto> specification = createSpecification(criteria);
        return produtoRepository.findAll(specification, page)
            .map(produtoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProdutoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Produto> specification = createSpecification(criteria);
        return produtoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProdutoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Produto> createSpecification(ProdutoCriteria criteria) {
        Specification<Produto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Produto_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Produto_.nome));
            }
            if (criteria.getPreco() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreco(), Produto_.preco));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), Produto_.data));
            }
            if (criteria.getHora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHora(), Produto_.hora));
            }
            if (criteria.getCategoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaId(),
                    root -> root.join(Produto_.categorias, JoinType.LEFT).get(Categoria_.id)));
            }
        }
        return specification;
    }
}
