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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.Atividade} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AtividadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /atividades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AtividadeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private ZonedDateTimeFilter inicio;

    private ZonedDateTimeFilter termino;

    private StringFilter observacao;

    private BooleanFilter realizado;

    private LongFilter atendimentoId;

    private LongFilter modeloAtividadeId;

    public AtividadeCriteria(){
    }

    public AtividadeCriteria(AtividadeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.inicio = other.inicio == null ? null : other.inicio.copy();
        this.termino = other.termino == null ? null : other.termino.copy();
        this.observacao = other.observacao == null ? null : other.observacao.copy();
        this.realizado = other.realizado == null ? null : other.realizado.copy();
        this.atendimentoId = other.atendimentoId == null ? null : other.atendimentoId.copy();
        this.modeloAtividadeId = other.modeloAtividadeId == null ? null : other.modeloAtividadeId.copy();
    }

    @Override
    public AtividadeCriteria copy() {
        return new AtividadeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public ZonedDateTimeFilter getInicio() {
        return inicio;
    }

    public void setInicio(ZonedDateTimeFilter inicio) {
        this.inicio = inicio;
    }

    public ZonedDateTimeFilter getTermino() {
        return termino;
    }

    public void setTermino(ZonedDateTimeFilter termino) {
        this.termino = termino;
    }

    public StringFilter getObservacao() {
        return observacao;
    }

    public void setObservacao(StringFilter observacao) {
        this.observacao = observacao;
    }

    public BooleanFilter getRealizado() {
        return realizado;
    }

    public void setRealizado(BooleanFilter realizado) {
        this.realizado = realizado;
    }

    public LongFilter getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(LongFilter atendimentoId) {
        this.atendimentoId = atendimentoId;
    }

    public LongFilter getModeloAtividadeId() {
        return modeloAtividadeId;
    }

    public void setModeloAtividadeId(LongFilter modeloAtividadeId) {
        this.modeloAtividadeId = modeloAtividadeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AtividadeCriteria that = (AtividadeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(inicio, that.inicio) &&
            Objects.equals(termino, that.termino) &&
            Objects.equals(observacao, that.observacao) &&
            Objects.equals(realizado, that.realizado) &&
            Objects.equals(atendimentoId, that.atendimentoId) &&
            Objects.equals(modeloAtividadeId, that.modeloAtividadeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        inicio,
        termino,
        observacao,
        realizado,
        atendimentoId,
        modeloAtividadeId
        );
    }

    @Override
    public String toString() {
        return "AtividadeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (inicio != null ? "inicio=" + inicio + ", " : "") +
                (termino != null ? "termino=" + termino + ", " : "") +
                (observacao != null ? "observacao=" + observacao + ", " : "") +
                (realizado != null ? "realizado=" + realizado + ", " : "") +
                (atendimentoId != null ? "atendimentoId=" + atendimentoId + ", " : "") +
                (modeloAtividadeId != null ? "modeloAtividadeId=" + modeloAtividadeId + ", " : "") +
            "}";
    }

}
