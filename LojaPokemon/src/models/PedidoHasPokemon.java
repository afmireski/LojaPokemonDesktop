/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author AFMireski
 */
@Entity
@Table(name = "pedido_has_pokemon")
@NamedQueries({
    @NamedQuery(name = "PedidoHasPokemon.findAll", query = "SELECT p FROM PedidoHasPokemon p")})
public class PedidoHasPokemon implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PedidoHasPokemonPK pedidoHasPokemonPK;
    @Basic(optional = false)
    @Column(name = "quantidade")
    private int quantidade;
    @Basic(optional = false)
    @Column(name = "valor_unitario")
    private double valorUnitario;

    public PedidoHasPokemon() {
    }

    public PedidoHasPokemon(PedidoHasPokemonPK pedidoHasPokemonPK) {
        this.pedidoHasPokemonPK = pedidoHasPokemonPK;
    }

    public PedidoHasPokemon(PedidoHasPokemonPK pedidoHasPokemonPK, int quantidade, double valorUnitario) {
        this.pedidoHasPokemonPK = pedidoHasPokemonPK;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public PedidoHasPokemon(int pedidoID, int pokemonID) {
        this.pedidoHasPokemonPK = new PedidoHasPokemonPK(pedidoID, pokemonID);
    }

    public PedidoHasPokemonPK getPedidoHasPokemonPK() {
        return pedidoHasPokemonPK;
    }

    public void setPedidoHasPokemonPK(PedidoHasPokemonPK pedidoHasPokemonPK) {
        this.pedidoHasPokemonPK = pedidoHasPokemonPK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedidoHasPokemonPK != null ? pedidoHasPokemonPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidoHasPokemon)) {
            return false;
        }
        PedidoHasPokemon other = (PedidoHasPokemon) object;
        if ((this.pedidoHasPokemonPK == null && other.pedidoHasPokemonPK != null) || (this.pedidoHasPokemonPK != null && !this.pedidoHasPokemonPK.equals(other.pedidoHasPokemonPK))) {
            return false;
        }
        return true;
    }
    
    public String toCSV() {
        return pedidoHasPokemonPK.getPedidoID() + ";" + pedidoHasPokemonPK.getPokemonID() + ";" + quantidade + ";" + valorUnitario;
    }

    @Override
    public String toString() {
        return "models.PedidoHasPokemon[ pedidoHasPokemonPK=" + pedidoHasPokemonPK + " ]";
    }
    
}
