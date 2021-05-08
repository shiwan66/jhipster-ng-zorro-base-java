package br.com.ngzorro.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import br.com.ngzorro.domain.enumeration.TipoConsumo;

/**
 * A Consumo.
 */
@Entity
@Table(name = "consumo")
public class Consumo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoConsumo tipo;

    @Column(name = "estoque", precision = 21, scale = 2)
    private BigDecimal estoque;

    @Column(name = "valor_unitario", precision = 21, scale = 2)
    private BigDecimal valorUnitario;

    @OneToMany(mappedBy = "consumo")
    private Set<VendaConsumo> vendaConsumos = new HashSet<>();

    @OneToMany(mappedBy = "consumo")
    private Set<MovimentacaoDeEstoque> movimentacaoDeEstoques = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Consumo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoConsumo getTipo() {
        return tipo;
    }

    public Consumo tipo(TipoConsumo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoConsumo tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getEstoque() {
        return estoque;
    }

    public Consumo estoque(BigDecimal estoque) {
        this.estoque = estoque;
        return this;
    }

    public void setEstoque(BigDecimal estoque) {
        this.estoque = estoque;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public Consumo valorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
        return this;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Set<VendaConsumo> getVendaConsumos() {
        return vendaConsumos;
    }

    public Consumo vendaConsumos(Set<VendaConsumo> vendaConsumos) {
        this.vendaConsumos = vendaConsumos;
        return this;
    }

    public Consumo addVendaConsumo(VendaConsumo vendaConsumo) {
        this.vendaConsumos.add(vendaConsumo);
        vendaConsumo.setConsumo(this);
        return this;
    }

    public Consumo removeVendaConsumo(VendaConsumo vendaConsumo) {
        this.vendaConsumos.remove(vendaConsumo);
        vendaConsumo.setConsumo(null);
        return this;
    }

    public void setVendaConsumos(Set<VendaConsumo> vendaConsumos) {
        this.vendaConsumos = vendaConsumos;
    }

    public Set<MovimentacaoDeEstoque> getMovimentacaoDeEstoques() {
        return movimentacaoDeEstoques;
    }

    public Consumo movimentacaoDeEstoques(Set<MovimentacaoDeEstoque> movimentacaoDeEstoques) {
        this.movimentacaoDeEstoques = movimentacaoDeEstoques;
        return this;
    }

    public Consumo addMovimentacaoDeEstoque(MovimentacaoDeEstoque movimentacaoDeEstoque) {
        this.movimentacaoDeEstoques.add(movimentacaoDeEstoque);
        movimentacaoDeEstoque.setConsumo(this);
        return this;
    }

    public Consumo removeMovimentacaoDeEstoque(MovimentacaoDeEstoque movimentacaoDeEstoque) {
        this.movimentacaoDeEstoques.remove(movimentacaoDeEstoque);
        movimentacaoDeEstoque.setConsumo(null);
        return this;
    }

    public void setMovimentacaoDeEstoques(Set<MovimentacaoDeEstoque> movimentacaoDeEstoques) {
        this.movimentacaoDeEstoques = movimentacaoDeEstoques;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consumo)) {
            return false;
        }
        return id != null && id.equals(((Consumo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Consumo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", estoque=" + getEstoque() +
            ", valorUnitario=" + getValorUnitario() +
            "}";
    }
}
