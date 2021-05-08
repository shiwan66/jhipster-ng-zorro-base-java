package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnimalCarrapaticidaMapperTest {

    private AnimalCarrapaticidaMapper animalCarrapaticidaMapper;

    @BeforeEach
    public void setUp() {
        animalCarrapaticidaMapper = new AnimalCarrapaticidaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(animalCarrapaticidaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(animalCarrapaticidaMapper.fromId(null)).isNull();
    }
}
