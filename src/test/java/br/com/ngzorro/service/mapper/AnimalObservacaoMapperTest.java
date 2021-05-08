package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnimalObservacaoMapperTest {

    private AnimalObservacaoMapper animalObservacaoMapper;

    @BeforeEach
    public void setUp() {
        animalObservacaoMapper = new AnimalObservacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(animalObservacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(animalObservacaoMapper.fromId(null)).isNull();
    }
}
