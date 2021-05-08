package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalTipoDeAlteracaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalTipoDeAlteracaoDTO.class);
        AnimalTipoDeAlteracaoDTO animalTipoDeAlteracaoDTO1 = new AnimalTipoDeAlteracaoDTO();
        animalTipoDeAlteracaoDTO1.setId(1L);
        AnimalTipoDeAlteracaoDTO animalTipoDeAlteracaoDTO2 = new AnimalTipoDeAlteracaoDTO();
        assertThat(animalTipoDeAlteracaoDTO1).isNotEqualTo(animalTipoDeAlteracaoDTO2);
        animalTipoDeAlteracaoDTO2.setId(animalTipoDeAlteracaoDTO1.getId());
        assertThat(animalTipoDeAlteracaoDTO1).isEqualTo(animalTipoDeAlteracaoDTO2);
        animalTipoDeAlteracaoDTO2.setId(2L);
        assertThat(animalTipoDeAlteracaoDTO1).isNotEqualTo(animalTipoDeAlteracaoDTO2);
        animalTipoDeAlteracaoDTO1.setId(null);
        assertThat(animalTipoDeAlteracaoDTO1).isNotEqualTo(animalTipoDeAlteracaoDTO2);
    }
}
