package br.com.ngzorro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.ngzorro.domain.enumeration.AtendimentoSituacao;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.Atendimento} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AtendimentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /atendimentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AtendimentoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AtendimentoSituacao
     */
    public static class AtendimentoSituacaoFilter extends Filter<AtendimentoSituacao> {

        public AtendimentoSituacaoFilter() {
        }

        public AtendimentoSituacaoFilter(AtendimentoSituacaoFilter filter) {
            super(filter);
        }

        @Override
        public AtendimentoSituacaoFilter copy() {
            return new AtendimentoSituacaoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AtendimentoSituacaoFilter situacao;

    private ZonedDateTimeFilter dataDeChegada;

    private ZonedDateTimeFilter dataDeSaida;

    private StringFilter observacao;

    private LongFilter atividadeId;

    private LongFilter vendaId;

    private LongFilter anexoId;

    private LongFilter animalId;

    public AtendimentoCriteria(){
    }

    public AtendimentoCriteria(AtendimentoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.situacao = other.situacao == null ? null : other.situacao.copy();
        this.dataDeChegada = other.dataDeChegada == null ? null : other.dataDeChegada.copy();
        this.dataDeSaida = other.dataDeSaida == null ? null : other.dataDeSaida.copy();
        this.observacao = other.observacao == null ? null : other.observacao.copy();
        this.atividadeId = other.atividadeId == null ? null : other.atividadeId.copy();
        this.vendaId = other.vendaId == null ? null : other.vendaId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.animalId = other.animalId == null ? null : other.animalId.copy();
    }

    @Override
    public AtendimentoCriteria copy() {
        return new AtendimentoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AtendimentoSituacaoFilter getSituacao() {
        return situacao;
    }

    public void setSituacao(AtendimentoSituacaoFilter situacao) {
        this.situacao = situacao;
    }

    public ZonedDateTimeFilter getDataDeChegada() {
        return dataDeChegada;
    }

    public void setDataDeChegada(ZonedDateTimeFilter dataDeChegada) {
        this.dataDeChegada = dataDeChegada;
    }

    public ZonedDateTimeFilter getDataDeSaida() {
        return dataDeSaida;
    }

    public void setDataDeSaida(ZonedDateTimeFilter dataDeSaida) {
        this.dataDeSaida = dataDeSaida;
    }

    public StringFilter getObservacao() {
        return observacao;
    }

    public void setObservacao(StringFilter observacao) {
        this.observacao = observacao;
    }

    public LongFilter getAtividadeId() {
        return atividadeId;
    }

    public void setAtividadeId(LongFilter atividadeId) {
        this.atividadeId = atividadeId;
    }

    public LongFilter getVendaId() {
        return vendaId;
    }

    public void setVendaId(LongFilter vendaId) {
        this.vendaId = vendaId;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
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
        final AtendimentoCriteria that = (AtendimentoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(dataDeChegada, that.dataDeChegada) &&
            Objects.equals(dataDeSaida, that.dataDeSaida) &&
            Objects.equals(observacao, that.observacao) &&
            Objects.equals(atividadeId, that.atividadeId) &&
            Objects.equals(vendaId, that.vendaId) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(animalId, that.animalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        situacao,
        dataDeChegada,
        dataDeSaida,
        observacao,
        atividadeId,
        vendaId,
        anexoId,
        animalId
        );
    }

    @Override
    public String toString() {
        return "AtendimentoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (situacao != null ? "situacao=" + situacao + ", " : "") +
                (dataDeChegada != null ? "dataDeChegada=" + dataDeChegada + ", " : "") +
                (dataDeSaida != null ? "dataDeSaida=" + dataDeSaida + ", " : "") +
                (observacao != null ? "observacao=" + observacao + ", " : "") +
                (atividadeId != null ? "atividadeId=" + atividadeId + ", " : "") +
                (vendaId != null ? "vendaId=" + vendaId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (animalId != null ? "animalId=" + animalId + ", " : "") +
            "}";
    }

}
