package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnimalVermifugo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalVermifugo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalVermifugoRepository extends JpaRepository<AnimalVermifugo, Long>, JpaSpecificationExecutor<AnimalVermifugo> {

}
