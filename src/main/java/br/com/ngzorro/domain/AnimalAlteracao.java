package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AnimalAlteracao.
 */
@Entity
@Table(name = "animal_alteracao")
public class AnimalAlteracao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_alteracao")
    private LocalDate dataAlteracao;

    @ManyToOne
    @JsonIgnoreProperties("animalAlteracaos")
    private AnimalTipoDeAlteracao animalTipoDeAlteracao;

    @ManyToOne
    @JsonIgnoreProperties("animalAlteracaos")
    private Animal animal;

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

    public AnimalAlteracao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public AnimalAlteracao dataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
        return this;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public AnimalTipoDeAlteracao getAnimalTipoDeAlteracao() {
        return animalTipoDeAlteracao;
    }

    public AnimalAlteracao animalTipoDeAlteracao(AnimalTipoDeAlteracao animalTipoDeAlteracao) {
        this.animalTipoDeAlteracao = animalTipoDeAlteracao;
        return this;
    }

    public void setAnimalTipoDeAlteracao(AnimalTipoDeAlteracao animalTipoDeAlteracao) {
        this.animalTipoDeAlteracao = animalTipoDeAlteracao;
    }

    public Animal getAnimal() {
        return animal;
    }

    public AnimalAlteracao animal(Animal animal) {
        this.animal = animal;
        return this;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnimalAlteracao)) {
            return false;
        }
        return id != null && id.equals(((AnimalAlteracao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnimalAlteracao{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", dataAlteracao='" + getDataAlteracao() + "'" +
            "}";
    }
}
