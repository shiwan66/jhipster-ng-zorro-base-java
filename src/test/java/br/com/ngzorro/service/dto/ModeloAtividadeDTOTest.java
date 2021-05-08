package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class ModeloAtividadeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeloAtividadeDTO.class);
        ModeloAtividadeDTO modeloAtividadeDTO1 = new ModeloAtividadeDTO();
        modeloAtividadeDTO1.setId(1L);
        ModeloAtividadeDTO modeloAtividadeDTO2 = new ModeloAtividadeDTO();
        assertThat(modeloAtividadeDTO1).isNotEqualTo(modeloAtividadeDTO2);
        modeloAtividadeDTO2.setId(modeloAtividadeDTO1.getId());
        assertThat(modeloAtividadeDTO1).isEqualTo(modeloAtividadeDTO2);
        modeloAtividadeDTO2.setId(2L);
        assertThat(modeloAtividadeDTO1).isNotEqualTo(modeloAtividadeDTO2);
        modeloAtividadeDTO1.setId(null);
        assertThat(modeloAtividadeDTO1).isNotEqualTo(modeloAtividadeDTO2);
    }
}
