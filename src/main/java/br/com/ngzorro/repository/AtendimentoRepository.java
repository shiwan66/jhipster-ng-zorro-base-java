package br.com.ngzorro.repository;
import br.com.ngzorro.domain.Atendimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Atendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long>, JpaSpecificationExecutor<Atendimento> {

}
