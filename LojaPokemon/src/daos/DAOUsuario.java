/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.util.ArrayList;
import java.util.List;
import models.Usuario;

/**
 *
 * @author Matheus 
 */
public class DAOUsuario extends DAOGeneric<Usuario>{

    public DAOUsuario() {
        super(Usuario.class);
    }
    
   public List<Usuario> searchByID(Integer id) {
       return em.createQuery("SELECT e FROM Usuario e WHERE e.id = :id", Usuario.class).setParameter("id", id).getResultList();
   }
     
    public List<String> getFKList() {
        List<String> fks = new ArrayList<>();
        this.list().forEach((e) -> {
            fks.add(e.toFK());
        });
        return fks;
    }

    public List<String> getSpecificFKList(List<Usuario> p) {
        List<String> fks = new ArrayList<>();
        p.forEach((e) -> {
            fks.add(e.toFK());
        });
        return fks;
    }

    public List<Usuario> searchFast(String search) {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.email like :search").
                setParameter("search", "%" + search + "%").getResultList();
    }
    
     public static void main(String[] args) {
        ///VERIFICA OS DADOS NO BANCO
        DAOUsuario daoUsuario = new DAOUsuario();
        List<Usuario> end = daoUsuario.list();

        end.forEach((e) -> {
            System.out.println(e.getId() + "-" + e.getPessoaCPF());
        });
    }

}
