/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.CadastroPgtoJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.CadastroPgto;

/**
 *
 * @author elcior.carvalho
 */
@ManagedBean
@ViewScoped
public class CadastroPgtoBean implements Serializable
{

    private CadastroPgto pgto;
    private List<CadastroPgto>  pgtos;
    private final CadastroPgtoJpaController dao;
    
    public CadastroPgtoBean() {
        pgto = new CadastroPgto();        
        dao = new CadastroPgtoJpaController(javax.persistence.Persistence.createEntityManagerFactory("controle_pagamentosPU"));
    }
    
    public CadastroPgto getPgto() {
        return pgto;
    }
    
    public void setPgto(CadastroPgto conta) {
        this.pgto = conta;
    }
    
    public List<CadastroPgto> getPgtos() {
       this.pgtos = dao.findCadastroPgtoEntities();
       return pgtos;
    }
    
    public void inserir() {
        dao.create(pgto);
    }
    
    public void consultar(int id) {
        this.pgto = dao.findCadastroPgto(id);
    }
    
    public void alterar() {
        try {
            dao.edit(pgto);
        } catch (Exception ex) {
            Logger.getLogger(CadastroContasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<CadastroPgto> listar() {
        return dao.findCadastroPgtoEntities();
    }
    
    public void deletar(int id){
        try {
            dao.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CadastroPgtoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
