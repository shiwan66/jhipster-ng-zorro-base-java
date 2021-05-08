package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class AnimalObservacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalObservacaoDTO.class);
        AnimalObservacaoDTO animalObservacaoDTO1 = new AnimalObservacaoDTO();
        animalObservacaoDTO1.setId(1L);
        AnimalObservacaoDTO animalObservacaoDTO2 = new AnimalObservacaoDTO();
        assertThat(animalObservacaoDTO1).isNotEqualTo(animalObservacaoDTO2);
        animalObservacaoDTO2.setId(animalObservacaoDTO1.getId());
        assertThat(animalObservacaoDTO1).isEqualTo(animalObservacaoDTO2);
        animalObservacaoDTO2.setId(2L);
        assertThat(animalObservacaoDTO1).isNotEqualTo(animalObservacaoDTO2);
        animalObservacaoDTO1.setId(null);
        assertThat(animalObservacaoDTO1).isNotEqualTo(animalObservacaoDTO2);
    }
}
