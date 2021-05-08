package br.com.ngzorro.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.ngzorro.domain.ModeloAtividade} entity.
 */
public class ModeloAtividadeDTO implements Serializable {

    private Long id;

    private String descricao;


    private Set<AtividadeDTO> atividades = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<AtividadeDTO> getAtividades() {
        return atividades;
    }

    public void setAtividades(Set<AtividadeDTO> atividades) {
        this.atividades = atividades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModeloAtividadeDTO modeloAtividadeDTO = (ModeloAtividadeDTO) o;
        if (modeloAtividadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modeloAtividadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModeloAtividadeDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
