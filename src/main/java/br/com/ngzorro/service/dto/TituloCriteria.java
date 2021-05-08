package br.com.ngzorro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.ngzorro.domain.enumeration.TipoTitulo;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.Titulo} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.TituloResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /titulos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TituloCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoTitulo
     */
    public static class TipoTituloFilter extends Filter<TipoTitulo> {

        public TipoTituloFilter() {
        }

        public TipoTituloFilter(TipoTituloFilter filter) {
            super(filter);
        }

        @Override
        public TipoTituloFilter copy() {
            return new TipoTituloFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isPago;

    private TipoTituloFilter tipo;

    private BigDecimalFilter valor;

    private LocalDateFilter dataEmissao;

    private LocalDateFilter dataPagamento;

    private LocalDateFilter dataVencimento;

    private LongFilter tutorId;

    private LongFilter fornecedorId;

    public TituloCriteria(){
    }

    public TituloCriteria(TituloCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.isPago = other.isPago == null ? null : other.isPago.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.dataEmissao = other.dataEmissao == null ? null : other.dataEmissao.copy();
        this.dataPagamento = other.dataPagamento == null ? null : other.dataPagamento.copy();
        this.dataVencimento = other.dataVencimento == null ? null : other.dataVencimento.copy();
        this.tutorId = other.tutorId == null ? null : other.tutorId.copy();
        this.fornecedorId = other.fornecedorId == null ? null : other.fornecedorId.copy();
    }

    @Override
    public TituloCriteria copy() {
        return new TituloCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getIsPago() {
        return isPago;
    }

    public void setIsPago(BooleanFilter isPago) {
        this.isPago = isPago;
    }

    public TipoTituloFilter getTipo() {
        return tipo;
    }

    public void setTipo(TipoTituloFilter tipo) {
        this.tipo = tipo;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public LocalDateFilter getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateFilter dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDateFilter getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateFilter dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDateFilter getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateFilter dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LongFilter getTutorId() {
        return tutorId;
    }

    public void setTutorId(LongFilter tutorId) {
        this.tutorId = tutorId;
    }

    public LongFilter getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(LongFilter fornecedorId) {
        this.fornecedorId = fornecedorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TituloCriteria that = (TituloCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isPago, that.isPago) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(dataEmissao, that.dataEmissao) &&
            Objects.equals(dataPagamento, that.dataPagamento) &&
            Objects.equals(dataVencimento, that.dataVencimento) &&
            Objects.equals(tutorId, that.tutorId) &&
            Objects.equals(fornecedorId, that.fornecedorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isPago,
        tipo,
        valor,
        dataEmissao,
        dataPagamento,
        dataVencimento,
        tutorId,
        fornecedorId
        );
    }

    @Override
    public String toString() {
        return "TituloCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isPago != null ? "isPago=" + isPago + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (dataEmissao != null ? "dataEmissao=" + dataEmissao + ", " : "") +
                (dataPagamento != null ? "dataPagamento=" + dataPagamento + ", " : "") +
                (dataVencimento != null ? "dataVencimento=" + dataVencimento + ", " : "") +
                (tutorId != null ? "tutorId=" + tutorId + ", " : "") +
                (fornecedorId != null ? "fornecedorId=" + fornecedorId + ", " : "") +
            "}";
    }

}
