package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A AnexoAtendimento.
 */
@Entity
@Table(name = "anexo_atendimento")
public class AnexoAtendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "anexo")
    private byte[] anexo;

    @Column(name = "anexo_content_type")
    private String anexoContentType;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "url")
    private String url;

    @Column(name = "url_thumbnail")
    private String urlThumbnail;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private Atendimento atendimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public AnexoAtendimento anexo(byte[] anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getAnexoContentType() {
        return anexoContentType;
    }

    public AnexoAtendimento anexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
        return this;
    }

    public void setAnexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
    }

    public String getDescricao() {
        return descricao;
    }

    public AnexoAtendimento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public AnexoAtendimento data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public AnexoAtendimento url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public AnexoAtendimento urlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
        return this;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public AnexoAtendimento atendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
        return this;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoAtendimento)) {
            return false;
        }
        return id != null && id.equals(((AnexoAtendimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnexoAtendimento{" +
            "id=" + getId() +
            ", anexo='" + getAnexo() + "'" +
            ", anexoContentType='" + getAnexoContentType() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", data='" + getData() + "'" +
            ", url='" + getUrl() + "'" +
            ", urlThumbnail='" + getUrlThumbnail() + "'" +
            "}";
    }
}
