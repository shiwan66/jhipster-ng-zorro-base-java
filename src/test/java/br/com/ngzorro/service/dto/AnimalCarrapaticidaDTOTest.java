package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalCarrapaticidaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalCarrapaticidaDTO.class);
        AnimalCarrapaticidaDTO animalCarrapaticidaDTO1 = new AnimalCarrapaticidaDTO();
        animalCarrapaticidaDTO1.setId(1L);
        AnimalCarrapaticidaDTO animalCarrapaticidaDTO2 = new AnimalCarrapaticidaDTO();
        assertThat(animalCarrapaticidaDTO1).isNotEqualTo(animalCarrapaticidaDTO2);
        animalCarrapaticidaDTO2.setId(animalCarrapaticidaDTO1.getId());
        assertThat(animalCarrapaticidaDTO1).isEqualTo(animalCarrapaticidaDTO2);
        animalCarrapaticidaDTO2.setId(2L);
        assertThat(animalCarrapaticidaDTO1).isNotEqualTo(animalCarrapaticidaDTO2);
        animalCarrapaticidaDTO1.setId(null);
        assertThat(animalCarrapaticidaDTO1).isNotEqualTo(animalCarrapaticidaDTO2);
    }
}
