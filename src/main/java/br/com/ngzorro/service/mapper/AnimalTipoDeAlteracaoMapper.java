package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalTipoDeAlteracaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalTipoDeAlteracao} and its DTO {@link AnimalTipoDeAlteracaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnimalTipoDeAlteracaoMapper extends EntityMapper<AnimalTipoDeAlteracaoDTO, AnimalTipoDeAlteracao> {



    default AnimalTipoDeAlteracao fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalTipoDeAlteracao animalTipoDeAlteracao = new AnimalTipoDeAlteracao();
        animalTipoDeAlteracao.setId(id);
        return animalTipoDeAlteracao;
    }
}
