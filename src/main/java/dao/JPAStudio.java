package dao;

import models.Studio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class JPAStudio {

    EntityManager entityManager = Persistence.createEntityManagerFactory("pers").createEntityManager();


    public void create(Studio studio) {
        entityManager.getTransaction().begin();
        entityManager.persist(studio);
        entityManager.getTransaction().commit();
    }

    public void delete(long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(getById(id));
        entityManager.getTransaction().commit();
    }

    public void update(Studio studio) {
        entityManager.getTransaction().begin();
        entityManager.merge(studio);
        entityManager.getTransaction().commit();
    }

    public Studio getById(long id) {
        return entityManager.find(Studio.class, id);
    }

    public List<Studio> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Studio e");
        return query.getResultList();
    }
}
