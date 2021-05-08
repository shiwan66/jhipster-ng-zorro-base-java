package br.com.ngzorro.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.ngzorro.domain.VendaConsumo} entity.
 */
public class VendaConsumoDTO implements Serializable {

    private Long id;

    private BigDecimal quantidade;

    private BigDecimal valorUnitario;

    private BigDecimal valorTotal;


    private Long vendaId;

    private Long consumoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    public Long getConsumoId() {
        return consumoId;
    }

    public void setConsumoId(Long consumoId) {
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

        VendaConsumoDTO vendaConsumoDTO = (VendaConsumoDTO) o;
        if (vendaConsumoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendaConsumoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VendaConsumoDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", valorUnitario=" + getValorUnitario() +
            ", valorTotal=" + getValorTotal() +
            ", venda=" + getVendaId() +
            ", consumo=" + getConsumoId() +
            "}";
    }
}
