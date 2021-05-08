package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnexoAtendimentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoAtendimento.class);
        AnexoAtendimento anexoAtendimento1 = new AnexoAtendimento();
        anexoAtendimento1.setId(1L);
        AnexoAtendimento anexoAtendimento2 = new AnexoAtendimento();
        anexoAtendimento2.setId(anexoAtendimento1.getId());
        assertThat(anexoAtendimento1).isEqualTo(anexoAtendimento2);
        anexoAtendimento2.setId(2L);
        assertThat(anexoAtendimento1).isNotEqualTo(anexoAtendimento2);
        anexoAtendimento1.setId(null);
        assertThat(anexoAtendimento1).isNotEqualTo(anexoAtendimento2);
    }
}
