package br.com.ngzorro.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import br.com.ngzorro.domain.enumeration.AtendimentoSituacao;

/**
 * A DTO for the {@link br.com.ngzorro.domain.Atendimento} entity.
 */
public class AtendimentoDTO implements Serializable {

    private Long id;

    private AtendimentoSituacao situacao;

    private ZonedDateTime dataDeChegada;

    private ZonedDateTime dataDeSaida;

    private String observacao;


    private Long animalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AtendimentoSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(AtendimentoSituacao situacao) {
        this.situacao = situacao;
    }

    public ZonedDateTime getDataDeChegada() {
        return dataDeChegada;
    }

    public void setDataDeChegada(ZonedDateTime dataDeChegada) {
        this.dataDeChegada = dataDeChegada;
    }

    public ZonedDateTime getDataDeSaida() {
        return dataDeSaida;
    }

    public void setDataDeSaida(ZonedDateTime dataDeSaida) {
        this.dataDeSaida = dataDeSaida;
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

        AtendimentoDTO atendimentoDTO = (AtendimentoDTO) o;
        if (atendimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), atendimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AtendimentoDTO{" +
            "id=" + getId() +
            ", situacao='" + getSituacao() + "'" +
            ", dataDeChegada='" + getDataDeChegada() + "'" +
            ", dataDeSaida='" + getDataDeSaida() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", animal=" + getAnimalId() +
            "}";
    }
}
