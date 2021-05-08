package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalVeterinarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalVeterinario} and its DTO {@link AnimalVeterinarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnimalVeterinarioMapper extends EntityMapper<AnimalVeterinarioDTO, AnimalVeterinario> {



    default AnimalVeterinario fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalVeterinario animalVeterinario = new AnimalVeterinario();
        animalVeterinario.setId(id);
        return animalVeterinario;
    }
}
