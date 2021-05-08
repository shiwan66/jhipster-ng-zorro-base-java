package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class TituloTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Titulo.class);
        Titulo titulo1 = new Titulo();
        titulo1.setId(1L);
        Titulo titulo2 = new Titulo();
        titulo2.setId(titulo1.getId());
        assertThat(titulo1).isEqualTo(titulo2);
        titulo2.setId(2L);
        assertThat(titulo1).isNotEqualTo(titulo2);
        titulo1.setId(null);
        assertThat(titulo1).isNotEqualTo(titulo2);
    }
}
