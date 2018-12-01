/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.CadastroCredorJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.CadastroCredor;

/**
 *
 * @author elcior.carvalho
 */
@ManagedBean
@ViewScoped
public class CadastroCredorBean implements Serializable
{
    
    private CadastroCredor credor;
    private List<CadastroCredor> credores;
    private final CadastroCredorJpaController dao;
            
    public CadastroCredorBean() {        
        credor = new CadastroCredor();
        dao = new CadastroCredorJpaController(javax.persistence.Persistence.createEntityManagerFactory("controle_pagamentosPU"));
    }
    
    public CadastroCredor getCredor() {
        return credor;
    }
    
    public List<CadastroCredor> getCredores() {
       this.credores = dao.findCadastroCredorEntities();
       return credores;
    }
    
    public void inserir() {
        dao.create(credor);
        credor.setNome(null);
        credor.setCnpj(null);
        credor.setEndereco(null);
    }
    
    public void consultar(int id) {
        this.credor = dao.findCadastroCredor(id);
    }
    
    public CadastroCredor retornaCredor(int id) {
        return dao.findCadastroCredor(id);
    }
    
    public void alterar() {
        try {          
            dao.edit(credor);
        } catch (Exception ex) {
            Logger.getLogger(CadastroContasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public List<CadastroCredor> listar() {
        return dao.findCadastroCredorEntities();
    }
    
    public void deletar(int id){
        try {
            dao.destroy(id);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(CadastroCredorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
