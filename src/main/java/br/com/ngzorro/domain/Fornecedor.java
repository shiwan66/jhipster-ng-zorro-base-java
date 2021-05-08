package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fornecedor.
 */
@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "ponto_referencia")
    private String pontoReferencia;

    @OneToMany(mappedBy = "fornecedor")
    private Set<Titulo> titulos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("fornecedors")
    private Endereco endereco;

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

    public Fornecedor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Fornecedor telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public Fornecedor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public Fornecedor pontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
        return this;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public Set<Titulo> getTitulos() {
        return titulos;
    }

    public Fornecedor titulos(Set<Titulo> titulos) {
        this.titulos = titulos;
        return this;
    }

    public Fornecedor addTitulo(Titulo titulo) {
        this.titulos.add(titulo);
        titulo.setFornecedor(this);
        return this;
    }

    public Fornecedor removeTitulo(Titulo titulo) {
        this.titulos.remove(titulo);
        titulo.setFornecedor(null);
        return this;
    }

    public void setTitulos(Set<Titulo> titulos) {
        this.titulos = titulos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Fornecedor endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fornecedor)) {
            return false;
        }
        return id != null && id.equals(((Fornecedor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", pontoReferencia='" + getPontoReferencia() + "'" +
            "}";
    }
}
