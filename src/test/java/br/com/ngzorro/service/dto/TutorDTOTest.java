package br.com.ngzorro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.ngzorro.web.rest.TestUtil;

public class TutorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorDTO.class);
        TutorDTO tutorDTO1 = new TutorDTO();
        tutorDTO1.setId(1L);
        TutorDTO tutorDTO2 = new TutorDTO();
        assertThat(tutorDTO1).isNotEqualTo(tutorDTO2);
        tutorDTO2.setId(tutorDTO1.getId());
        assertThat(tutorDTO1).isEqualTo(tutorDTO2);
        tutorDTO2.setId(2L);
        assertThat(tutorDTO1).isNotEqualTo(tutorDTO2);
        tutorDTO1.setId(null);
        assertThat(tutorDTO1).isNotEqualTo(tutorDTO2);
    }
}
