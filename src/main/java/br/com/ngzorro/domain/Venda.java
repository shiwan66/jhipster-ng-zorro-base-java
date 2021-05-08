package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.ngzorro.domain.enumeration.TipoSituacaoDoLancamento;

/**
 * A Venda.
 */
@Entity
@Table(name = "venda")
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_da_compra")
    private ZonedDateTime dataDaCompra;

    @Column(name = "data_do_pagamento")
    private ZonedDateTime dataDoPagamento;

    @Column(name = "desconto", precision = 21, scale = 2)
    private BigDecimal desconto;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private TipoSituacaoDoLancamento situacao;

    @Column(name = "valor_total", precision = 21, scale = 2)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "venda")
    private Set<VendaConsumo> vendaConsumos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("vendas")
    private Atendimento atendimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public Venda observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ZonedDateTime getDataDaCompra() {
        return dataDaCompra;
    }

    public Venda dataDaCompra(ZonedDateTime dataDaCompra) {
        this.dataDaCompra = dataDaCompra;
        return this;
    }

    public void setDataDaCompra(ZonedDateTime dataDaCompra) {
        this.dataDaCompra = dataDaCompra;
    }

    public ZonedDateTime getDataDoPagamento() {
        return dataDoPagamento;
    }

    public Venda dataDoPagamento(ZonedDateTime dataDoPagamento) {
        this.dataDoPagamento = dataDoPagamento;
        return this;
    }

    public void setDataDoPagamento(ZonedDateTime dataDoPagamento) {
        this.dataDoPagamento = dataDoPagamento;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public Venda desconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public TipoSituacaoDoLancamento getSituacao() {
        return situacao;
    }

    public Venda situacao(TipoSituacaoDoLancamento situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(TipoSituacaoDoLancamento situacao) {
        this.situacao = situacao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public Venda valorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Set<VendaConsumo> getVendaConsumos() {
        return vendaConsumos;
    }

    public Venda vendaConsumos(Set<VendaConsumo> vendaConsumos) {
        this.vendaConsumos = vendaConsumos;
        return this;
    }

    public Venda addVendaConsumo(VendaConsumo vendaConsumo) {
        this.vendaConsumos.add(vendaConsumo);
        vendaConsumo.setVenda(this);
        return this;
    }

    public Venda removeVendaConsumo(VendaConsumo vendaConsumo) {
        this.vendaConsumos.remove(vendaConsumo);
        vendaConsumo.setVenda(null);
        return this;
    }

    public void setVendaConsumos(Set<VendaConsumo> vendaConsumos) {
        this.vendaConsumos = vendaConsumos;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public Venda atendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
        return this;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venda)) {
            return false;
        }
        return id != null && id.equals(((Venda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Venda{" +
            "id=" + getId() +
            ", observacao='" + getObservacao() + "'" +
            ", dataDaCompra='" + getDataDaCompra() + "'" +
            ", dataDoPagamento='" + getDataDoPagamento() + "'" +
            ", desconto=" + getDesconto() +
            ", situacao='" + getSituacao() + "'" +
            ", valorTotal=" + getValorTotal() +
            "}";
    }
}
