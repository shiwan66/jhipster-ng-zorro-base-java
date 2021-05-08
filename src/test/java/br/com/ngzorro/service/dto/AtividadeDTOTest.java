package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AtividadeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtividadeDTO.class);
        AtividadeDTO atividadeDTO1 = new AtividadeDTO();
        atividadeDTO1.setId(1L);
        AtividadeDTO atividadeDTO2 = new AtividadeDTO();
        assertThat(atividadeDTO1).isNotEqualTo(atividadeDTO2);
        atividadeDTO2.setId(atividadeDTO1.getId());
        assertThat(atividadeDTO1).isEqualTo(atividadeDTO2);
        atividadeDTO2.setId(2L);
        assertThat(atividadeDTO1).isNotEqualTo(atividadeDTO2);
        atividadeDTO1.setId(null);
        assertThat(atividadeDTO1).isNotEqualTo(atividadeDTO2);
    }
}
