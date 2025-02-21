
package com.nahdaicue.login.persistecia;

import com.nahdaicue.login.logica.Rol;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.nahdaicue.login.logica.Usuario;
import com.nahdaicue.login.persistecia.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;


public class RolJpaController implements Serializable {
    
    //Conecta con la BD---------------------------------------------------------
    public RolJpaController() {
        emf = Persistence.createEntityManagerFactory("loginPU");
    }
    //--------------------------------------------------------------------------
    

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getListaUsuario() == null) {
            rol.setListaUsuario(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedListaUsuario = new ArrayList<Usuario>();
            for (Usuario listaUsuarioUsuarioToAttach : rol.getListaUsuario()) {
                listaUsuarioUsuarioToAttach = em.getReference(listaUsuarioUsuarioToAttach.getClass(), listaUsuarioUsuarioToAttach.getId());
                attachedListaUsuario.add(listaUsuarioUsuarioToAttach);
            }
            rol.setListaUsuario(attachedListaUsuario);
            em.persist(rol);
            for (Usuario listaUsuarioUsuario : rol.getListaUsuario()) {
                Rol oldUnRolOfListaUsuarioUsuario = listaUsuarioUsuario.getUnRol();
                listaUsuarioUsuario.setUnRol(rol);
                listaUsuarioUsuario = em.merge(listaUsuarioUsuario);
                if (oldUnRolOfListaUsuarioUsuario != null) {
                    oldUnRolOfListaUsuarioUsuario.getListaUsuario().remove(listaUsuarioUsuario);
                    oldUnRolOfListaUsuarioUsuario = em.merge(oldUnRolOfListaUsuarioUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getId());
            List<Usuario> listaUsuarioOld = persistentRol.getListaUsuario();
            List<Usuario> listaUsuarioNew = rol.getListaUsuario();
            List<Usuario> attachedListaUsuarioNew = new ArrayList<Usuario>();
            for (Usuario listaUsuarioNewUsuarioToAttach : listaUsuarioNew) {
                listaUsuarioNewUsuarioToAttach = em.getReference(listaUsuarioNewUsuarioToAttach.getClass(), listaUsuarioNewUsuarioToAttach.getId());
                attachedListaUsuarioNew.add(listaUsuarioNewUsuarioToAttach);
            }
            listaUsuarioNew = attachedListaUsuarioNew;
            rol.setListaUsuario(listaUsuarioNew);
            rol = em.merge(rol);
            for (Usuario listaUsuarioOldUsuario : listaUsuarioOld) {
                if (!listaUsuarioNew.contains(listaUsuarioOldUsuario)) {
                    listaUsuarioOldUsuario.setUnRol(null);
                    listaUsuarioOldUsuario = em.merge(listaUsuarioOldUsuario);
                }
            }
            for (Usuario listaUsuarioNewUsuario : listaUsuarioNew) {
                if (!listaUsuarioOld.contains(listaUsuarioNewUsuario)) {
                    Rol oldUnRolOfListaUsuarioNewUsuario = listaUsuarioNewUsuario.getUnRol();
                    listaUsuarioNewUsuario.setUnRol(rol);
                    listaUsuarioNewUsuario = em.merge(listaUsuarioNewUsuario);
                    if (oldUnRolOfListaUsuarioNewUsuario != null && !oldUnRolOfListaUsuarioNewUsuario.equals(rol)) {
                        oldUnRolOfListaUsuarioNewUsuario.getListaUsuario().remove(listaUsuarioNewUsuario);
                        oldUnRolOfListaUsuarioNewUsuario = em.merge(oldUnRolOfListaUsuarioNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = rol.getId();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<Usuario> listaUsuario = rol.getListaUsuario();
            for (Usuario listaUsuarioUsuario : listaUsuario) {
                listaUsuarioUsuario.setUnRol(null);
                listaUsuarioUsuario = em.merge(listaUsuarioUsuario);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
