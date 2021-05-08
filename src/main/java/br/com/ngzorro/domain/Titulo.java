package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.ngzorro.domain.enumeration.TipoTitulo;

/**
 * A Titulo.
 */
@Entity
@Table(name = "titulo")
public class Titulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_pago")
    private Boolean isPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTitulo tipo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @ManyToOne
    @JsonIgnoreProperties("titulos")
    private Tutor tutor;

    @ManyToOne
    @JsonIgnoreProperties("titulos")
    private Fornecedor fornecedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsPago() {
        return isPago;
    }

    public Titulo isPago(Boolean isPago) {
        this.isPago = isPago;
        return this;
    }

    public void setIsPago(Boolean isPago) {
        this.isPago = isPago;
    }

    public TipoTitulo getTipo() {
        return tipo;
    }

    public Titulo tipo(TipoTitulo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoTitulo tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Titulo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Titulo valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public Titulo dataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
        return this;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public Titulo dataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
        return this;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public Titulo dataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public Titulo tutor(Tutor tutor) {
        this.tutor = tutor;
        return this;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Titulo fornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        return this;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Titulo)) {
            return false;
        }
        return id != null && id.equals(((Titulo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Titulo{" +
            "id=" + getId() +
            ", isPago='" + isIsPago() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            ", dataEmissao='" + getDataEmissao() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            "}";
    }
}
