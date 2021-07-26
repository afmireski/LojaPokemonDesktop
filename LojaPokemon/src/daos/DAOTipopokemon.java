/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.List;
import models.Tipopokemon;

/**
 *
 * @author Matheus
 */
public class DAOTipopokemon extends DAOGeneric<Tipopokemon> {

    public DAOTipopokemon() {
        super(Tipopokemon.class);
    }
       
    public List<Tipopokemon> searchByID(int id) {
        return em.createQuery("SELECT e FROM Tipopokemon e WHERE e.id = :id").setParameter("id", id).getResultList();
    }
    
    public List<Tipopokemon> listOrderByID() {
        return em.createQuery("SELECT e FROM Tipopokemon e ORDER BY e.id").getResultList();
    }

    public static void main(String[] args) {
        ///VERIFICA OS DADOS NO BANCO
        DAOTipopokemon daoTipopokemon = new DAOTipopokemon();
        List<Tipopokemon> end = daoTipopokemon.list();

        end.forEach((e) -> {
            System.out.println(e.getId() + "-" + e.getSigla());
        });
    }
}
