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
 * Criteria class for the {@link br.com.ngzorro.domain.AnimalVeterinario} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AnimalVeterinarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /animal-veterinarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnimalVeterinarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter telefone;

    private StringFilter clinica;

    public AnimalVeterinarioCriteria(){
    }

    public AnimalVeterinarioCriteria(AnimalVeterinarioCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.clinica = other.clinica == null ? null : other.clinica.copy();
    }

    @Override
    public AnimalVeterinarioCriteria copy() {
        return new AnimalVeterinarioCriteria(this);
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

    public StringFilter getTelefone() {
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public StringFilter getClinica() {
        return clinica;
    }

    public void setClinica(StringFilter clinica) {
        this.clinica = clinica;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnimalVeterinarioCriteria that = (AnimalVeterinarioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(clinica, that.clinica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        telefone,
        clinica
        );
    }

    @Override
    public String toString() {
        return "AnimalVeterinarioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (telefone != null ? "telefone=" + telefone + ", " : "") +
                (clinica != null ? "clinica=" + clinica + ", " : "") +
            "}";
    }

}
