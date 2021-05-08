package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnimalObservacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalObservacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalObservacaoRepository extends JpaRepository<AnimalObservacao, Long>, JpaSpecificationExecutor<AnimalObservacao> {

}
