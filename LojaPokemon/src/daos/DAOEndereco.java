/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.List;
import models.Endereco;
import models.EnderecoPK;

/**
 *
 * @author AFMireski
 */
public class DAOEndereco extends DAOGeneric<Endereco> {

    public DAOEndereco() {
        super(Endereco.class);
    }

    public Endereco get(EnderecoPK pk) {
        return (Endereco) em.find(Endereco.class, pk);
    }

    public List<Endereco> searchByCEP(String cep) {
        return em.createQuery("SELECT e FROM Endereco e WHERE e.enderecoPK.cep like :cep").setParameter("cep", "%" + cep + "%").getResultList();
    }

    public List<Endereco> searchByNCasa(int n) {
        return em.createQuery("SELECT e FROM Endereco e WHERE e.enderecoPK.nCasaep = :n").setParameter("n", n).getResultList();
    }

    public List<Endereco> searchByNome(String nome) {
        return em.createQuery("SELECT e FROM Endereco e WHERE e.nome like :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Endereco> listOrderByNome() {
        return em.createQuery("SELECT e FROM Endereco e ORDER BY e.nome").getResultList();
    }

    public List<Endereco> listOrderByUf() {
        return em.createQuery("SELECT e FROM Endereco e ORDER BY e.uf").getResultList();
    }

    public static void main(String[] args) {
        ///VERIFICA OS DADOS NO BANCO
        DAOEndereco daoEndereco = new DAOEndereco();
        List<Endereco> end = daoEndereco.list();

        end.forEach((e) -> {
            System.out.println(e.getEnderecoPK().getCep() + " - " + e.getEnderecoPK().getNCasa());
        });
    }

}
