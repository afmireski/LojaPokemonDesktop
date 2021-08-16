/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.util.ArrayList;
import java.util.List;
import models.Cartao;

/**
 *
 * @author Matheus 
 */
public class DAOCartao extends DAOGeneric<Cartao>{
    
    public DAOCartao() {
        super(Cartao.class);
    }
    
    public List<Cartao> searchFast(String search) {
        return em.createQuery("SELECT e FROM Cartao e WHERE "
                + "e.id like :search or "
                + "e.nome like :search", Cartao.class)
                .setParameter("search", "%" + search + "%").getResultList();
    }
    
    public List<String> toFKList() {
        List<String> fks = new ArrayList<>();
        
        this.list().forEach((c) -> {
            fks.add(c.toFK());
        });
        
        return fks;
    }
    
    public List<String> toSpecificFKList(List<Cartao> cartaos) {
        List<String> fks = new ArrayList<>();
        
        cartaos.forEach((c) -> {
            fks.add(c.toFK());
        });
        
        return fks;
    }
    
     public static void main(String[] args) {
        ///VERIFICA OS DADOS NO BANCO
        DAOCartao daoCartao = new DAOCartao();
        List<Cartao> end = daoCartao.list();

        end.forEach((e) -> {
            System.out.println(e.getId() + "-" + e.getNome());
        });
    }

    
    
    
    

}
