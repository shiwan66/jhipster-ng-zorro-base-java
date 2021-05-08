package br.com.ngzorro.repository;
import br.com.ngzorro.domain.VendaConsumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VendaConsumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendaConsumoRepository extends JpaRepository<VendaConsumo, Long>, JpaSpecificationExecutor<VendaConsumo> {

}
