package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AtendimentoMapperTest {

    private AtendimentoMapper atendimentoMapper;

    @BeforeEach
    public void setUp() {
        atendimentoMapper = new AtendimentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(atendimentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(atendimentoMapper.fromId(null)).isNull();
    }
}
