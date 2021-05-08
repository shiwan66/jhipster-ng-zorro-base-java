package br.com.ngzorro.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link br.com.ngzorro.domain.Anexo} entity.
 */
public class AnexoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] anexo;

    private String anexoContentType;
    private String descricao;

    private ZonedDateTime data;

    private String url;

    private String urlThumbnail;


    private Long animalId;

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

        AnexoDTO anexoDTO = (AnexoDTO) o;
        if (anexoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnexoDTO{" +
            "id=" + getId() +
            ", anexo='" + getAnexo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", data='" + getData() + "'" +
            ", url='" + getUrl() + "'" +
            ", urlThumbnail='" + getUrlThumbnail() + "'" +
            ", animal=" + getAnimalId() +
            "}";
    }
}
