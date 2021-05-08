package br.com.ngzorro.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.ngzorro.domain.enumeration.TipoConsumo;

/**
 * A DTO for the {@link br.com.ngzorro.domain.Consumo} entity.
 */
public class ConsumoDTO implements Serializable {

    private Long id;

    private String nome;

    private TipoConsumo tipo;

    private BigDecimal estoque;

    private BigDecimal valorUnitario;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoConsumo getTipo() {
        return tipo;
    }

    public void setTipo(TipoConsumo tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getEstoque() {
        return estoque;
    }

    public void setEstoque(BigDecimal estoque) {
        this.estoque = estoque;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsumoDTO consumoDTO = (ConsumoDTO) o;
        if (consumoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consumoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsumoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", estoque=" + getEstoque() +
            ", valorUnitario=" + getValorUnitario() +
            "}";
    }
}
