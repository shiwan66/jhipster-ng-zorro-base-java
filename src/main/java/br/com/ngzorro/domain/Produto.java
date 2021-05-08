package br.com.ngzorro.domain;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco", precision = 21, scale = 2)
    private BigDecimal preco;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "hora")
    private Instant hora;

    @ManyToMany
    @JoinTable(name = "produto_categoria",
               joinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id"))
    private Set<Categoria> categorias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public Produto imagem(byte[] imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return imagemContentType;
    }

    public Produto imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public String getNome() {
        return nome;
    }

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Produto preco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public LocalDate getData() {
        return data;
    }

    public Produto data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Instant getHora() {
        return hora;
    }

    public Produto hora(Instant hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(Instant hora) {
        this.hora = hora;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public Produto categorias(Set<Categoria> categorias) {
        this.categorias = categorias;
        return this;
    }

    public Produto addCategoria(Categoria categoria) {
        this.categorias.add(categoria);
        categoria.getProdutos().add(this);
        return this;
    }

    public Produto removeCategoria(Categoria categoria) {
        this.categorias.remove(categoria);
        categoria.getProdutos().remove(this);
        return this;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return id != null && id.equals(((Produto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", preco=" + getPreco() +
            ", data='" + getData() + "'" +
            ", hora='" + getHora() + "'" +
            "}";
    }
}
