package dao;

import models.Anime;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;


@Stateless
public class JPAAnime {

    EntityManager entityManager = Persistence.createEntityManagerFactory("pers").createEntityManager();

    public void create(Anime Anime) {
        entityManager.getTransaction().begin();
        entityManager.persist(Anime);
        entityManager.getTransaction().commit();
    }

    public void delete(long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(getById(id));
        entityManager.getTransaction().commit();
    }

    public void update(Anime Anime) {
        entityManager.getTransaction().begin();
        entityManager.merge(Anime);
        entityManager.getTransaction().commit();
    }

    public Anime getById(long id) {
        return entityManager.find(Anime.class, id);
    }

    public List<Anime> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Anime e");
        return query.getResultList();
    }
}
