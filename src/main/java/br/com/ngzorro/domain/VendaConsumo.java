package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A VendaConsumo.
 */
@Entity
@Table(name = "venda_consumo")
public class VendaConsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantidade", precision = 21, scale = 2)
    private BigDecimal quantidade;

    @Column(name = "valor_unitario", precision = 21, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", precision = 21, scale = 2)
    private BigDecimal valorTotal;

    @ManyToOne
    @JsonIgnoreProperties("vendaConsumos")
    private Venda venda;

    @ManyToOne
    @JsonIgnoreProperties("vendaConsumos")
    private Consumo consumo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public VendaConsumo quantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public VendaConsumo valorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
        return this;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public VendaConsumo valorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Venda getVenda() {
        return venda;
    }

    public VendaConsumo venda(Venda venda) {
        this.venda = venda;
        return this;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Consumo getConsumo() {
        return consumo;
    }

    public VendaConsumo consumo(Consumo consumo) {
        this.consumo = consumo;
        return this;
    }

    public void setConsumo(Consumo consumo) {
        this.consumo = consumo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendaConsumo)) {
            return false;
        }
        return id != null && id.equals(((VendaConsumo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VendaConsumo{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", valorUnitario=" + getValorUnitario() +
            ", valorTotal=" + getValorTotal() +
            "}";
    }
}
