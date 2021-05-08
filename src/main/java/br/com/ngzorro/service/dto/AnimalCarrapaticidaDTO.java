package br.com.ngzorro.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.ngzorro.domain.AnimalCarrapaticida} entity.
 */
public class AnimalCarrapaticidaDTO implements Serializable {

    private Long id;

    private String nome;

    private LocalDate dataAplicacao;


    private Long animalId;

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

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
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

        AnimalCarrapaticidaDTO animalCarrapaticidaDTO = (AnimalCarrapaticidaDTO) o;
        if (animalCarrapaticidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalCarrapaticidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalCarrapaticidaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataAplicacao='" + getDataAplicacao() + "'" +
            ", animal=" + getAnimalId() +
            "}";
    }
}
