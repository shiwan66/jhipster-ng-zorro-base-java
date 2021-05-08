package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnimalVacinaMapperTest {

    private AnimalVacinaMapper animalVacinaMapper;

    @BeforeEach
    public void setUp() {
        animalVacinaMapper = new AnimalVacinaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(animalVacinaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(animalVacinaMapper.fromId(null)).isNull();
    }
}
