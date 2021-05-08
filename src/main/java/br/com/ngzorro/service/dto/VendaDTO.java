package br.com.ngzorro.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import br.com.ngzorro.domain.enumeration.TipoSituacaoDoLancamento;

/**
 * A DTO for the {@link br.com.ngzorro.domain.Venda} entity.
 */
public class VendaDTO implements Serializable {

    private Long id;

    @Lob
    private String observacao;

    private ZonedDateTime dataDaCompra;

    private ZonedDateTime dataDoPagamento;

    private BigDecimal desconto;

    private TipoSituacaoDoLancamento situacao;

    private BigDecimal valorTotal;


    private Long atendimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ZonedDateTime getDataDaCompra() {
        return dataDaCompra;
    }

    public void setDataDaCompra(ZonedDateTime dataDaCompra) {
        this.dataDaCompra = dataDaCompra;
    }

    public ZonedDateTime getDataDoPagamento() {
        return dataDoPagamento;
    }

    public void setDataDoPagamento(ZonedDateTime dataDoPagamento) {
        this.dataDoPagamento = dataDoPagamento;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public TipoSituacaoDoLancamento getSituacao() {
        return situacao;
    }

    public void setSituacao(TipoSituacaoDoLancamento situacao) {
        this.situacao = situacao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(Long atendimentoId) {
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

        VendaDTO vendaDTO = (VendaDTO) o;
        if (vendaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VendaDTO{" +
            "id=" + getId() +
            ", observacao='" + getObservacao() + "'" +
            ", dataDaCompra='" + getDataDaCompra() + "'" +
            ", dataDoPagamento='" + getDataDoPagamento() + "'" +
            ", desconto=" + getDesconto() +
            ", situacao='" + getSituacao() + "'" +
            ", valorTotal=" + getValorTotal() +
            ", atendimento=" + getAtendimentoId() +
            "}";
    }
}
