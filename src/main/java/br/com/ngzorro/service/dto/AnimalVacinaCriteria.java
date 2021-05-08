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
 * Criteria class for the {@link br.com.ngzorro.domain.AnimalVacina} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AnimalVacinaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /animal-vacinas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnimalVacinaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dataDaAplicacao;

    private LongFilter animalTipoDeVacinaId;

    private LongFilter animalId;

    public AnimalVacinaCriteria(){
    }

    public AnimalVacinaCriteria(AnimalVacinaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataDaAplicacao = other.dataDaAplicacao == null ? null : other.dataDaAplicacao.copy();
        this.animalTipoDeVacinaId = other.animalTipoDeVacinaId == null ? null : other.animalTipoDeVacinaId.copy();
        this.animalId = other.animalId == null ? null : other.animalId.copy();
    }

    @Override
    public AnimalVacinaCriteria copy() {
        return new AnimalVacinaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDataDaAplicacao() {
        return dataDaAplicacao;
    }

    public void setDataDaAplicacao(LocalDateFilter dataDaAplicacao) {
        this.dataDaAplicacao = dataDaAplicacao;
    }

    public LongFilter getAnimalTipoDeVacinaId() {
        return animalTipoDeVacinaId;
    }

    public void setAnimalTipoDeVacinaId(LongFilter animalTipoDeVacinaId) {
        this.animalTipoDeVacinaId = animalTipoDeVacinaId;
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
        final AnimalVacinaCriteria that = (AnimalVacinaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataDaAplicacao, that.dataDaAplicacao) &&
            Objects.equals(animalTipoDeVacinaId, that.animalTipoDeVacinaId) &&
            Objects.equals(animalId, that.animalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataDaAplicacao,
        animalTipoDeVacinaId,
        animalId
        );
    }

    @Override
    public String toString() {
        return "AnimalVacinaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataDaAplicacao != null ? "dataDaAplicacao=" + dataDaAplicacao + ", " : "") +
                (animalTipoDeVacinaId != null ? "animalTipoDeVacinaId=" + animalTipoDeVacinaId + ", " : "") +
                (animalId != null ? "animalId=" + animalId + ", " : "") +
            "}";
    }

}
