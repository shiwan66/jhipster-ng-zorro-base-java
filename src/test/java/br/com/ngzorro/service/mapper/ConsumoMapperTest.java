package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ConsumoMapperTest {

    private ConsumoMapper consumoMapper;

    @BeforeEach
    public void setUp() {
        consumoMapper = new ConsumoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(consumoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(consumoMapper.fromId(null)).isNull();
    }
}
