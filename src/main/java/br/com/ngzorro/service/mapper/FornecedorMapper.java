package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.FornecedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fornecedor} and its DTO {@link FornecedorDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface FornecedorMapper extends EntityMapper<FornecedorDTO, Fornecedor> {

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "endereco.logradouro", target = "enderecoLogradouro")
    FornecedorDTO toDto(Fornecedor fornecedor);

    @Mapping(target = "titulos", ignore = true)
    @Mapping(target = "removeTitulo", ignore = true)
    @Mapping(source = "enderecoId", target = "endereco")
    Fornecedor toEntity(FornecedorDTO fornecedorDTO);

    default Fornecedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        return fornecedor;
    }
}
