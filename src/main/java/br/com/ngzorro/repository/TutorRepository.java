package br.com.ngzorro.repository;
import br.com.ngzorro.domain.Tutor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tutor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long>, JpaSpecificationExecutor<Tutor> {

}
