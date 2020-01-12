package dao;

import models.Manga;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class JPAManga {

    EntityManager entityManager = Persistence.createEntityManagerFactory("pers").createEntityManager();


    public void create(Manga Manga) {
        entityManager.getTransaction().begin();
        entityManager.persist(Manga);
        entityManager.getTransaction().commit();
    }

    public void delete(long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(getById(id));
        entityManager.getTransaction().commit();
    }

    public void update(Manga Manga) {
        entityManager.getTransaction().begin();
        entityManager.merge(Manga);
        entityManager.getTransaction().commit();
    }

    public Manga getById(long id) {
        return entityManager.find(Manga.class, id);
    }

    public List getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Manga e");
        return query.getResultList();
    }
}
