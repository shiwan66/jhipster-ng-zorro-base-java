package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalCioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalCio.class);
        AnimalCio animalCio1 = new AnimalCio();
        animalCio1.setId(1L);
        AnimalCio animalCio2 = new AnimalCio();
        animalCio2.setId(animalCio1.getId());
        assertThat(animalCio1).isEqualTo(animalCio2);
        animalCio2.setId(2L);
        assertThat(animalCio1).isNotEqualTo(animalCio2);
        animalCio1.setId(null);
        assertThat(animalCio1).isNotEqualTo(animalCio2);
    }
}
