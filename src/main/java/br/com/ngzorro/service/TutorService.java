package br.com.ngzorro.service;

import br.com.ngzorro.domain.Tutor;
import br.com.ngzorro.repository.TutorRepository;
import br.com.ngzorro.service.dto.TutorDTO;
import br.com.ngzorro.service.mapper.TutorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tutor}.
 */
@Service
@Transactional
public class TutorService {

    private final Logger log = LoggerFactory.getLogger(TutorService.class);

    private final TutorRepository tutorRepository;

    private final TutorMapper tutorMapper;

    public TutorService(TutorRepository tutorRepository, TutorMapper tutorMapper) {
        this.tutorRepository = tutorRepository;
        this.tutorMapper = tutorMapper;
    }

    /**
     * Save a tutor.
     *
     * @param tutorDTO the entity to save.
     * @return the persisted entity.
     */
    public TutorDTO save(TutorDTO tutorDTO) {
        log.debug("Request to save Tutor : {}", tutorDTO);
        Tutor tutor = tutorMapper.toEntity(tutorDTO);
        tutor = tutorRepository.save(tutor);
        return tutorMapper.toDto(tutor);
    }

    /**
     * Get all the tutors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TutorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tutors");
        return tutorRepository.findAll(pageable)
            .map(tutorMapper::toDto);
    }


    /**
     * Get one tutor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TutorDTO> findOne(Long id) {
        log.debug("Request to get Tutor : {}", id);
        return tutorRepository.findById(id)
            .map(tutorMapper::toDto);
    }

    /**
     * Delete the tutor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tutor : {}", id);
        tutorRepository.deleteById(id);
    }
}
