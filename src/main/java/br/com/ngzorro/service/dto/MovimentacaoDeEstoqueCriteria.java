package br.com.ngzorro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.ngzorro.domain.enumeration.TipoMovimentacaoDeEstoque;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.MovimentacaoDeEstoque} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.MovimentacaoDeEstoqueResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /movimentacao-de-estoques?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MovimentacaoDeEstoqueCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoMovimentacaoDeEstoque
     */
    public static class TipoMovimentacaoDeEstoqueFilter extends Filter<TipoMovimentacaoDeEstoque> {

        public TipoMovimentacaoDeEstoqueFilter() {
        }

        public TipoMovimentacaoDeEstoqueFilter(TipoMovimentacaoDeEstoqueFilter filter) {
            super(filter);
        }

        @Override
        public TipoMovimentacaoDeEstoqueFilter copy() {
            return new TipoMovimentacaoDeEstoqueFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipoMovimentacaoDeEstoqueFilter tipo;

    private ZonedDateTimeFilter data;

    private DoubleFilter quantidade;

    private LongFilter consumoId;

    public MovimentacaoDeEstoqueCriteria(){
    }

    public MovimentacaoDeEstoqueCriteria(MovimentacaoDeEstoqueCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.consumoId = other.consumoId == null ? null : other.consumoId.copy();
    }

    @Override
    public MovimentacaoDeEstoqueCriteria copy() {
        return new MovimentacaoDeEstoqueCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TipoMovimentacaoDeEstoqueFilter getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacaoDeEstoqueFilter tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTimeFilter getData() {
        return data;
    }

    public void setData(ZonedDateTimeFilter data) {
        this.data = data;
    }

    public DoubleFilter getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(DoubleFilter quantidade) {
        this.quantidade = quantidade;
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
        final MovimentacaoDeEstoqueCriteria that = (MovimentacaoDeEstoqueCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(data, that.data) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(consumoId, that.consumoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipo,
        data,
        quantidade,
        consumoId
        );
    }

    @Override
    public String toString() {
        return "MovimentacaoDeEstoqueCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
                (consumoId != null ? "consumoId=" + consumoId + ", " : "") +
            "}";
    }

}
