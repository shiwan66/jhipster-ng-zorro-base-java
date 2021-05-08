package br.com.ngzorro.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.ngzorro.domain.Fornecedor} entity.
 */
public class FornecedorDTO implements Serializable {

    private Long id;

    private String nome;

    private String telefone;

    private String email;

    private String pontoReferencia;


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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
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

        FornecedorDTO fornecedorDTO = (FornecedorDTO) o;
        if (fornecedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fornecedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FornecedorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", pontoReferencia='" + getPontoReferencia() + "'" +
            ", endereco=" + getEnderecoId() +
            ", endereco='" + getEnderecoLogradouro() + "'" +
            "}";
    }
}
