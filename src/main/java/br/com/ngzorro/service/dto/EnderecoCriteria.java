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
 * Criteria class for the {@link br.com.ngzorro.domain.Endereco} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.EnderecoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enderecos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnderecoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cep;

    private StringFilter logradouro;

    private StringFilter numero;

    private StringFilter complemento;

    private StringFilter bairro;

    private StringFilter localidade;

    private StringFilter uf;

    private StringFilter ibge;

    public EnderecoCriteria(){
    }

    public EnderecoCriteria(EnderecoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.cep = other.cep == null ? null : other.cep.copy();
        this.logradouro = other.logradouro == null ? null : other.logradouro.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.complemento = other.complemento == null ? null : other.complemento.copy();
        this.bairro = other.bairro == null ? null : other.bairro.copy();
        this.localidade = other.localidade == null ? null : other.localidade.copy();
        this.uf = other.uf == null ? null : other.uf.copy();
        this.ibge = other.ibge == null ? null : other.ibge.copy();
    }

    @Override
    public EnderecoCriteria copy() {
        return new EnderecoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCep() {
        return cep;
    }

    public void setCep(StringFilter cep) {
        this.cep = cep;
    }

    public StringFilter getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(StringFilter logradouro) {
        this.logradouro = logradouro;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StringFilter getComplemento() {
        return complemento;
    }

    public void setComplemento(StringFilter complemento) {
        this.complemento = complemento;
    }

    public StringFilter getBairro() {
        return bairro;
    }

    public void setBairro(StringFilter bairro) {
        this.bairro = bairro;
    }

    public StringFilter getLocalidade() {
        return localidade;
    }

    public void setLocalidade(StringFilter localidade) {
        this.localidade = localidade;
    }

    public StringFilter getUf() {
        return uf;
    }

    public void setUf(StringFilter uf) {
        this.uf = uf;
    }

    public StringFilter getIbge() {
        return ibge;
    }

    public void setIbge(StringFilter ibge) {
        this.ibge = ibge;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnderecoCriteria that = (EnderecoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cep, that.cep) &&
            Objects.equals(logradouro, that.logradouro) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(complemento, that.complemento) &&
            Objects.equals(bairro, that.bairro) &&
            Objects.equals(localidade, that.localidade) &&
            Objects.equals(uf, that.uf) &&
            Objects.equals(ibge, that.ibge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cep,
        logradouro,
        numero,
        complemento,
        bairro,
        localidade,
        uf,
        ibge
        );
    }

    @Override
    public String toString() {
        return "EnderecoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cep != null ? "cep=" + cep + ", " : "") +
                (logradouro != null ? "logradouro=" + logradouro + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (complemento != null ? "complemento=" + complemento + ", " : "") +
                (bairro != null ? "bairro=" + bairro + ", " : "") +
                (localidade != null ? "localidade=" + localidade + ", " : "") +
                (uf != null ? "uf=" + uf + ", " : "") +
                (ibge != null ? "ibge=" + ibge + ", " : "") +
            "}";
    }

}
