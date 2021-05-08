package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnexoAtendimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnexoAtendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnexoAtendimentoRepository extends JpaRepository<AnexoAtendimento, Long>, JpaSpecificationExecutor<AnexoAtendimento> {

}
