package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalTipoDeAlteracaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalTipoDeAlteracao.class);
        AnimalTipoDeAlteracao animalTipoDeAlteracao1 = new AnimalTipoDeAlteracao();
        animalTipoDeAlteracao1.setId(1L);
        AnimalTipoDeAlteracao animalTipoDeAlteracao2 = new AnimalTipoDeAlteracao();
        animalTipoDeAlteracao2.setId(animalTipoDeAlteracao1.getId());
        assertThat(animalTipoDeAlteracao1).isEqualTo(animalTipoDeAlteracao2);
        animalTipoDeAlteracao2.setId(2L);
        assertThat(animalTipoDeAlteracao1).isNotEqualTo(animalTipoDeAlteracao2);
        animalTipoDeAlteracao1.setId(null);
        assertThat(animalTipoDeAlteracao1).isNotEqualTo(animalTipoDeAlteracao2);
    }
}
