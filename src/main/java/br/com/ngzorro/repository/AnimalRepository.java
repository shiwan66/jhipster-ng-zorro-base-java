package br.com.ngzorro.repository;
import br.com.ngzorro.domain.Animal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Animal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {

}
