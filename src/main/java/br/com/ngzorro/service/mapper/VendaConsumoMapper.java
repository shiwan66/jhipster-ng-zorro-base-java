package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.VendaConsumoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VendaConsumo} and its DTO {@link VendaConsumoDTO}.
 */
@Mapper(componentModel = "spring", uses = {VendaMapper.class, ConsumoMapper.class})
public interface VendaConsumoMapper extends EntityMapper<VendaConsumoDTO, VendaConsumo> {

    @Mapping(source = "venda.id", target = "vendaId")
    @Mapping(source = "consumo.id", target = "consumoId")
    VendaConsumoDTO toDto(VendaConsumo vendaConsumo);

    @Mapping(source = "vendaId", target = "venda")
    @Mapping(source = "consumoId", target = "consumo")
    VendaConsumo toEntity(VendaConsumoDTO vendaConsumoDTO);

    default VendaConsumo fromId(Long id) {
        if (id == null) {
            return null;
        }
        VendaConsumo vendaConsumo = new VendaConsumo();
        vendaConsumo.setId(id);
        return vendaConsumo;
    }
}
