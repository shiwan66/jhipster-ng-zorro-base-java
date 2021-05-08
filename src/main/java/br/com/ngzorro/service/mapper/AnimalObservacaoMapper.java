package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalObservacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalObservacao} and its DTO {@link AnimalObservacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnimalMapper.class})
public interface AnimalObservacaoMapper extends EntityMapper<AnimalObservacaoDTO, AnimalObservacao> {

    @Mapping(source = "animal.id", target = "animalId")
    AnimalObservacaoDTO toDto(AnimalObservacao animalObservacao);

    @Mapping(source = "animalId", target = "animal")
    AnimalObservacao toEntity(AnimalObservacaoDTO animalObservacaoDTO);

    default AnimalObservacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalObservacao animalObservacao = new AnimalObservacao();
        animalObservacao.setId(id);
        return animalObservacao;
    }
}
