/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.List;
import models.Pokemon;

/**
 *
 * @author AFMireski
 */
public class DAOPokemon extends DAOGeneric<Pokemon>{
    
    public DAOPokemon() {
        super(Pokemon.class);
    }
    
    List<Pokemon> searchByNome(String nome) {
        return em.createQuery("SELECT e FROM Pokemon e where e.nome like :nome").
                setParameter("nome", "%"+nome+"%").getResultList();
    }
    
    List<Pokemon> searchByID(int id) {
        return em.createQuery("SELECT e FROM Pokemon e where e.id = :id").
                setParameter("id", id).getResultList();
    }
    
    List<Pokemon> searchFast(String search) {
        return em.createQuery("SELECT e FROM Pokemon e where e.id = :search or e.nome like %:search%").
                setParameter("search", search).getResultList();
    }
    
    List<Pokemon> orderByID(boolean isDesc) {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.id " + (isDesc ? "DESC" : "ASC")).getResultList();
    }
    
    List<Pokemon> orderByNome(boolean isDesc) {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.nome " + (isDesc ? "DESC" : "ASC")).getResultList();
    }
    
    List<Pokemon> orderByDataCadastro(boolean isDesc) {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.dataCadastro " + (isDesc ? "DESC" : "ASC")).getResultList();
    }
    
    List<Pokemon> orderByEstoque(boolean isDesc) {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.estoque " + (isDesc ? "DESC" : "ASC")).getResultList();
    }
    
    List<Pokemon> orderByTipoPokemon() {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.tipoPokemonID DESC").getResultList();
    }
    
    public static void main(String[] args) {
        ///VERIFICA OS DADOS NO BANCO
        DAOPokemon dao = new DAOPokemon();
        List<Pokemon> poke = dao.list();
        
        poke.forEach((p) -> {
            System.out.println(p.getId() + " - " + p.getNome() + " " + p.getTipoPokemonID().getSigla());
        });
    }
            
            
            
    
}
