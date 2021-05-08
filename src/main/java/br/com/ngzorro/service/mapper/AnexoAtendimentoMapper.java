package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnexoAtendimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnexoAtendimento} and its DTO {@link AnexoAtendimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AtendimentoMapper.class})
public interface AnexoAtendimentoMapper extends EntityMapper<AnexoAtendimentoDTO, AnexoAtendimento> {

    @Mapping(source = "atendimento.id", target = "atendimentoId")
    AnexoAtendimentoDTO toDto(AnexoAtendimento anexoAtendimento);

    @Mapping(source = "atendimentoId", target = "atendimento")
    AnexoAtendimento toEntity(AnexoAtendimentoDTO anexoAtendimentoDTO);

    default AnexoAtendimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnexoAtendimento anexoAtendimento = new AnexoAtendimento();
        anexoAtendimento.setId(id);
        return anexoAtendimento;
    }
}
