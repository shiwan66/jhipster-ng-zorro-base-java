package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalCioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalCioDTO.class);
        AnimalCioDTO animalCioDTO1 = new AnimalCioDTO();
        animalCioDTO1.setId(1L);
        AnimalCioDTO animalCioDTO2 = new AnimalCioDTO();
        assertThat(animalCioDTO1).isNotEqualTo(animalCioDTO2);
        animalCioDTO2.setId(animalCioDTO1.getId());
        assertThat(animalCioDTO1).isEqualTo(animalCioDTO2);
        animalCioDTO2.setId(2L);
        assertThat(animalCioDTO1).isNotEqualTo(animalCioDTO2);
        animalCioDTO1.setId(null);
        assertThat(animalCioDTO1).isNotEqualTo(animalCioDTO2);
    }
}
