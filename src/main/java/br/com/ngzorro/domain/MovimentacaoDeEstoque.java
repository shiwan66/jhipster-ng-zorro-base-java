package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import br.com.ngzorro.domain.enumeration.TipoMovimentacaoDeEstoque;

/**
 * A MovimentacaoDeEstoque.
 */
@Entity
@Table(name = "movimentacao_de_estoque")
public class MovimentacaoDeEstoque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoMovimentacaoDeEstoque tipo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "quantidade")
    private Double quantidade;

    @ManyToOne
    @JsonIgnoreProperties("movimentacaoDeEstoques")
    private Consumo consumo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMovimentacaoDeEstoque getTipo() {
        return tipo;
    }

    public MovimentacaoDeEstoque tipo(TipoMovimentacaoDeEstoque tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoMovimentacaoDeEstoque tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public MovimentacaoDeEstoque descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public MovimentacaoDeEstoque data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public MovimentacaoDeEstoque quantidade(Double quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Consumo getConsumo() {
        return consumo;
    }

    public MovimentacaoDeEstoque consumo(Consumo consumo) {
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
        if (!(o instanceof MovimentacaoDeEstoque)) {
            return false;
        }
        return id != null && id.equals(((MovimentacaoDeEstoque) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MovimentacaoDeEstoque{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", data='" + getData() + "'" +
            ", quantidade=" + getQuantidade() +
            "}";
    }
}
