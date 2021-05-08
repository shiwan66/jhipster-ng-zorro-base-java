package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ModeloAtividadeMapperTest {

    private ModeloAtividadeMapper modeloAtividadeMapper;

    @BeforeEach
    public void setUp() {
        modeloAtividadeMapper = new ModeloAtividadeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(modeloAtividadeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(modeloAtividadeMapper.fromId(null)).isNull();
    }
}
