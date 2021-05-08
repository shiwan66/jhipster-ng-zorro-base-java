package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnexoMapperTest {

    private AnexoMapper anexoMapper;

    @BeforeEach
    public void setUp() {
        anexoMapper = new AnexoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(anexoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(anexoMapper.fromId(null)).isNull();
    }
}
