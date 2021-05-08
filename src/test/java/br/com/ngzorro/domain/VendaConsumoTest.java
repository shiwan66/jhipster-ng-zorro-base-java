package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class VendaConsumoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendaConsumo.class);
        VendaConsumo vendaConsumo1 = new VendaConsumo();
        vendaConsumo1.setId(1L);
        VendaConsumo vendaConsumo2 = new VendaConsumo();
        vendaConsumo2.setId(vendaConsumo1.getId());
        assertThat(vendaConsumo1).isEqualTo(vendaConsumo2);
        vendaConsumo2.setId(2L);
        assertThat(vendaConsumo1).isNotEqualTo(vendaConsumo2);
        vendaConsumo1.setId(null);
        assertThat(vendaConsumo1).isNotEqualTo(vendaConsumo2);
    }
}
