/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.ArrayList;
import java.util.List;
import models.Pessoa;

/**
 *
 * @author Matheus
 */
public class DAOPessoa extends DAOGeneric<Pessoa> {

    public DAOPessoa() {
        super(Pessoa.class);
    }

    public List<String> getFKList() {
        List<String> fks = new ArrayList<>();
        this.list().forEach((e) -> {
            fks.add(e.toFK());
        });
        return fks;
    }

    public List<String> getSpecificFKList(List<Pessoa> p) {
        List<String> fks = new ArrayList<>();
        p.forEach((e) -> {
            fks.add(e.toFK());
        });
        return fks;
    }

    public List<Pessoa> searchFast(String search) {
        return em.createQuery("SELECT p FROM Pessoa p WHERE p.cpf like :search or p.nome like :search").
                setParameter("search", "%" + search + "%").getResultList();
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
