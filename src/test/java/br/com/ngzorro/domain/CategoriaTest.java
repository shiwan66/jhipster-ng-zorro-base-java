package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class CategoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categoria.class);
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        Categoria categoria2 = new Categoria();
        categoria2.setId(categoria1.getId());
        assertThat(categoria1).isEqualTo(categoria2);
        categoria2.setId(2L);
        assertThat(categoria1).isNotEqualTo(categoria2);
        categoria1.setId(null);
        assertThat(categoria1).isNotEqualTo(categoria2);
    }
}
