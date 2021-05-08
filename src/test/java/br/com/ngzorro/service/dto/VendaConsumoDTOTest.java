package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class VendaConsumoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendaConsumoDTO.class);
        VendaConsumoDTO vendaConsumoDTO1 = new VendaConsumoDTO();
        vendaConsumoDTO1.setId(1L);
        VendaConsumoDTO vendaConsumoDTO2 = new VendaConsumoDTO();
        assertThat(vendaConsumoDTO1).isNotEqualTo(vendaConsumoDTO2);
        vendaConsumoDTO2.setId(vendaConsumoDTO1.getId());
        assertThat(vendaConsumoDTO1).isEqualTo(vendaConsumoDTO2);
        vendaConsumoDTO2.setId(2L);
        assertThat(vendaConsumoDTO1).isNotEqualTo(vendaConsumoDTO2);
        vendaConsumoDTO1.setId(null);
        assertThat(vendaConsumoDTO1).isNotEqualTo(vendaConsumoDTO2);
    }
}
