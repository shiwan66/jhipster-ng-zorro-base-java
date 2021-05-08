package br.com.ngzorro.service;

import br.com.ngzorro.domain.Consumo;
import br.com.ngzorro.repository.ConsumoRepository;
import br.com.ngzorro.service.dto.ConsumoDTO;
import br.com.ngzorro.service.mapper.ConsumoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Consumo}.
 */
@Service
@Transactional
public class ConsumoService {

    private final Logger log = LoggerFactory.getLogger(ConsumoService.class);

    private final ConsumoRepository consumoRepository;

    private final ConsumoMapper consumoMapper;

    public ConsumoService(ConsumoRepository consumoRepository, ConsumoMapper consumoMapper) {
        this.consumoRepository = consumoRepository;
        this.consumoMapper = consumoMapper;
    }

    /**
     * Save a consumo.
     *
     * @param consumoDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsumoDTO save(ConsumoDTO consumoDTO) {
        log.debug("Request to save Consumo : {}", consumoDTO);
        Consumo consumo = consumoMapper.toEntity(consumoDTO);
        consumo = consumoRepository.save(consumo);
        return consumoMapper.toDto(consumo);
    }

    /**
     * Get all the consumos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsumoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consumos");
        return consumoRepository.findAll(pageable)
            .map(consumoMapper::toDto);
    }


    /**
     * Get one consumo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConsumoDTO> findOne(Long id) {
        log.debug("Request to get Consumo : {}", id);
        return consumoRepository.findById(id)
            .map(consumoMapper::toDto);
    }

    /**
     * Delete the consumo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Consumo : {}", id);
        consumoRepository.deleteById(id);
    }
}
