import models.Anime;
import models.Manga;
import models.Studio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.Hibernate;

import java.util.List;

public class Main {
    public static void main(String[] args){
        Studio studio = new Studio("PogChomp");
        Manga manga = new Manga("JJBA","sex","god","mydickpick.com");
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.save(studio);
            session.save(manga);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            List< Studio > studios = session.createQuery("from Studio", Studio.class).list();
            studios.forEach(s -> System.out.println(s.getName()));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
