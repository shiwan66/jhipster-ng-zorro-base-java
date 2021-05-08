package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.TituloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Titulo} and its DTO {@link TituloDTO}.
 */
@Mapper(componentModel = "spring", uses = {TutorMapper.class, FornecedorMapper.class})
public interface TituloMapper extends EntityMapper<TituloDTO, Titulo> {

    @Mapping(source = "tutor.id", target = "tutorId")
    @Mapping(source = "fornecedor.id", target = "fornecedorId")
    TituloDTO toDto(Titulo titulo);

    @Mapping(source = "tutorId", target = "tutor")
    @Mapping(source = "fornecedorId", target = "fornecedor")
    Titulo toEntity(TituloDTO tituloDTO);

    default Titulo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Titulo titulo = new Titulo();
        titulo.setId(id);
        return titulo;
    }
}
