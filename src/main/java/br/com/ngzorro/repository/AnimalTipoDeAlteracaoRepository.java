package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnimalTipoDeAlteracao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalTipoDeAlteracao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalTipoDeAlteracaoRepository extends JpaRepository<AnimalTipoDeAlteracao, Long>, JpaSpecificationExecutor<AnimalTipoDeAlteracao> {

}
