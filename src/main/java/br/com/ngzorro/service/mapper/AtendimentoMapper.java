package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AtendimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Atendimento} and its DTO {@link AtendimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnimalMapper.class})
public interface AtendimentoMapper extends EntityMapper<AtendimentoDTO, Atendimento> {

    @Mapping(source = "animal.id", target = "animalId")
    AtendimentoDTO toDto(Atendimento atendimento);

    @Mapping(target = "atividades", ignore = true)
    @Mapping(target = "removeAtividade", ignore = true)
    @Mapping(target = "vendas", ignore = true)
    @Mapping(target = "removeVenda", ignore = true)
    @Mapping(target = "anexos", ignore = true)
    @Mapping(target = "removeAnexo", ignore = true)
    @Mapping(source = "animalId", target = "animal")
    Atendimento toEntity(AtendimentoDTO atendimentoDTO);

    default Atendimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Atendimento atendimento = new Atendimento();
        atendimento.setId(id);
        return atendimento;
    }
}
