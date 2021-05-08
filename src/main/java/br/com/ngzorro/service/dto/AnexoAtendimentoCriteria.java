package br.com.ngzorro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.AnexoAtendimento} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AnexoAtendimentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /anexo-atendimentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnexoAtendimentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private ZonedDateTimeFilter data;

    private StringFilter url;

    private StringFilter urlThumbnail;

    private LongFilter atendimentoId;

    public AnexoAtendimentoCriteria(){
    }

    public AnexoAtendimentoCriteria(AnexoAtendimentoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.urlThumbnail = other.urlThumbnail == null ? null : other.urlThumbnail.copy();
        this.atendimentoId = other.atendimentoId == null ? null : other.atendimentoId.copy();
    }

    @Override
    public AnexoAtendimentoCriteria copy() {
        return new AnexoAtendimentoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTimeFilter getData() {
        return data;
    }

    public void setData(ZonedDateTimeFilter data) {
        this.data = data;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(StringFilter urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public LongFilter getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(LongFilter atendimentoId) {
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
        final AnexoAtendimentoCriteria that = (AnexoAtendimentoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(data, that.data) &&
            Objects.equals(url, that.url) &&
            Objects.equals(urlThumbnail, that.urlThumbnail) &&
            Objects.equals(atendimentoId, that.atendimentoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        descricao,
        data,
        url,
        urlThumbnail,
        atendimentoId
        );
    }

    @Override
    public String toString() {
        return "AnexoAtendimentoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (urlThumbnail != null ? "urlThumbnail=" + urlThumbnail + ", " : "") +
                (atendimentoId != null ? "atendimentoId=" + atendimentoId + ", " : "") +
            "}";
    }

}
