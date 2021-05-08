package br.com.ngzorro.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link br.com.ngzorro.domain.AnexoAtendimento} entity.
 */
public class AnexoAtendimentoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] anexo;

    private String anexoContentType;
    private String descricao;

    private ZonedDateTime data;

    private String url;

    private String urlThumbnail;


    private Long atendimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getAnexoContentType() {
        return anexoContentType;
    }

    public void setAnexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
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

        AnexoAtendimentoDTO anexoAtendimentoDTO = (AnexoAtendimentoDTO) o;
        if (anexoAtendimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexoAtendimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnexoAtendimentoDTO{" +
            "id=" + getId() +
            ", anexo='" + getAnexo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", data='" + getData() + "'" +
            ", url='" + getUrl() + "'" +
            ", urlThumbnail='" + getUrlThumbnail() + "'" +
            ", atendimento=" + getAtendimentoId() +
            "}";
    }
}
