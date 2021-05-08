package br.com.ngzorro.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import br.com.ngzorro.domain.enumeration.Sexo;

/**
 * A DTO for the {@link br.com.ngzorro.domain.Tutor} entity.
 */
public class TutorDTO implements Serializable {

    private Long id;

    private String nome;

    private String sobrenome;

    private String telefone1;

    private String telefone2;

    @NotNull
    private String email;

    @Lob
    private byte[] foto;

    private String fotoContentType;
    private String fotoUrl;

    private String cpf;

    private Sexo sexo;

    private LocalDate dataCadastro;


    private Long userId;

    private String userFirstName;

    private Long enderecoId;

    private String enderecoLogradouro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TutorDTO tutorDTO = (TutorDTO) o;
        if (tutorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tutorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TutorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", telefone1='" + getTelefone1() + "'" +
            ", telefone2='" + getTelefone2() + "'" +
            ", email='" + getEmail() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoUrl='" + getFotoUrl() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserFirstName() + "'" +
            ", endereco=" + getEnderecoId() +
            ", endereco='" + getEnderecoLogradouro() + "'" +
            "}";
    }
}
