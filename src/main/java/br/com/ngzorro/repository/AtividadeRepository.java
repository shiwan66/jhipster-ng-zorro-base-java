package br.com.ngzorro.repository;
import br.com.ngzorro.domain.Atividade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Atividade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long>, JpaSpecificationExecutor<Atividade> {

}
