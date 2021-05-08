package br.com.ngzorro.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.ngzorro.domain.AnimalVacina} entity.
 */
public class AnimalVacinaDTO implements Serializable {

    private Long id;

    private LocalDate dataDaAplicacao;


    private Long animalTipoDeVacinaId;

    private String animalTipoDeVacinaDescricao;

    private Long animalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataDaAplicacao() {
        return dataDaAplicacao;
    }

    public void setDataDaAplicacao(LocalDate dataDaAplicacao) {
        this.dataDaAplicacao = dataDaAplicacao;
    }

    public Long getAnimalTipoDeVacinaId() {
        return animalTipoDeVacinaId;
    }

    public void setAnimalTipoDeVacinaId(Long animalTipoDeVacinaId) {
        this.animalTipoDeVacinaId = animalTipoDeVacinaId;
    }

    public String getAnimalTipoDeVacinaDescricao() {
        return animalTipoDeVacinaDescricao;
    }

    public void setAnimalTipoDeVacinaDescricao(String animalTipoDeVacinaDescricao) {
        this.animalTipoDeVacinaDescricao = animalTipoDeVacinaDescricao;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnimalVacinaDTO animalVacinaDTO = (AnimalVacinaDTO) o;
        if (animalVacinaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalVacinaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalVacinaDTO{" +
            "id=" + getId() +
            ", dataDaAplicacao='" + getDataDaAplicacao() + "'" +
            ", animalTipoDeVacina=" + getAnimalTipoDeVacinaId() +
            ", animalTipoDeVacina='" + getAnimalTipoDeVacinaDescricao() + "'" +
            ", animal=" + getAnimalId() +
            "}";
    }
}
