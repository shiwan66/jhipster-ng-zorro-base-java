package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Atividade.
 */
@Entity
@Table(name = "atividade")
public class Atividade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "inicio")
    private ZonedDateTime inicio;

    @Column(name = "termino")
    private ZonedDateTime termino;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "realizado")
    private Boolean realizado;

    @ManyToOne
    @JsonIgnoreProperties("atividades")
    private Atendimento atendimento;

    @ManyToMany(mappedBy = "atividades")
    @JsonIgnore
    private Set<ModeloAtividade> modeloAtividades = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Atividade titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ZonedDateTime getInicio() {
        return inicio;
    }

    public Atividade inicio(ZonedDateTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(ZonedDateTime inicio) {
        this.inicio = inicio;
    }

    public ZonedDateTime getTermino() {
        return termino;
    }

    public Atividade termino(ZonedDateTime termino) {
        this.termino = termino;
        return this;
    }

    public void setTermino(ZonedDateTime termino) {
        this.termino = termino;
    }

    public String getObservacao() {
        return observacao;
    }

    public Atividade observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean isRealizado() {
        return realizado;
    }

    public Atividade realizado(Boolean realizado) {
        this.realizado = realizado;
        return this;
    }

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public Atividade atendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
        return this;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public Set<ModeloAtividade> getModeloAtividades() {
        return modeloAtividades;
    }

    public Atividade modeloAtividades(Set<ModeloAtividade> modeloAtividades) {
        this.modeloAtividades = modeloAtividades;
        return this;
    }

    public Atividade addModeloAtividade(ModeloAtividade modeloAtividade) {
        this.modeloAtividades.add(modeloAtividade);
        modeloAtividade.getAtividades().add(this);
        return this;
    }

    public Atividade removeModeloAtividade(ModeloAtividade modeloAtividade) {
        this.modeloAtividades.remove(modeloAtividade);
        modeloAtividade.getAtividades().remove(this);
        return this;
    }

    public void setModeloAtividades(Set<ModeloAtividade> modeloAtividades) {
        this.modeloAtividades = modeloAtividades;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Atividade)) {
            return false;
        }
        return id != null && id.equals(((Atividade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Atividade{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", termino='" + getTermino() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", realizado='" + isRealizado() + "'" +
            "}";
    }
}
