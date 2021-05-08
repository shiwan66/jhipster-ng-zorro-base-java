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

import br.com.ngzorro.domain.Tutor;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.TutorRepository;
import br.com.ngzorro.service.dto.TutorCriteria;
import br.com.ngzorro.service.dto.TutorDTO;
import br.com.ngzorro.service.mapper.TutorMapper;

/**
 * Service for executing complex queries for {@link Tutor} entities in the database.
 * The main input is a {@link TutorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TutorDTO} or a {@link Page} of {@link TutorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TutorQueryService extends QueryService<Tutor> {

    private final Logger log = LoggerFactory.getLogger(TutorQueryService.class);

    private final TutorRepository tutorRepository;

    private final TutorMapper tutorMapper;

    public TutorQueryService(TutorRepository tutorRepository, TutorMapper tutorMapper) {
        this.tutorRepository = tutorRepository;
        this.tutorMapper = tutorMapper;
    }

    /**
     * Return a {@link List} of {@link TutorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TutorDTO> findByCriteria(TutorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tutor> specification = createSpecification(criteria);
        return tutorMapper.toDto(tutorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TutorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TutorDTO> findByCriteria(TutorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tutor> specification = createSpecification(criteria);
        return tutorRepository.findAll(specification, page)
            .map(tutorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TutorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tutor> specification = createSpecification(criteria);
        return tutorRepository.count(specification);
    }

    /**
     * Function to convert {@link TutorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tutor> createSpecification(TutorCriteria criteria) {
        Specification<Tutor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tutor_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Tutor_.nome));
            }
            if (criteria.getSobrenome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSobrenome(), Tutor_.sobrenome));
            }
            if (criteria.getTelefone1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone1(), Tutor_.telefone1));
            }
            if (criteria.getTelefone2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone2(), Tutor_.telefone2));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Tutor_.email));
            }
            if (criteria.getFotoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFotoUrl(), Tutor_.fotoUrl));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpf(), Tutor_.cpf));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildSpecification(criteria.getSexo(), Tutor_.sexo));
            }
            if (criteria.getDataCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCadastro(), Tutor_.dataCadastro));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Tutor_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getTituloId() != null) {
                specification = specification.and(buildSpecification(criteria.getTituloId(),
                    root -> root.join(Tutor_.titulos, JoinType.LEFT).get(Titulo_.id)));
            }
            if (criteria.getAnimalId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalId(),
                    root -> root.join(Tutor_.animals, JoinType.LEFT).get(Animal_.id)));
            }
            if (criteria.getEnderecoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnderecoId(),
                    root -> root.join(Tutor_.endereco, JoinType.LEFT).get(Endereco_.id)));
            }
        }
        return specification;
    }
}
