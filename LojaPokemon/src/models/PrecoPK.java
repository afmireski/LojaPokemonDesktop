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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author AFMireski
 */
@Embeddable
public class PrecoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "data_vigencia")
    @Temporal(TemporalType.DATE)
    private Date dataVigencia;
    @Basic(optional = false)
    @Column(name = "Pokemon_ID")
    private int pokemonID;

    public PrecoPK() {
    }

    public PrecoPK(Date dataVigencia, int pokemonID) {
        this.dataVigencia = dataVigencia;
        this.pokemonID = pokemonID;
    }

    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    public int getPokemonID() {
        return pokemonID;
    }

    public void setPokemonID(int pokemonID) {
        this.pokemonID = pokemonID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataVigencia != null ? dataVigencia.hashCode() : 0);
        hash += (int) pokemonID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrecoPK)) {
            return false;
        }
        PrecoPK other = (PrecoPK) object;
        if ((this.dataVigencia == null && other.dataVigencia != null) || (this.dataVigencia != null && !this.dataVigencia.equals(other.dataVigencia))) {
            return false;
        }
        if (this.pokemonID != other.pokemonID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.PrecoPK[ dataVigencia=" + dataVigencia + ", pokemonID=" + pokemonID + " ]";
    }
    
}
