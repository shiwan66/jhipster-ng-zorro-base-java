package br.com.ngzorro.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import br.com.ngzorro.domain.enumeration.AnimalSexo;

/**
 * A DTO for the {@link br.com.ngzorro.domain.Animal} entity.
 */
public class AnimalDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] foto;

    private String fotoContentType;
    private String fotoUrl;

    private String nome;

    private AnimalSexo sexo;

    private String pelagem;

    private String temperamento;

    private Boolean emAtendimento;

    private LocalDate dataNascimento;


    private Long enderecoId;

    private String enderecoLogradouro;

    private Long veterinarioId;

    private String veterinarioNome;

    private Long racaId;

    private String racaNome;

    private Long tutorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AnimalSexo getSexo() {
        return sexo;
    }

    public void setSexo(AnimalSexo sexo) {
        this.sexo = sexo;
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }

    public Boolean isEmAtendimento() {
        return emAtendimento;
    }

    public void setEmAtendimento(Boolean emAtendimento) {
        this.emAtendimento = emAtendimento;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getEnderecoLogradouro() {
        return enderecoLogradouro;
    }

    public void setEnderecoLogradouro(String enderecoLogradouro) {
        this.enderecoLogradouro = enderecoLogradouro;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long animalVeterinarioId) {
        this.veterinarioId = animalVeterinarioId;
    }

    public String getVeterinarioNome() {
        return veterinarioNome;
    }

    public void setVeterinarioNome(String animalVeterinarioNome) {
        this.veterinarioNome = animalVeterinarioNome;
    }

    public Long getRacaId() {
        return racaId;
    }

    public void setRacaId(Long racaId) {
        this.racaId = racaId;
    }

    public String getRacaNome() {
        return racaNome;
    }

    public void setRacaNome(String racaNome) {
        this.racaNome = racaNome;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnimalDTO animalDTO = (AnimalDTO) o;
        if (animalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalDTO{" +
            "id=" + getId() +
            ", foto='" + getFoto() + "'" +
            ", fotoUrl='" + getFotoUrl() + "'" +
            ", nome='" + getNome() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", pelagem='" + getPelagem() + "'" +
            ", temperamento='" + getTemperamento() + "'" +
            ", emAtendimento='" + isEmAtendimento() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", endereco=" + getEnderecoId() +
            ", endereco='" + getEnderecoLogradouro() + "'" +
            ", veterinario=" + getVeterinarioId() +
            ", veterinario='" + getVeterinarioNome() + "'" +
            ", raca=" + getRacaId() +
            ", raca='" + getRacaNome() + "'" +
            ", tutor=" + getTutorId() +
            "}";
    }
}
