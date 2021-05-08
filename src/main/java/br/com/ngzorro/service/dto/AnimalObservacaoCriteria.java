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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.AnimalObservacao} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AnimalObservacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /animal-observacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnimalObservacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dataAlteracao;

    private LongFilter animalId;

    public AnimalObservacaoCriteria(){
    }

    public AnimalObservacaoCriteria(AnimalObservacaoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataAlteracao = other.dataAlteracao == null ? null : other.dataAlteracao.copy();
        this.animalId = other.animalId == null ? null : other.animalId.copy();
    }

    @Override
    public AnimalObservacaoCriteria copy() {
        return new AnimalObservacaoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateFilter dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public LongFilter getAnimalId() {
        return animalId;
    }

    public void setAnimalId(LongFilter animalId) {
        this.animalId = animalId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnimalObservacaoCriteria that = (AnimalObservacaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataAlteracao, that.dataAlteracao) &&
            Objects.equals(animalId, that.animalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataAlteracao,
        animalId
        );
    }

    @Override
    public String toString() {
        return "AnimalObservacaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataAlteracao != null ? "dataAlteracao=" + dataAlteracao + ", " : "") +
                (animalId != null ? "animalId=" + animalId + ", " : "") +
            "}";
    }

}
