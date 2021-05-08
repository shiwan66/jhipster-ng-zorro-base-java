package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.ConsumoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consumo} and its DTO {@link ConsumoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConsumoMapper extends EntityMapper<ConsumoDTO, Consumo> {


    @Mapping(target = "vendaConsumos", ignore = true)
    @Mapping(target = "removeVendaConsumo", ignore = true)
    @Mapping(target = "movimentacaoDeEstoques", ignore = true)
    @Mapping(target = "removeMovimentacaoDeEstoque", ignore = true)
    Consumo toEntity(ConsumoDTO consumoDTO);

    default Consumo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consumo consumo = new Consumo();
        consumo.setId(id);
        return consumo;
    }
}
