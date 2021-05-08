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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.Produto} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.ProdutoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produtos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProdutoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BigDecimalFilter preco;

    private LocalDateFilter data;

    private InstantFilter hora;

    private LongFilter categoriaId;

    public ProdutoCriteria(){
    }

    public ProdutoCriteria(ProdutoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.preco = other.preco == null ? null : other.preco.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.hora = other.hora == null ? null : other.hora.copy();
        this.categoriaId = other.categoriaId == null ? null : other.categoriaId.copy();
    }

    @Override
    public ProdutoCriteria copy() {
        return new ProdutoCriteria(this);
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

    public BigDecimalFilter getPreco() {
        return preco;
    }

    public void setPreco(BigDecimalFilter preco) {
        this.preco = preco;
    }

    public LocalDateFilter getData() {
        return data;
    }

    public void setData(LocalDateFilter data) {
        this.data = data;
    }

    public InstantFilter getHora() {
        return hora;
    }

    public void setHora(InstantFilter hora) {
        this.hora = hora;
    }

    public LongFilter getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(LongFilter categoriaId) {
        this.categoriaId = categoriaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProdutoCriteria that = (ProdutoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(preco, that.preco) &&
            Objects.equals(data, that.data) &&
            Objects.equals(hora, that.hora) &&
            Objects.equals(categoriaId, that.categoriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        preco,
        data,
        hora,
        categoriaId
        );
    }

    @Override
    public String toString() {
        return "ProdutoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (preco != null ? "preco=" + preco + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (hora != null ? "hora=" + hora + ", " : "") +
                (categoriaId != null ? "categoriaId=" + categoriaId + ", " : "") +
            "}";
    }

}
