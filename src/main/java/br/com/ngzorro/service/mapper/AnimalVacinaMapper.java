package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalVacinaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalVacina} and its DTO {@link AnimalVacinaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnimalTipoDeVacinaMapper.class, AnimalMapper.class})
public interface AnimalVacinaMapper extends EntityMapper<AnimalVacinaDTO, AnimalVacina> {

    @Mapping(source = "animalTipoDeVacina.id", target = "animalTipoDeVacinaId")
    @Mapping(source = "animalTipoDeVacina.descricao", target = "animalTipoDeVacinaDescricao")
    @Mapping(source = "animal.id", target = "animalId")
    AnimalVacinaDTO toDto(AnimalVacina animalVacina);

    @Mapping(source = "animalTipoDeVacinaId", target = "animalTipoDeVacina")
    @Mapping(source = "animalId", target = "animal")
    AnimalVacina toEntity(AnimalVacinaDTO animalVacinaDTO);

    default AnimalVacina fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalVacina animalVacina = new AnimalVacina();
        animalVacina.setId(id);
        return animalVacina;
    }
}
