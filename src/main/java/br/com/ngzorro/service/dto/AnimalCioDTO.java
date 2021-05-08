package br.com.ngzorro.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link br.com.ngzorro.domain.AnimalCio} entity.
 */
public class AnimalCioDTO implements Serializable {

    private Long id;

    private LocalDate dataDoCio;

    @Lob
    private String observacao;


    private Long animalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataDoCio() {
        return dataDoCio;
    }

    public void setDataDoCio(LocalDate dataDoCio) {
        this.dataDoCio = dataDoCio;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

        AnimalCioDTO animalCioDTO = (AnimalCioDTO) o;
        if (animalCioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalCioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalCioDTO{" +
            "id=" + getId() +
            ", dataDoCio='" + getDataDoCio() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", animal=" + getAnimalId() +
            "}";
    }
}
