package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalObservacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalObservacao.class);
        AnimalObservacao animalObservacao1 = new AnimalObservacao();
        animalObservacao1.setId(1L);
        AnimalObservacao animalObservacao2 = new AnimalObservacao();
        animalObservacao2.setId(animalObservacao1.getId());
        assertThat(animalObservacao1).isEqualTo(animalObservacao2);
        animalObservacao2.setId(2L);
        assertThat(animalObservacao1).isNotEqualTo(animalObservacao2);
        animalObservacao1.setId(null);
        assertThat(animalObservacao1).isNotEqualTo(animalObservacao2);
    }
}
