package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnimalMapperTest {

    private AnimalMapper animalMapper;

    @BeforeEach
    public void setUp() {
        animalMapper = new AnimalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(animalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(animalMapper.fromId(null)).isNull();
    }
}
