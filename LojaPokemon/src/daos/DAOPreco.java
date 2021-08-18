/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.Date;
import java.util.List;
import models.Preco;
import models.PrecoPK;

/**
 *
 * @author AFMireski
 */
public class DAOPreco extends DAOGeneric<Preco> {

    public DAOPreco() {
        super(Preco.class);
    }
    
    public Preco get(PrecoPK pk) {
        return (Preco) em.find(Preco.class, pk);
    }
    
    public List<Preco> searchByDataVigencia(Date date) {
        return em.createQuery("SELECT e FROM Preco e WHERE e.precoPK.dataVigencia = :date").
                setParameter("date", date).getResultList();
    }
    
    public List<Preco> searchByPokemon(Integer pokeID) {
        return em.createQuery("SELECT e FROM Preco e WHERE e.precoPK.pokemonID = :id").
                setParameter("id", pokeID).getResultList();
    }
    
    public List<Preco> searchByValor(double valor) {
        return em.createQuery("SELECT e FROM Preco e WHERE e.valor = :valor").
                setParameter("valor", valor).getResultList();
    }
    
    public Preco getPrecoVigenteByPokemon(Integer pokeID) {
        return em.createQuery("SELECT e FROM Preco e WHERE e.precoPK.pokemonID = :id ORDER BY e.precoPK.dataVigencia DESC", Preco.class)
                .setParameter("id", pokeID).getResultList().get(0);
    }
    
    public List<Preco> orderByDataVigencia(boolean isDesc) {
        return em.createQuery("SELECT e FROM Preco e ORDER BY e.precoPK.dataVigencia " + (isDesc ? "DESC" : "ASC"))
                .getResultList();
    }
    
    public List<Preco> orderByPokemonID(boolean isDesc) {
        return em.createQuery("SELECT e FROM Preco e ORDER BY e.precoPK.pokemonID " + (isDesc ? "DESC" : "ASC"))
                .getResultList();
    }
    
    public List<Preco> orderByValor(boolean isDesc) {
        return em.createQuery("SELECT e FROM Preco e ORDER BY e.valor " + (isDesc ? "DESC" : "ASC"))
                .getResultList();
    }
    
    public static void main(String[] args) {
        
    }
    
    
    
    
    
}
