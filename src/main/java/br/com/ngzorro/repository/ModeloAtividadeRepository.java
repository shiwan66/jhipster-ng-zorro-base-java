package br.com.ngzorro.repository;
import br.com.ngzorro.domain.ModeloAtividade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ModeloAtividade entity.
 */
@Repository
public interface ModeloAtividadeRepository extends JpaRepository<ModeloAtividade, Long>, JpaSpecificationExecutor<ModeloAtividade> {

    @Query(value = "select distinct modeloAtividade from ModeloAtividade modeloAtividade left join fetch modeloAtividade.atividades",
        countQuery = "select count(distinct modeloAtividade) from ModeloAtividade modeloAtividade")
    Page<ModeloAtividade> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct modeloAtividade from ModeloAtividade modeloAtividade left join fetch modeloAtividade.atividades")
    List<ModeloAtividade> findAllWithEagerRelationships();

    @Query("select modeloAtividade from ModeloAtividade modeloAtividade left join fetch modeloAtividade.atividades where modeloAtividade.id =:id")
    Optional<ModeloAtividade> findOneWithEagerRelationships(@Param("id") Long id);

}
