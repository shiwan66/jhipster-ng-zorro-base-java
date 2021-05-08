package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class ConsumoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumoDTO.class);
        ConsumoDTO consumoDTO1 = new ConsumoDTO();
        consumoDTO1.setId(1L);
        ConsumoDTO consumoDTO2 = new ConsumoDTO();
        assertThat(consumoDTO1).isNotEqualTo(consumoDTO2);
        consumoDTO2.setId(consumoDTO1.getId());
        assertThat(consumoDTO1).isEqualTo(consumoDTO2);
        consumoDTO2.setId(2L);
        assertThat(consumoDTO1).isNotEqualTo(consumoDTO2);
        consumoDTO1.setId(null);
        assertThat(consumoDTO1).isNotEqualTo(consumoDTO2);
    }
}
