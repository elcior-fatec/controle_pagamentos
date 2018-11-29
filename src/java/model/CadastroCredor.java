/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author elcior.carvalho
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadastroCredor.findAll", query = "SELECT c FROM CadastroCredor c")
    , @NamedQuery(name = "CadastroCredor.findById", query = "SELECT c FROM CadastroCredor c WHERE c.id = :id")
    , @NamedQuery(name = "CadastroCredor.findByNome", query = "SELECT c FROM CadastroCredor c WHERE c.nome = :nome")
    , @NamedQuery(name = "CadastroCredor.findByCnpj", query = "SELECT c FROM CadastroCredor c WHERE c.cnpj = :cnpj")
    , @NamedQuery(name = "CadastroCredor.findByEndereco", query = "SELECT c FROM CadastroCredor c WHERE c.endereco = :endereco")})
public class CadastroCredor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    private String nome;
    
    @Basic(optional = false)
    private String cnpj;
    
    @Basic(optional = false)
    private String endereco;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCredor")
    private Collection<CadastroContas> cadastroContasCollection;

    public CadastroCredor() {
    }

    public CadastroCredor(Integer id) {
        this.id = id;
    }

    public CadastroCredor(Integer id, String nome, String cnpj, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @XmlTransient
    public Collection<CadastroContas> getCadastroContasCollection() {
        return cadastroContasCollection;
    }

    public void setCadastroContasCollection(Collection<CadastroContas> cadastroContasCollection) {
        this.cadastroContasCollection = cadastroContasCollection;
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
        if (!(object instanceof CadastroCredor)) {
            return false;
        }
        CadastroCredor other = (CadastroCredor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "model.CadastroCredor[ id=" + id + " ]";
        return "Id: " + this.id +
                "; Nome: " + this.nome +
                "; CNPJ: " + this.cnpj +
                "; Endereco: " + this.endereco;
    }
    
}
