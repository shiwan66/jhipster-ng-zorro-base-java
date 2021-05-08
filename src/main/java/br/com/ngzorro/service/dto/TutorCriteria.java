package br.com.ngzorro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.ngzorro.domain.enumeration.Sexo;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.Tutor} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.TutorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tutors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TutorCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Sexo
     */
    public static class SexoFilter extends Filter<Sexo> {

        public SexoFilter() {
        }

        public SexoFilter(SexoFilter filter) {
            super(filter);
        }

        @Override
        public SexoFilter copy() {
            return new SexoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter sobrenome;

    private StringFilter telefone1;

    private StringFilter telefone2;

    private StringFilter email;

    private StringFilter fotoUrl;

    private StringFilter cpf;

    private SexoFilter sexo;

    private LocalDateFilter dataCadastro;

    private LongFilter userId;

    private LongFilter tituloId;

    private LongFilter animalId;

    private LongFilter enderecoId;

    public TutorCriteria(){
    }

    public TutorCriteria(TutorCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.sobrenome = other.sobrenome == null ? null : other.sobrenome.copy();
        this.telefone1 = other.telefone1 == null ? null : other.telefone1.copy();
        this.telefone2 = other.telefone2 == null ? null : other.telefone2.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.fotoUrl = other.fotoUrl == null ? null : other.fotoUrl.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.dataCadastro = other.dataCadastro == null ? null : other.dataCadastro.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.tituloId = other.tituloId == null ? null : other.tituloId.copy();
        this.animalId = other.animalId == null ? null : other.animalId.copy();
        this.enderecoId = other.enderecoId == null ? null : other.enderecoId.copy();
    }

    @Override
    public TutorCriteria copy() {
        return new TutorCriteria(this);
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

    public StringFilter getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(StringFilter sobrenome) {
        this.sobrenome = sobrenome;
    }

    public StringFilter getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(StringFilter telefone1) {
        this.telefone1 = telefone1;
    }

    public StringFilter getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(StringFilter telefone2) {
        this.telefone2 = telefone2;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(StringFilter fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public SexoFilter getSexo() {
        return sexo;
    }

    public void setSexo(SexoFilter sexo) {
        this.sexo = sexo;
    }

    public LocalDateFilter getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateFilter dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getTituloId() {
        return tituloId;
    }

    public void setTituloId(LongFilter tituloId) {
        this.tituloId = tituloId;
    }

    public LongFilter getAnimalId() {
        return animalId;
    }

    public void setAnimalId(LongFilter animalId) {
        this.animalId = animalId;
    }

    public LongFilter getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(LongFilter enderecoId) {
        this.enderecoId = enderecoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TutorCriteria that = (TutorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(sobrenome, that.sobrenome) &&
            Objects.equals(telefone1, that.telefone1) &&
            Objects.equals(telefone2, that.telefone2) &&
            Objects.equals(email, that.email) &&
            Objects.equals(fotoUrl, that.fotoUrl) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(dataCadastro, that.dataCadastro) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(tituloId, that.tituloId) &&
            Objects.equals(animalId, that.animalId) &&
            Objects.equals(enderecoId, that.enderecoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        sobrenome,
        telefone1,
        telefone2,
        email,
        fotoUrl,
        cpf,
        sexo,
        dataCadastro,
        userId,
        tituloId,
        animalId,
        enderecoId
        );
    }

    @Override
    public String toString() {
        return "TutorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (sobrenome != null ? "sobrenome=" + sobrenome + ", " : "") +
                (telefone1 != null ? "telefone1=" + telefone1 + ", " : "") +
                (telefone2 != null ? "telefone2=" + telefone2 + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (fotoUrl != null ? "fotoUrl=" + fotoUrl + ", " : "") +
                (cpf != null ? "cpf=" + cpf + ", " : "") +
                (sexo != null ? "sexo=" + sexo + ", " : "") +
                (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (tituloId != null ? "tituloId=" + tituloId + ", " : "") +
                (animalId != null ? "animalId=" + animalId + ", " : "") +
                (enderecoId != null ? "enderecoId=" + enderecoId + ", " : "") +
            "}";
    }

}
