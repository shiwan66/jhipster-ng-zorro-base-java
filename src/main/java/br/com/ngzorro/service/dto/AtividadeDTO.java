package br.com.ngzorro.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.ngzorro.domain.Atividade} entity.
 */
public class AtividadeDTO implements Serializable {

    private Long id;

    private String titulo;

    private ZonedDateTime inicio;

    private ZonedDateTime termino;

    private String observacao;

    private Boolean realizado;


    private Long atendimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ZonedDateTime getInicio() {
        return inicio;
    }

    public void setInicio(ZonedDateTime inicio) {
        this.inicio = inicio;
    }

    public ZonedDateTime getTermino() {
        return termino;
    }

    public void setTermino(ZonedDateTime termino) {
        this.termino = termino;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }

    public Long getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(Long atendimentoId) {
        this.atendimentoId = atendimentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AtividadeDTO atividadeDTO = (AtividadeDTO) o;
        if (atividadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), atividadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AtividadeDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", termino='" + getTermino() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", realizado='" + isRealizado() + "'" +
            ", atendimento=" + getAtendimentoId() +
            "}";
    }
}
