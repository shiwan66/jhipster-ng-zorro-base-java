package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnexoAtendimentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoAtendimentoDTO.class);
        AnexoAtendimentoDTO anexoAtendimentoDTO1 = new AnexoAtendimentoDTO();
        anexoAtendimentoDTO1.setId(1L);
        AnexoAtendimentoDTO anexoAtendimentoDTO2 = new AnexoAtendimentoDTO();
        assertThat(anexoAtendimentoDTO1).isNotEqualTo(anexoAtendimentoDTO2);
        anexoAtendimentoDTO2.setId(anexoAtendimentoDTO1.getId());
        assertThat(anexoAtendimentoDTO1).isEqualTo(anexoAtendimentoDTO2);
        anexoAtendimentoDTO2.setId(2L);
        assertThat(anexoAtendimentoDTO1).isNotEqualTo(anexoAtendimentoDTO2);
        anexoAtendimentoDTO1.setId(null);
        assertThat(anexoAtendimentoDTO1).isNotEqualTo(anexoAtendimentoDTO2);
    }
}
