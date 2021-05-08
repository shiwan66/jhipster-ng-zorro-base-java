package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnimalVeterinarioMapperTest {

    private AnimalVeterinarioMapper animalVeterinarioMapper;

    @BeforeEach
    public void setUp() {
        animalVeterinarioMapper = new AnimalVeterinarioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(animalVeterinarioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(animalVeterinarioMapper.fromId(null)).isNull();
    }
}
