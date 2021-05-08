package br.com.ngzorro.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.ngzorro.domain.AnimalVeterinario} entity.
 */
public class AnimalVeterinarioDTO implements Serializable {

    private Long id;

    private String nome;

    private String telefone;

    private String clinica;


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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getClinica() {
        return clinica;
    }

    public void setClinica(String clinica) {
        this.clinica = clinica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnimalVeterinarioDTO animalVeterinarioDTO = (AnimalVeterinarioDTO) o;
        if (animalVeterinarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalVeterinarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalVeterinarioDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", clinica='" + getClinica() + "'" +
            "}";
    }
}
