package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalVacinaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalVacina.class);
        AnimalVacina animalVacina1 = new AnimalVacina();
        animalVacina1.setId(1L);
        AnimalVacina animalVacina2 = new AnimalVacina();
        animalVacina2.setId(animalVacina1.getId());
        assertThat(animalVacina1).isEqualTo(animalVacina2);
        animalVacina2.setId(2L);
        assertThat(animalVacina1).isNotEqualTo(animalVacina2);
        animalVacina1.setId(null);
        assertThat(animalVacina1).isNotEqualTo(animalVacina2);
    }
}
