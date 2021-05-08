package br.com.ngzorro.repository;
import br.com.ngzorro.domain.Raca;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Raca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacaRepository extends JpaRepository<Raca, Long>, JpaSpecificationExecutor<Raca> {

}
