package br.com.ngzorro.repository;
import br.com.ngzorro.domain.Consumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Consumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, Long>, JpaSpecificationExecutor<Consumo> {

}
