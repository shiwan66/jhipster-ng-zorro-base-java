package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalTipoDeVacinaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalTipoDeVacinaDTO.class);
        AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO1 = new AnimalTipoDeVacinaDTO();
        animalTipoDeVacinaDTO1.setId(1L);
        AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO2 = new AnimalTipoDeVacinaDTO();
        assertThat(animalTipoDeVacinaDTO1).isNotEqualTo(animalTipoDeVacinaDTO2);
        animalTipoDeVacinaDTO2.setId(animalTipoDeVacinaDTO1.getId());
        assertThat(animalTipoDeVacinaDTO1).isEqualTo(animalTipoDeVacinaDTO2);
        animalTipoDeVacinaDTO2.setId(2L);
        assertThat(animalTipoDeVacinaDTO1).isNotEqualTo(animalTipoDeVacinaDTO2);
        animalTipoDeVacinaDTO1.setId(null);
        assertThat(animalTipoDeVacinaDTO1).isNotEqualTo(animalTipoDeVacinaDTO2);
    }
}
