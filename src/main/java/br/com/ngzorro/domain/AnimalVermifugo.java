package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AnimalVermifugo.
 */
@Entity
@Table(name = "animal_vermifugo")
public class AnimalVermifugo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_da_aplicacao")
    private LocalDate dataDaAplicacao;

    @ManyToOne
    @JsonIgnoreProperties("animalVermifugos")
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

    public AnimalVermifugo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDaAplicacao() {
        return dataDaAplicacao;
    }

    public AnimalVermifugo dataDaAplicacao(LocalDate dataDaAplicacao) {
        this.dataDaAplicacao = dataDaAplicacao;
        return this;
    }

    public void setDataDaAplicacao(LocalDate dataDaAplicacao) {
        this.dataDaAplicacao = dataDaAplicacao;
    }

    public Animal getAnimal() {
        return animal;
    }

    public AnimalVermifugo animal(Animal animal) {
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
        if (!(o instanceof AnimalVermifugo)) {
            return false;
        }
        return id != null && id.equals(((AnimalVermifugo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnimalVermifugo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataDaAplicacao='" + getDataDaAplicacao() + "'" +
            "}";
    }
}
