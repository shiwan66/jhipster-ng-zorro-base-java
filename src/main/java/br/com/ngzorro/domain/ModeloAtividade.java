package br.com.ngzorro.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ModeloAtividade.
 */
@Entity
@Table(name = "modelo_atividade")
public class ModeloAtividade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @ManyToMany
    @JoinTable(name = "modelo_atividade_atividade",
               joinColumns = @JoinColumn(name = "modelo_atividade_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "atividade_id", referencedColumnName = "id"))
    private Set<Atividade> atividades = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public ModeloAtividade descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Atividade> getAtividades() {
        return atividades;
    }

    public ModeloAtividade atividades(Set<Atividade> atividades) {
        this.atividades = atividades;
        return this;
    }

    public ModeloAtividade addAtividade(Atividade atividade) {
        this.atividades.add(atividade);
        atividade.getModeloAtividades().add(this);
        return this;
    }

    public ModeloAtividade removeAtividade(Atividade atividade) {
        this.atividades.remove(atividade);
        atividade.getModeloAtividades().remove(this);
        return this;
    }

    public void setAtividades(Set<Atividade> atividades) {
        this.atividades = atividades;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModeloAtividade)) {
            return false;
        }
        return id != null && id.equals(((ModeloAtividade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ModeloAtividade{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
