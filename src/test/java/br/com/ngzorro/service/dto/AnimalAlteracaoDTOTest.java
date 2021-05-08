package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalAlteracaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalAlteracaoDTO.class);
        AnimalAlteracaoDTO animalAlteracaoDTO1 = new AnimalAlteracaoDTO();
        animalAlteracaoDTO1.setId(1L);
        AnimalAlteracaoDTO animalAlteracaoDTO2 = new AnimalAlteracaoDTO();
        assertThat(animalAlteracaoDTO1).isNotEqualTo(animalAlteracaoDTO2);
        animalAlteracaoDTO2.setId(animalAlteracaoDTO1.getId());
        assertThat(animalAlteracaoDTO1).isEqualTo(animalAlteracaoDTO2);
        animalAlteracaoDTO2.setId(2L);
        assertThat(animalAlteracaoDTO1).isNotEqualTo(animalAlteracaoDTO2);
        animalAlteracaoDTO1.setId(null);
        assertThat(animalAlteracaoDTO1).isNotEqualTo(animalAlteracaoDTO2);
    }
}
