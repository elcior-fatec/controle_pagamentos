/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author elcior.carvalho
 */
@Entity
@Table(name = "cadastro_pgto", catalog = "controle_pagamentos", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadastroPgto.findAll", query = "SELECT c FROM CadastroPgto c")
    , @NamedQuery(name = "CadastroPgto.findById", query = "SELECT c FROM CadastroPgto c WHERE c.id = :id")
    , @NamedQuery(name = "CadastroPgto.findByVrPago", query = "SELECT c FROM CadastroPgto c WHERE c.vrPago = :vrPago")
    , @NamedQuery(name = "CadastroPgto.findByDataPgto", query = "SELECT c FROM CadastroPgto c WHERE c.dataPgto = :dataPgto")})
public class CadastroPgto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "vr_pago")
    private BigInteger vrPago;
    
    @Basic(optional = false)
    @Column(name = "data_pgto")
    @Temporal(TemporalType.DATE)
    private Date dataPgto;
    
    @JoinColumn(name = "fk_conta", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CadastroContas fkConta;

    public CadastroPgto() {
    }

    public CadastroPgto(Integer id) {
        this.id = id;
    }

    public CadastroPgto(Integer id, BigInteger vrPago, Date dataPgto) {
        this.id = id;
        this.vrPago = vrPago;
        this.dataPgto = dataPgto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getVrPago() {
        return vrPago;
    }

    public void setVrPago(BigInteger vrPago) {
        this.vrPago = vrPago;
    }

    public Date getDataPgto() {
        return dataPgto;
    }

    public void setDataPgto(Date dataPgto) {
        this.dataPgto = dataPgto;
    }

    public CadastroContas getFkConta() {
        return fkConta;
    }

    public void setFkConta(CadastroContas fkConta) {
        this.fkConta = fkConta;
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
        if (!(object instanceof CadastroPgto)) {
            return false;
        }
        CadastroPgto other = (CadastroPgto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CadastroPgto[ id=" + id + " ]";
    }
    
}
