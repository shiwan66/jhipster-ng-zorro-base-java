package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnexoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Anexo} and its DTO {@link AnexoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnimalMapper.class})
public interface AnexoMapper extends EntityMapper<AnexoDTO, Anexo> {

    @Mapping(source = "animal.id", target = "animalId")
    AnexoDTO toDto(Anexo anexo);

    @Mapping(source = "animalId", target = "animal")
    Anexo toEntity(AnexoDTO anexoDTO);

    default Anexo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Anexo anexo = new Anexo();
        anexo.setId(id);
        return anexo;
    }
}
