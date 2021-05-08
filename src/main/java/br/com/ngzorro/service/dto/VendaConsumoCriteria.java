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
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.VendaConsumo} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.VendaConsumoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /venda-consumos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VendaConsumoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter quantidade;

    private BigDecimalFilter valorUnitario;

    private BigDecimalFilter valorTotal;

    private LongFilter vendaId;

    private LongFilter consumoId;

    public VendaConsumoCriteria(){
    }

    public VendaConsumoCriteria(VendaConsumoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.valorUnitario = other.valorUnitario == null ? null : other.valorUnitario.copy();
        this.valorTotal = other.valorTotal == null ? null : other.valorTotal.copy();
        this.vendaId = other.vendaId == null ? null : other.vendaId.copy();
        this.consumoId = other.consumoId == null ? null : other.consumoId.copy();
    }

    @Override
    public VendaConsumoCriteria copy() {
        return new VendaConsumoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimalFilter quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimalFilter getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimalFilter valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimalFilter getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimalFilter valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LongFilter getVendaId() {
        return vendaId;
    }

    public void setVendaId(LongFilter vendaId) {
        this.vendaId = vendaId;
    }

    public LongFilter getConsumoId() {
        return consumoId;
    }

    public void setConsumoId(LongFilter consumoId) {
        this.consumoId = consumoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VendaConsumoCriteria that = (VendaConsumoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(valorUnitario, that.valorUnitario) &&
            Objects.equals(valorTotal, that.valorTotal) &&
            Objects.equals(vendaId, that.vendaId) &&
            Objects.equals(consumoId, that.consumoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantidade,
        valorUnitario,
        valorTotal,
        vendaId,
        consumoId
        );
    }

    @Override
    public String toString() {
        return "VendaConsumoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
                (valorUnitario != null ? "valorUnitario=" + valorUnitario + ", " : "") +
                (valorTotal != null ? "valorTotal=" + valorTotal + ", " : "") +
                (vendaId != null ? "vendaId=" + vendaId + ", " : "") +
                (consumoId != null ? "consumoId=" + consumoId + ", " : "") +
            "}";
    }

}
