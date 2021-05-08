package br.com.ngzorro.repository;
import br.com.ngzorro.domain.Anexo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Anexo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long>, JpaSpecificationExecutor<Anexo> {

}
