package br.com.ngzorro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.ngzorro.domain.enumeration.AtendimentoSituacao;

/**
 * A Atendimento.
 */
@Entity
@Table(name = "atendimento")
public class Atendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private AtendimentoSituacao situacao;

    @Column(name = "data_de_chegada")
    private ZonedDateTime dataDeChegada;

    @Column(name = "data_de_saida")
    private ZonedDateTime dataDeSaida;

    @Column(name = "observacao")
    private String observacao;

    @OneToMany(mappedBy = "atendimento")
    private Set<Atividade> atividades = new HashSet<>();

    @OneToMany(mappedBy = "atendimento")
    private Set<Venda> vendas = new HashSet<>();

    @OneToMany(mappedBy = "atendimento")
    private Set<AnexoAtendimento> anexos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("atendimentos")
    private Animal animal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AtendimentoSituacao getSituacao() {
        return situacao;
    }

    public Atendimento situacao(AtendimentoSituacao situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(AtendimentoSituacao situacao) {
        this.situacao = situacao;
    }

    public ZonedDateTime getDataDeChegada() {
        return dataDeChegada;
    }

    public Atendimento dataDeChegada(ZonedDateTime dataDeChegada) {
        this.dataDeChegada = dataDeChegada;
        return this;
    }

    public void setDataDeChegada(ZonedDateTime dataDeChegada) {
        this.dataDeChegada = dataDeChegada;
    }

    public ZonedDateTime getDataDeSaida() {
        return dataDeSaida;
    }

    public Atendimento dataDeSaida(ZonedDateTime dataDeSaida) {
        this.dataDeSaida = dataDeSaida;
        return this;
    }

    public void setDataDeSaida(ZonedDateTime dataDeSaida) {
        this.dataDeSaida = dataDeSaida;
    }

    public String getObservacao() {
        return observacao;
    }

    public Atendimento observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Set<Atividade> getAtividades() {
        return atividades;
    }

    public Atendimento atividades(Set<Atividade> atividades) {
        this.atividades = atividades;
        return this;
    }

    public Atendimento addAtividade(Atividade atividade) {
        this.atividades.add(atividade);
        atividade.setAtendimento(this);
        return this;
    }

    public Atendimento removeAtividade(Atividade atividade) {
        this.atividades.remove(atividade);
        atividade.setAtendimento(null);
        return this;
    }

    public void setAtividades(Set<Atividade> atividades) {
        this.atividades = atividades;
    }

    public Set<Venda> getVendas() {
        return vendas;
    }

    public Atendimento vendas(Set<Venda> vendas) {
        this.vendas = vendas;
        return this;
    }

    public Atendimento addVenda(Venda venda) {
        this.vendas.add(venda);
        venda.setAtendimento(this);
        return this;
    }

    public Atendimento removeVenda(Venda venda) {
        this.vendas.remove(venda);
        venda.setAtendimento(null);
        return this;
    }

    public void setVendas(Set<Venda> vendas) {
        this.vendas = vendas;
    }

    public Set<AnexoAtendimento> getAnexos() {
        return anexos;
    }

    public Atendimento anexos(Set<AnexoAtendimento> anexoAtendimentos) {
        this.anexos = anexoAtendimentos;
        return this;
    }

    public Atendimento addAnexo(AnexoAtendimento anexoAtendimento) {
        this.anexos.add(anexoAtendimento);
        anexoAtendimento.setAtendimento(this);
        return this;
    }

    public Atendimento removeAnexo(AnexoAtendimento anexoAtendimento) {
        this.anexos.remove(anexoAtendimento);
        anexoAtendimento.setAtendimento(null);
        return this;
    }

    public void setAnexos(Set<AnexoAtendimento> anexoAtendimentos) {
        this.anexos = anexoAtendimentos;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Atendimento animal(Animal animal) {
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
        if (!(o instanceof Atendimento)) {
            return false;
        }
        return id != null && id.equals(((Atendimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Atendimento{" +
            "id=" + getId() +
            ", situacao='" + getSituacao() + "'" +
            ", dataDeChegada='" + getDataDeChegada() + "'" +
            ", dataDeSaida='" + getDataDeSaida() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
