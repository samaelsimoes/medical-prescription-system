package com.company.clinic.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class BaseRepository {

    @Inject
    private EntityManager em;

    public <T> T find(Class<T> type, Object id) { return em.find(type, id); }

    public <T> T save(T entity) {
        T managed = em.merge(entity);
        em.flush();
        return managed;
    }

    public void remove(Object entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        em.flush();
    }

    public <T> List<T> listPage(Class<T> type, int first, int pageSize, String jpql, Object... params) {
        TypedQuery<T> q = em.createQuery(jpql, type);
        for (int i = 0; i < params.length; i++) q.setParameter(i + 1, params[i]);
        return q.setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public long count(String jpql, Object... params) {
        TypedQuery<Long> q = em.createQuery(jpql, Long.class);
        for (int i = 0; i < params.length; i++) q.setParameter(i + 1, params[i]);
        return q.getSingleResult();
    }

    protected EntityManager em() { return em; }

    public <T> T findById(Class<T> clazz, Long id) {
        return em.find(clazz, id);
    }

}
