package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnexoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoDTO.class);
        AnexoDTO anexoDTO1 = new AnexoDTO();
        anexoDTO1.setId(1L);
        AnexoDTO anexoDTO2 = new AnexoDTO();
        assertThat(anexoDTO1).isNotEqualTo(anexoDTO2);
        anexoDTO2.setId(anexoDTO1.getId());
        assertThat(anexoDTO1).isEqualTo(anexoDTO2);
        anexoDTO2.setId(2L);
        assertThat(anexoDTO1).isNotEqualTo(anexoDTO2);
        anexoDTO1.setId(null);
        assertThat(anexoDTO1).isNotEqualTo(anexoDTO2);
    }
}
