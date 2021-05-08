package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class MovimentacaoDeEstoqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimentacaoDeEstoque.class);
        MovimentacaoDeEstoque movimentacaoDeEstoque1 = new MovimentacaoDeEstoque();
        movimentacaoDeEstoque1.setId(1L);
        MovimentacaoDeEstoque movimentacaoDeEstoque2 = new MovimentacaoDeEstoque();
        movimentacaoDeEstoque2.setId(movimentacaoDeEstoque1.getId());
        assertThat(movimentacaoDeEstoque1).isEqualTo(movimentacaoDeEstoque2);
        movimentacaoDeEstoque2.setId(2L);
        assertThat(movimentacaoDeEstoque1).isNotEqualTo(movimentacaoDeEstoque2);
        movimentacaoDeEstoque1.setId(null);
        assertThat(movimentacaoDeEstoque1).isNotEqualTo(movimentacaoDeEstoque2);
    }
}
