package br.com.ngzorro.service;

import br.com.ngzorro.domain.VendaConsumo;
import br.com.ngzorro.repository.VendaConsumoRepository;
import br.com.ngzorro.service.dto.VendaConsumoDTO;
import br.com.ngzorro.service.mapper.VendaConsumoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VendaConsumo}.
 */
@Service
@Transactional
public class VendaConsumoService {

    private final Logger log = LoggerFactory.getLogger(VendaConsumoService.class);

    private final VendaConsumoRepository vendaConsumoRepository;

    private final VendaConsumoMapper vendaConsumoMapper;

    public VendaConsumoService(VendaConsumoRepository vendaConsumoRepository, VendaConsumoMapper vendaConsumoMapper) {
        this.vendaConsumoRepository = vendaConsumoRepository;
        this.vendaConsumoMapper = vendaConsumoMapper;
    }

    /**
     * Save a vendaConsumo.
     *
     * @param vendaConsumoDTO the entity to save.
     * @return the persisted entity.
     */
    public VendaConsumoDTO save(VendaConsumoDTO vendaConsumoDTO) {
        log.debug("Request to save VendaConsumo : {}", vendaConsumoDTO);
        VendaConsumo vendaConsumo = vendaConsumoMapper.toEntity(vendaConsumoDTO);
        vendaConsumo = vendaConsumoRepository.save(vendaConsumo);
        return vendaConsumoMapper.toDto(vendaConsumo);
    }

    /**
     * Get all the vendaConsumos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VendaConsumoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VendaConsumos");
        return vendaConsumoRepository.findAll(pageable)
            .map(vendaConsumoMapper::toDto);
    }


    /**
     * Get one vendaConsumo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VendaConsumoDTO> findOne(Long id) {
        log.debug("Request to get VendaConsumo : {}", id);
        return vendaConsumoRepository.findById(id)
            .map(vendaConsumoMapper::toDto);
    }

    /**
     * Delete the vendaConsumo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VendaConsumo : {}", id);
        vendaConsumoRepository.deleteById(id);
    }
}
