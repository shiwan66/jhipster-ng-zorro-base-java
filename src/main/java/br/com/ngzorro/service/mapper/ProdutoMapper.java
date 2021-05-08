package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.ProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produto} and its DTO {@link ProdutoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {


    @Mapping(target = "removeCategoria", ignore = true)

    default Produto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produto produto = new Produto();
        produto.setId(id);
        return produto;
    }
}
