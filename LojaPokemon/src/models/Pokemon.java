/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author AFMireski
 */
@Entity
@Table(name = "pokemon")
@NamedQueries({
    @NamedQuery(name = "Pokemon.findAll", query = "SELECT p FROM Pokemon p")})
public class Pokemon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Basic(optional = false)
    @Column(name = "estoque")
    private int estoque;
    @Basic(optional = false)
    @Column(name = "imagem")
    private String imagem;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemon")
    private List<Preco> precoList;
    @JoinColumn(name = "TipoPokemon_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tipopokemon tipoPokemonID;

    public Pokemon() {
    }

    public Pokemon(Integer id) {
        this.id = id;
    }

    public Pokemon(Integer id, String nome, Date dataCadastro, int estoque, String imagem) {
        this.id = id;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.estoque = estoque;
        this.imagem = imagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<Preco> getPrecoList() {
        return precoList;
    }

    public void setPrecoList(List<Preco> precoList) {
        this.precoList = precoList;
    }

    public Tipopokemon getTipoPokemonID() {
        return tipoPokemonID;
    }

    public void setTipoPokemonID(Tipopokemon tipoPokemonID) {
        this.tipoPokemonID = tipoPokemonID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pokemon)) {
            return false;
        }
        Pokemon other = (Pokemon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Pokemon[ id=" + id + " ]";
    }
    
}
