package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalCarrapaticidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalCarrapaticida} and its DTO {@link AnimalCarrapaticidaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnimalMapper.class})
public interface AnimalCarrapaticidaMapper extends EntityMapper<AnimalCarrapaticidaDTO, AnimalCarrapaticida> {

    @Mapping(source = "animal.id", target = "animalId")
    AnimalCarrapaticidaDTO toDto(AnimalCarrapaticida animalCarrapaticida);

    @Mapping(source = "animalId", target = "animal")
    AnimalCarrapaticida toEntity(AnimalCarrapaticidaDTO animalCarrapaticidaDTO);

    default AnimalCarrapaticida fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalCarrapaticida animalCarrapaticida = new AnimalCarrapaticida();
        animalCarrapaticida.setId(id);
        return animalCarrapaticida;
    }
}
