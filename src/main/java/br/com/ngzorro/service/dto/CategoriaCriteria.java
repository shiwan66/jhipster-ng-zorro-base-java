package br.com.ngzorro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.Categoria} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.CategoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private LongFilter produtoId;

    public CategoriaCriteria(){
    }

    public CategoriaCriteria(CategoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
    }

    @Override
    public CategoriaCriteria copy() {
        return new CategoriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoriaCriteria that = (CategoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        produtoId
        );
    }

    @Override
    public String toString() {
        return "CategoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
            "}";
    }

}
