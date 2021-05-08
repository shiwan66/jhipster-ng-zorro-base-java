package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalVeterinarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalVeterinario.class);
        AnimalVeterinario animalVeterinario1 = new AnimalVeterinario();
        animalVeterinario1.setId(1L);
        AnimalVeterinario animalVeterinario2 = new AnimalVeterinario();
        animalVeterinario2.setId(animalVeterinario1.getId());
        assertThat(animalVeterinario1).isEqualTo(animalVeterinario2);
        animalVeterinario2.setId(2L);
        assertThat(animalVeterinario1).isNotEqualTo(animalVeterinario2);
        animalVeterinario1.setId(null);
        assertThat(animalVeterinario1).isNotEqualTo(animalVeterinario2);
    }
}
