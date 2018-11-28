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
import model.CadastroContas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.CadastroCredor;

/**
 *
 * @author elcior.carvalho
 */
public class CadastroCredorJpaController implements Serializable {

    public CadastroCredorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CadastroCredor cadastroCredor) {
        if (cadastroCredor.getCadastroContasCollection() == null) {
            cadastroCredor.setCadastroContasCollection(new ArrayList<CadastroContas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<CadastroContas> attachedCadastroContasCollection = new ArrayList<CadastroContas>();
            for (CadastroContas cadastroContasCollectionCadastroContasToAttach : cadastroCredor.getCadastroContasCollection()) {
                cadastroContasCollectionCadastroContasToAttach = em.getReference(cadastroContasCollectionCadastroContasToAttach.getClass(), cadastroContasCollectionCadastroContasToAttach.getId());
                attachedCadastroContasCollection.add(cadastroContasCollectionCadastroContasToAttach);
            }
            cadastroCredor.setCadastroContasCollection(attachedCadastroContasCollection);
            em.persist(cadastroCredor);
            for (CadastroContas cadastroContasCollectionCadastroContas : cadastroCredor.getCadastroContasCollection()) {
                CadastroCredor oldFkCredorOfCadastroContasCollectionCadastroContas = cadastroContasCollectionCadastroContas.getFkCredor();
                cadastroContasCollectionCadastroContas.setFkCredor(cadastroCredor);
                cadastroContasCollectionCadastroContas = em.merge(cadastroContasCollectionCadastroContas);
                if (oldFkCredorOfCadastroContasCollectionCadastroContas != null) {
                    oldFkCredorOfCadastroContasCollectionCadastroContas.getCadastroContasCollection().remove(cadastroContasCollectionCadastroContas);
                    oldFkCredorOfCadastroContasCollectionCadastroContas = em.merge(oldFkCredorOfCadastroContasCollectionCadastroContas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CadastroCredor cadastroCredor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroCredor persistentCadastroCredor = em.find(CadastroCredor.class, cadastroCredor.getId());
            Collection<CadastroContas> cadastroContasCollectionOld = persistentCadastroCredor.getCadastroContasCollection();
            Collection<CadastroContas> cadastroContasCollectionNew = cadastroCredor.getCadastroContasCollection();
            List<String> illegalOrphanMessages = null;
            for (CadastroContas cadastroContasCollectionOldCadastroContas : cadastroContasCollectionOld) {
                if (!cadastroContasCollectionNew.contains(cadastroContasCollectionOldCadastroContas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CadastroContas " + cadastroContasCollectionOldCadastroContas + " since its fkCredor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<CadastroContas> attachedCadastroContasCollectionNew = new ArrayList<CadastroContas>();
            for (CadastroContas cadastroContasCollectionNewCadastroContasToAttach : cadastroContasCollectionNew) {
                cadastroContasCollectionNewCadastroContasToAttach = em.getReference(cadastroContasCollectionNewCadastroContasToAttach.getClass(), cadastroContasCollectionNewCadastroContasToAttach.getId());
                attachedCadastroContasCollectionNew.add(cadastroContasCollectionNewCadastroContasToAttach);
            }
            cadastroContasCollectionNew = attachedCadastroContasCollectionNew;
            cadastroCredor.setCadastroContasCollection(cadastroContasCollectionNew);
            cadastroCredor = em.merge(cadastroCredor);
            for (CadastroContas cadastroContasCollectionNewCadastroContas : cadastroContasCollectionNew) {
                if (!cadastroContasCollectionOld.contains(cadastroContasCollectionNewCadastroContas)) {
                    CadastroCredor oldFkCredorOfCadastroContasCollectionNewCadastroContas = cadastroContasCollectionNewCadastroContas.getFkCredor();
                    cadastroContasCollectionNewCadastroContas.setFkCredor(cadastroCredor);
                    cadastroContasCollectionNewCadastroContas = em.merge(cadastroContasCollectionNewCadastroContas);
                    if (oldFkCredorOfCadastroContasCollectionNewCadastroContas != null && !oldFkCredorOfCadastroContasCollectionNewCadastroContas.equals(cadastroCredor)) {
                        oldFkCredorOfCadastroContasCollectionNewCadastroContas.getCadastroContasCollection().remove(cadastroContasCollectionNewCadastroContas);
                        oldFkCredorOfCadastroContasCollectionNewCadastroContas = em.merge(oldFkCredorOfCadastroContasCollectionNewCadastroContas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cadastroCredor.getId();
                if (findCadastroCredor(id) == null) {
                    throw new NonexistentEntityException("The cadastroCredor with id " + id + " no longer exists.");
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
            CadastroCredor cadastroCredor;
            try {
                cadastroCredor = em.getReference(CadastroCredor.class, id);
                cadastroCredor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cadastroCredor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CadastroContas> cadastroContasCollectionOrphanCheck = cadastroCredor.getCadastroContasCollection();
            for (CadastroContas cadastroContasCollectionOrphanCheckCadastroContas : cadastroContasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CadastroCredor (" + cadastroCredor + ") cannot be destroyed since the CadastroContas " + cadastroContasCollectionOrphanCheckCadastroContas + " in its cadastroContasCollection field has a non-nullable fkCredor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cadastroCredor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CadastroCredor> findCadastroCredorEntities() {
        return findCadastroCredorEntities(true, -1, -1);
    }

    public List<CadastroCredor> findCadastroCredorEntities(int maxResults, int firstResult) {
        return findCadastroCredorEntities(false, maxResults, firstResult);
    }

    private List<CadastroCredor> findCadastroCredorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CadastroCredor.class));
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

    public CadastroCredor findCadastroCredor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CadastroCredor.class, id);
        } finally {
            em.close();
        }
    }

    public int getCadastroCredorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CadastroCredor> rt = cq.from(CadastroCredor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
