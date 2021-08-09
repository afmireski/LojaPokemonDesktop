/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.ArrayList;
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
    
   public Integer autoincrement() {
       Integer i = (Integer) em.createQuery("SELECT MAX(e.id) from Pokemon e").getSingleResult();
       
       if (i != null) {
           return i + 1;
       } else {
           return 1;
       }
   }
    
    public List<Pokemon> searchByNome(String nome) {
        return em.createQuery("SELECT e FROM Pokemon e where e.nome like :nome").
                setParameter("nome", "%"+nome+"%").getResultList();
    }
    
    public List<Pokemon> searchByID(int id) {
        return em.createQuery("SELECT e FROM Pokemon e where e.id = :id").
                setParameter("id", id).getResultList();
    }
    
    public List<Pokemon> searchFast(String search) {
        return em.createQuery("SELECT e FROM Pokemon e WHERE e.nome like :search or "
                + "e.tipoPokemonID.sigla like :search or "
                + "e.tipoPokemonID.descricao like :search").
                setParameter("search", "%"+search+"%").getResultList();
    }
    
    public List<Pokemon> orderByID(boolean isDesc) {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.id " + (isDesc ? "DESC" : "ASC")).getResultList();
    }
    
    public List<Pokemon> orderByNome(boolean isDesc) {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.nome " + (isDesc ? "DESC" : "ASC")).getResultList();
    }
    
    public List<Pokemon> orderByDataCadastro(boolean isDesc) {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.dataCadastro " + (isDesc ? "DESC" : "ASC")).getResultList();
    }
    
    public List<Pokemon> orderByEstoque(boolean isDesc) {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.estoque " + (isDesc ? "DESC" : "ASC")).getResultList();
    }
    
    public List<Pokemon> orderByTipoPokemon() {
        return em.createQuery("SELECT e from Pokemon e ORDER BY e.tipoPokemonID DESC").getResultList();
    }
    
    public List<String> getFKList() {
        List<String> fks = new ArrayList<>();
        
        this.orderByID(false).forEach((p) -> {
            fks.add(p.toFK());
        });
        return fks;
    }
    
    public List<String> getEspecificFKList(List<Pokemon> poke) {
        List<String> fks = new ArrayList<>();
        
        poke.forEach((p) -> {
            fks.add(p.toFK());
        });
        return fks;
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
