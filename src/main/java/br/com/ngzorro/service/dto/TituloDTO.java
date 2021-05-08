package br.com.ngzorro.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import br.com.ngzorro.domain.enumeration.TipoTitulo;

/**
 * A DTO for the {@link br.com.ngzorro.domain.Titulo} entity.
 */
public class TituloDTO implements Serializable {

    private Long id;

    private Boolean isPago;

    private TipoTitulo tipo;

    @Lob
    private String descricao;

    private BigDecimal valor;

    private LocalDate dataEmissao;

    private LocalDate dataPagamento;

    private LocalDate dataVencimento;


    private Long tutorId;

    private Long fornecedorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsPago() {
        return isPago;
    }

    public void setIsPago(Boolean isPago) {
        this.isPago = isPago;
    }

    public TipoTitulo getTipo() {
        return tipo;
    }

    public void setTipo(TipoTitulo tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
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

        TituloDTO tituloDTO = (TituloDTO) o;
        if (tituloDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tituloDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TituloDTO{" +
            "id=" + getId() +
            ", isPago='" + isIsPago() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            ", dataEmissao='" + getDataEmissao() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", tutor=" + getTutorId() +
            ", fornecedor=" + getFornecedorId() +
            "}";
    }
}
