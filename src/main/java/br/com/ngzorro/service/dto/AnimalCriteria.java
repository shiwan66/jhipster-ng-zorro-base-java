package br.com.ngzorro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.ngzorro.domain.enumeration.AnimalSexo;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link br.com.ngzorro.domain.Animal} entity. This class is used
 * in {@link br.com.ngzorro.web.rest.AnimalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /animals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnimalCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AnimalSexo
     */
    public static class AnimalSexoFilter extends Filter<AnimalSexo> {

        public AnimalSexoFilter() {
        }

        public AnimalSexoFilter(AnimalSexoFilter filter) {
            super(filter);
        }

        @Override
        public AnimalSexoFilter copy() {
            return new AnimalSexoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fotoUrl;

    private StringFilter nome;

    private AnimalSexoFilter sexo;

    private StringFilter pelagem;

    private StringFilter temperamento;

    private BooleanFilter emAtendimento;

    private LocalDateFilter dataNascimento;

    private LongFilter atendimentoId;

    private LongFilter tipoVacinaId;

    private LongFilter animalAlteracaoId;

    private LongFilter animalVermifugoId;

    private LongFilter animalCarrapaticidaId;

    private LongFilter observacaoId;

    private LongFilter anexoId;

    private LongFilter animalCioId;

    private LongFilter enderecoId;

    private LongFilter veterinarioId;

    private LongFilter racaId;

    private LongFilter tutorId;

    public AnimalCriteria(){
    }

    public AnimalCriteria(AnimalCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fotoUrl = other.fotoUrl == null ? null : other.fotoUrl.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.pelagem = other.pelagem == null ? null : other.pelagem.copy();
        this.temperamento = other.temperamento == null ? null : other.temperamento.copy();
        this.emAtendimento = other.emAtendimento == null ? null : other.emAtendimento.copy();
        this.dataNascimento = other.dataNascimento == null ? null : other.dataNascimento.copy();
        this.atendimentoId = other.atendimentoId == null ? null : other.atendimentoId.copy();
        this.tipoVacinaId = other.tipoVacinaId == null ? null : other.tipoVacinaId.copy();
        this.animalAlteracaoId = other.animalAlteracaoId == null ? null : other.animalAlteracaoId.copy();
        this.animalVermifugoId = other.animalVermifugoId == null ? null : other.animalVermifugoId.copy();
        this.animalCarrapaticidaId = other.animalCarrapaticidaId == null ? null : other.animalCarrapaticidaId.copy();
        this.observacaoId = other.observacaoId == null ? null : other.observacaoId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.animalCioId = other.animalCioId == null ? null : other.animalCioId.copy();
        this.enderecoId = other.enderecoId == null ? null : other.enderecoId.copy();
        this.veterinarioId = other.veterinarioId == null ? null : other.veterinarioId.copy();
        this.racaId = other.racaId == null ? null : other.racaId.copy();
        this.tutorId = other.tutorId == null ? null : other.tutorId.copy();
    }

    @Override
    public AnimalCriteria copy() {
        return new AnimalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(StringFilter fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public AnimalSexoFilter getSexo() {
        return sexo;
    }

    public void setSexo(AnimalSexoFilter sexo) {
        this.sexo = sexo;
    }

    public StringFilter getPelagem() {
        return pelagem;
    }

    public void setPelagem(StringFilter pelagem) {
        this.pelagem = pelagem;
    }

    public StringFilter getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(StringFilter temperamento) {
        this.temperamento = temperamento;
    }

    public BooleanFilter getEmAtendimento() {
        return emAtendimento;
    }

    public void setEmAtendimento(BooleanFilter emAtendimento) {
        this.emAtendimento = emAtendimento;
    }

    public LocalDateFilter getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateFilter dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LongFilter getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(LongFilter atendimentoId) {
        this.atendimentoId = atendimentoId;
    }

    public LongFilter getTipoVacinaId() {
        return tipoVacinaId;
    }

    public void setTipoVacinaId(LongFilter tipoVacinaId) {
        this.tipoVacinaId = tipoVacinaId;
    }

    public LongFilter getAnimalAlteracaoId() {
        return animalAlteracaoId;
    }

    public void setAnimalAlteracaoId(LongFilter animalAlteracaoId) {
        this.animalAlteracaoId = animalAlteracaoId;
    }

    public LongFilter getAnimalVermifugoId() {
        return animalVermifugoId;
    }

    public void setAnimalVermifugoId(LongFilter animalVermifugoId) {
        this.animalVermifugoId = animalVermifugoId;
    }

    public LongFilter getAnimalCarrapaticidaId() {
        return animalCarrapaticidaId;
    }

    public void setAnimalCarrapaticidaId(LongFilter animalCarrapaticidaId) {
        this.animalCarrapaticidaId = animalCarrapaticidaId;
    }

    public LongFilter getObservacaoId() {
        return observacaoId;
    }

    public void setObservacaoId(LongFilter observacaoId) {
        this.observacaoId = observacaoId;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getAnimalCioId() {
        return animalCioId;
    }

    public void setAnimalCioId(LongFilter animalCioId) {
        this.animalCioId = animalCioId;
    }

    public LongFilter getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(LongFilter enderecoId) {
        this.enderecoId = enderecoId;
    }

    public LongFilter getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(LongFilter veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public LongFilter getRacaId() {
        return racaId;
    }

    public void setRacaId(LongFilter racaId) {
        this.racaId = racaId;
    }

    public LongFilter getTutorId() {
        return tutorId;
    }

    public void setTutorId(LongFilter tutorId) {
        this.tutorId = tutorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnimalCriteria that = (AnimalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fotoUrl, that.fotoUrl) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(pelagem, that.pelagem) &&
            Objects.equals(temperamento, that.temperamento) &&
            Objects.equals(emAtendimento, that.emAtendimento) &&
            Objects.equals(dataNascimento, that.dataNascimento) &&
            Objects.equals(atendimentoId, that.atendimentoId) &&
            Objects.equals(tipoVacinaId, that.tipoVacinaId) &&
            Objects.equals(animalAlteracaoId, that.animalAlteracaoId) &&
            Objects.equals(animalVermifugoId, that.animalVermifugoId) &&
            Objects.equals(animalCarrapaticidaId, that.animalCarrapaticidaId) &&
            Objects.equals(observacaoId, that.observacaoId) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(animalCioId, that.animalCioId) &&
            Objects.equals(enderecoId, that.enderecoId) &&
            Objects.equals(veterinarioId, that.veterinarioId) &&
            Objects.equals(racaId, that.racaId) &&
            Objects.equals(tutorId, that.tutorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fotoUrl,
        nome,
        sexo,
        pelagem,
        temperamento,
        emAtendimento,
        dataNascimento,
        atendimentoId,
        tipoVacinaId,
        animalAlteracaoId,
        animalVermifugoId,
        animalCarrapaticidaId,
        observacaoId,
        anexoId,
        animalCioId,
        enderecoId,
        veterinarioId,
        racaId,
        tutorId
        );
    }

    @Override
    public String toString() {
        return "AnimalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fotoUrl != null ? "fotoUrl=" + fotoUrl + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (sexo != null ? "sexo=" + sexo + ", " : "") +
                (pelagem != null ? "pelagem=" + pelagem + ", " : "") +
                (temperamento != null ? "temperamento=" + temperamento + ", " : "") +
                (emAtendimento != null ? "emAtendimento=" + emAtendimento + ", " : "") +
                (dataNascimento != null ? "dataNascimento=" + dataNascimento + ", " : "") +
                (atendimentoId != null ? "atendimentoId=" + atendimentoId + ", " : "") +
                (tipoVacinaId != null ? "tipoVacinaId=" + tipoVacinaId + ", " : "") +
                (animalAlteracaoId != null ? "animalAlteracaoId=" + animalAlteracaoId + ", " : "") +
                (animalVermifugoId != null ? "animalVermifugoId=" + animalVermifugoId + ", " : "") +
                (animalCarrapaticidaId != null ? "animalCarrapaticidaId=" + animalCarrapaticidaId + ", " : "") +
                (observacaoId != null ? "observacaoId=" + observacaoId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (animalCioId != null ? "animalCioId=" + animalCioId + ", " : "") +
                (enderecoId != null ? "enderecoId=" + enderecoId + ", " : "") +
                (veterinarioId != null ? "veterinarioId=" + veterinarioId + ", " : "") +
                (racaId != null ? "racaId=" + racaId + ", " : "") +
                (tutorId != null ? "tutorId=" + tutorId + ", " : "") +
            "}";
    }

}
