package br.com.ngzorro.repository;
import br.com.ngzorro.domain.Venda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Venda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, JpaSpecificationExecutor<Venda> {

}
