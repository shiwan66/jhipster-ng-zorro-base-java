package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AnimalObservacao.
 */
@Entity
@Table(name = "animal_observacao")
public class AnimalObservacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_alteracao")
    private LocalDate dataAlteracao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JsonIgnoreProperties("observacaos")
    private Animal animal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public AnimalObservacao dataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
        return this;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getObservacao() {
        return observacao;
    }

    public AnimalObservacao observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Animal getAnimal() {
        return animal;
    }

    public AnimalObservacao animal(Animal animal) {
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
        if (!(o instanceof AnimalObservacao)) {
            return false;
        }
        return id != null && id.equals(((AnimalObservacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnimalObservacao{" +
            "id=" + getId() +
            ", dataAlteracao='" + getDataAlteracao() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
