package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.MovimentacaoDeEstoqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MovimentacaoDeEstoque} and its DTO {@link MovimentacaoDeEstoqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConsumoMapper.class})
public interface MovimentacaoDeEstoqueMapper extends EntityMapper<MovimentacaoDeEstoqueDTO, MovimentacaoDeEstoque> {

    @Mapping(source = "consumo.id", target = "consumoId")
    MovimentacaoDeEstoqueDTO toDto(MovimentacaoDeEstoque movimentacaoDeEstoque);

    @Mapping(source = "consumoId", target = "consumo")
    MovimentacaoDeEstoque toEntity(MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO);

    default MovimentacaoDeEstoque fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovimentacaoDeEstoque movimentacaoDeEstoque = new MovimentacaoDeEstoque();
        movimentacaoDeEstoque.setId(id);
        return movimentacaoDeEstoque;
    }
}
