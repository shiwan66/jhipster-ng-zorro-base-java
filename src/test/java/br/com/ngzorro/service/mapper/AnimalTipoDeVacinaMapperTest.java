package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnimalTipoDeVacinaMapperTest {

    private AnimalTipoDeVacinaMapper animalTipoDeVacinaMapper;

    @BeforeEach
    public void setUp() {
        animalTipoDeVacinaMapper = new AnimalTipoDeVacinaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(animalTipoDeVacinaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(animalTipoDeVacinaMapper.fromId(null)).isNull();
    }
}
