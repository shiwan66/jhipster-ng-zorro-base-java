package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.ModeloAtividadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModeloAtividade} and its DTO {@link ModeloAtividadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {AtividadeMapper.class})
public interface ModeloAtividadeMapper extends EntityMapper<ModeloAtividadeDTO, ModeloAtividade> {


    @Mapping(target = "removeAtividade", ignore = true)

    default ModeloAtividade fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModeloAtividade modeloAtividade = new ModeloAtividade();
        modeloAtividade.setId(id);
        return modeloAtividade;
    }
}
