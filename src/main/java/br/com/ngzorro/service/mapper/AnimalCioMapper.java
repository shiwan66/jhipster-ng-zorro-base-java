package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalCioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalCio} and its DTO {@link AnimalCioDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnimalMapper.class})
public interface AnimalCioMapper extends EntityMapper<AnimalCioDTO, AnimalCio> {

    @Mapping(source = "animal.id", target = "animalId")
    AnimalCioDTO toDto(AnimalCio animalCio);

    @Mapping(source = "animalId", target = "animal")
    AnimalCio toEntity(AnimalCioDTO animalCioDTO);

    default AnimalCio fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalCio animalCio = new AnimalCio();
        animalCio.setId(id);
        return animalCio;
    }
}
