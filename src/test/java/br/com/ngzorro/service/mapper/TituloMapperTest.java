package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TituloMapperTest {

    private TituloMapper tituloMapper;

    @BeforeEach
    public void setUp() {
        tituloMapper = new TituloMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(tituloMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tituloMapper.fromId(null)).isNull();
    }
}
