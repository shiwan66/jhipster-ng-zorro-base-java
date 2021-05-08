package br.com.ngzorro.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AnimalTipoDeVacina.
 */
@Entity
@Table(name = "animal_tipo_de_vacina")
public class AnimalTipoDeVacina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

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

    public AnimalTipoDeVacina descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnimalTipoDeVacina)) {
            return false;
        }
        return id != null && id.equals(((AnimalTipoDeVacina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnimalTipoDeVacina{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
