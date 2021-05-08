package br.com.ngzorro.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.ngzorro.domain.AnimalVermifugo} entity.
 */
public class AnimalVermifugoDTO implements Serializable {

    private Long id;

    private String nome;

    private LocalDate dataDaAplicacao;


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

    public LocalDate getDataDaAplicacao() {
        return dataDaAplicacao;
    }

    public void setDataDaAplicacao(LocalDate dataDaAplicacao) {
        this.dataDaAplicacao = dataDaAplicacao;
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

        AnimalVermifugoDTO animalVermifugoDTO = (AnimalVermifugoDTO) o;
        if (animalVermifugoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalVermifugoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalVermifugoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataDaAplicacao='" + getDataDaAplicacao() + "'" +
            ", animal=" + getAnimalId() +
            "}";
    }
}
