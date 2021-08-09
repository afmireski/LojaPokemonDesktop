/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author AFMireski
 */
@Entity
@Table(name = "preco")
@NamedQueries({
    @NamedQuery(name = "Preco.findAll", query = "SELECT p FROM Preco p")})
public class Preco implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrecoPK precoPK;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;
    @JoinColumn(name = "Pokemon_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pokemon pokemon;

    public Preco() {
    }

    public Preco(PrecoPK precoPK) {
        this.precoPK = precoPK;
    }

    public Preco(PrecoPK precoPK, double valor) {
        this.precoPK = precoPK;
        this.valor = valor;
    }

    public Preco(Date dataVigencia, int pokemonID) {
        this.precoPK = new PrecoPK(dataVigencia, pokemonID);
    }

    public PrecoPK getPrecoPK() {
        return precoPK;
    }

    public void setPrecoPK(PrecoPK precoPK) {
        this.precoPK = precoPK;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (precoPK != null ? precoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preco)) {
            return false;
        }
        Preco other = (Preco) object;
        if ((this.precoPK == null && other.precoPK != null) || (this.precoPK != null && !this.precoPK.equals(other.precoPK))) {
            return false;
        }
        return true;
    }
    
    public String toCSV() {
        return this.precoPK.getDataVigencia() + ";" + this.precoPK.getPokemonID() + ";" + this.valor;
    }

    @Override
    public String toString() {
        return "models.Preco[ precoPK=" + precoPK + " ]";
    }
    
}
