package br.com.ngzorro.service.mapper;

import br.com.ngzorro.domain.*;
import br.com.ngzorro.service.dto.AnimalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Animal} and its DTO {@link AnimalDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnderecoMapper.class, AnimalVeterinarioMapper.class, RacaMapper.class, TutorMapper.class})
public interface AnimalMapper extends EntityMapper<AnimalDTO, Animal> {

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "endereco.logradouro", target = "enderecoLogradouro")
    @Mapping(source = "veterinario.id", target = "veterinarioId")
    @Mapping(source = "veterinario.nome", target = "veterinarioNome")
    @Mapping(source = "raca.id", target = "racaId")
    @Mapping(source = "raca.nome", target = "racaNome")
    @Mapping(source = "tutor.id", target = "tutorId")
    AnimalDTO toDto(Animal animal);

    @Mapping(target = "atendimentos", ignore = true)
    @Mapping(target = "removeAtendimento", ignore = true)
    @Mapping(target = "tipoVacinas", ignore = true)
    @Mapping(target = "removeTipoVacina", ignore = true)
    @Mapping(target = "animalAlteracaos", ignore = true)
    @Mapping(target = "removeAnimalAlteracao", ignore = true)
    @Mapping(target = "animalVermifugos", ignore = true)
    @Mapping(target = "removeAnimalVermifugo", ignore = true)
    @Mapping(target = "animalCarrapaticidas", ignore = true)
    @Mapping(target = "removeAnimalCarrapaticida", ignore = true)
    @Mapping(target = "observacaos", ignore = true)
    @Mapping(target = "removeObservacao", ignore = true)
    @Mapping(target = "anexos", ignore = true)
    @Mapping(target = "removeAnexo", ignore = true)
    @Mapping(target = "animalCios", ignore = true)
    @Mapping(target = "removeAnimalCio", ignore = true)
    @Mapping(source = "enderecoId", target = "endereco")
    @Mapping(source = "veterinarioId", target = "veterinario")
    @Mapping(source = "racaId", target = "raca")
    @Mapping(source = "tutorId", target = "tutor")
    Animal toEntity(AnimalDTO animalDTO);

    default Animal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Animal animal = new Animal();
        animal.setId(id);
        return animal;
    }
}
