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
 * Criteria class for the {@link br.com.ngzorro.domain.AnimalCio} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AnimalCioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /animal-cios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnimalCioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dataDoCio;

    private LongFilter animalId;

    public AnimalCioCriteria(){
    }

    public AnimalCioCriteria(AnimalCioCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataDoCio = other.dataDoCio == null ? null : other.dataDoCio.copy();
        this.animalId = other.animalId == null ? null : other.animalId.copy();
    }

    @Override
    public AnimalCioCriteria copy() {
        return new AnimalCioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDataDoCio() {
        return dataDoCio;
    }

    public void setDataDoCio(LocalDateFilter dataDoCio) {
        this.dataDoCio = dataDoCio;
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
        final AnimalCioCriteria that = (AnimalCioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataDoCio, that.dataDoCio) &&
            Objects.equals(animalId, that.animalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataDoCio,
        animalId
        );
    }

    @Override
    public String toString() {
        return "AnimalCioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataDoCio != null ? "dataDoCio=" + dataDoCio + ", " : "") +
                (animalId != null ? "animalId=" + animalId + ", " : "") +
            "}";
    }

}
