package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class TituloDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TituloDTO.class);
        TituloDTO tituloDTO1 = new TituloDTO();
        tituloDTO1.setId(1L);
        TituloDTO tituloDTO2 = new TituloDTO();
        assertThat(tituloDTO1).isNotEqualTo(tituloDTO2);
        tituloDTO2.setId(tituloDTO1.getId());
        assertThat(tituloDTO1).isEqualTo(tituloDTO2);
        tituloDTO2.setId(2L);
        assertThat(tituloDTO1).isNotEqualTo(tituloDTO2);
        tituloDTO1.setId(null);
        assertThat(tituloDTO1).isNotEqualTo(tituloDTO2);
    }
}
