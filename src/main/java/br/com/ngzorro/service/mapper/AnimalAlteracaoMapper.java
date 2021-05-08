package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalAlteracaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalAlteracao} and its DTO {@link AnimalAlteracaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnimalTipoDeAlteracaoMapper.class, AnimalMapper.class})
public interface AnimalAlteracaoMapper extends EntityMapper<AnimalAlteracaoDTO, AnimalAlteracao> {

    @Mapping(source = "animalTipoDeAlteracao.id", target = "animalTipoDeAlteracaoId")
    @Mapping(source = "animalTipoDeAlteracao.descricao", target = "animalTipoDeAlteracaoDescricao")
    @Mapping(source = "animal.id", target = "animalId")
    AnimalAlteracaoDTO toDto(AnimalAlteracao animalAlteracao);

    @Mapping(source = "animalTipoDeAlteracaoId", target = "animalTipoDeAlteracao")
    @Mapping(source = "animalId", target = "animal")
    AnimalAlteracao toEntity(AnimalAlteracaoDTO animalAlteracaoDTO);

    default AnimalAlteracao fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalAlteracao animalAlteracao = new AnimalAlteracao();
        animalAlteracao.setId(id);
        return animalAlteracao;
    }
}
