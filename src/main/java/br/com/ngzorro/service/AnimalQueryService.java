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

import br.com.ngzorro.domain.Animal;
import br.com.ngzorro.domain.*; // for static metamodels
import br.com.ngzorro.repository.AnimalRepository;
import br.com.ngzorro.service.dto.AnimalCriteria;
import br.com.ngzorro.service.dto.AnimalDTO;
import br.com.ngzorro.service.mapper.AnimalMapper;

/**
 * Service for executing complex queries for {@link Animal} entities in the database.
 * The main input is a {@link AnimalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnimalDTO} or a {@link Page} of {@link AnimalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnimalQueryService extends QueryService<Animal> {

    private final Logger log = LoggerFactory.getLogger(AnimalQueryService.class);

    private final AnimalRepository animalRepository;

    private final AnimalMapper animalMapper;

    public AnimalQueryService(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    /**
     * Return a {@link List} of {@link AnimalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> findByCriteria(AnimalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Animal> specification = createSpecification(criteria);
        return animalMapper.toDto(animalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnimalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnimalDTO> findByCriteria(AnimalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Animal> specification = createSpecification(criteria);
        return animalRepository.findAll(specification, page)
            .map(animalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnimalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Animal> specification = createSpecification(criteria);
        return animalRepository.count(specification);
    }

    /**
     * Function to convert {@link AnimalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Animal> createSpecification(AnimalCriteria criteria) {
        Specification<Animal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Animal_.id));
            }
            if (criteria.getFotoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFotoUrl(), Animal_.fotoUrl));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Animal_.nome));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildSpecification(criteria.getSexo(), Animal_.sexo));
            }
            if (criteria.getPelagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPelagem(), Animal_.pelagem));
            }
            if (criteria.getTemperamento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemperamento(), Animal_.temperamento));
            }
            if (criteria.getEmAtendimento() != null) {
                specification = specification.and(buildSpecification(criteria.getEmAtendimento(), Animal_.emAtendimento));
            }
            if (criteria.getDataNascimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataNascimento(), Animal_.dataNascimento));
            }
            if (criteria.getAtendimentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAtendimentoId(),
                    root -> root.join(Animal_.atendimentos, JoinType.LEFT).get(Atendimento_.id)));
            }
            if (criteria.getTipoVacinaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoVacinaId(),
                    root -> root.join(Animal_.tipoVacinas, JoinType.LEFT).get(AnimalVacina_.id)));
            }
            if (criteria.getAnimalAlteracaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalAlteracaoId(),
                    root -> root.join(Animal_.animalAlteracaos, JoinType.LEFT).get(AnimalAlteracao_.id)));
            }
            if (criteria.getAnimalVermifugoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalVermifugoId(),
                    root -> root.join(Animal_.animalVermifugos, JoinType.LEFT).get(AnimalVermifugo_.id)));
            }
            if (criteria.getAnimalCarrapaticidaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalCarrapaticidaId(),
                    root -> root.join(Animal_.animalCarrapaticidas, JoinType.LEFT).get(AnimalCarrapaticida_.id)));
            }
            if (criteria.getObservacaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getObservacaoId(),
                    root -> root.join(Animal_.observacaos, JoinType.LEFT).get(AnimalObservacao_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(Animal_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getAnimalCioId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnimalCioId(),
                    root -> root.join(Animal_.animalCios, JoinType.LEFT).get(AnimalCio_.id)));
            }
            if (criteria.getEnderecoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnderecoId(),
                    root -> root.join(Animal_.endereco, JoinType.LEFT).get(Endereco_.id)));
            }
            if (criteria.getVeterinarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getVeterinarioId(),
                    root -> root.join(Animal_.veterinario, JoinType.LEFT).get(AnimalVeterinario_.id)));
            }
            if (criteria.getRacaId() != null) {
                specification = specification.and(buildSpecification(criteria.getRacaId(),
                    root -> root.join(Animal_.raca, JoinType.LEFT).get(Raca_.id)));
            }
            if (criteria.getTutorId() != null) {
                specification = specification.and(buildSpecification(criteria.getTutorId(),
                    root -> root.join(Animal_.tutor, JoinType.LEFT).get(Tutor_.id)));
            }
        }
        return specification;
    }
}
