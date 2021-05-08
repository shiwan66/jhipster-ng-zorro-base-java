package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class ModeloAtividadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeloAtividade.class);
        ModeloAtividade modeloAtividade1 = new ModeloAtividade();
        modeloAtividade1.setId(1L);
        ModeloAtividade modeloAtividade2 = new ModeloAtividade();
        modeloAtividade2.setId(modeloAtividade1.getId());
        assertThat(modeloAtividade1).isEqualTo(modeloAtividade2);
        modeloAtividade2.setId(2L);
        assertThat(modeloAtividade1).isNotEqualTo(modeloAtividade2);
        modeloAtividade1.setId(null);
        assertThat(modeloAtividade1).isNotEqualTo(modeloAtividade2);
    }
}
