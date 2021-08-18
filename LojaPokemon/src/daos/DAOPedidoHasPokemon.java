/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.List;
import models.PedidoHasPokemon;
import models.PedidoHasPokemonPK;

/**
 *
 * @author AFMireski
 */
public class DAOPedidoHasPokemon extends DAOGeneric<PedidoHasPokemon> {
    
    public DAOPedidoHasPokemon() {
        super(PedidoHasPokemon.class);
    }    
    
    public PedidoHasPokemon get(PedidoHasPokemonPK pk) {
        return (PedidoHasPokemon) em.find(PedidoHasPokemon.class, pk);        
    }
    
    public List<PedidoHasPokemon> searchByPedidoID(Integer pedidoID) {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e "
                + "WHERE e.pedidoHasPokemonPK.pedidoID = :id", PedidoHasPokemon.class)
                .setParameter(":id", pedidoID).getResultList();
    }
    
    public List<PedidoHasPokemon> searchByPokemonID(Integer pokemonID) {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e "
                + "WHERE e.pedidoHasPokemonPK.pokemonID = :id", PedidoHasPokemon.class)
                .setParameter(":id", pokemonID).getResultList();
    }
    
    public List<PedidoHasPokemon> orderByPokemonID() {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e ORDER BY e.pedidoHasPokemonPK.pokemonID", PedidoHasPokemon.class).getResultList();
    }
    
    public List<PedidoHasPokemon> orderByPedidoID() {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e ORDER BY e.pedidoHasPokemonPK.pedidoID", PedidoHasPokemon.class).getResultList();
    }
    
    public List<PedidoHasPokemon> orderByQuantidade() {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e ORDER BY e.quantidade", PedidoHasPokemon.class).getResultList();
    }
    
    public List<PedidoHasPokemon> orderByValorUnitario() {
        return em.createQuery("SELECT e FROM PedidoHasPokemon e ORDER BY e.valorUnitario", PedidoHasPokemon.class).getResultList();
    }   
    
    
    public static void main(String[] args) {
        DAOPedidoHasPokemon daoPedidoHasPokemon = new DAOPedidoHasPokemon();
        
        daoPedidoHasPokemon.list().forEach((php) -> {
            System.out.println(php.toString());
        });
    }
 
           
    
    
    
}
