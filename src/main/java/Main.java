import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        final ObjectMapper mappper = new ObjectMapper();
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            //session.save(studio); //раскомментить чтобы сохранить студию / мангу
            //session.save(manga);
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
            List< Manga > mangas = session.createQuery("from Manga", Manga.class).list();

            Anime anime = new Anime("JOJA","sex",false,"mydickpick.com/anime"); // Для того чтобы сохранть обьект в БД создаем его
            anime.setManga(mangas.get(0)); //если надо, кидаем в него ссылающиеся обьекты
            anime.setStudio(studios.get(0));
            transaction = session.beginTransaction(); // создаем транзакцию

            session.save(anime); //сохраняем обьект или несколькко обьектов
            transaction.commit(); // и отправляем в БД


            List< Anime > animos = session.createQuery("from Anime", Anime.class).list();
            animos.forEach(s -> {
                try {
                    System.out.println(
                            mappper.writeValueAsString(s)// Это ЖСОН, который нужно будет отправлять
                    );
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
