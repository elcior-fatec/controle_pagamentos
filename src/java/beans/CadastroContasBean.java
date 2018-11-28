/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.CadastroContasJpaController;
import controller.CadastroCredorJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
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

    private HtmlInputText idCredor;
    private List<CadastroCredor> credores;
    private CadastroCredorJpaController credorDAO;
    private CadastroContas conta;
    private List<CadastroContas> contas;
    private final CadastroContasJpaController dao;
    
    public CadastroContasBean() {
        conta = new CadastroContas();        
        dao = new CadastroContasJpaController(javax.persistence.Persistence.createEntityManagerFactory("controle_pagamentosPU"));
    }

    public HtmlInputText getIdCredor() {
        return idCredor;
    }

    public void setIdCredor(HtmlInputText idCredor) {
        this.idCredor = idCredor;
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
    
    public void inserir() {
        System.out.println("COMEÇOU A INSERÇÃO");
        credorDAO = new CadastroCredorJpaController(javax.persistence.Persistence.createEntityManagerFactory("controle_pagamentosPU"));
        credores = credorDAO.findCadastroCredorEntities();
        for(CadastroCredor credor : credores){
            System.out.println("LOOPING");
            System.out.println(credor);
            if(Objects.equals(credor.getId(), this.idCredor.getValue())){
                conta.setFkCredor(credor);
            }
        }
        dao.create(conta);
        System.out.println("FINALIZA A INSERÇÃO!");
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
