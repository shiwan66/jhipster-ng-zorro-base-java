package br.com.ngzorro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TutorMapperTest {

    private TutorMapper tutorMapper;

    @BeforeEach
    public void setUp() {
        tutorMapper = new TutorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(tutorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tutorMapper.fromId(null)).isNull();
    }
}
