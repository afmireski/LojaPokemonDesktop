package daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DAOGeneric<T> {

    public static EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
    private Class cls;

    public DAOGeneric(Class cls) {
        this.cls = cls;
    }

    public void insert(T e) {
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
    }

    public void update(T e) {
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }

    public void delete(T e) {
        em.getTransaction().begin();
        em.remove(e);
        em.getTransaction().commit();
    }

    public T get(Long id) {
        return (T) em.find(cls, id);
    }

    public T get(Integer id) {
        return (T) em.find(cls, id);
    }

    public T get(String id) {

        return (T) em.find(cls, id);
    }

    public List<T> list() {
        return em.createQuery("SELECT e FROM " + cls.getSimpleName() + " e").getResultList();
    }
}
