/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import tools.CaixaDeFerramentas;

/**
 *
 * @author AFMireski
 */
@Entity
@Table(name = "pedido")
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p")})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "data_pedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPedido;
    @JoinColumn(name = "Cartao_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cartao cartaoID;
    @JoinColumn(name = "Usuario_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioID;

    public Pedido() {
    }

    public Pedido(Integer id) {
        this.id = id;
    }

    public Pedido(Integer id, Date dataPedido) {
        this.id = id;
        this.dataPedido = dataPedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Cartao getCartaoID() {
        return cartaoID;
    }

    public void setCartaoID(Cartao cartaoID) {
        this.cartaoID = cartaoID;
    }

    public Usuario getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Usuario usuarioID) {
        this.usuarioID = usuarioID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public String toCSV() {
        final CaixaDeFerramentas cf = new CaixaDeFerramentas();
        return id + ";" + cf.converteDeDateParaString(dataPedido) + ";" + cartaoID.getId() + ";" + usuarioID.getId();
    }
    
    public String toFK() {
        CaixaDeFerramentas cf = new CaixaDeFerramentas();
        
        return id + " - " + cf.converteDeDateParaString(dataPedido);
    }
    

    @Override
    public String toString() {
        return "models.Pedido[ id=" + id + " ]";
    }
    
}
