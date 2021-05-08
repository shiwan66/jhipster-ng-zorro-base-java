package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalTipoDeVacinaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalTipoDeVacina} and its DTO {@link AnimalTipoDeVacinaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnimalTipoDeVacinaMapper extends EntityMapper<AnimalTipoDeVacinaDTO, AnimalTipoDeVacina> {



    default AnimalTipoDeVacina fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalTipoDeVacina animalTipoDeVacina = new AnimalTipoDeVacina();
        animalTipoDeVacina.setId(id);
        return animalTipoDeVacina;
    }
}
