package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnimalTipoDeAlteracaoMapperTest {

    private AnimalTipoDeAlteracaoMapper animalTipoDeAlteracaoMapper;

    @BeforeEach
    public void setUp() {
        animalTipoDeAlteracaoMapper = new AnimalTipoDeAlteracaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(animalTipoDeAlteracaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(animalTipoDeAlteracaoMapper.fromId(null)).isNull();
    }
}
