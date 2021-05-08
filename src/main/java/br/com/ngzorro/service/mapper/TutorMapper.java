package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.TutorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tutor} and its DTO {@link TutorDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EnderecoMapper.class})
public interface TutorMapper extends EntityMapper<TutorDTO, Tutor> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "endereco.logradouro", target = "enderecoLogradouro")
    TutorDTO toDto(Tutor tutor);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "titulos", ignore = true)
    @Mapping(target = "removeTitulo", ignore = true)
    @Mapping(target = "animals", ignore = true)
    @Mapping(target = "removeAnimal", ignore = true)
    @Mapping(source = "enderecoId", target = "endereco")
    Tutor toEntity(TutorDTO tutorDTO);

    default Tutor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tutor tutor = new Tutor();
        tutor.setId(id);
        return tutor;
    }
}
