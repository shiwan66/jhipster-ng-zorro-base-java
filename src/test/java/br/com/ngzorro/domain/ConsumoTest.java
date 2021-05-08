package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class ConsumoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consumo.class);
        Consumo consumo1 = new Consumo();
        consumo1.setId(1L);
        Consumo consumo2 = new Consumo();
        consumo2.setId(consumo1.getId());
        assertThat(consumo1).isEqualTo(consumo2);
        consumo2.setId(2L);
        assertThat(consumo1).isNotEqualTo(consumo2);
        consumo1.setId(null);
        assertThat(consumo1).isNotEqualTo(consumo2);
    }
}
