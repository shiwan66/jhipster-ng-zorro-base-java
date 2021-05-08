package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalVermifugoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalVermifugo} and its DTO {@link AnimalVermifugoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnimalMapper.class})
public interface AnimalVermifugoMapper extends EntityMapper<AnimalVermifugoDTO, AnimalVermifugo> {

    @Mapping(source = "animal.id", target = "animalId")
    AnimalVermifugoDTO toDto(AnimalVermifugo animalVermifugo);

    @Mapping(source = "animalId", target = "animal")
    AnimalVermifugo toEntity(AnimalVermifugoDTO animalVermifugoDTO);

    default AnimalVermifugo fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalVermifugo animalVermifugo = new AnimalVermifugo();
        animalVermifugo.setId(id);
        return animalVermifugo;
    }
}
