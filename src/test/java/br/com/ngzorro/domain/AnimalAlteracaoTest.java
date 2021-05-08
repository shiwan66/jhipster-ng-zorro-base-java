package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalAlteracaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalAlteracao.class);
        AnimalAlteracao animalAlteracao1 = new AnimalAlteracao();
        animalAlteracao1.setId(1L);
        AnimalAlteracao animalAlteracao2 = new AnimalAlteracao();
        animalAlteracao2.setId(animalAlteracao1.getId());
        assertThat(animalAlteracao1).isEqualTo(animalAlteracao2);
        animalAlteracao2.setId(2L);
        assertThat(animalAlteracao1).isNotEqualTo(animalAlteracao2);
        animalAlteracao1.setId(null);
        assertThat(animalAlteracao1).isNotEqualTo(animalAlteracao2);
    }
}
