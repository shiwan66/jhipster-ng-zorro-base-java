package br.com.ngzorro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.ngzorro.domain.enumeration.TipoConsumo;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.Consumo} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.ConsumoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /consumos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConsumoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoConsumo
     */
    public static class TipoConsumoFilter extends Filter<TipoConsumo> {

        public TipoConsumoFilter() {
        }

        public TipoConsumoFilter(TipoConsumoFilter filter) {
            super(filter);
        }

        @Override
        public TipoConsumoFilter copy() {
            return new TipoConsumoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private TipoConsumoFilter tipo;

    private BigDecimalFilter estoque;

    private BigDecimalFilter valorUnitario;

    private LongFilter vendaConsumoId;

    private LongFilter movimentacaoDeEstoqueId;

    public ConsumoCriteria(){
    }

    public ConsumoCriteria(ConsumoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.estoque = other.estoque == null ? null : other.estoque.copy();
        this.valorUnitario = other.valorUnitario == null ? null : other.valorUnitario.copy();
        this.vendaConsumoId = other.vendaConsumoId == null ? null : other.vendaConsumoId.copy();
        this.movimentacaoDeEstoqueId = other.movimentacaoDeEstoqueId == null ? null : other.movimentacaoDeEstoqueId.copy();
    }

    @Override
    public ConsumoCriteria copy() {
        return new ConsumoCriteria(this);
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

    public TipoConsumoFilter getTipo() {
        return tipo;
    }

    public void setTipo(TipoConsumoFilter tipo) {
        this.tipo = tipo;
    }

    public BigDecimalFilter getEstoque() {
        return estoque;
    }

    public void setEstoque(BigDecimalFilter estoque) {
        this.estoque = estoque;
    }

    public BigDecimalFilter getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimalFilter valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public LongFilter getVendaConsumoId() {
        return vendaConsumoId;
    }

    public void setVendaConsumoId(LongFilter vendaConsumoId) {
        this.vendaConsumoId = vendaConsumoId;
    }

    public LongFilter getMovimentacaoDeEstoqueId() {
        return movimentacaoDeEstoqueId;
    }

    public void setMovimentacaoDeEstoqueId(LongFilter movimentacaoDeEstoqueId) {
        this.movimentacaoDeEstoqueId = movimentacaoDeEstoqueId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConsumoCriteria that = (ConsumoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(estoque, that.estoque) &&
            Objects.equals(valorUnitario, that.valorUnitario) &&
            Objects.equals(vendaConsumoId, that.vendaConsumoId) &&
            Objects.equals(movimentacaoDeEstoqueId, that.movimentacaoDeEstoqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        tipo,
        estoque,
        valorUnitario,
        vendaConsumoId,
        movimentacaoDeEstoqueId
        );
    }

    @Override
    public String toString() {
        return "ConsumoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (estoque != null ? "estoque=" + estoque + ", " : "") +
                (valorUnitario != null ? "valorUnitario=" + valorUnitario + ", " : "") +
                (vendaConsumoId != null ? "vendaConsumoId=" + vendaConsumoId + ", " : "") +
                (movimentacaoDeEstoqueId != null ? "movimentacaoDeEstoqueId=" + movimentacaoDeEstoqueId + ", " : "") +
            "}";
    }

}
