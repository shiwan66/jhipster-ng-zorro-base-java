package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalVacinaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalVacinaDTO.class);
        AnimalVacinaDTO animalVacinaDTO1 = new AnimalVacinaDTO();
        animalVacinaDTO1.setId(1L);
        AnimalVacinaDTO animalVacinaDTO2 = new AnimalVacinaDTO();
        assertThat(animalVacinaDTO1).isNotEqualTo(animalVacinaDTO2);
        animalVacinaDTO2.setId(animalVacinaDTO1.getId());
        assertThat(animalVacinaDTO1).isEqualTo(animalVacinaDTO2);
        animalVacinaDTO2.setId(2L);
        assertThat(animalVacinaDTO1).isNotEqualTo(animalVacinaDTO2);
        animalVacinaDTO1.setId(null);
        assertThat(animalVacinaDTO1).isNotEqualTo(animalVacinaDTO2);
    }
}
