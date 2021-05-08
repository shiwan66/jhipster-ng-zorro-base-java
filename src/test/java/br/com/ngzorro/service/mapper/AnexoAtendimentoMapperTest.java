package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AnexoAtendimentoMapperTest {

    private AnexoAtendimentoMapper anexoAtendimentoMapper;

    @BeforeEach
    public void setUp() {
        anexoAtendimentoMapper = new AnexoAtendimentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(anexoAtendimentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(anexoAtendimentoMapper.fromId(null)).isNull();
    }
}
