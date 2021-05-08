package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.ngzorro.domain.enumeration.Sexo;

/**
 * A Tutor.
 */
@Entity
@Table(name = "tutor")
public class Tutor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Column(name = "telefone_1")
    private String telefone1;

    @Column(name = "telefone_2")
    private String telefone2;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "cpf")
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "tutor")
    private Set<Titulo> titulos = new HashSet<>();

    @OneToMany(mappedBy = "tutor")
    private Set<Animal> animals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tutors")
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

    public Tutor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Tutor sobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return this;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public Tutor telefone1(String telefone1) {
        this.telefone1 = telefone1;
        return this;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public Tutor telefone2(String telefone2) {
        this.telefone2 = telefone2;
        return this;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return email;
    }

    public Tutor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Tutor foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Tutor fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public Tutor fotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
        return this;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getCpf() {
        return cpf;
    }

    public Tutor cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Tutor sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Tutor dataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public User getUser() {
        return user;
    }

    public Tutor user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Titulo> getTitulos() {
        return titulos;
    }

    public Tutor titulos(Set<Titulo> titulos) {
        this.titulos = titulos;
        return this;
    }

    public Tutor addTitulo(Titulo titulo) {
        this.titulos.add(titulo);
        titulo.setTutor(this);
        return this;
    }

    public Tutor removeTitulo(Titulo titulo) {
        this.titulos.remove(titulo);
        titulo.setTutor(null);
        return this;
    }

    public void setTitulos(Set<Titulo> titulos) {
        this.titulos = titulos;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public Tutor animals(Set<Animal> animals) {
        this.animals = animals;
        return this;
    }

    public Tutor addAnimal(Animal animal) {
        this.animals.add(animal);
        animal.setTutor(this);
        return this;
    }

    public Tutor removeAnimal(Animal animal) {
        this.animals.remove(animal);
        animal.setTutor(null);
        return this;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Tutor endereco(Endereco endereco) {
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
        if (!(o instanceof Tutor)) {
            return false;
        }
        return id != null && id.equals(((Tutor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tutor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", telefone1='" + getTelefone1() + "'" +
            ", telefone2='" + getTelefone2() + "'" +
            ", email='" + getEmail() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", fotoUrl='" + getFotoUrl() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            "}";
    }
}
