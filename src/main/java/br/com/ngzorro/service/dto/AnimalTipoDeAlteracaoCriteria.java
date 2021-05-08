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
 * Criteria class for the {@link br.com.ngzorro.domain.AnimalTipoDeAlteracao} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AnimalTipoDeAlteracaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /animal-tipo-de-alteracaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnimalTipoDeAlteracaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    public AnimalTipoDeAlteracaoCriteria(){
    }

    public AnimalTipoDeAlteracaoCriteria(AnimalTipoDeAlteracaoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
    }

    @Override
    public AnimalTipoDeAlteracaoCriteria copy() {
        return new AnimalTipoDeAlteracaoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnimalTipoDeAlteracaoCriteria that = (AnimalTipoDeAlteracaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        descricao
        );
    }

    @Override
    public String toString() {
        return "AnimalTipoDeAlteracaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
            "}";
    }

}
