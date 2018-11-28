/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.CadastroContas;
import model.CadastroPgto;

/**
 *
 * @author elcior.carvalho
 */
public class CadastroPgtoJpaController implements Serializable {

    public CadastroPgtoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CadastroPgto cadastroPgto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroContas fkConta = cadastroPgto.getFkConta();
            if (fkConta != null) {
                fkConta = em.getReference(fkConta.getClass(), fkConta.getId());
                cadastroPgto.setFkConta(fkConta);
            }
            em.persist(cadastroPgto);
            if (fkConta != null) {
                fkConta.getCadastroPgtoCollection().add(cadastroPgto);
                fkConta = em.merge(fkConta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CadastroPgto cadastroPgto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroPgto persistentCadastroPgto = em.find(CadastroPgto.class, cadastroPgto.getId());
            CadastroContas fkContaOld = persistentCadastroPgto.getFkConta();
            CadastroContas fkContaNew = cadastroPgto.getFkConta();
            if (fkContaNew != null) {
                fkContaNew = em.getReference(fkContaNew.getClass(), fkContaNew.getId());
                cadastroPgto.setFkConta(fkContaNew);
            }
            cadastroPgto = em.merge(cadastroPgto);
            if (fkContaOld != null && !fkContaOld.equals(fkContaNew)) {
                fkContaOld.getCadastroPgtoCollection().remove(cadastroPgto);
                fkContaOld = em.merge(fkContaOld);
            }
            if (fkContaNew != null && !fkContaNew.equals(fkContaOld)) {
                fkContaNew.getCadastroPgtoCollection().add(cadastroPgto);
                fkContaNew = em.merge(fkContaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cadastroPgto.getId();
                if (findCadastroPgto(id) == null) {
                    throw new NonexistentEntityException("The cadastroPgto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroPgto cadastroPgto;
            try {
                cadastroPgto = em.getReference(CadastroPgto.class, id);
                cadastroPgto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cadastroPgto with id " + id + " no longer exists.", enfe);
            }
            CadastroContas fkConta = cadastroPgto.getFkConta();
            if (fkConta != null) {
                fkConta.getCadastroPgtoCollection().remove(cadastroPgto);
                fkConta = em.merge(fkConta);
            }
            em.remove(cadastroPgto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CadastroPgto> findCadastroPgtoEntities() {
        return findCadastroPgtoEntities(true, -1, -1);
    }

    public List<CadastroPgto> findCadastroPgtoEntities(int maxResults, int firstResult) {
        return findCadastroPgtoEntities(false, maxResults, firstResult);
    }

    private List<CadastroPgto> findCadastroPgtoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CadastroPgto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CadastroPgto findCadastroPgto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CadastroPgto.class, id);
        } finally {
            em.close();
        }
    }

    public int getCadastroPgtoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CadastroPgto> rt = cq.from(CadastroPgto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
