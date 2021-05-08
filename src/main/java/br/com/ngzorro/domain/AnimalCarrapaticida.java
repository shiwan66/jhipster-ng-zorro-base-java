package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AnimalCarrapaticida.
 */
@Entity
@Table(name = "animal_carrapaticida")
public class AnimalCarrapaticida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_aplicacao")
    private LocalDate dataAplicacao;

    @ManyToOne
    @JsonIgnoreProperties("animalCarrapaticidas")
    private Animal animal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public AnimalCarrapaticida nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public AnimalCarrapaticida dataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
        return this;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public Animal getAnimal() {
        return animal;
    }

    public AnimalCarrapaticida animal(Animal animal) {
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
        if (!(o instanceof AnimalCarrapaticida)) {
            return false;
        }
        return id != null && id.equals(((AnimalCarrapaticida) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnimalCarrapaticida{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataAplicacao='" + getDataAplicacao() + "'" +
            "}";
    }
}
