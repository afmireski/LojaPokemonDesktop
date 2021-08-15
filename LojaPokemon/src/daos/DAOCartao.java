/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

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
    
     public static void main(String[] args) {
        ///VERIFICA OS DADOS NO BANCO
        DAOCartao daoCartao = new DAOCartao();
        List<Cartao> end = daoCartao.list();

        end.forEach((e) -> {
            System.out.println(e.getId() + "-" + e.getNome());
        });
    }

    
    
    
    

}
