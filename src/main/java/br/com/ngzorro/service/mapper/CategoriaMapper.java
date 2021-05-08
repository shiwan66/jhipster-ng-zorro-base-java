package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.CategoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categoria} and its DTO {@link CategoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaMapper extends EntityMapper<CategoriaDTO, Categoria> {


    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProduto", ignore = true)
    Categoria toEntity(CategoriaDTO categoriaDTO);

    default Categoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }
}
