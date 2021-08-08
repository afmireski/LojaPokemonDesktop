/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author AFMireski
 */
@Entity
@Table(name = "tipopokemon")
@NamedQueries({
    @NamedQuery(name = "Tipopokemon.findAll", query = "SELECT t FROM Tipopokemon t")})
public class Tipopokemon implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPokemonID")
    private List<Pokemon> pokemonList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "sigla")
    private String sigla;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;

    public Tipopokemon() {
    }

    public Tipopokemon(Integer id) {
        this.id = id;
    }

    public Tipopokemon(Integer id, String sigla, String descricao) {
        this.id = id;
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(object instanceof Tipopokemon)) {
            return false;
        }
        Tipopokemon other = (Tipopokemon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public String toFK() {
        return id + " - " + sigla;
    }

    @Override
    public String toString() {
        return id + ";" + sigla + ";" + descricao;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

}
