package br.com.ngzorro.repository;
import br.com.ngzorro.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Produto entity.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    @Query(value = "select distinct produto from Produto produto left join fetch produto.categorias",
        countQuery = "select count(distinct produto) from Produto produto")
    Page<Produto> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct produto from Produto produto left join fetch produto.categorias")
    List<Produto> findAllWithEagerRelationships();

    @Query("select produto from Produto produto left join fetch produto.categorias where produto.id =:id")
    Optional<Produto> findOneWithEagerRelationships(@Param("id") Long id);

}
