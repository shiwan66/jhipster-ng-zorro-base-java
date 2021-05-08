package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AtendimentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtendimentoDTO.class);
        AtendimentoDTO atendimentoDTO1 = new AtendimentoDTO();
        atendimentoDTO1.setId(1L);
        AtendimentoDTO atendimentoDTO2 = new AtendimentoDTO();
        assertThat(atendimentoDTO1).isNotEqualTo(atendimentoDTO2);
        atendimentoDTO2.setId(atendimentoDTO1.getId());
        assertThat(atendimentoDTO1).isEqualTo(atendimentoDTO2);
        atendimentoDTO2.setId(2L);
        assertThat(atendimentoDTO1).isNotEqualTo(atendimentoDTO2);
        atendimentoDTO1.setId(null);
        assertThat(atendimentoDTO1).isNotEqualTo(atendimentoDTO2);
    }
}
