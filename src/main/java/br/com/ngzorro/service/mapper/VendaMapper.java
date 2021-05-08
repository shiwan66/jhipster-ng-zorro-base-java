package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.VendaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venda} and its DTO {@link VendaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AtendimentoMapper.class})
public interface VendaMapper extends EntityMapper<VendaDTO, Venda> {

    @Mapping(source = "atendimento.id", target = "atendimentoId")
    VendaDTO toDto(Venda venda);

    @Mapping(target = "vendaConsumos", ignore = true)
    @Mapping(target = "removeVendaConsumo", ignore = true)
    @Mapping(source = "atendimentoId", target = "atendimento")
    Venda toEntity(VendaDTO vendaDTO);

    default Venda fromId(Long id) {
        if (id == null) {
            return null;
        }
        Venda venda = new Venda();
        venda.setId(id);
        return venda;
    }
}
