/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author AFMireski
 */
@Entity
@Table(name = "endereco")
@NamedQueries({
    @NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e")})
public class Endereco implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "endereco")
    private List<Pessoa> pessoaList;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EnderecoPK enderecoPK;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "cidade")
    private String cidade;
    @Basic(optional = false)
    @Column(name = "uf")
    private int uf;
    @Basic(optional = false)
    @Column(name = "uf_descricao")
    private String ufDescricao;

    public Endereco() {
    }

    public Endereco(EnderecoPK enderecoPK) {
        this.enderecoPK = enderecoPK;
    }

    public Endereco(EnderecoPK enderecoPK, String nome, String cidade, int uf, String ufDescricao) {
        this.enderecoPK = enderecoPK;
        this.nome = nome;
        this.cidade = cidade;
        this.uf = uf;
        this.ufDescricao = ufDescricao;
    }

    public Endereco(String cep, int nCasa) {
        this.enderecoPK = new EnderecoPK(cep, nCasa);
    }

    public EnderecoPK getEnderecoPK() {
        return enderecoPK;
    }

    public void setEnderecoPK(EnderecoPK enderecoPK) {
        this.enderecoPK = enderecoPK;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getUf() {
        return uf;
    }

    public void setUf(int uf) {
        this.uf = uf;
    }

    public String getUfDescricao() {
        return ufDescricao;
    }

    public void setUfDescricao(String ufDescricao) {
        this.ufDescricao = ufDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enderecoPK != null ? enderecoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        if ((this.enderecoPK == null && other.enderecoPK != null) || (this.enderecoPK != null && !this.enderecoPK.equals(other.enderecoPK))) {
            return false;
        }
        return true;
    }
    
    public String toCSV() {
        return enderecoPK.getCep() + ";" + enderecoPK.getNCasa() + ";" + nome + ";" + cidade + ";" + uf + ";" + ufDescricao;
    }

    @Override
    public String toString() {
        return "models.Endereco[ enderecoPK=" + enderecoPK + " ]";
    }

    public List<Pessoa> getPessoaList() {
        return pessoaList;
    }

    public void setPessoaList(List<Pessoa> pessoaList) {
        this.pessoaList = pessoaList;
    }

}
