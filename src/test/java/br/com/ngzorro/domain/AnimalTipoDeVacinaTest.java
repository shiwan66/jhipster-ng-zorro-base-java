package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalTipoDeVacinaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalTipoDeVacina.class);
        AnimalTipoDeVacina animalTipoDeVacina1 = new AnimalTipoDeVacina();
        animalTipoDeVacina1.setId(1L);
        AnimalTipoDeVacina animalTipoDeVacina2 = new AnimalTipoDeVacina();
        animalTipoDeVacina2.setId(animalTipoDeVacina1.getId());
        assertThat(animalTipoDeVacina1).isEqualTo(animalTipoDeVacina2);
        animalTipoDeVacina2.setId(2L);
        assertThat(animalTipoDeVacina1).isNotEqualTo(animalTipoDeVacina2);
        animalTipoDeVacina1.setId(null);
        assertThat(animalTipoDeVacina1).isNotEqualTo(animalTipoDeVacina2);
    }
}
