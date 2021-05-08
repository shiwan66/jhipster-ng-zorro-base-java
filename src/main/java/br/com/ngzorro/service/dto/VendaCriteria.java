package br.com.ngzorro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.ngzorro.domain.enumeration.TipoSituacaoDoLancamento;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.Venda} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.VendaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vendas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VendaCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoSituacaoDoLancamento
     */
    public static class TipoSituacaoDoLancamentoFilter extends Filter<TipoSituacaoDoLancamento> {

        public TipoSituacaoDoLancamentoFilter() {
        }

        public TipoSituacaoDoLancamentoFilter(TipoSituacaoDoLancamentoFilter filter) {
            super(filter);
        }

        @Override
        public TipoSituacaoDoLancamentoFilter copy() {
            return new TipoSituacaoDoLancamentoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter dataDaCompra;

    private ZonedDateTimeFilter dataDoPagamento;

    private BigDecimalFilter desconto;

    private TipoSituacaoDoLancamentoFilter situacao;

    private BigDecimalFilter valorTotal;

    private LongFilter vendaConsumoId;

    private LongFilter atendimentoId;

    public VendaCriteria(){
    }

    public VendaCriteria(VendaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataDaCompra = other.dataDaCompra == null ? null : other.dataDaCompra.copy();
        this.dataDoPagamento = other.dataDoPagamento == null ? null : other.dataDoPagamento.copy();
        this.desconto = other.desconto == null ? null : other.desconto.copy();
        this.situacao = other.situacao == null ? null : other.situacao.copy();
        this.valorTotal = other.valorTotal == null ? null : other.valorTotal.copy();
        this.vendaConsumoId = other.vendaConsumoId == null ? null : other.vendaConsumoId.copy();
        this.atendimentoId = other.atendimentoId == null ? null : other.atendimentoId.copy();
    }

    @Override
    public VendaCriteria copy() {
        return new VendaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getDataDaCompra() {
        return dataDaCompra;
    }

    public void setDataDaCompra(ZonedDateTimeFilter dataDaCompra) {
        this.dataDaCompra = dataDaCompra;
    }

    public ZonedDateTimeFilter getDataDoPagamento() {
        return dataDoPagamento;
    }

    public void setDataDoPagamento(ZonedDateTimeFilter dataDoPagamento) {
        this.dataDoPagamento = dataDoPagamento;
    }

    public BigDecimalFilter getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimalFilter desconto) {
        this.desconto = desconto;
    }

    public TipoSituacaoDoLancamentoFilter getSituacao() {
        return situacao;
    }

    public void setSituacao(TipoSituacaoDoLancamentoFilter situacao) {
        this.situacao = situacao;
    }

    public BigDecimalFilter getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimalFilter valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LongFilter getVendaConsumoId() {
        return vendaConsumoId;
    }

    public void setVendaConsumoId(LongFilter vendaConsumoId) {
        this.vendaConsumoId = vendaConsumoId;
    }

    public LongFilter getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(LongFilter atendimentoId) {
        this.atendimentoId = atendimentoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VendaCriteria that = (VendaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataDaCompra, that.dataDaCompra) &&
            Objects.equals(dataDoPagamento, that.dataDoPagamento) &&
            Objects.equals(desconto, that.desconto) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(valorTotal, that.valorTotal) &&
            Objects.equals(vendaConsumoId, that.vendaConsumoId) &&
            Objects.equals(atendimentoId, that.atendimentoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataDaCompra,
        dataDoPagamento,
        desconto,
        situacao,
        valorTotal,
        vendaConsumoId,
        atendimentoId
        );
    }

    @Override
    public String toString() {
        return "VendaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataDaCompra != null ? "dataDaCompra=" + dataDaCompra + ", " : "") +
                (dataDoPagamento != null ? "dataDoPagamento=" + dataDoPagamento + ", " : "") +
                (desconto != null ? "desconto=" + desconto + ", " : "") +
                (situacao != null ? "situacao=" + situacao + ", " : "") +
                (valorTotal != null ? "valorTotal=" + valorTotal + ", " : "") +
                (vendaConsumoId != null ? "vendaConsumoId=" + vendaConsumoId + ", " : "") +
                (atendimentoId != null ? "atendimentoId=" + atendimentoId + ", " : "") +
            "}";
    }

}
