package br.com.ngzorro.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link br.com.ngzorro.domain.AnimalObservacao} entity.
 */
public class AnimalObservacaoDTO implements Serializable {

    private Long id;

    private LocalDate dataAlteracao;

    @Lob
    private String observacao;


    private Long animalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
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

        AnimalObservacaoDTO animalObservacaoDTO = (AnimalObservacaoDTO) o;
        if (animalObservacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalObservacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalObservacaoDTO{" +
            "id=" + getId() +
            ", dataAlteracao='" + getDataAlteracao() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", animal=" + getAnimalId() +
            "}";
    }
}
