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
public class PedidoHasPokemonPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Pedido_ID")
    private int pedidoID;
    @Basic(optional = false)
    @Column(name = "Pokemon_ID")
    private int pokemonID;

    public PedidoHasPokemonPK() {
    }

    public PedidoHasPokemonPK(int pedidoID, int pokemonID) {
        this.pedidoID = pedidoID;
        this.pokemonID = pokemonID;
    }

    public int getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(int pedidoID) {
        this.pedidoID = pedidoID;
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
        hash += (int) pedidoID;
        hash += (int) pokemonID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidoHasPokemonPK)) {
            return false;
        }
        PedidoHasPokemonPK other = (PedidoHasPokemonPK) object;
        if (this.pedidoID != other.pedidoID) {
            return false;
        }
        if (this.pokemonID != other.pokemonID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.PedidoHasPokemonPK[ pedidoID=" + pedidoID + ", pokemonID=" + pokemonID + " ]";
    }
    
}
