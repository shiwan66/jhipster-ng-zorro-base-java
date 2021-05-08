package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.ngzorro.domain.enumeration.AnimalSexo;

/**
 * A Animal.
 */
@Entity
@Table(name = "animal")
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private AnimalSexo sexo;

    @Column(name = "pelagem")
    private String pelagem;

    @Column(name = "temperamento")
    private String temperamento;

    @Column(name = "em_atendimento")
    private Boolean emAtendimento;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "animal")
    private Set<Atendimento> atendimentos = new HashSet<>();

    @OneToMany(mappedBy = "animal")
    private Set<AnimalVacina> tipoVacinas = new HashSet<>();

    @OneToMany(mappedBy = "animal")
    private Set<AnimalAlteracao> animalAlteracaos = new HashSet<>();

    @OneToMany(mappedBy = "animal")
    private Set<AnimalVermifugo> animalVermifugos = new HashSet<>();

    @OneToMany(mappedBy = "animal")
    private Set<AnimalCarrapaticida> animalCarrapaticidas = new HashSet<>();

    @OneToMany(mappedBy = "animal")
    private Set<AnimalObservacao> observacaos = new HashSet<>();

    @OneToMany(mappedBy = "animal")
    private Set<Anexo> anexos = new HashSet<>();

    @OneToMany(mappedBy = "animal")
    private Set<AnimalCio> animalCios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("animals")
    private Endereco endereco;

    @ManyToOne
    @JsonIgnoreProperties("animals")
    private AnimalVeterinario veterinario;

    @ManyToOne
    @JsonIgnoreProperties("animals")
    private Raca raca;

    @ManyToOne
    @JsonIgnoreProperties("animals")
    private Tutor tutor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Animal foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Animal fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public Animal fotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
        return this;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getNome() {
        return nome;
    }

    public Animal nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AnimalSexo getSexo() {
        return sexo;
    }

    public Animal sexo(AnimalSexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(AnimalSexo sexo) {
        this.sexo = sexo;
    }

    public String getPelagem() {
        return pelagem;
    }

    public Animal pelagem(String pelagem) {
        this.pelagem = pelagem;
        return this;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public Animal temperamento(String temperamento) {
        this.temperamento = temperamento;
        return this;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }

    public Boolean isEmAtendimento() {
        return emAtendimento;
    }

    public Animal emAtendimento(Boolean emAtendimento) {
        this.emAtendimento = emAtendimento;
        return this;
    }

    public void setEmAtendimento(Boolean emAtendimento) {
        this.emAtendimento = emAtendimento;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Animal dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Set<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public Animal atendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
        return this;
    }

    public Animal addAtendimento(Atendimento atendimento) {
        this.atendimentos.add(atendimento);
        atendimento.setAnimal(this);
        return this;
    }

    public Animal removeAtendimento(Atendimento atendimento) {
        this.atendimentos.remove(atendimento);
        atendimento.setAnimal(null);
        return this;
    }

    public void setAtendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public Set<AnimalVacina> getTipoVacinas() {
        return tipoVacinas;
    }

    public Animal tipoVacinas(Set<AnimalVacina> animalVacinas) {
        this.tipoVacinas = animalVacinas;
        return this;
    }

    public Animal addTipoVacina(AnimalVacina animalVacina) {
        this.tipoVacinas.add(animalVacina);
        animalVacina.setAnimal(this);
        return this;
    }

    public Animal removeTipoVacina(AnimalVacina animalVacina) {
        this.tipoVacinas.remove(animalVacina);
        animalVacina.setAnimal(null);
        return this;
    }

    public void setTipoVacinas(Set<AnimalVacina> animalVacinas) {
        this.tipoVacinas = animalVacinas;
    }

    public Set<AnimalAlteracao> getAnimalAlteracaos() {
        return animalAlteracaos;
    }

    public Animal animalAlteracaos(Set<AnimalAlteracao> animalAlteracaos) {
        this.animalAlteracaos = animalAlteracaos;
        return this;
    }

    public Animal addAnimalAlteracao(AnimalAlteracao animalAlteracao) {
        this.animalAlteracaos.add(animalAlteracao);
        animalAlteracao.setAnimal(this);
        return this;
    }

    public Animal removeAnimalAlteracao(AnimalAlteracao animalAlteracao) {
        this.animalAlteracaos.remove(animalAlteracao);
        animalAlteracao.setAnimal(null);
        return this;
    }

    public void setAnimalAlteracaos(Set<AnimalAlteracao> animalAlteracaos) {
        this.animalAlteracaos = animalAlteracaos;
    }

    public Set<AnimalVermifugo> getAnimalVermifugos() {
        return animalVermifugos;
    }

    public Animal animalVermifugos(Set<AnimalVermifugo> animalVermifugos) {
        this.animalVermifugos = animalVermifugos;
        return this;
    }

    public Animal addAnimalVermifugo(AnimalVermifugo animalVermifugo) {
        this.animalVermifugos.add(animalVermifugo);
        animalVermifugo.setAnimal(this);
        return this;
    }

    public Animal removeAnimalVermifugo(AnimalVermifugo animalVermifugo) {
        this.animalVermifugos.remove(animalVermifugo);
        animalVermifugo.setAnimal(null);
        return this;
    }

    public void setAnimalVermifugos(Set<AnimalVermifugo> animalVermifugos) {
        this.animalVermifugos = animalVermifugos;
    }

    public Set<AnimalCarrapaticida> getAnimalCarrapaticidas() {
        return animalCarrapaticidas;
    }

    public Animal animalCarrapaticidas(Set<AnimalCarrapaticida> animalCarrapaticidas) {
        this.animalCarrapaticidas = animalCarrapaticidas;
        return this;
    }

    public Animal addAnimalCarrapaticida(AnimalCarrapaticida animalCarrapaticida) {
        this.animalCarrapaticidas.add(animalCarrapaticida);
        animalCarrapaticida.setAnimal(this);
        return this;
    }

    public Animal removeAnimalCarrapaticida(AnimalCarrapaticida animalCarrapaticida) {
        this.animalCarrapaticidas.remove(animalCarrapaticida);
        animalCarrapaticida.setAnimal(null);
        return this;
    }

    public void setAnimalCarrapaticidas(Set<AnimalCarrapaticida> animalCarrapaticidas) {
        this.animalCarrapaticidas = animalCarrapaticidas;
    }

    public Set<AnimalObservacao> getObservacaos() {
        return observacaos;
    }

    public Animal observacaos(Set<AnimalObservacao> animalObservacaos) {
        this.observacaos = animalObservacaos;
        return this;
    }

    public Animal addObservacao(AnimalObservacao animalObservacao) {
        this.observacaos.add(animalObservacao);
        animalObservacao.setAnimal(this);
        return this;
    }

    public Animal removeObservacao(AnimalObservacao animalObservacao) {
        this.observacaos.remove(animalObservacao);
        animalObservacao.setAnimal(null);
        return this;
    }

    public void setObservacaos(Set<AnimalObservacao> animalObservacaos) {
        this.observacaos = animalObservacaos;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public Animal anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public Animal addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.setAnimal(this);
        return this;
    }

    public Animal removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.setAnimal(null);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }

    public Set<AnimalCio> getAnimalCios() {
        return animalCios;
    }

    public Animal animalCios(Set<AnimalCio> animalCios) {
        this.animalCios = animalCios;
        return this;
    }

    public Animal addAnimalCio(AnimalCio animalCio) {
        this.animalCios.add(animalCio);
        animalCio.setAnimal(this);
        return this;
    }

    public Animal removeAnimalCio(AnimalCio animalCio) {
        this.animalCios.remove(animalCio);
        animalCio.setAnimal(null);
        return this;
    }

    public void setAnimalCios(Set<AnimalCio> animalCios) {
        this.animalCios = animalCios;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Animal endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public AnimalVeterinario getVeterinario() {
        return veterinario;
    }

    public Animal veterinario(AnimalVeterinario animalVeterinario) {
        this.veterinario = animalVeterinario;
        return this;
    }

    public void setVeterinario(AnimalVeterinario animalVeterinario) {
        this.veterinario = animalVeterinario;
    }

    public Raca getRaca() {
        return raca;
    }

    public Animal raca(Raca raca) {
        this.raca = raca;
        return this;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public Animal tutor(Tutor tutor) {
        this.tutor = tutor;
        return this;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Animal)) {
            return false;
        }
        return id != null && id.equals(((Animal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Animal{" +
            "id=" + getId() +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", fotoUrl='" + getFotoUrl() + "'" +
            ", nome='" + getNome() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", pelagem='" + getPelagem() + "'" +
            ", temperamento='" + getTemperamento() + "'" +
            ", emAtendimento='" + isEmAtendimento() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            "}";
    }
}
