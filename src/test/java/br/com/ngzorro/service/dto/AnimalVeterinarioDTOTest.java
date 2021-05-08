package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalVeterinarioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalVeterinarioDTO.class);
        AnimalVeterinarioDTO animalVeterinarioDTO1 = new AnimalVeterinarioDTO();
        animalVeterinarioDTO1.setId(1L);
        AnimalVeterinarioDTO animalVeterinarioDTO2 = new AnimalVeterinarioDTO();
        assertThat(animalVeterinarioDTO1).isNotEqualTo(animalVeterinarioDTO2);
        animalVeterinarioDTO2.setId(animalVeterinarioDTO1.getId());
        assertThat(animalVeterinarioDTO1).isEqualTo(animalVeterinarioDTO2);
        animalVeterinarioDTO2.setId(2L);
        assertThat(animalVeterinarioDTO1).isNotEqualTo(animalVeterinarioDTO2);
        animalVeterinarioDTO1.setId(null);
        assertThat(animalVeterinarioDTO1).isNotEqualTo(animalVeterinarioDTO2);
    }
}
