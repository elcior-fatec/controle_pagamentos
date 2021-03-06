/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.CadastroContasJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.CadastroContas;
import model.CadastroCredor;

/**
 *
 * @author elcior.carvalho
 */
@ManagedBean(name = "cadastroContasBean")
@ViewScoped
public class CadastroContasBean implements Serializable
{    
    private CadastroContas conta;
    private CadastroCredorBean credorBean;
    private List<CadastroContas> contas;
    int idCredor;
    private CadastroCredor credor;
    private final CadastroContasJpaController dao;
    
    public CadastroContasBean() {
        credorBean = new CadastroCredorBean();
        conta = new CadastroContas();        
        dao = new CadastroContasJpaController(javax.persistence.Persistence.createEntityManagerFactory("controle_pagamentosPU"));
    }  
    
    public CadastroContas getConta() {
        return conta;
    }
    
    public void setConta(CadastroContas conta) {
        this.conta = conta;
    }
    
    public List<CadastroContas> getContas() {
       this.contas = dao.findCadastroContasEntities();
       return contas;
    }

    public CadastroCredorBean getCredorBean() {
        return credorBean;
    }

    public void setCredorBean(CadastroCredorBean credorBean) {
        this.credorBean = credorBean;
    }

    public int getIdCredor() {
        return idCredor;
    }

    public void setIdCredor(int idCredor) {
        this.idCredor = idCredor;
    }

    public CadastroCredor getCredor() {
        return credor;
    }

    public void setCredor(CadastroCredor credor) {
        this.credor = credor;
    }
    
    public void inserir() {
        credor = credorBean.retornaCredor(idCredor);
        conta.setFkCredor(credor);
        System.out.println(conta);
        dao.create(conta);        
    }
    
    public void consultar(int id) {
        this.conta = dao.findCadastroContas(id);
    }
    
    public void alterar() {       
        try {
            dao.edit(conta);
        } catch (Exception ex) {
            Logger.getLogger(CadastroContasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<CadastroContas> listar() {
        return dao.findCadastroContasEntities();
    }
    
    public void deletar(int id){
        try {
            dao.destroy(id);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(CadastroContasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
