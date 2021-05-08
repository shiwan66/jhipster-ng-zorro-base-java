package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AtividadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Atividade} and its DTO {@link AtividadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {AtendimentoMapper.class})
public interface AtividadeMapper extends EntityMapper<AtividadeDTO, Atividade> {

    @Mapping(source = "atendimento.id", target = "atendimentoId")
    AtividadeDTO toDto(Atividade atividade);

    @Mapping(source = "atendimentoId", target = "atendimento")
    @Mapping(target = "modeloAtividades", ignore = true)
    @Mapping(target = "removeModeloAtividade", ignore = true)
    Atividade toEntity(AtividadeDTO atividadeDTO);

    default Atividade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Atividade atividade = new Atividade();
        atividade.setId(id);
        return atividade;
    }
}
