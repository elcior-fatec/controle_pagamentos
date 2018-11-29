/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
<<<<<<< HEAD
=======
import java.time.format.DateTimeFormatter;
>>>>>>> 40f4b776891bfbd905079924e84650111319fd2c
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author elcior.carvalho
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadastroContas.findAll", query = "SELECT c FROM CadastroContas c")
    , @NamedQuery(name = "CadastroContas.findById", query = "SELECT c FROM CadastroContas c WHERE c.id = :id")
    , @NamedQuery(name = "CadastroContas.findByDescricao", query = "SELECT c FROM CadastroContas c WHERE c.descricao = :descricao")
    , @NamedQuery(name = "CadastroContas.findByValor", query = "SELECT c FROM CadastroContas c WHERE c.valor = :valor")
    , @NamedQuery(name = "CadastroContas.findByDataVencimento", query = "SELECT c FROM CadastroContas c WHERE c.dataVencimento = :dataVencimento")})
public class CadastroContas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    private String descricao;
    
    @Basic(optional = false)
    private float valor;
    
    @Basic(optional = false)
    @Column(name = "data_vencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkConta")
    private Collection<CadastroPgto> cadastroPgtoCollection;
    
    @JoinColumn(name = "fk_credor", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CadastroCredor fkCredor;

    public CadastroContas() {
    }

    public CadastroContas(Integer id) {
        this.id = id;
    }

    public CadastroContas(Integer id, String descricao, float valor, Date dataVencimento) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }
    
    public String getDataVencimentoString() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(dataVencimento);
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @XmlTransient
    public Collection<CadastroPgto> getCadastroPgtoCollection() {
        return cadastroPgtoCollection;
    }

    public void setCadastroPgtoCollection(Collection<CadastroPgto> cadastroPgtoCollection) {
        this.cadastroPgtoCollection = cadastroPgtoCollection;
    }

    public CadastroCredor getFkCredor() {
        return fkCredor;
    }
    
    public int getFkCredorValor() {
        return this.fkCredor.getId();
    }

    public void setFkCredor(CadastroCredor fkCredor) {
        this.fkCredor = fkCredor;
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
        if (!(object instanceof CadastroContas)) {
            return false;
        }
        CadastroContas other = (CadastroContas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CadastroContas[ id=" + id + " ]";
    }
    
}
