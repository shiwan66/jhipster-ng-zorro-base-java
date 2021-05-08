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
 * Criteria class for the {@link br.com.ngzorro.domain.AnimalCarrapaticida} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AnimalCarrapaticidaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /animal-carrapaticidas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnimalCarrapaticidaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private LocalDateFilter dataAplicacao;

    private LongFilter animalId;

    public AnimalCarrapaticidaCriteria(){
    }

    public AnimalCarrapaticidaCriteria(AnimalCarrapaticidaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.dataAplicacao = other.dataAplicacao == null ? null : other.dataAplicacao.copy();
        this.animalId = other.animalId == null ? null : other.animalId.copy();
    }

    @Override
    public AnimalCarrapaticidaCriteria copy() {
        return new AnimalCarrapaticidaCriteria(this);
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

    public LocalDateFilter getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDateFilter dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
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
        final AnimalCarrapaticidaCriteria that = (AnimalCarrapaticidaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(dataAplicacao, that.dataAplicacao) &&
            Objects.equals(animalId, that.animalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        dataAplicacao,
        animalId
        );
    }

    @Override
    public String toString() {
        return "AnimalCarrapaticidaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (dataAplicacao != null ? "dataAplicacao=" + dataAplicacao + ", " : "") +
                (animalId != null ? "animalId=" + animalId + ", " : "") +
            "}";
    }

}
