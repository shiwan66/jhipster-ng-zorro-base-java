package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnimalCarrapaticida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalCarrapaticida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalCarrapaticidaRepository extends JpaRepository<AnimalCarrapaticida, Long>, JpaSpecificationExecutor<AnimalCarrapaticida> {

}
