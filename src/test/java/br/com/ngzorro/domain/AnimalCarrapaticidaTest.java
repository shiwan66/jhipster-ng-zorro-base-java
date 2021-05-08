package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalCarrapaticidaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalCarrapaticida.class);
        AnimalCarrapaticida animalCarrapaticida1 = new AnimalCarrapaticida();
        animalCarrapaticida1.setId(1L);
        AnimalCarrapaticida animalCarrapaticida2 = new AnimalCarrapaticida();
        animalCarrapaticida2.setId(animalCarrapaticida1.getId());
        assertThat(animalCarrapaticida1).isEqualTo(animalCarrapaticida2);
        animalCarrapaticida2.setId(2L);
        assertThat(animalCarrapaticida1).isNotEqualTo(animalCarrapaticida2);
        animalCarrapaticida1.setId(null);
        assertThat(animalCarrapaticida1).isNotEqualTo(animalCarrapaticida2);
    }
}
