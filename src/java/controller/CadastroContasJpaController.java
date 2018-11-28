/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.CadastroCredor;
import model.CadastroPgto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.CadastroContas;

/**
 *
 * @author elcior.carvalho
 */
public class CadastroContasJpaController implements Serializable {

    public CadastroContasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CadastroContas cadastroContas) {
        if (cadastroContas.getCadastroPgtoCollection() == null) {
            cadastroContas.setCadastroPgtoCollection(new ArrayList<CadastroPgto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroCredor fkCredor = cadastroContas.getFkCredor();
            if (fkCredor != null) {
                fkCredor = em.getReference(fkCredor.getClass(), fkCredor.getId());
                cadastroContas.setFkCredor(fkCredor);
            }
            Collection<CadastroPgto> attachedCadastroPgtoCollection = new ArrayList<CadastroPgto>();
            for (CadastroPgto cadastroPgtoCollectionCadastroPgtoToAttach : cadastroContas.getCadastroPgtoCollection()) {
                cadastroPgtoCollectionCadastroPgtoToAttach = em.getReference(cadastroPgtoCollectionCadastroPgtoToAttach.getClass(), cadastroPgtoCollectionCadastroPgtoToAttach.getId());
                attachedCadastroPgtoCollection.add(cadastroPgtoCollectionCadastroPgtoToAttach);
            }
            cadastroContas.setCadastroPgtoCollection(attachedCadastroPgtoCollection);
            em.persist(cadastroContas);
            if (fkCredor != null) {
                fkCredor.getCadastroContasCollection().add(cadastroContas);
                fkCredor = em.merge(fkCredor);
            }
            for (CadastroPgto cadastroPgtoCollectionCadastroPgto : cadastroContas.getCadastroPgtoCollection()) {
                CadastroContas oldFkContaOfCadastroPgtoCollectionCadastroPgto = cadastroPgtoCollectionCadastroPgto.getFkConta();
                cadastroPgtoCollectionCadastroPgto.setFkConta(cadastroContas);
                cadastroPgtoCollectionCadastroPgto = em.merge(cadastroPgtoCollectionCadastroPgto);
                if (oldFkContaOfCadastroPgtoCollectionCadastroPgto != null) {
                    oldFkContaOfCadastroPgtoCollectionCadastroPgto.getCadastroPgtoCollection().remove(cadastroPgtoCollectionCadastroPgto);
                    oldFkContaOfCadastroPgtoCollectionCadastroPgto = em.merge(oldFkContaOfCadastroPgtoCollectionCadastroPgto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CadastroContas cadastroContas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroContas persistentCadastroContas = em.find(CadastroContas.class, cadastroContas.getId());
            CadastroCredor fkCredorOld = persistentCadastroContas.getFkCredor();
            CadastroCredor fkCredorNew = cadastroContas.getFkCredor();
            Collection<CadastroPgto> cadastroPgtoCollectionOld = persistentCadastroContas.getCadastroPgtoCollection();
            Collection<CadastroPgto> cadastroPgtoCollectionNew = cadastroContas.getCadastroPgtoCollection();
            List<String> illegalOrphanMessages = null;
            for (CadastroPgto cadastroPgtoCollectionOldCadastroPgto : cadastroPgtoCollectionOld) {
                if (!cadastroPgtoCollectionNew.contains(cadastroPgtoCollectionOldCadastroPgto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CadastroPgto " + cadastroPgtoCollectionOldCadastroPgto + " since its fkConta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkCredorNew != null) {
                fkCredorNew = em.getReference(fkCredorNew.getClass(), fkCredorNew.getId());
                cadastroContas.setFkCredor(fkCredorNew);
            }
            Collection<CadastroPgto> attachedCadastroPgtoCollectionNew = new ArrayList<CadastroPgto>();
            for (CadastroPgto cadastroPgtoCollectionNewCadastroPgtoToAttach : cadastroPgtoCollectionNew) {
                cadastroPgtoCollectionNewCadastroPgtoToAttach = em.getReference(cadastroPgtoCollectionNewCadastroPgtoToAttach.getClass(), cadastroPgtoCollectionNewCadastroPgtoToAttach.getId());
                attachedCadastroPgtoCollectionNew.add(cadastroPgtoCollectionNewCadastroPgtoToAttach);
            }
            cadastroPgtoCollectionNew = attachedCadastroPgtoCollectionNew;
            cadastroContas.setCadastroPgtoCollection(cadastroPgtoCollectionNew);
            cadastroContas = em.merge(cadastroContas);
            if (fkCredorOld != null && !fkCredorOld.equals(fkCredorNew)) {
                fkCredorOld.getCadastroContasCollection().remove(cadastroContas);
                fkCredorOld = em.merge(fkCredorOld);
            }
            if (fkCredorNew != null && !fkCredorNew.equals(fkCredorOld)) {
                fkCredorNew.getCadastroContasCollection().add(cadastroContas);
                fkCredorNew = em.merge(fkCredorNew);
            }
            for (CadastroPgto cadastroPgtoCollectionNewCadastroPgto : cadastroPgtoCollectionNew) {
                if (!cadastroPgtoCollectionOld.contains(cadastroPgtoCollectionNewCadastroPgto)) {
                    CadastroContas oldFkContaOfCadastroPgtoCollectionNewCadastroPgto = cadastroPgtoCollectionNewCadastroPgto.getFkConta();
                    cadastroPgtoCollectionNewCadastroPgto.setFkConta(cadastroContas);
                    cadastroPgtoCollectionNewCadastroPgto = em.merge(cadastroPgtoCollectionNewCadastroPgto);
                    if (oldFkContaOfCadastroPgtoCollectionNewCadastroPgto != null && !oldFkContaOfCadastroPgtoCollectionNewCadastroPgto.equals(cadastroContas)) {
                        oldFkContaOfCadastroPgtoCollectionNewCadastroPgto.getCadastroPgtoCollection().remove(cadastroPgtoCollectionNewCadastroPgto);
                        oldFkContaOfCadastroPgtoCollectionNewCadastroPgto = em.merge(oldFkContaOfCadastroPgtoCollectionNewCadastroPgto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cadastroContas.getId();
                if (findCadastroContas(id) == null) {
                    throw new NonexistentEntityException("The cadastroContas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroContas cadastroContas;
            try {
                cadastroContas = em.getReference(CadastroContas.class, id);
                cadastroContas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cadastroContas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CadastroPgto> cadastroPgtoCollectionOrphanCheck = cadastroContas.getCadastroPgtoCollection();
            for (CadastroPgto cadastroPgtoCollectionOrphanCheckCadastroPgto : cadastroPgtoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CadastroContas (" + cadastroContas + ") cannot be destroyed since the CadastroPgto " + cadastroPgtoCollectionOrphanCheckCadastroPgto + " in its cadastroPgtoCollection field has a non-nullable fkConta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CadastroCredor fkCredor = cadastroContas.getFkCredor();
            if (fkCredor != null) {
                fkCredor.getCadastroContasCollection().remove(cadastroContas);
                fkCredor = em.merge(fkCredor);
            }
            em.remove(cadastroContas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CadastroContas> findCadastroContasEntities() {
        return findCadastroContasEntities(true, -1, -1);
    }

    public List<CadastroContas> findCadastroContasEntities(int maxResults, int firstResult) {
        return findCadastroContasEntities(false, maxResults, firstResult);
    }

    private List<CadastroContas> findCadastroContasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CadastroContas.class));
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

    public CadastroContas findCadastroContas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CadastroContas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCadastroContasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CadastroContas> rt = cq.from(CadastroContas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
