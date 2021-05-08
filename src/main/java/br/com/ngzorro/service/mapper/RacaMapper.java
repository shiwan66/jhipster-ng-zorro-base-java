package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.RacaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Raca} and its DTO {@link RacaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RacaMapper extends EntityMapper<RacaDTO, Raca> {



    default Raca fromId(Long id) {
        if (id == null) {
            return null;
        }
        Raca raca = new Raca();
        raca.setId(id);
        return raca;
    }
}
