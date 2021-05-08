package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class MovimentacaoDeEstoqueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimentacaoDeEstoqueDTO.class);
        MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO1 = new MovimentacaoDeEstoqueDTO();
        movimentacaoDeEstoqueDTO1.setId(1L);
        MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO2 = new MovimentacaoDeEstoqueDTO();
        assertThat(movimentacaoDeEstoqueDTO1).isNotEqualTo(movimentacaoDeEstoqueDTO2);
        movimentacaoDeEstoqueDTO2.setId(movimentacaoDeEstoqueDTO1.getId());
        assertThat(movimentacaoDeEstoqueDTO1).isEqualTo(movimentacaoDeEstoqueDTO2);
        movimentacaoDeEstoqueDTO2.setId(2L);
        assertThat(movimentacaoDeEstoqueDTO1).isNotEqualTo(movimentacaoDeEstoqueDTO2);
        movimentacaoDeEstoqueDTO1.setId(null);
        assertThat(movimentacaoDeEstoqueDTO1).isNotEqualTo(movimentacaoDeEstoqueDTO2);
    }
}
