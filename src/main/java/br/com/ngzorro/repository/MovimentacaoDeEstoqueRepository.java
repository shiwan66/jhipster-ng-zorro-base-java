package br.com.ngzorro.repository;
import br.com.ngzorro.domain.MovimentacaoDeEstoque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MovimentacaoDeEstoque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovimentacaoDeEstoqueRepository extends JpaRepository<MovimentacaoDeEstoque, Long>, JpaSpecificationExecutor<MovimentacaoDeEstoque> {

}
