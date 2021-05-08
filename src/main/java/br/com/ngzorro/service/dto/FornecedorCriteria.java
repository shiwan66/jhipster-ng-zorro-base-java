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
 * Criteria class for the {@link br.com.ngzorro.domain.Fornecedor} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.FornecedorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fornecedors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FornecedorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter telefone;

    private StringFilter email;

    private StringFilter pontoReferencia;

    private LongFilter tituloId;

    private LongFilter enderecoId;

    public FornecedorCriteria(){
    }

    public FornecedorCriteria(FornecedorCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.pontoReferencia = other.pontoReferencia == null ? null : other.pontoReferencia.copy();
        this.tituloId = other.tituloId == null ? null : other.tituloId.copy();
        this.enderecoId = other.enderecoId == null ? null : other.enderecoId.copy();
    }

    @Override
    public FornecedorCriteria copy() {
        return new FornecedorCriteria(this);
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

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(StringFilter pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public LongFilter getTituloId() {
        return tituloId;
    }

    public void setTituloId(LongFilter tituloId) {
        this.tituloId = tituloId;
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
        final FornecedorCriteria that = (FornecedorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(pontoReferencia, that.pontoReferencia) &&
            Objects.equals(tituloId, that.tituloId) &&
            Objects.equals(enderecoId, that.enderecoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        telefone,
        email,
        pontoReferencia,
        tituloId,
        enderecoId
        );
    }

    @Override
    public String toString() {
        return "FornecedorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (telefone != null ? "telefone=" + telefone + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (pontoReferencia != null ? "pontoReferencia=" + pontoReferencia + ", " : "") +
                (tituloId != null ? "tituloId=" + tituloId + ", " : "") +
                (enderecoId != null ? "enderecoId=" + enderecoId + ", " : "") +
            "}";
    }

}
