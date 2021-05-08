package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnimalAlteracao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalAlteracao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalAlteracaoRepository extends JpaRepository<AnimalAlteracao, Long>, JpaSpecificationExecutor<AnimalAlteracao> {

}
