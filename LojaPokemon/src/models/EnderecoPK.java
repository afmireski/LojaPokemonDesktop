/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author AFMireski
 */
@Embeddable
public class EnderecoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CEP")
    private String cep;
    @Basic(optional = false)
    @Column(name = "nCasa")
    private int nCasa;

    public EnderecoPK() {
    }

    public EnderecoPK(String cep, int nCasa) {
        this.cep = cep;
        this.nCasa = nCasa;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getNCasa() {
        return nCasa;
    }

    public void setNCasa(int nCasa) {
        this.nCasa = nCasa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cep != null ? cep.hashCode() : 0);
        hash += (int) nCasa;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EnderecoPK)) {
            return false;
        }
        EnderecoPK other = (EnderecoPK) object;
        if ((this.cep == null && other.cep != null) || (this.cep != null && !this.cep.equals(other.cep))) {
            return false;
        }
        if (this.nCasa != other.nCasa) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.EnderecoPK[ cep=" + cep + ", nCasa=" + nCasa + " ]";
    }
    
}
