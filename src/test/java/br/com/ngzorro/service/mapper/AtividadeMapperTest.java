package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AtividadeMapperTest {

    private AtividadeMapper atividadeMapper;

    @BeforeEach
    public void setUp() {
        atividadeMapper = new AtividadeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(atividadeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(atividadeMapper.fromId(null)).isNull();
    }
}
