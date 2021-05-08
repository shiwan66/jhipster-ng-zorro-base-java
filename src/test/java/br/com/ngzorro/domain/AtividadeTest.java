package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AtividadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Atividade.class);
        Atividade atividade1 = new Atividade();
        atividade1.setId(1L);
        Atividade atividade2 = new Atividade();
        atividade2.setId(atividade1.getId());
        assertThat(atividade1).isEqualTo(atividade2);
        atividade2.setId(2L);
        assertThat(atividade1).isNotEqualTo(atividade2);
        atividade1.setId(null);
        assertThat(atividade1).isNotEqualTo(atividade2);
    }
}
