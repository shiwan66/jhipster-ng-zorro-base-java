package br.com.ngzorro.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.ngzorro.domain.AnimalTipoDeVacina} entity.
 */
public class AnimalTipoDeVacinaDTO implements Serializable {

    private Long id;

    private String descricao;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnimalTipoDeVacinaDTO animalTipoDeVacinaDTO = (AnimalTipoDeVacinaDTO) o;
        if (animalTipoDeVacinaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalTipoDeVacinaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalTipoDeVacinaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
