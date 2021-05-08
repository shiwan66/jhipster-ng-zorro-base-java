package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnimalVermifugoMapperTest {

    private AnimalVermifugoMapper animalVermifugoMapper;

    @BeforeEach
    public void setUp() {
        animalVermifugoMapper = new AnimalVermifugoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(animalVermifugoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(animalVermifugoMapper.fromId(null)).isNull();
    }
}
