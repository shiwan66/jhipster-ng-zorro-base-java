package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MovimentacaoDeEstoqueMapperTest {

    private MovimentacaoDeEstoqueMapper movimentacaoDeEstoqueMapper;

    @BeforeEach
    public void setUp() {
        movimentacaoDeEstoqueMapper = new MovimentacaoDeEstoqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(movimentacaoDeEstoqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movimentacaoDeEstoqueMapper.fromId(null)).isNull();
    }
}
