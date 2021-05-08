package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnimalAlteracaoMapperTest {

    private AnimalAlteracaoMapper animalAlteracaoMapper;

    @BeforeEach
    public void setUp() {
        animalAlteracaoMapper = new AnimalAlteracaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(animalAlteracaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(animalAlteracaoMapper.fromId(null)).isNull();
    }
}
