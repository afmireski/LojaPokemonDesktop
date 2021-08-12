/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daos;

import java.util.List;
import models.Pessoa;

/**
 *
 * @author Matheus 
 */
public class DAOPessoa extends DAOGeneric<Pessoa>{
    
    public DAOPessoa() {
        super(Pessoa.class);
    }
    
     public static void main(String[] args) {
        ///VERIFICA OS DADOS NO BANCO
        DAOPessoa daoPessoa = new DAOPessoa();
        List<Pessoa> end = daoPessoa.list();

        end.forEach((e) -> {
            System.out.println(e.getCpf() + "-" + e.getNome());
        });
    }
    

}
