package br.com.ngzorro.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AnimalVeterinario.
 */
@Entity
@Table(name = "animal_veterinario")
public class AnimalVeterinario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "clinica")
    private String clinica;

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

    public AnimalVeterinario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public AnimalVeterinario telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getClinica() {
        return clinica;
    }

    public AnimalVeterinario clinica(String clinica) {
        this.clinica = clinica;
        return this;
    }

    public void setClinica(String clinica) {
        this.clinica = clinica;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnimalVeterinario)) {
            return false;
        }
        return id != null && id.equals(((AnimalVeterinario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnimalVeterinario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", clinica='" + getClinica() + "'" +
            "}";
    }
}
