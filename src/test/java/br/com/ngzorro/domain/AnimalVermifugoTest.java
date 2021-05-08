package br.com.ngzorro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalVermifugoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalVermifugo.class);
        AnimalVermifugo animalVermifugo1 = new AnimalVermifugo();
        animalVermifugo1.setId(1L);
        AnimalVermifugo animalVermifugo2 = new AnimalVermifugo();
        animalVermifugo2.setId(animalVermifugo1.getId());
        assertThat(animalVermifugo1).isEqualTo(animalVermifugo2);
        animalVermifugo2.setId(2L);
        assertThat(animalVermifugo1).isNotEqualTo(animalVermifugo2);
        animalVermifugo1.setId(null);
        assertThat(animalVermifugo1).isNotEqualTo(animalVermifugo2);
    }
}
