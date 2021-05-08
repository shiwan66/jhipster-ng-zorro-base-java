package br.com.ngzorro.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link br.com.ngzorro.domain.AnimalAlteracao} entity.
 */
public class AnimalAlteracaoDTO implements Serializable {

    private Long id;

    @Lob
    private String descricao;

    private LocalDate dataAlteracao;


    private Long animalTipoDeAlteracaoId;

    private String animalTipoDeAlteracaoDescricao;

    private Long animalId;

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

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Long getAnimalTipoDeAlteracaoId() {
        return animalTipoDeAlteracaoId;
    }

    public void setAnimalTipoDeAlteracaoId(Long animalTipoDeAlteracaoId) {
        this.animalTipoDeAlteracaoId = animalTipoDeAlteracaoId;
    }

    public String getAnimalTipoDeAlteracaoDescricao() {
        return animalTipoDeAlteracaoDescricao;
    }

    public void setAnimalTipoDeAlteracaoDescricao(String animalTipoDeAlteracaoDescricao) {
        this.animalTipoDeAlteracaoDescricao = animalTipoDeAlteracaoDescricao;
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

        AnimalAlteracaoDTO animalAlteracaoDTO = (AnimalAlteracaoDTO) o;
        if (animalAlteracaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalAlteracaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalAlteracaoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", dataAlteracao='" + getDataAlteracao() + "'" +
            ", animalTipoDeAlteracao=" + getAnimalTipoDeAlteracaoId() +
            ", animalTipoDeAlteracao='" + getAnimalTipoDeAlteracaoDescricao() + "'" +
            ", animal=" + getAnimalId() +
            "}";
    }
}
