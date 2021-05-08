package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalVermifugoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalVermifugoDTO.class);
        AnimalVermifugoDTO animalVermifugoDTO1 = new AnimalVermifugoDTO();
        animalVermifugoDTO1.setId(1L);
        AnimalVermifugoDTO animalVermifugoDTO2 = new AnimalVermifugoDTO();
        assertThat(animalVermifugoDTO1).isNotEqualTo(animalVermifugoDTO2);
        animalVermifugoDTO2.setId(animalVermifugoDTO1.getId());
        assertThat(animalVermifugoDTO1).isEqualTo(animalVermifugoDTO2);
        animalVermifugoDTO2.setId(2L);
        assertThat(animalVermifugoDTO1).isNotEqualTo(animalVermifugoDTO2);
        animalVermifugoDTO1.setId(null);
        assertThat(animalVermifugoDTO1).isNotEqualTo(animalVermifugoDTO2);
    }
}
