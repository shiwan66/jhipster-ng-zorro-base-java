package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AnimalVacina.
 */
@Entity
@Table(name = "animal_vacina")
public class AnimalVacina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_da_aplicacao")
    private LocalDate dataDaAplicacao;

    @ManyToOne
    @JsonIgnoreProperties("animalVacinas")
    private AnimalTipoDeVacina animalTipoDeVacina;

    @ManyToOne
    @JsonIgnoreProperties("tipoVacinas")
    private Animal animal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataDaAplicacao() {
        return dataDaAplicacao;
    }

    public AnimalVacina dataDaAplicacao(LocalDate dataDaAplicacao) {
        this.dataDaAplicacao = dataDaAplicacao;
        return this;
    }

    public void setDataDaAplicacao(LocalDate dataDaAplicacao) {
        this.dataDaAplicacao = dataDaAplicacao;
    }

    public AnimalTipoDeVacina getAnimalTipoDeVacina() {
        return animalTipoDeVacina;
    }

    public AnimalVacina animalTipoDeVacina(AnimalTipoDeVacina animalTipoDeVacina) {
        this.animalTipoDeVacina = animalTipoDeVacina;
        return this;
    }

    public void setAnimalTipoDeVacina(AnimalTipoDeVacina animalTipoDeVacina) {
        this.animalTipoDeVacina = animalTipoDeVacina;
    }

    public Animal getAnimal() {
        return animal;
    }

    public AnimalVacina animal(Animal animal) {
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
        if (!(o instanceof AnimalVacina)) {
            return false;
        }
        return id != null && id.equals(((AnimalVacina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnimalVacina{" +
            "id=" + getId() +
            ", dataDaAplicacao='" + getDataDaAplicacao() + "'" +
            "}";
    }
}
