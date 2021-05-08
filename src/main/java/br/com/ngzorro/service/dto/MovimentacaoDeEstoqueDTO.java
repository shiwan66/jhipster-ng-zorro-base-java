package br.com.ngzorro.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import br.com.ngzorro.domain.enumeration.TipoMovimentacaoDeEstoque;

/**
 * A DTO for the {@link br.com.ngzorro.domain.MovimentacaoDeEstoque} entity.
 */
public class MovimentacaoDeEstoqueDTO implements Serializable {

    private Long id;

    private TipoMovimentacaoDeEstoque tipo;

    @Lob
    private String descricao;

    private ZonedDateTime data;

    private Double quantidade;


    private Long consumoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMovimentacaoDeEstoque getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacaoDeEstoque tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
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

        MovimentacaoDeEstoqueDTO movimentacaoDeEstoqueDTO = (MovimentacaoDeEstoqueDTO) o;
        if (movimentacaoDeEstoqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movimentacaoDeEstoqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovimentacaoDeEstoqueDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", data='" + getData() + "'" +
            ", quantidade=" + getQuantidade() +
            ", consumo=" + getConsumoId() +
            "}";
    }
}
