package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnimalVeterinario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalVeterinario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalVeterinarioRepository extends JpaRepository<AnimalVeterinario, Long>, JpaSpecificationExecutor<AnimalVeterinario> {

}
